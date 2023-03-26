package com.Project1;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.*;
@SpringBootApplication
public class Project1Application {

	public static void main(String[] args) throws ParseException {
		ConfigurableApplicationContext context =
				SpringApplication.run(Project1Application.class, args);
		Student student = context.getBean(Student.class);
		Course course = context.getBean(Course.class);


		parseJSOn("https://hccs-advancejava.s3.amazonaws.com/student_course.json");
	}

	public static void parseJSOn(String url) throws ParseException {
		Client client = Client.create();
		WebResource webResource = client.resource(url);

		ClientResponse clientResponse =
				webResource.accept("application/json").get(ClientResponse.class);
		if(clientResponse.getStatus() !=200) {
			throw new RuntimeException("Failed" + clientResponse.toString());
		}

		JSONArray jsonArray =
				(JSONArray) new JSONParser().parse(clientResponse.getEntity(String.class));

		Iterator<Object> it = jsonArray.iterator();
		List<Student> students = new ArrayList<>();

		while(it.hasNext()) {
			JSONObject jsonObject = (JSONObject) it.next();
			JSONArray jsonArr;

			Student student = new Student();
			String firstName = String.valueOf(jsonObject.get("first_name"));
			String email = String.valueOf(jsonObject.get("email"));
			String gender = String.valueOf(jsonObject.get("gender"));

			jsonArr = ((JSONArray) jsonObject.get("course"));

			student.setFirstName(firstName);
			student.setEmail(email);
			student.setGender(gender);
			if(jsonArr != null && jsonArr.size() > 0) {
				for (Object cor : jsonArr){
					Course course = new Course();
					JSONObject lineItem = (JSONObject) cor;
					String courseNo = String.valueOf(lineItem.get("courseNo"));
					String grade = String.valueOf(lineItem.get("grade"));
					String creditHours = String.valueOf(lineItem.get("creditHours"));
					course.setCourseNo(courseNo);
					course.setGrade(grade);
					course.setCreditHours(creditHours);
					student.getCourses().add(course);
				}
			}
			students.add(student);
		}
		search(students);
	}

	public static void search(List<Student> students) {
		int code = 0;
		String name;
		String courseNo;

		while(code != 4) {
			Scanner input = new Scanner(System.in);
			Scanner search = new Scanner(System.in);
			System.out.println("\nEnter 1 To Search By Name");
			System.out.println("Enter 2 To Search By Course Number");
			System.out.println("Enter 3 To Calculate GPA");
			System.out.println("Enter 4 To Exit");
			code = input.nextInt();
//			System.out.println("\n");
			if(code == 1) {
				System.out.println("Enter a First Name:");
				name = search.nextLine();
				searchByName(students, name);
			}
			if (code == 2) {
				System.out.println("Enter a Course Number:");
				courseNo = search.nextLine();
				System.out.println("Students taking that course are:");
				searchByCourseNo(students, courseNo);
			}
			if(code ==3){
				System.out.println("Enter a First Name");
				name = search.nextLine();
				double gpa = calcGpa(name, students);
				searchByName(students, name);
				System.out.println("GPA: " + gpa);
			}
		}
	}

	public static double calcGpa(String name, List<Student> students) {
		double totalGradePoints = 0.0;
		double totalCreditHours = 0.0;
		double gpa = 0.0;
		for (Student student: students) {
			if(name.equals(student.getFirstName())) {
				List<Course> courses = student.getCourses();
				for (Course course : courses) {
					if(course.getCourseNo() == null){
						return gpa;
					}
					String grade = course.getGrade();
					int creditHours = Integer.parseInt(course.getCreditHours());
					int gradePoints = switch (grade) {
						case "A" -> 4;
						case "B" -> 3;
						case "C" -> 2;
						case "D" -> 1;
						default -> 0;
					};
					totalGradePoints += (gradePoints * creditHours);
					totalCreditHours += creditHours;
				}
			}
		}
		gpa = totalGradePoints / totalCreditHours;
		return gpa;
	}

	public static void searchByName(List<Student> students, String name){
		for (Student student : students) {
			if (Objects.equals(student.getFirstName(), name)) {
				System.out.println(student);
			}
		}
	}

	public static void searchByCourseNo(List<Student> students, String courseNo){
		for (Student student : students) {
			List<Course> course = student.getCourses();
			for (Course c: course){
				if(Objects.equals(c.getCourseNo(), courseNo)) {
					System.out.println(student.getFirstName());
				}
			}
		}
	}
}

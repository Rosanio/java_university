import java.util.HashMap;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("departments", Department.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/students", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("students", Student.all());
      model.put("departments", Department.all());
      model.put("template", "templates/students.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/courses", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("courses", Course.all());
      model.put("departments", Department.all());
      model.put("template", "templates/courses.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/departments", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("departments", Department.all());
      model.put("template", "templates/departments.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/students", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String enrollment = request.queryParams("enrollment");
      String major = request.queryParams("major");
      Student newStudent = new Student(name, enrollment, major);
      newStudent.save();
      response.redirect("/students");
      return null;
    });

    post("/courses", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      int courseNumber = Integer.parseInt(request.queryParams("courseNumber"));
      int department = Integer.parseInt(request.queryParams("departmentId"));
      Course newCourse = new Course(name, courseNumber, department);
      newCourse.save();
      response.redirect("/courses");
      return null;
    });

    post("/departments", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      Department newDepartment = new Department(name);
      newDepartment.save();
      response.redirect("/departments");
      return null;
    });

    get("/students/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Student student = Student.find(id);
      model.put("student", student);
      model.put("department", student.getDepartment());
      model.put("departments", Department.all());
      model.put("courses", Course.all());
      model.put("template", "templates/student.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/courses/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Course course = Course.find(id);
      model.put("course", course);
      model.put("department", course.getDepartment());
      model.put("students", Student.all());
      model.put("template", "templates/course.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/departments/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Department department = Department.find(id);
      model.put("department", department);
      model.put("template", "templates/department.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/add_courses", (request, response) -> {
      int studentId = Integer.parseInt(request.queryParams("student_id"));
      int courseId = Integer.parseInt(request.queryParams("course_id"));
      Course course = Course.find(courseId);
      Student student = Student.find(studentId);
      if(!student.getCourses().contains(course)) {
        student.addCourse(course);
      }
      response.redirect("/students/" +studentId);
      return null;
    });

    post("/students/:id/complete", (request, response) -> {
      int studentId = Integer.parseInt(request.params(":id"));
      int courseId = Integer.parseInt(request.queryParams("course_id"));
      Course course = Course.find(courseId);
      Student student = Student.find(studentId);
      student.complete(course);
      response.redirect("/students/" +studentId);
      return null;
    });

    post("/add_students", (request, response) -> {
      int studentId = Integer.parseInt(request.queryParams("student_id"));
      int courseId = Integer.parseInt(request.queryParams("course_id"));
      Course course = Course.find(courseId);
      Student student = Student.find(studentId);
      if(!course.getStudents().contains(student)) {
        course.addStudent(student);
      }
      response.redirect("/courses/" +courseId);
      return null;
    });

    post("/students/:id/delete", (request, response) -> {
      int id = Integer.parseInt(request.params("id"));
      Student student = Student.find(id);
      student.delete();
      response.redirect("/students");
      return null;
    });

    post("/courses/:id/delete", (request, response) -> {
      int id = Integer.parseInt(request.params("id"));
      Course course = Course.find(id);
      course.delete();
      response.redirect("/courses");
      return null;
    });

    post("/departments/:id/delete", (request, response) -> {
      int id = Integer.parseInt(request.params("id"));
      Department department = Department.find(id);
      department.delete();
      response.redirect("/departments");
      return null;
    });

    post("/students/:id/changeMajor", (request, response) -> {
      int id = Integer.parseInt(request.params("id"));
      Student student = Student.find(id);
      String name = request.queryParams("studentName");
      String newMajor = request.queryParams("updateMajor");
      student.update(name, newMajor);
      response.redirect("/students/" + student.getId());
      return null;
    });



  }
}

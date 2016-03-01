import java.util.*;
import org.sql2o.*;

public class Course {
  private int id;
  private String name;
  private int course_number;
  private int department_id;

  public int getId() {
    return id;
  }

  public int getCourseNumber() {
    return course_number;
  }

  public String getName() {
    return name;
  }

  public int getDepartmentId() {
    return department_id;
  }

  public Course(String name, int course_number, int department_id) {
    this.name = name;
    this.course_number = course_number;
    this.department_id = department_id;
  }

  @Override
  public boolean equals(Object otherCourse){
    if (!(otherCourse instanceof Course)) {
      return false;
    } else {
      Course newCourse = (Course) otherCourse;
      return this.getName().equals(newCourse.getName()) &&
             this.getId() == newCourse.getId();
    }
  }

  public static List<Course> all() {
    String sql = "SELECT * FROM courses";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Course.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO courses(name, course_number, department_id) VALUES (:name, :course_number, :department_id)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", name)
        .addParameter("course_number", course_number)
          .addParameter("department_id", department_id)
        .executeUpdate()
        .getKey();
    }
  }

  public static Course find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM courses where id=:id";
      Course course = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Course.class);
      return course;
    }
  }

  public Department getDepartment() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM departments WHERE id = :department_id";
      return con.createQuery(sql)
        .addParameter("department_id", department_id)
        .executeAndFetchFirst(Department.class);
    }
  }

  public void addStudent(Student student) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students_courses (student_id, course_id, completed) VALUES (:student_id, :course_id, false)";
      con.createQuery(sql)
      .addParameter("student_id", student.getId())
      .addParameter("course_id", id)
      .executeUpdate();
    }
  }

  public List<Student> getStudents() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT students.* FROM courses JOIN students_courses ON (courses.id = students_courses.course_id) JOIN students ON (students_courses.student_id = students.id) WHERE course_id=:id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetch(Student.class);
    }
  }

  public void update(String name, int number) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE courses SET name = :name, course_number = :number WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("number", number)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
    String sql = "DELETE FROM courses WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();

    String deleteQuery = "DELETE FROM students_courses WHERE course_id = :id";
      con.createQuery(deleteQuery)
        .addParameter("id", id)
        .executeUpdate();
    }
  }
}

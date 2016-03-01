import java.util.*;
import org.sql2o.*;

public class Student {
  private int id;
  private String name;
  private String enrollment_date;
  private String major;

  public int getId() {
    return id;
  }

  public String getenrollmentDate() {
    return enrollment_date;
  }

  public String getName() {
    return name;
  }

  public String getMajor() {
    return major;
  }

  public Student(String name, String enrollment_date, String major) {
    this.name = name;
    this.enrollment_date = enrollment_date;
    this.major = major;
  }

  @Override
  public boolean equals(Object otherStudent){
    if (!(otherStudent instanceof Student)) {
      return false;
    } else {
      Student newStudent = (Student) otherStudent;
      return this.getName().equals(newStudent.getName()) &&
             this.getId() == newStudent.getId();
    }
  }

  public static List<Student> all() {
    String sql = "SELECT * FROM students";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Student.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students(name, enrollment_date, major) VALUES (:name, :enrollment_date, :major)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", name)
        .addParameter("enrollment_date", enrollment_date)
        .addParameter("major", major)
        .executeUpdate()
        .getKey();
    }
  }

  public static Student find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM students where id=:id";
      Student student = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Student.class);
      return student;
    }
  }

  public void addCourse(Course course) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students_courses (course_id, student_id, completed) VALUES (:course_id, :student_id, false)";
      con.createQuery(sql)
      .addParameter("course_id", course.getId())
      .addParameter("student_id", id)
      .executeUpdate();
    }
  }

  public List<Course> getCourses() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT courses.* FROM students JOIN students_courses ON (students.id = students_courses.student_id) JOIN courses ON (students_courses.course_id = courses.id) WHERE student_id=:id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetch(Course.class);
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

  public Department getDepartment() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM departments WHERE name = :major";
      return con.createQuery(sql)
        .addParameter("major", major)
        .executeAndFetchFirst(Department.class);
    }
  }

  public Boolean completed(Course course) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT completed FROM students_courses WHERE student_id = :student_id AND course_id = :course_id";
      Boolean completed = con.createQuery(sql)
        .addParameter("student_id", id)
        .addParameter("course_id", course.getId())
        .executeAndFetchFirst(Boolean.class);
      return completed;
    }
  }

  public void complete(Course course) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE students_courses SET completed = true WHERE student_id = :student_id AND course_id = :course_id";
      con.createQuery(sql)
        .addParameter("student_id", id)
        .addParameter("course_id", course.getId())
        .executeUpdate();
    }
  }

  public void update(String name, String major) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE students SET name = :name, major = :major WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("major", major)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
    String sql = "DELETE FROM students WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();

    String deleteQuery = "DELETE FROM students_courses WHERE student_id = :id";
      con.createQuery(deleteQuery)
        .addParameter("id", id)
        .executeUpdate();
    }
  }
}

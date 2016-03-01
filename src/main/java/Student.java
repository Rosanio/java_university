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
      String sql = "INSERT INTO students_courses (course_id, student_id) VALUES (:course_id, :student_id)";
      con.createQuery(sql)
      .addParameter("course_id", course.getId())
      .addParameter("student_id", id)
      .executeUpdate();
    }
  }

  public ArrayList<Course> getCourses() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT course_id FROM students_courses WHERE student_id=:id";
      List<Integer> courseIds = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetch(Integer.class);
      ArrayList<Course> myCourses = new ArrayList<Course>();
      for(Integer courseId : courseIds) {
        String courseQuery = "SELECT * FROM courses where id=:id";
        Course course = con.createQuery(courseQuery)
          .addParameter("id", courseId)
          .executeAndFetchFirst(Course.class);
          myCourses.add(course);
      }
    return myCourses;
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

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

  public Course(String name, int course_number, int deparment_id) {
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

  public void addStudent(Student student) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students_courses (student_id, course_id, completed) VALUES (:student_id, :course_id, false)";
      con.createQuery(sql)
      .addParameter("student_id", student.getId())
      .addParameter("course_id", id)
      .executeUpdate();
    }
  }

  public ArrayList<Student> getStudents() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT student_id FROM students_courses WHERE course_id=:id";
      List<Integer> studentIds = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetch(Integer.class);
      ArrayList<Student> myStudents = new ArrayList<Student>();
      for(Integer studentId : studentIds) {
        String studentQuery = "SELECT * FROM students where id=:id";
        Student student = con.createQuery(studentQuery)
          .addParameter("id", studentId)
          .executeAndFetchFirst(Student.class);
          myStudents.add(student);
      }
    return myStudents;
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

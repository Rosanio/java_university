import java.util.*;
import org.sql2o.*;

public class Department {
  private int id;
  private String name;

  public int getId() {
    return id;
  }


  public String getName() {
    return name;
  }

  public Department(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object otherDepartment){
    if (!(otherDepartment instanceof Department)) {
      return false;
    } else {
      Department newDepartment = (Department) otherDepartment;
      return this.getName().equals(newDepartment.getName()) &&
             this.getId() == newDepartment.getId();
    }
  }

  public static List<Department> all() {
    String sql = "SELECT * FROM departments";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Department.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO departments(name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Department find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM departments where id=:id";
      Department course = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Department.class);
      return course;
    }
  }

  public List<Student> getStudents() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM students WHERE major = :name";
      return con.createQuery(sql)
        .addParameter("name", name)
        .executeAndFetch(Student.class);
    }
  }

  public List<Course> getCourses() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM courses WHERE department_id = :id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetch(Course.class);
    }
  }

  public void update(String name, int number) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE departments SET name = :name WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
    String sql = "DELETE FROM departments WHERE id = :id;";
    con.createQuery(sql)
      .addParameter("id", id)
      .executeUpdate();

    String deleteCoursesQuery = "DELETE FROM courses WHERE department_id = :id";
    con.createQuery(deleteCoursesQuery)
      .addParameter("id", id)
      .executeUpdate();

    String deleteStudentsQuery = "DELETE FROM students WHERE major = :name";
    con.createQuery(deleteStudentsQuery).addParameter("name", name).executeUpdate();
    }
  }
}

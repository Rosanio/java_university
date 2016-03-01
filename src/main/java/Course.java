import java.util.*;
import org.sql2o.*;

public class Course {
  private int id;
  private String name;
  private int course_number;

  public int getId() {
    return id;
  }

  public int getCourseNumber() {
    return course_number;
  }

  public String getName() {
    return name;
  }

  public Course(String name, int course_number) {
    this.name = name;
    this.course_number = course_number;
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
      String sql = "INSERT INTO courses(name, course_number) VALUES (:name, :course_number)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", name)
        .addParameter("course_number", course_number)
        .executeUpdate()
        .getKey();
    }
  }

  public static Course find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM courses where id=:id";
      Course task = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Course.class);
      return task;
    }
  }

  // public void addCategory(Category category) {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "INSERT INTO courses_categories (category_id, task_id) VALUES (:category_id, :task_id)";
  //     con.createQuery(sql)
  //     .addParameter("category_id", category.getId())
  //     .addParameter("task_id", id)
  //     .executeUpdate();
  //   }
  // }

  // public ArrayList<Category> getCategories() {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "SELECT category_id FROM courses_categories WHERE task_id=:id";
  //     List<Integer> categoryIds = con.createQuery(sql)
  //       .addParameter("id", id)
  //       .executeAndFetch(Integer.class);
  //     ArrayList<Category> myCategories = new ArrayList<Category>();
  //     for(Integer categoryId : categoryIds) {
  //       String categoryQuery = "SELECT * FROM categories where id=:id";
  //       Category category = con.createQuery(categoryQuery)
  //         .addParameter("id", categoryId)
  //         .executeAndFetchFirst(Category.class);
  //         myCategories.add(category);
  //     }
  //   return myCategories;
  //   }
  // }

  public void updateName(String name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE courses SET name = :name WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void updateNumber(int course_number) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE courses SET course_number = :course_number WHERE id = :id";
      con.createQuery(sql)
        .addParameter("course_number", course_number)
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

    String deleteQuery = "DELETE FROM courses_categories WHERE task_id = :id";
      con.createQuery(deleteQuery)
        .addParameter("id", id)
        .executeUpdate();
    }
  }
}

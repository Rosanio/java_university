import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/university_test", null, null);
   }

  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String studentsQuery = "DELETE FROM students *;";
      String coursesQuery = "DELETE FROM courses *;";
      String studentsCoursesQuery = "DELETE FROM students_courses *;";
      String departmentsQuery = "DELETE FROM departments *;";
      con.createQuery(studentsQuery).executeUpdate();
      con.createQuery(coursesQuery).executeUpdate();
      con.createQuery(studentsCoursesQuery).executeUpdate();
      con.createQuery(departmentsQuery).executeUpdate();
    }
  }
}

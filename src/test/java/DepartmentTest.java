import org.junit.*;
import java.util.*;
import static org.junit.Assert.*;

public class DepartmentTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Department.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame() {
    Department firstDepartment = new Department("Matt");
    Department secondDepartment = new Department("Matt");
    assertTrue(firstDepartment.equals(secondDepartment));
  }

  @Test
  public void save_savesObjectIntoDatabase() {
    Department myDepartment = new Department("Matt");
    myDepartment.save();
    Department savedDepartment = Department.all().get(0);
    assertTrue(savedDepartment.equals(myDepartment));
  }

  @Test
  public void save_assignsIdToObject() {
    Department myDepartment = new Department("Matt");
    myDepartment.save();
    Department savedDepartment = Department.all().get(0);
    assertEquals(myDepartment.getId(), savedDepartment.getId());
  }

  @Test
  public void find_findsDepartmentInDatabase_true() {
    Department myDepartment = new Department("Matt");
    myDepartment.save();
    Department savedDepartment = Department.find(myDepartment.getId());
    assertTrue(myDepartment.equals(savedDepartment));
  }
  // @Test
  // public void addCourse_addsCourseToDepartment() {
  //   Course myCourse = new Course("HIST", 101);
  //   myCourse.save();
  //
  //   Department myDepartment = new Department("Matt");
  //   myDepartment.save();
  //
  //   myDepartment.addCourse(myCourse);
  //   Course savedCourse = myDepartment.getCourses().get(0);
  //   assertTrue(myCourse.equals(savedCourse));
  // }
  //
  // @Test
  // public void getCourses_returnsAllCourses_ArrayList() {
  //   Course myCourse = new Course("HIST", 101);
  //   myCourse.save();
  //
  //   Department myDepartment = new Department("Matt");
  //   myDepartment.save();
  //
  //   myDepartment.addCourse(myCourse);
  //   List savedCourses = myDepartment.getCourses();
  //   assertEquals(savedCourses.size(), 1);
  // }
  //
  // @Test
  // public void delete_deletesAllDepartmentsAndListsAssoicationes() {
  //   Course myCourse = new Course("HIST", 101);
  //   myCourse.save();
  //
  //   Department myDepartment = new Department("Matt");
  //   myDepartment.save();
  //
  //   myDepartment.addCourse(myCourse);
  //   myDepartment.delete();
  //   assertEquals(myCourse.getDepartments().size(), 0);
  // }
}

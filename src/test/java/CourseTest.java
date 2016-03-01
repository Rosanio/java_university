import org.junit.*;
import java.util.*;
import static org.junit.Assert.*;

public class CourseTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Course.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame() {
    Course firstCourse = new Course("HIST", 101);
    Course secondCourse = new Course("HIST", 101);
    assertTrue(firstCourse.equals(secondCourse));
  }

  @Test
  public void save_savesObjectIntoDatabase() {
    Course myCourse = new Course("HIST", 101);
    myCourse.save();
    Course savedCourse = Course.all().get(0);
    assertTrue(savedCourse.equals(myCourse));
  }

  @Test
  public void save_assignsIdToObject() {
    Course myCourse = new Course("HIST", 101);
    myCourse.save();
    Course savedCourse = Course.all().get(0);
    assertEquals(myCourse.getId(), savedCourse.getId());
  }

  @Test
  public void find_findsCourseInDatabase_true() {
    Course myCourse = new Course("HIST", 101);
    myCourse.save();
    Course savedCourse = Course.find(myCourse.getId());
    assertTrue(myCourse.equals(savedCourse));
  }
  // @Test
  // public void addCategory_addsCategoryToCourse() {
  //   Category myCategory = new Category("Household chores");
  //   myCategory.save();
  //
  //   Course myCourse = new Course("HIST", 101);
  //   myCourse.save();
  //
  //   myCourse.addCategory(myCategory);
  //   Category savedCategory = myCourse.getCategories().get(0);
  //   assertTrue(myCategory.equals(savedCategory));
  // }

  // @Test
  // public void getCategories_returnsAllCategories_ArrayList() {
  //   Category myCategory = new Category("Household chores");
  //   myCategory.save();
  //
  //   Course myCourse = new Course("HIST", 101);
  //   myCourse.save();
  //
  //   myCourse.addCategory(myCategory);
  //   List savedCategories = myCourse.getCategories();
  //   assertEquals(savedCategories.size(), 1);
  // }

  // @Test
  // public void delete_deletesAllCoursesAndListsAssoicationes() {
  //   Category myCategory = new Category("Household chores");
  //   myCategory.save();
  //
  //   Course myCourse = new Course("HIST", 101);
  //   myCourse.save();
  //
  //   myCourse.addCategory(myCategory);
  //   myCourse.delete();
  //   assertEquals(myCategory.getCourses().size(), 0);
  // }
}

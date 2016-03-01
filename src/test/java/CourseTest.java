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
    Course firstCourse = new Course("HIST", 101, 1);
    Course secondCourse = new Course("HIST", 101, 1);
    assertTrue(firstCourse.equals(secondCourse));
  }

  @Test
  public void save_savesObjectIntoDatabase() {
    Course myCourse = new Course("HIST", 101, 1);
    myCourse.save();
    Course savedCourse = Course.all().get(0);
    assertTrue(savedCourse.equals(myCourse));
  }

  @Test
  public void save_assignsIdToObject() {
    Course myCourse = new Course("HIST", 101, 1);
    myCourse.save();
    Course savedCourse = Course.all().get(0);
    assertEquals(myCourse.getId(), savedCourse.getId());
  }

  @Test
  public void find_findsCourseInDatabase_true() {
    Course myCourse = new Course("HIST", 101, 1);
    myCourse.save();
    Course savedCourse = Course.find(myCourse.getId());
    assertTrue(myCourse.equals(savedCourse));
  }
  @Test
  public void addStudent_addsStudentToCourse() {
    Student myStudent = new Student("John", "12-23-2015", "History");
    myStudent.save();

    Course myCourse = new Course("HIST", 101, 1);
    myCourse.save();

    myCourse.addStudent(myStudent);
    Student savedStudent = myCourse.getStudents().get(0);
    assertTrue(myStudent.equals(savedStudent));
  }

  @Test
  public void getStudents_returnsAllStudents_ArrayList() {
    Student myStudent = new Student("John", "12-23-2015", "History");
    myStudent.save();

    Course myCourse = new Course("HIST", 101, 1);
    myCourse.save();

    myCourse.addStudent(myStudent);
    List savedStudents = myCourse.getStudents();
    assertEquals(savedStudents.size(), 1);
  }

  @Test
  public void update_updatesCourseNameAndNumber() {
    Course myCourse = new Course("HIST", 101, 1);
    myCourse.save();
    myCourse.update("BUSI", 102);
    assertEquals(Course.all().get(0).getName(), "BUSI");
    assertEquals(Course.all().get(0).getCourseNumber(), 102);
  }

  @Test
  public void delete_deletesAllCoursesAndListsAssoicationes() {
    Student myStudent = new Student("Matt", "2010-08-15", "History");
    myStudent.save();

    Course myCourse = new Course("HIST", 101, 1);
    myCourse.save();

    myCourse.addStudent(myStudent);
    myCourse.delete();
    assertEquals(myStudent.getCourses().size(), 0);
  }

  @Test
  public void getDepartment_returnsDepartmentOfCourse() {
    Department department = new Department("History");
    department.save();

    Course course = new Course("HIST", 101, department.getId());
    course.save();

    assertTrue(course.getDepartment().equals(department));
  }
}

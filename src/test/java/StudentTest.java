import org.junit.*;
import java.util.*;
import static org.junit.Assert.*;

public class StudentTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Student.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame() {
    Student firstStudent = new Student("Matt", "2010-08-15", "History");
    Student secondStudent = new Student("Matt", "2010-08-15", "History");
    assertTrue(firstStudent.equals(secondStudent));
  }

  @Test
  public void save_savesObjectIntoDatabase() {
    Student myStudent = new Student("Matt", "2010-08-15", "History");
    myStudent.save();
    Student savedStudent = Student.all().get(0);
    assertTrue(savedStudent.equals(myStudent));
  }

  @Test
  public void save_assignsIdToObject() {
    Student myStudent = new Student("Matt", "2010-08-15", "History");
    myStudent.save();
    Student savedStudent = Student.all().get(0);
    assertEquals(myStudent.getId(), savedStudent.getId());
  }

  @Test
  public void find_findsStudentInDatabase_true() {
    Student myStudent = new Student("Matt", "2010-08-15", "History");
    myStudent.save();
    Student savedStudent = Student.find(myStudent.getId());
    assertTrue(myStudent.equals(savedStudent));
  }
  @Test
  public void addCourse_addsCourseToStudent() {
    Course myCourse = new Course("HIST", 101, 1);
    myCourse.save();

    Student myStudent = new Student("Matt", "2010-08-15", "History");
    myStudent.save();

    myStudent.addCourse(myCourse);
    Course savedCourse = myStudent.getCourses().get(0);
    assertTrue(myCourse.equals(savedCourse));
  }

  @Test
  public void getCourses_returnsAllCourses_ArrayList() {
    Course myCourse = new Course("HIST", 101, 1);
    myCourse.save();

    Student myStudent = new Student("Matt", "2010-08-15", "History");
    myStudent.save();

    myStudent.addCourse(myCourse);
    List savedCourses = myStudent.getCourses();
    assertEquals(savedCourses.size(), 1);
  }

  @Test
  public void delete_deletesAllStudentsAndListsAssoicationes() {
    Course myCourse = new Course("HIST", 101, 1);
    myCourse.save();

    Student myStudent = new Student("Matt", "2010-08-15", "History");
    myStudent.save();

    myStudent.addCourse(myCourse);
    myStudent.delete();
    assertEquals(myCourse.getStudents().size(), 0);
  }

  @Test
  public void completed_returnsFalseAtFirst() {
    Course myCourse = new Course("HIST", 101, 1);
    myCourse.save();

    Student myStudent = new Student("Matt", "2010-08-15", "History");
    myStudent.save();

    myStudent.addCourse(myCourse);
    myStudent.complete(myCourse);

    assertEquals(true, myStudent.completed(myCourse));
  }
}

package ru.project.search;

import org.junit.jupiter.api.Test;
import ru.project.list.MyList;
import ru.project.list.MyLinkedList;
import ru.project.student.Student;

import static org.junit.jupiter.api.Assertions.*;

class StudentSearchTest {

    @Test
    void testGroupNumberRangeMatchesInside() {
        StudentSearchCriteria criteria = new GroupNumberRangeCriteria("A10", "C30");
        Student student = new Student("B20", 4.5, 123);
        assertTrue(criteria.matches(student));
    }

    @Test
    void testGroupNumberRangeMatchesLowerBoundary() {
        StudentSearchCriteria criteria = new GroupNumberRangeCriteria("A10", "C30");
        Student student = new Student("A10", 4.5, 123);
        assertTrue(criteria.matches(student));
    }

    @Test
    void testGroupNumberRangeMatchesUpperBoundary() {
        StudentSearchCriteria criteria = new GroupNumberRangeCriteria("A10", "C30");
        Student student = new Student("C30", 4.5, 123);
        assertTrue(criteria.matches(student));
    }

    @Test
    void testGroupNumberRangeDoesNotMatchOutside() {
        StudentSearchCriteria criteria = new GroupNumberRangeCriteria("A10", "C30");
        Student student = new Student("D00", 4.5, 123);
        assertFalse(criteria.matches(student));
    }

    @Test
    void testGroupNumberRangeInvalidMinMaxThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new GroupNumberRangeCriteria("C30", "A10"));
    }

    @Test
    void testGroupNumberRangeNullValuesThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new GroupNumberRangeCriteria(null, "A10"));
        assertThrows(IllegalArgumentException.class,
                () -> new GroupNumberRangeCriteria("A10", null));
    }

    @Test
    void testAverageGradeRangeMatchesInside() {
        StudentSearchCriteria criteria = new AverageGradeRangeCriteria(4.0, 5.0);
        Student student = new Student("A12", 4.5, 123);
        assertTrue(criteria.matches(student));
    }

    @Test
    void testAverageGradeRangeMatchesLowerBoundary() {
        StudentSearchCriteria criteria = new AverageGradeRangeCriteria(4.0, 5.0);
        Student student = new Student("A12", 4.0, 123);
        assertTrue(criteria.matches(student));
    }

    @Test
    void testAverageGradeRangeMatchesUpperBoundary() {
        StudentSearchCriteria criteria = new AverageGradeRangeCriteria(4.0, 5.0);
        Student student = new Student("A12", 5.0, 123);
        assertTrue(criteria.matches(student));
    }

    @Test
    void testAverageGradeRangeDoesNotMatchBelow() {
        StudentSearchCriteria criteria = new AverageGradeRangeCriteria(4.0, 5.0);
        Student student = new Student("A12", 3.9, 123);
        assertFalse(criteria.matches(student));
    }

    @Test
    void testAverageGradeRangeDoesNotMatchAbove() {
        StudentSearchCriteria criteria = new AverageGradeRangeCriteria(4.0, 5.0);
        Student student = new Student("A12", 5.1, 123);
        assertFalse(criteria.matches(student));
    }

    @Test
    void testAverageGradeRangeInvalidMinMaxThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new AverageGradeRangeCriteria(5.0, 4.0));
    }

    @Test
    void testRecordBookNumberRangeMatchesInside() {
        StudentSearchCriteria criteria = new RecordBookNumberRangeCriteria(100, 200);
        Student student = new Student("A12", 4.5, 150);
        assertTrue(criteria.matches(student));
    }

    @Test
    void testRecordBookNumberRangeMatchesLowerBoundary() {
        StudentSearchCriteria criteria = new RecordBookNumberRangeCriteria(100, 200);
        Student student = new Student("A12", 4.5, 100);
        assertTrue(criteria.matches(student));
    }

    @Test
    void testRecordBookNumberRangeMatchesUpperBoundary() {
        StudentSearchCriteria criteria = new RecordBookNumberRangeCriteria(100, 200);
        Student student = new Student("A12", 4.5, 200);
        assertTrue(criteria.matches(student));
    }

    @Test
    void testRecordBookNumberRangeDoesNotMatchBelow() {
        StudentSearchCriteria criteria = new RecordBookNumberRangeCriteria(100, 200);
        Student student = new Student("A12", 4.5, 99);
        assertFalse(criteria.matches(student));
    }

    @Test
    void testRecordBookNumberRangeDoesNotMatchAbove() {
        StudentSearchCriteria criteria = new RecordBookNumberRangeCriteria(100, 200);
        Student student = new Student("A12", 4.5, 201);
        assertFalse(criteria.matches(student));
    }

    @Test
    void testRecordBookNumberRangeInvalidMinMaxThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new RecordBookNumberRangeCriteria(200, 100));
    }

    @Test
    void testCompositeIntersectionEmptyReturnsTrue() {
        CompositeStudentSearchCriteria composite = new CompositeStudentSearchCriteria(SearchOperation.INTERSECTION);
        Student student = new Student("A12", 4.5, 123);
        assertTrue(composite.matches(student));
    }

    @Test
    void testCompositeUnionEmptyReturnsFalse() {
        CompositeStudentSearchCriteria composite = new CompositeStudentSearchCriteria(SearchOperation.UNION);
        Student student = new Student("A12", 4.5, 123);
        assertFalse(composite.matches(student));
    }

    @Test
    void testCompositeIntersectionAllTrue() {
        CompositeStudentSearchCriteria composite = new CompositeStudentSearchCriteria(SearchOperation.INTERSECTION);
        composite.add(new GroupNumberRangeCriteria("A10", "C30"));
        composite.add(new AverageGradeRangeCriteria(4.0, 5.0));
        Student student = new Student("B20", 4.5, 123);
        assertTrue(composite.matches(student));
    }

    @Test
    void testCompositeIntersectionOneFalse() {
        CompositeStudentSearchCriteria composite = new CompositeStudentSearchCriteria(SearchOperation.INTERSECTION);
        composite.add(new GroupNumberRangeCriteria("A10", "C30"));
        composite.add(new AverageGradeRangeCriteria(4.0, 5.0));
        Student student = new Student("B20", 3.5, 123);
        assertFalse(composite.matches(student));
    }

    @Test
    void testCompositeUnionOneTrue() {
        CompositeStudentSearchCriteria composite = new CompositeStudentSearchCriteria(SearchOperation.UNION);
        composite.add(new GroupNumberRangeCriteria("A10", "C30"));
        composite.add(new AverageGradeRangeCriteria(4.0, 5.0));
        Student student = new Student("D00", 4.5, 123);
        assertTrue(composite.matches(student));
    }

    @Test
    void testCompositeUnionAllFalse() {
        CompositeStudentSearchCriteria composite = new CompositeStudentSearchCriteria(SearchOperation.UNION);
        composite.add(new GroupNumberRangeCriteria("A10", "C30"));
        composite.add(new AverageGradeRangeCriteria(4.0, 5.0));
        Student student = new Student("D00", 3.5, 123);
        assertFalse(composite.matches(student));
    }

    @Test
    void testCompositeWithNestedComposite() {
        CompositeStudentSearchCriteria outer = new CompositeStudentSearchCriteria(SearchOperation.INTERSECTION);
        outer.add(new GroupNumberRangeCriteria("A10", "C30"));

        CompositeStudentSearchCriteria inner = new CompositeStudentSearchCriteria(SearchOperation.UNION);
        inner.add(new AverageGradeRangeCriteria(4.0, 5.0));
        inner.add(new RecordBookNumberRangeCriteria(100, 200));

        outer.add(inner);

        Student student = new Student("B20", 4.5, 150);
        assertTrue(outer.matches(student));
        student = new Student("B20", 3.5, 150);
        assertFalse(outer.matches(student));
        student = new Student("D00", 4.5, 150);
        assertFalse(outer.matches(student));
    }

    @Test
    void testSearchServiceWithSingleCriteria() {
        MyList<Student> students = new MyLinkedList<>();
        students.add(new Student("A10", 4.5, 1));
        students.add(new Student("B20", 3.8, 2));
        students.add(new Student("C30", 4.9, 3));

        StudentSearchCriteria criteria = new AverageGradeRangeCriteria(4.0, 5.0);
        StudentSearchService service = new StudentSearchService(criteria);
        MyList<Student> result = service.find(students);

        assertEquals(2, result.size());
        assertEquals("A10", result.get(0).getGroupNumber());
        assertEquals("C30", result.get(1).getGroupNumber());
    }

    @Test
    void testSearchServiceEmptyResult() {
        MyList<Student> students = new MyLinkedList<>();
        students.add(new Student("A10", 3.5, 1));
        students.add(new Student("B20", 3.8, 2));

        StudentSearchCriteria criteria = new AverageGradeRangeCriteria(4.0, 5.0);
        StudentSearchService service = new StudentSearchService(criteria);
        MyList<Student> result = service.find(students);

        assertEquals(0, result.size());
    }

    @Test
    void testSearchServiceEmptyCollection() {
        MyList<Student> students = new MyLinkedList<>();
        StudentSearchCriteria criteria = new AverageGradeRangeCriteria(4.0, 5.0);
        StudentSearchService service = new StudentSearchService(criteria);
        MyList<Student> result = service.find(students);
        assertEquals(0, result.size());
    }

    @Test
    void testSearchServicePreservesOrder() {
        MyList<Student> students = new MyLinkedList<>();
        students.add(new Student("Z00", 4.5, 1));
        students.add(new Student("A10", 4.5, 2));
        students.add(new Student("M30", 4.5, 3));

        StudentSearchCriteria criteria = new AverageGradeRangeCriteria(4.0, 5.0);
        StudentSearchService service = new StudentSearchService(criteria);
        MyList<Student> result = service.find(students);

        assertEquals(3, result.size());
        assertEquals("Z00", result.get(0).getGroupNumber());
        assertEquals("A10", result.get(1).getGroupNumber());
        assertEquals("M30", result.get(2).getGroupNumber());
    }

    @Test
    void testSearchServiceDoesNotModifyOriginal() {
        MyList<Student> original = new MyLinkedList<>();
        original.add(new Student("A10", 4.5, 1));
        original.add(new Student("B20", 3.8, 2));

        MyList<Student> copy = new MyLinkedList<>();
        for (int i = 0; i < original.size(); i++) {
            copy.add(original.get(i));
        }

        StudentSearchCriteria criteria = new AverageGradeRangeCriteria(4.0, 5.0);
        StudentSearchService service = new StudentSearchService(criteria);
        service.find(original);

        assertEquals(copy.size(), original.size());
        for (int i = 0; i < copy.size(); i++) {
            assertEquals(copy.get(i).getGroupNumber(), original.get(i).getGroupNumber());
        }
    }

    @Test
    void testSearchServiceWithNullCriteriaThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new StudentSearchService(null));
    }

    @Test
    void testSearchServiceWithCompositeCriteria() {
        MyList<Student> students = new MyLinkedList<>();
        students.add(new Student("A10", 4.5, 1));
        students.add(new Student("B20", 4.5, 2));
        students.add(new Student("C30", 3.5, 3));

        CompositeStudentSearchCriteria composite = new CompositeStudentSearchCriteria(SearchOperation.INTERSECTION);
        composite.add(new GroupNumberRangeCriteria("A10", "B20"));
        composite.add(new AverageGradeRangeCriteria(4.0, 5.0));

        StudentSearchService service = new StudentSearchService(composite);
        MyList<Student> result = service.find(students);

        assertEquals(2, result.size());
        assertEquals("A10", result.get(0).getGroupNumber());
        assertEquals("B20", result.get(1).getGroupNumber());
    }

    @Test
    void testSearchServiceWithNullStudentListReturnsEmpty() {
        StudentSearchCriteria criteria = new AverageGradeRangeCriteria(4.0, 5.0);
        StudentSearchService service = new StudentSearchService(criteria);
        MyList<Student> result = service.find(null);
        assertNotNull(result);
        assertEquals(0, result.size());
    }
}
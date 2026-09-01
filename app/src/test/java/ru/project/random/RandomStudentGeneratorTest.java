package ru.project.random;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.project.collection.MyLinkedList;
import ru.project.collection.MyList;
import ru.project.student.Student;
import java.util.regex.Pattern;
import static org.junit.jupiter.api.Assertions.*;
public class RandomStudentGeneratorTest
{
    private static final Pattern GROUP_PATTERN = Pattern.compile("^[A-Z][0-9]{2}$");
    private static final double MIN_GRADE = 0.0;
    private static final double MAX_GRADE = 5.0;
    @Test
    void shouldTestgenerate_withoutConstraints_returnsCorrectSizeAndValidStudents() {
        int size = 100;
        RandomStudentGenerator generator = new RandomStudentGenerator();
        MyList<Student> students = generator.generate(size);
        assertEquals(size, students.size(), "Размер списка должен совпадать с запрошенным");
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            assertTrue(GROUP_PATTERN.matcher(s.getGroupNumber()).matches(),
                    "groupNumber должен соответствовать шаблону A00–Z99");
            assertTrue(s.getAverageGrade() >= MIN_GRADE && s.getAverageGrade() <= MAX_GRADE,
                    "averageGrade должен быть в диапазоне [0.0, 5.0]");
            assertTrue(s.getRecordBookNumber() >= 1,
                    "recordBookNumber должен быть >= 1");
        }
    }
    @Test
    void shouldTestgenerate_withGroupRange_respectsLexicographicalBounds() {
        RandomStudentGenerator generator = new RandomStudentGenerator()
                .setMinGroupNumber("A00")
                .setMaxGroupNumber("B99");
        MyList<Student> students = generator.generate(200);
        assertEquals(200, students.size());
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            String g = s.getGroupNumber();
            assertTrue(g.compareTo("A00") >= 0, "Группа не должна быть меньше A00");
            assertTrue(g.compareTo("B99") <= 0, "Группа не должна быть больше B99");
            assertTrue(GROUP_PATTERN.matcher(g).matches(), "Группа должна соответствовать шаблону");
        }
    }
    @Test
    void shouldTestgenerate_withGradeRange_respectsBounds() {
        double minGrade = 3.0;
        double maxGrade = 4.0;

        RandomStudentGenerator generator = new RandomStudentGenerator()
                .setMinAverageGrade(minGrade)
                .setMaxAverageGrade(maxGrade);
        MyList<Student> students = generator.generate(100);

        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            double g = s.getAverageGrade();
            assertTrue(g >= minGrade && g <= maxGrade,
                    "averageGrade должен быть в заданном диапазоне");
        }
    }
    @Test
    void shouldTestgenerate_withRecordBookRange_respectsBounds() {
        int minRec = 10_000;
        int maxRec = 20_000;

        RandomStudentGenerator generator = new RandomStudentGenerator()
                .setMinRecordBookNumber(minRec)
                .setMaxRecordBookNumber(maxRec);
        MyList<Student> students = generator.generate(100);

        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            int r = s.getRecordBookNumber();
            assertTrue(r >= minRec && r <= maxRec,
                    "recordBookNumber должен быть в заданном диапазоне");
        }
    }

    @Test
    void shouldTestgenerate_negativeSize_throwsException() {
        RandomStudentGenerator generator = new RandomStudentGenerator();
        assertThrows(IllegalArgumentException.class, () -> generator.generate(-1));
    }
    @Test
    void shouldTestsetMinGroupNumber_invalidPattern_throwsException() {
        RandomStudentGenerator generator = new RandomStudentGenerator();
        assertThrows(IllegalArgumentException.class,
                () -> generator.setMinGroupNumber("invalid"));
    }
    @Test
    void shouldTestsetMaxGroupNumber_invalidPattern_throwsException() {
        RandomStudentGenerator generator = new RandomStudentGenerator();
        assertThrows(IllegalArgumentException.class,
                () -> generator.setMaxGroupNumber("ZZ")); // не 2 цифры
    }
    @Test
    void shouldTestsetMinMaxGroupNumber_minGreaterThanMax_throwsException() {
        RandomStudentGenerator generator = new RandomStudentGenerator();
        assertThrows(IllegalArgumentException.class,
                () -> generator.setMinGroupNumber("C00").setMaxGroupNumber("A99").generate(1));
    }
    @Test
    void shouldTestsetMinAverageGrade_outOfRange_throwsException() {
        RandomStudentGenerator generator = new RandomStudentGenerator();
        assertThrows(IllegalArgumentException.class,
                () -> generator.setMinAverageGrade(-0.1));
        assertThrows(IllegalArgumentException.class,
                () -> generator.setMinAverageGrade(5.1));
    }
    @Test
    void shouldTestsetMaxAverageGrade_outOfRange_throwsException() {
        RandomStudentGenerator generator = new RandomStudentGenerator();
        assertThrows(IllegalArgumentException.class,
                () -> generator.setMaxAverageGrade(-0.1));
        assertThrows(IllegalArgumentException.class,
                () -> generator.setMaxAverageGrade(5.1));
    }
    @Test
    void shouldTestsetMinMaxAverageGrade_minGreaterThanMax_throwsException() {
        RandomStudentGenerator generator = new RandomStudentGenerator();
        assertThrows(IllegalArgumentException.class,
                () -> generator.setMinAverageGrade(4.0).setMaxAverageGrade(3.0).generate(1));
    }
    @Test
    void shouldTestsetMinRecordBookNumber_lessThanAllowed_throwsException() {
        RandomStudentGenerator generator = new RandomStudentGenerator();
        assertThrows(IllegalArgumentException.class,
                () -> generator.setMinRecordBookNumber(0));
    }
    @Test
    void shouldTestsetMinMaxRecordBookNumber_minGreaterThanMax_throwsException() {
        RandomStudentGenerator generator = new RandomStudentGenerator();
        assertThrows(IllegalArgumentException.class,
                () -> generator.setMinRecordBookNumber(20_000).setMaxRecordBookNumber(10_000).generate(1));
    }
    @Test
    void shouldTestchainedBuilderStyle_worksAsExpected() {
        RandomStudentGenerator generator = new RandomStudentGenerator()
                .setMinGroupNumber("A00")
                .setMaxGroupNumber("Z99")
                .setMinAverageGrade(2.0)
                .setMaxAverageGrade(4.0)
                .setMinRecordBookNumber(1_000)
                .setMaxRecordBookNumber(9_999);
        MyList<Student> students = generator.generate(50);
        assertEquals(50, students.size());
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            assertTrue(s.getGroupNumber().compareTo("A00") >= 0
                    && s.getGroupNumber().compareTo("Z99") <= 0);
            assertTrue(s.getAverageGrade() >= 2.0 && s.getAverageGrade() <= 4.0);
            assertTrue(s.getRecordBookNumber() >= 1_000 && s.getRecordBookNumber() <= 9_999);
        }
    }
}

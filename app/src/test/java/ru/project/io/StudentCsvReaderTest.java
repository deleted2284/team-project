package ru.project.io;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.project.student.Student;
import ru.project.collection.MyLinkedList;
import ru.project.collection.MyList;
import ru.project.collectors.MyCollectors;
import ru.project.collectors.MyListCollector;
class StudentCsvReaderTest {
    @TempDir
    Path tempDir;
    @Test
    void shouldTestreadValidCsvIntoMyList() throws IOException {
        Path csv = tempDir.resolve("students.csv");
        String content = String.join("\n",
                "groupNumber,averageGrade,recordBookNumber",
                "M10,4.5,12345",   // валидно: 1 буква + 2 цифры
                "B05,3.8,12346"    // валидно
        );
        Files.writeString(csv, content, StandardCharsets.UTF_8);
        try (var reader = new StudentCsvReader(csv.toAbsolutePath())) {
            var studentStream = StreamSupport.stream(
                    Spliterators.spliteratorUnknownSize(reader, 0),
                    false
            );
            MyList<Student> students = studentStream
                    .collect(MyCollectors.toMyList());
            assertEquals(2, students.size());
            assertEquals("M10", students.get(0).getGroupNumber());
            assertEquals(4.5, students.get(0).getAverageGrade(), 0.0);
            assertEquals(12345, students.get(0).getRecordBookNumber());
            assertEquals("B05", students.get(1).getGroupNumber());
            assertEquals(3.8, students.get(1).getAverageGrade(), 0.0);
            assertEquals(12346, students.get(1).getRecordBookNumber());
        }
    }
    @Test
    void shouldTestinvalidHeaderThrowsException() throws IOException {
        Path csv = tempDir.resolve("bad-header.csv");
        String content = """
                badGroup,badGrade,badBook
                M101,4.5,12345
                """;
        Files.writeString(csv, content, StandardCharsets.UTF_8);
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new StudentCsvReader(csv.toAbsolutePath())
        );
        assertTrue(ex.getMessage().contains("Invalid CSV header"));
    }
    @Test
    void shouldTestwrongColumnCountThrowsException() throws IOException {
        Path csv = tempDir.resolve("wrong-columns.csv");
        String content = """
                groupNumber,averageGrade,recordBookNumber
                M101,4.5
                """;
        Files.writeString(csv, content, StandardCharsets.UTF_8);
        var reader = new StudentCsvReader(csv.toAbsolutePath());
        assertTrue(reader.hasNext());
        // следующая строка имеет только 2 колонки
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                reader::next
        );
        assertTrue(ex.getMessage().contains("Expected 3 columns"));
    }
    @Test
    void shouldTestparseErrorThrowsExceptionWithDetails() throws IOException {
        Path csv = tempDir.resolve("parse-error.csv");
        String content = """
                groupNumber,averageGrade,recordBookNumber
                M101,not-a-number,12345
                """;
        Files.writeString(csv, content, StandardCharsets.UTF_8);

        var reader = new StudentCsvReader(csv.toAbsolutePath());
        assertTrue(reader.hasNext());
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                reader::next
        );
        assertTrue(ex.getMessage().contains("cannot parse averageGrade"));
    }
    @Test
    void shouldTestemptyDataAfterHeaderThrowsNoSuchElement() throws IOException {
        Path csv = tempDir.resolve("empty-data.csv");
        String content = """
                groupNumber,averageGrade,recordBookNumber
                """;
        Files.writeString(csv, content, StandardCharsets.UTF_8);
        var reader = new StudentCsvReader(csv.toAbsolutePath());
        assertFalse(reader.hasNext());
        assertThrows(NoSuchElementException.class, reader::next);
    }
    @Test
    void shouldTestrelativePathThrowsException() {
        Path relative = Path.of("relative-path.csv");
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new StudentCsvReader(relative)
        );
        assertTrue(ex.getMessage().contains("absolute"));
    }
}

package ru.project.datasource;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import ru.project.student.Student;
import ru.project.collection.MyList;
import ru.project.collection.MyLinkedList;


public class StudentCsvWriter{
    private static final String[] HEADER = {
            "groupNumber", "averageGrade", "recordBookNumber"
    };
    private static final DateTimeFormatter FILE_NAME_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");

    private final Path requestedPath;
    private Path actualPath;

    public StudentCsvWriter(String absoluteFilePath) {
        Path path = Paths.get(absoluteFilePath);
        if (!path.isAbsolute()) {
            throw new IllegalArgumentException("File path must be absolute");
        }
        this.requestedPath = path;
    }

    public void write(Student student) throws IOException {
        Path target = resolveTargetPath();
        boolean fileExists = Files.exists(target);

        StandardOpenOption[] options = fileExists
                ? new StandardOpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND}
                : new StandardOpenOption[]{StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE};

        try (BufferedWriter writer = Files.newBufferedWriter(target, StandardCharsets.UTF_8, options);
             CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            if (!fileExists) {
                printer.printRecord((Object[]) HEADER);
            }
            printer.printRecord(
                    student.getGroupNumber(),
                    Double.toString(student.getAverageGrade()),
                    student.getRecordBookNumber()
            );
        }
    }

    private Path resolveTargetPath() {
        if (actualPath != null) {
            return actualPath;
        }
        if (Files.exists(requestedPath)) {
            actualPath = requestedPath;
            return actualPath;
        }

        Path parent = requestedPath.getParent();
        String fileName = "students-" + LocalDateTime.now().format(FILE_NAME_FORMAT) + ".csv";
        actualPath = parent == null ? Paths.get(fileName).toAbsolutePath() : parent.resolve(fileName);
        return actualPath;
    }
}
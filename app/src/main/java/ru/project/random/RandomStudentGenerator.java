package ru.project.random;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.project.collection.MyLinkedList;
import ru.project.collection.MyList;
import ru.project.student.Student;
import java.util.Random;
import java.util.regex.Pattern;

public class RandomStudentGenerator
{
    private static final Random RANDOM = new Random();
    private String minGroupNumber;
    private String maxGroupNumber;
    private Double minAverageGrade;
    private Double maxAverageGrade;
    private Integer minRecordBookNumber;
    private Integer maxRecordBookNumber;
    private static final double DEFAULT_MIN_GRADE = 0.0;
    private static final double DEFAULT_MAX_GRADE = 5.0;
    private static final int DEFAULT_MIN_RECORD_BOOK = 1;
    private static final String GROUP_PATTERN_REGEX = "^[A-Z][0-9]{2}$";
    private static final Pattern GROUP_PATTERN = Pattern.compile(GROUP_PATTERN_REGEX);
    public RandomStudentGenerator setMinGroupNumber(String minGroupNumber) {
        if (minGroupNumber != null && !GROUP_PATTERN.matcher(minGroupNumber).matches()) {
            throw new IllegalArgumentException("minGroupNumber does not match Student group pattern: " + GROUP_PATTERN_REGEX);
        }
        this.minGroupNumber = minGroupNumber;
        return this;
    }
    public RandomStudentGenerator setMaxGroupNumber(String maxGroupNumber) {
        if (maxGroupNumber != null && !GROUP_PATTERN.matcher(maxGroupNumber).matches()) {
            throw new IllegalArgumentException("maxGroupNumber does not match Student group pattern: " + GROUP_PATTERN_REGEX);
        }
        this.maxGroupNumber = maxGroupNumber;
        return this;
    }

    public RandomStudentGenerator setMinAverageGrade(double minAverageGrade) {
        if (minAverageGrade < DEFAULT_MIN_GRADE || minAverageGrade > DEFAULT_MAX_GRADE) {
            throw new IllegalArgumentException(
                    "minAverageGrade out of Student allowed range [" + DEFAULT_MIN_GRADE + ", " + DEFAULT_MAX_GRADE + "]"
            );
        }
        this.minAverageGrade = minAverageGrade;
        return this;
    }

    public RandomStudentGenerator setMaxAverageGrade(double maxAverageGrade) {
        if (maxAverageGrade < DEFAULT_MIN_GRADE || maxAverageGrade > DEFAULT_MAX_GRADE) {
            throw new IllegalArgumentException(
                    "maxAverageGrade out of Student allowed range [" + DEFAULT_MIN_GRADE + ", " + DEFAULT_MAX_GRADE + "]"
            );
        }
        this.maxAverageGrade = maxAverageGrade;
        return this;
    }

    public RandomStudentGenerator setMinRecordBookNumber(int minRecordBookNumber) {
        if (minRecordBookNumber < DEFAULT_MIN_RECORD_BOOK) {
            throw new IllegalArgumentException(
                    "minRecordBookNumber must be >= " + DEFAULT_MIN_RECORD_BOOK
            );
        }
        this.minRecordBookNumber = minRecordBookNumber;
        return this;
    }

    public RandomStudentGenerator setMaxRecordBookNumber(int maxRecordBookNumber) {
        if (maxRecordBookNumber < DEFAULT_MIN_RECORD_BOOK) {
            throw new IllegalArgumentException(
                    "maxRecordBookNumber must be >= " + DEFAULT_MIN_RECORD_BOOK
            );
        }
        this.maxRecordBookNumber = maxRecordBookNumber;
        return this;
    }
    public MyList<Student> generate(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("size must be non-negative");
        }
        validateRanges();
        MyList<Student> result = new MyLinkedList<>();
        for (int i = 0; i < size; i++) {
            String group = generateGroupNumber();
            double grade = generateAverageGrade();
            int recordBook = generateRecordBookNumber();

            Student student = new StudentBuilder()
                    .setGroupNumber(group)
                    .setAverageGrade(grade)
                    .setRecordBookNumber(recordBook)
                    .build();

            result.add(student);
        }
        return result;
    }
    private void validateRanges() {

        double effectiveMinGrade = (minAverageGrade != null) ? minAverageGrade : DEFAULT_MIN_GRADE;
        double effectiveMaxGrade = (maxAverageGrade != null) ? maxAverageGrade : DEFAULT_MAX_GRADE;
        if (effectiveMinGrade > effectiveMaxGrade) {
            throw new IllegalArgumentException("minAverageGrade cannot be greater than maxAverageGrade");
        }
        int effectiveMinRecord = (minRecordBookNumber != null) ? minRecordBookNumber : DEFAULT_MIN_RECORD_BOOK;
        int effectiveMaxRecord = (maxRecordBookNumber != null) ? maxRecordBookNumber : Integer.MAX_VALUE;
        if (effectiveMaxRecord == Integer.MAX_VALUE) {
            effectiveMaxRecord = effectiveMinRecord + 999_999;
        }
        if (effectiveMinRecord > effectiveMaxRecord) {
            throw new IllegalArgumentException("minRecordBookNumber cannot be greater than maxRecordBookNumber");
        }
        if (minGroupNumber != null && maxGroupNumber != null) {
            if (minGroupNumber.compareTo(maxGroupNumber) > 0) {
                throw new IllegalArgumentException("minGroupNumber cannot be greater than maxGroupNumber");
            }
        }
    }
    private String generateGroupNumber() {
        String min = (minGroupNumber != null) ? minGroupNumber : "A00";
        String max = (maxGroupNumber != null) ? maxGroupNumber : "Z99";
        char minLetter = min.charAt(0);
        char maxLetter = max.charAt(0);
        int minDigit = Integer.parseInt(min.substring(1));
        int maxDigit = Integer.parseInt(max.substring(1));
        char letter = (char) (minLetter + RANDOM.nextInt((maxLetter - minLetter) + 1));
        int digit;
        if (letter == minLetter && letter == maxLetter) {
            digit = minDigit + RANDOM.nextInt((maxDigit - minDigit) + 1);
        } else if (letter == minLetter) {
            digit = minDigit + RANDOM.nextInt(100 - minDigit);
        } else if (letter == maxLetter) {
            digit = RANDOM.nextInt(maxDigit + 1);
        } else {
            digit = RANDOM.nextInt(100);
        }
        return letter + String.format("%02d", digit);
    }

    private double generateAverageGrade() {
        double min = (minAverageGrade != null) ? minAverageGrade : DEFAULT_MIN_GRADE;
        double max = (maxAverageGrade != null) ? maxAverageGrade : DEFAULT_MAX_GRADE;

        double range = max - min;
        return min + (range * RANDOM.nextDouble());
    }
    private int generateRecordBookNumber() {
        int min = (minRecordBookNumber != null) ? minRecordBookNumber : DEFAULT_MIN_RECORD_BOOK;
        int max = (maxRecordBookNumber != null) ? maxRecordBookNumber : min + 999_999; // разумный максимум
        if (max < min) {
            max = min;
        }
        return min + RANDOM.nextInt((max - min) + 1);
    }
}

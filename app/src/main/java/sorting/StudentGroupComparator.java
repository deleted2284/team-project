package sorting;

import java.util.Comparator;

public class StudentGroupComparator implements Comparator<Student> {

    @Override
    public int compare(Student s1, Student s2) {
        return Integer.compare(s1.getGroupNumber(), s2.getGroupNumber());
    }
}
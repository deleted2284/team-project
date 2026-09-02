package ru.project.ui.creation.random;

import ru.project.collection.MyList;
import ru.project.random.RandomStudentGenerator;
import ru.project.student.Student;
import ru.project.ui.creation.FillStrategy;

public class RandomFillStrategy implements FillStrategy {

  private final RandomFillSettings settings;

  public RandomFillStrategy(RandomFillSettings settings) {
    this.settings = settings;
  }

  @Override
  public MyList<Student> create() {
    RandomStudentGenerator generator = new RandomStudentGenerator();

    if (settings.getMinGroupNumber() != null) {
      generator.setMinGroupNumber(settings.getMinGroupNumber());
    }

    if (settings.getMaxGroupNumber() != null) {
      generator.setMaxGroupNumber(settings.getMaxGroupNumber());
    }

    if (settings.getMinAverageGrade() != null) {
      generator.setMinAverageGrade(settings.getMinAverageGrade());
    }

    if (settings.getMaxAverageGrade() != null) {
      generator.setMaxAverageGrade(settings.getMaxAverageGrade());
    }

    if (settings.getMinRecordBookNumber() != null) {
      generator.setMinRecordBookNumber(settings.getMinRecordBookNumber());
    }

    if (settings.getMaxRecordBookNumber() != null) {
      generator.setMaxRecordBookNumber(settings.getMaxRecordBookNumber());
    }

    return generator.generate(settings.getCollectionSize());
  }
}

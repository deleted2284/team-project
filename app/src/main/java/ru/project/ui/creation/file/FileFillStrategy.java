package ru.project.ui.creation.file;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;
import ru.project.collection.MyList;
import ru.project.collectors.MyCollectors;
import ru.project.io.StudentCsvReader;
import ru.project.student.Student;
import ru.project.ui.creation.FillStrategy;

public class FileFillStrategy implements FillStrategy {

  private final FileFillSettings settings;

  public FileFillStrategy(FileFillSettings settings) {
    this.settings = settings;
  }

  @Override
  public MyList<Student> create() {
    Path path = Path.of(settings.getAbsoluteFilePath());

    try (StudentCsvReader reader = new StudentCsvReader(path)) {

      return StreamSupport.stream(
              Spliterators.spliteratorUnknownSize(reader, Spliterator.ORDERED), false)
          .collect(MyCollectors.toMyList());

    } catch (IOException e) {
      throw new RuntimeException("Не удалось прочитать коллекцию из файла.", e);
    }
  }
}

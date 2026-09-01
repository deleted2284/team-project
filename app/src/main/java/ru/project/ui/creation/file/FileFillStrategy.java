package ru.project.ui.creation.file;

import java.io.IOException;
import java.nio.file.Path;
import ru.project.collection.MyLinkedList;
import ru.project.collection.MyList;
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
    MyList<Student> collection = new MyLinkedList<>();

    Path path = Path.of(settings.getAbsoluteFilePath());

    try (StudentCsvReader reader = new StudentCsvReader(path)) {

      while (reader.hasNext()) {
        collection.add(reader.next());
      }

    } catch (IOException e) {
      throw new RuntimeException("Не удалось прочитать коллекцию из файла.", e);
    }

    return collection;
  }
}

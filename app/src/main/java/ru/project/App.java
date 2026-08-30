package ru.project;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import ru.project.model.AppState;
import ru.project.ui.main.MainMenu;

public final class App {

  private App() {}

  public static void main(String[] args) {
    Screen screen = null;

    try {
      DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();

      screen = terminalFactory.createScreen();
      screen.startScreen();

      MultiWindowTextGUI gui = new MultiWindowTextGUI(screen);

      AppState state = new AppState();

      MainMenu mainMenu = new MainMenu(gui, state);

      mainMenu.showModal();

    } catch (Exception e) {
      System.err.println("Произошла ошибка при выполнении программы:");
      e.printStackTrace();

    } finally {
      if (screen != null) {
        try {
          screen.stopScreen();
        } catch (Exception e) {
          System.err.println("Не удалось корректно завершить работу терминала:");
          e.printStackTrace();
        }
      }
    }
  }
}

package ru.project;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import ru.project.ui.MainMenu;
import ru.project.ui.Navigator;

public final class App {

  private App() {}

  public static void main(String[] args) throws Exception {
    DefaultTerminalFactory factory = new DefaultTerminalFactory();

    Screen screen = new TerminalScreen(factory.createTerminal());

    screen.startScreen();

    try {
      MultiWindowTextGUI gui = new MultiWindowTextGUI(screen);

      Navigator navigator = new Navigator(gui);

      gui.addWindowAndWait(new MainMenu(navigator));
    } finally {
      screen.stopScreen();
    }
  }
}

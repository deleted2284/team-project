package ru.project.ui;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;

public abstract class EmptyMenu extends BasicWindow {

  protected EmptyMenu(String title) {
    super(title);

    Panel panel = new Panel(new LinearLayout(Direction.VERTICAL));

    panel.addComponent(new Label("Раздел находится в разработке"));

    panel.addComponent(new Button("Назад", this::close));

    setComponent(panel);
  }
}

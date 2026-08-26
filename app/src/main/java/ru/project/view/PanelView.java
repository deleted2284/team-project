package ru.project.view;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;

public class PanelView extends BaseView {

  protected final Panel panel;

  public PanelView(String title) {
    this(new Panel(), title);
  }

  private PanelView(Panel panel, String title) {
    super(title, panel);

    this.panel = panel;
    panel.setLayoutManager(new LinearLayout());
  }

  public Button addButton(String text, Runnable action) {
    Button button = new Button(text, action);

    panel.addComponent(button);

    return button;
  }

  public Label addLabel(String text) {
    Label label = new Label(text);

    panel.addComponent(label);

    return label;
  }

  public TextBox addTextBox() {
    TextBox textBox = new TextBox();

    panel.addComponent(textBox);

    return textBox;
  }
}

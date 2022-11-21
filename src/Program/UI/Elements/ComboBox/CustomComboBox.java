package Program.UI.Elements.ComboBox;

import Program.UI.Colors.Palette;
import Program.UI.Elements.Buttons.Button;
import Program.UI.Elements.Panels.SubMenuPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Arrays;

public abstract class CustomComboBox extends JPanel {
    private static final int COMBO_BOX_BUTTON_WIDTH = 250;
    private static final int COMBO_BOX_BUTTON_HEIGHT = 60;
    private static final int POPUP_BUTTON_WIDTH = 240;
    private static final int POPUP_BUTTON_HEIGHT = 40;
    private final Button[] popUpButtons;
    private final Button comboBoxButton;
    private final JPanel popUpPanel;
    private boolean popUpIsShowing = false;
    private Object selectedItem = 0;

    public CustomComboBox(Object... items) {
        this.setLayout(null);
        this.setOpaque(true);
        this.setFocusable(false);
        popUpButtons = new Button[items.length];
        comboBoxButton = new Button(items[0].toString(), false) {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D graphics2D = (Graphics2D) g;
                int[] pointsX = {getWidth() - 30, getWidth() - 20, getWidth() - 20};
                int[] pointsY = {getHeight() / 2, getHeight() / 2 + 8, getHeight() / 2 - 8};
                Polygon polygon = new Polygon(pointsX, pointsY, 3);
                int center_x = Arrays.stream(polygon.xpoints).sum() / 3, center_y = Arrays.stream(polygon.ypoints).sum() / 3;
                if (popUpIsShowing) {
                    graphics2D.rotate(Math.toRadians(-90), center_x, center_y);
                }
                graphics2D.setColor(Palette.getFontColor());
                graphics2D.fill(polygon);
            }
        };
        comboBoxButton.setBounds(0, 0, COMBO_BOX_BUTTON_WIDTH, COMBO_BOX_BUTTON_HEIGHT);
        comboBoxButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (popUpIsShowing) {
                    hidePopUp();
                } else {
                    showPopUp();
                }
            }
        });
        this.setPreferredSize(new Dimension(COMBO_BOX_BUTTON_WIDTH, COMBO_BOX_BUTTON_HEIGHT + (items.length) * POPUP_BUTTON_HEIGHT));
        this.add(comboBoxButton);
        popUpPanel = new SubMenuPanel(null);
        popUpPanel.setBounds(COMBO_BOX_BUTTON_WIDTH - POPUP_BUTTON_WIDTH, COMBO_BOX_BUTTON_HEIGHT, POPUP_BUTTON_WIDTH, items.length * POPUP_BUTTON_HEIGHT);
        popUpPanel.setVisible(false);
        popUpPanel.setFocusable(true);
        this.add(popUpPanel);
        for (int i = 0; i < items.length; i++) {
            popUpButtons[i] = new Button(items[i].toString(), true) {
                @Override
                public float getAlpha() {
                    return this.getText().equals(comboBoxButton.getText()) ? 1 : 0.8f;
                }
            };
            popUpButtons[i].setBounds(0, i * POPUP_BUTTON_HEIGHT, POPUP_BUTTON_WIDTH, POPUP_BUTTON_HEIGHT);
            int finalI = i;
            popUpButtons[i].addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedItem = items[finalI];
                    comboBoxButton.setText(popUpButtons[finalI].getText());
                    hidePopUp();
                    itemChanged();
                }
            });
            popUpPanel.add(popUpButtons[i]);
            popUpPanel.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    showPopUp();
                }

                @Override
                public void focusLost(FocusEvent e) {
                    hidePopUp();
                }
            });
        }
    }

    public Object getSelectedItem() {
        return selectedItem;
    }

    public abstract void itemChanged();

    public void hidePopUp() {
        this.popUpPanel.setVisible(false);
        this.popUpIsShowing = false;
        this.getRootPane().repaint();
    }

    public void showPopUp() {
        this.popUpPanel.setVisible(true);
        this.popUpPanel.requestFocus();
        this.popUpIsShowing = true;
        this.getRootPane().repaint();
    }

    public void changeTheme() {
        comboBoxButton.changeTheme();
        for (Button button : popUpButtons) {
            button.changeTheme();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(new Color(0, 0, 0, 0));
        graphics2D.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
    }
}

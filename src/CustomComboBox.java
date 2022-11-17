import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

public class CustomComboBox extends JPanel {
    private final int COMBO_BOX_BUTTON_WIDTH = 250;
    private final int COMBO_BOX_BUTTON_HEIGHT = 60;
    private final int POPUP_BUTTON_WIDTH = 250;
    private final int POPUP_BUTTON_HEIGHT = 40;
    private Button[] popUpButtons;
    private Button comboBoxButton;
    private boolean popUpIsShowing = false;


    public CustomComboBox(Object[] items) {
        this.setLayout(null);
        this.setOpaque(true);
        popUpButtons = new Button[items.length];
        comboBoxButton = new Button(items[0].toString()) {
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
                popUpIsShowing = !popUpIsShowing;
                showPopUp(popUpIsShowing);
            }
        });


        this.setPreferredSize(new Dimension(COMBO_BOX_BUTTON_WIDTH, COMBO_BOX_BUTTON_HEIGHT + (items.length) * POPUP_BUTTON_HEIGHT));
        this.add(comboBoxButton);
        for (int i = 0; i < items.length; i++) {
            popUpButtons[i] = new Button(items[i].toString());
            popUpButtons[i].setBounds(0, COMBO_BOX_BUTTON_HEIGHT + i * POPUP_BUTTON_HEIGHT, POPUP_BUTTON_WIDTH, POPUP_BUTTON_HEIGHT);
            popUpButtons[i].setVisible(false);
            int finalI = i;
            popUpButtons[i].addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    comboBoxButton.setText(popUpButtons[finalI].getText());
                    popUpIsShowing = false;
                    showPopUp(false);
                    MainPanel.mode = (Modes)items[finalI];
                }
            });
            this.add(popUpButtons[i]);
        }
    }

    public void showPopUp(boolean popUpIsShowing) {
        for (Button button : popUpButtons) {
            button.setVisible(popUpIsShowing);
        }
        this.getRootPane().repaint();
    }

    public void changeTheme(){
        comboBoxButton.changeTheme();
        for (Button button:popUpButtons){
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

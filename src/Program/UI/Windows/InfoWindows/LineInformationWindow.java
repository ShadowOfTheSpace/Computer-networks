package Program.UI.Windows.InfoWindows;

import Program.Elements.Line;
import Program.UI.Elements.Buttons.Button;
import Program.UI.Elements.Label.MenuPanelLabel;
import Program.UI.Elements.Panels.SubMenuPanel;
import Program.UI.Elements.TextField.LineLengthTextField;
import Program.UI.Windows.MainWindow.Window;
import Program.UI.Windows.Panels.MainPanel;

import javax.swing.*;
import java.awt.*;

public class LineInformationWindow extends InfoWindow {
    private static final int WIDTH = 300, HEIGHT = 145;
    private final SubMenuPanel infoPanel = new SubMenuPanel(true) {
        @Override
        public float getAlpha() {
            return topBar.getAlpha();
        }
    };
    private final LineLengthTextField textField;
    private final Button submitButton;

    public LineInformationWindow(Line line) {
        super(WIDTH, HEIGHT, "Line info", Window.getMainPanel(), line.getMiddlePoint());
        textField = new LineLengthTextField("" + line.getLength(), MainPanel.getMetric());

        submitButton = new Button("OK", false) {
            @Override
            public void paint(Graphics g) {
                Graphics2D graphics2D = (Graphics2D) g;
                graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, topBar.getAlpha()));
                super.paint(g);
            }
        };
        submitButton.addActionListener(e -> {
            if (!textField.getText().isEmpty()) {
                int newLength = Integer.parseInt(textField.getText());
                if (newLength > 0 && newLength <= Line.MAX_LENGTH && textField.getText().charAt(0) != '0') {
                    line.setLength(newLength);
                    Window.getMainPanel().repaint();
                    closeWindow();
                }
            }
        });

        SubMenuPanel upperPanel = new SubMenuPanel(new GridBagLayout());
        SubMenuPanel middlePanel = new SubMenuPanel(new GridBagLayout());
        SubMenuPanel lowerPanel = new SubMenuPanel(new GridBagLayout());


        submitButton.setPreferredSize(new Dimension(100, 40));
        upperPanel.add(new MenuPanelLabel("Length " + line.getElementName(), JLabel.CENTER, JLabel.CENTER));
        middlePanel.add(textField);
        lowerPanel.add(submitButton);


        infoPanel.setLayout(new GridLayout(3, 1));
        infoPanel.add(upperPanel);
        infoPanel.add(middlePanel);
        infoPanel.add(lowerPanel);
    }

    @Override
    public Component getComponentForFocusListener() {
        return this.textField;
    }

    @Override
    public SubMenuPanel getInfoPanel() {
        return this.infoPanel;
    }

    @Override
    public void changeTheme() {
        submitButton.changeTheme();
        textField.repaint();
        super.changeTheme();
    }

    @Override
    public void closeWindow() {
        super.closeWindow();
        Window.getLineInfoWindows().remove(this);
    }
}

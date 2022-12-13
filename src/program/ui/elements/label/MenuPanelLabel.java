package program.ui.elements.label;

import program.ui.colors.Palette;

import javax.swing.*;
import java.awt.*;

public class MenuPanelLabel extends JLabel {
    public MenuPanelLabel(String text, int verticalPosition, int horizontalPosition) {
        super(text, JLabel.CENTER);
        this.setVerticalAlignment(verticalPosition);
        this.setHorizontalAlignment(horizontalPosition);
        this.setFont(new Font("Arial", Font.BOLD, 24));
        this.setBorder(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        this.setForeground(Palette.getFontColor());
        super.paintComponent(g);
    }
}

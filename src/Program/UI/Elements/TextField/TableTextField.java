package Program.UI.Elements.TextField;

import Program.UI.Colors.Palette;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class TableTextField extends JTextField {

    public TableTextField(String text,
                          boolean isTitleColumn,
                          int currentColumn,
                          int currentRow,
                          int[] width,
                          int height,
                          Font columnNameFont,
                          Font columnFont) {
        super(text);
        if (isTitleColumn) {
            this.setFont(columnNameFont);
        } else {
            this.setFont(columnFont);
        }
        this.setBorder(null);
        this.setFocusable(false);
        this.setEditable(false);
        if (currentRow == 0 || currentColumn == 1 || currentColumn == 2) {
            this.setHorizontalAlignment(JTextField.CENTER);
        } else {
            this.setHorizontalAlignment(JTextField.LEFT);
        }
        int thickness = 1;
        int x = Arrays.stream(width).limit(currentColumn).sum() + currentColumn * thickness;
        int y = currentRow * (height + thickness);
        this.setBounds(x, y, width[currentColumn], 40);
    }

    @Override
    public void paint(Graphics g) {
        this.setBackground(Palette.getMenuPanelBackground());
        this.setForeground(Palette.getFontColor());
        super.paint(g);
    }
}

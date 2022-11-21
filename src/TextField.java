import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TextField extends JTextField {
    public TextField(String text, Metric metric) {
        super(text);
        if (metric == Metric.HOPS || metric == Metric.DISTANCE) {
            this.setEditable(false);
        } else {
            this.selectAll();
        }
        this.setPreferredSize(new Dimension(100, 40));
        this.setFont(new Font("Arial", Font.BOLD, 24));
        this.setBorder(null);
        this.setHorizontalAlignment(JTextField.CENTER);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                } else {
                    if (!getText().isEmpty()) {
                        int number = Integer.parseInt(getText() + e.getKeyChar());
                        if (number > Line.MAX_LENGTH || number < 1) {
                            e.consume();
                        }
                    } else {
                        if (Integer.parseInt(String.valueOf(e.getKeyChar())) == 0) {
                            e.consume();
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        this.setBackground(Palette.getButtonPressBackground());
        this.setForeground(Palette.getFontColor());
        this.setCaretColor(Palette.getFontColor());
        super.paintComponent(g);
    }
}

package Program.UI.Elements.TopBar;

import Program.UI.Elements.Borders.RoundedCornerBorderForTopBar;
import Program.UI.Elements.Buttons.BarButton;
import Program.UI.Elements.Buttons.Button;
import Program.UI.Windows.InfoWindows.LineInfoWindow;
import Program.UI.Colors.Palette;
import Program.UI.Windows.MainWindow.Window;

import javax.swing.*;
import java.awt.*;

public class TopBar extends JPanel {
    private final int barWidth;
    private final int barHeight = 20;
    private final Button exitButton;
    private final Button minimizeButton;
    private String title;
    private float alpha = 1;

    public TopBar(int barWidth, String title) {
        this.title = title;
        this.barWidth = barWidth;
        this.setBorder(new RoundedCornerBorderForTopBar());
        this.setPreferredSize(new Dimension(barWidth, barHeight));
        exitButton = new BarButton("×",true){
            @Override
            public float getAlpha() {
                return alpha;
            }
        };
        exitButton.addActionListener(e -> {
            Window.getLineInfoWindows().remove((LineInfoWindow) (SwingUtilities.getWindowAncestor(this)));
            SwingUtilities.getWindowAncestor(this).dispose();
        });
        minimizeButton = new BarButton("-", false){

            @Override
            public float getAlpha() {
                return alpha;
            }
        };
        minimizeButton.addActionListener(e -> ((JFrame) SwingUtilities.getWindowAncestor(this)).setState(JFrame.ICONIFIED));

        exitButton.setBounds(barWidth - (2 * barHeight) - 3, 2, 2 * barHeight, 2 * barHeight - 10);
        minimizeButton.setBounds(barWidth - 4 * barHeight - 3, 2, 2 * barHeight, 2 * barHeight - 10);
        this.setLayout(null);
        this.add(exitButton);
        this.add(minimizeButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(Palette.getButtonPressBackground());
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        graphics2D.fillRect(0, 0, getWidth(), getHeight());
        graphics2D.setFont(new Font("Arial", Font.BOLD, 17));
        graphics2D.setColor(Palette.getFontColor());
        FontMetrics fm = getFontMetrics(getFont());
        int textY = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
        graphics2D.drawString(title, 15, textY);
    }

    public void changeTheme() {
        exitButton.changeTheme();
        minimizeButton.changeTheme();
        repaint();
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
}

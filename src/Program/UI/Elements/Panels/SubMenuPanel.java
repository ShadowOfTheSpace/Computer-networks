package Program.UI.Elements.Panels;

import Program.UI.Colors.Palette;
import Program.UI.Windows.Panels.MenuPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class SubMenuPanel extends JPanel {
    private boolean transparency;
    public SubMenuPanel(boolean transparency) {
        super();
        this.transparency = transparency;
    }

    public SubMenuPanel(LayoutManager layoutManager) {
        this.setLayout(layoutManager);
        this.setBorder(null);
        this.transparency = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        if(transparency){
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.getAlpha()));
        }
//        graphics2D.setColor(new Color(new Random().nextInt(256),new Random().nextInt(256),new Random().nextInt(256)));
        graphics2D.setColor(Palette.getMenuPanelBackground());
        graphics2D.fillRect(0, 0, getWidth(), getHeight());
    }

    public float getAlpha(){
        return 1f;
    }
}

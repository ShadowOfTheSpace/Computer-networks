package Program.UI.Windows.InfoWindows;

import Program.Elements.Node;
import Program.Elements.RoutingTable;
import Program.UI.Colors.Palette;
import Program.UI.Elements.Panels.SubMenuPanel;
import Program.UI.Elements.TextField.TableTextField;
import Program.UI.Windows.MainWindow.Window;

import java.awt.*;
import java.util.ArrayList;

public class NodeInfoWindow extends InfoWindow {
    private final static Font columnNameFont = new Font("Arial", Font.BOLD, 20);
    private final static Font columnFont = new Font("Arial", Font.BOLD, 17);
    private final static int columnHeight = 40;
    private final ArrayList<TableTextField> textFields = new ArrayList<>();
    private final SubMenuPanel infoPanel = new SubMenuPanel(true) {
        @Override
        public float getAlpha() {
            return topBar.getAlpha();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.setColor(Palette.getFontColor());
            graphics2D.fillRect(0, 0, getWidth(), getHeight());
        }
    };

    public NodeInfoWindow(Node node, RoutingTable routingTable) {
        super(
                routingTable.getTotalWidth(columnNameFont),
                routingTable.getTotalHeight(),
                "Routing table " + node.getElementName(),
                Window.getMainPanel(),
                node.getPoint());
        infoPanel.setLayout(null);
        ArrayList<ArrayList<String>> table = routingTable.getTable();
        int[] columnWidth = routingTable.getWidthOfEachColumn(columnNameFont);
        int rowsCount = routingTable.getRowsCount();
        int columnCount = routingTable.getColumnCount();
        for (int currentRow = 0; currentRow < rowsCount; currentRow++) {
            for (int currentColumn = 0; currentColumn < columnCount; currentColumn++) {
                TableTextField textField = new TableTextField(
                        table.get(currentColumn).get(currentRow),
                        currentRow == 0,
                        currentColumn,
                        currentRow,
                        columnWidth,
                        columnHeight,
                        columnNameFont,
                        columnFont);

                infoPanel.add(textField);
                textFields.add(textField);
            }
        }
    }


    @Override
    public Component getComponentForFocusListener() {
        return this.topBar;
    }

    @Override
    public SubMenuPanel getInfoPanel() {
        return this.infoPanel;
    }

    @Override
    public void changeTheme() {
        infoPanel.repaint();
        textFields.forEach(Component::repaint);
        super.changeTheme();
    }

    @Override
    public void closeWindow() {
        super.closeWindow();
        Window.getNodeInfoWindows().remove(this);
    }
}

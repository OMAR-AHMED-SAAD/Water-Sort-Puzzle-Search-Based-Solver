package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class WaterSortVisualizer extends JPanel {

    private final List<Node> pathToGoal;  // List to store the path from root to the final node
    private Node currentNode;  // The current node to be visualized
    private int currentIndex = 0;  // Track the current index in the path
    private JButton nextButton;
    private JButton previousButton;

    public WaterSortVisualizer(Node finalNode) {
        this.pathToGoal = getPathToGoal(finalNode);  // Calculate the path from root to final node
        this.currentNode = pathToGoal.get(0);  // Start with the first node in the path (root)
        initializeUI();
    }

    private List<Node> getPathToGoal(Node finalNode) {
        List<Node> path = new ArrayList<>();
        Node current = finalNode;

        // Traverse from the final node to the root
        while (current != null) {
            path.add(0, current);  // Insert at the beginning to reverse the order
            current = current.parent;
        }

        return path;
    }

    // Initialize the UI with buttons
    public void initializeUI() {
        JFrame frame = new JFrame("WaterSort Path to Goal Visualization");

        // Maximize the window but keep the title bar (so the window is resizable and has minimize/close buttons)
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);  // Maximize window

        frame.setLayout(new BorderLayout());  // Use BorderLayout for proper button placement

        // Add the visualizer panel (center)
        frame.add(this, BorderLayout.CENTER);

        // Create a panel for buttons (bottom)
        JPanel buttonPanel = new JPanel();
        nextButton = new JButton("Next");
        previousButton = new JButton("Previous");

        // Disable the previous button initially (since we start with the first node)
        previousButton.setEnabled(false);

        // Add action listeners for buttons
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex < pathToGoal.size() - 1) {
                    currentIndex++;
                    updateNode(pathToGoal.get(currentIndex));
                    previousButton.setEnabled(true);  // Enable the previous button once we move forward
                    if (currentIndex == pathToGoal.size() - 1) {
                        nextButton.setEnabled(false);  // Disable next button at the last node
                    }
                }
            }
        });

        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex > 0) {
                    currentIndex--;
                    updateNode(pathToGoal.get(currentIndex));
                    nextButton.setEnabled(true);  // Enable the next button when moving back
                    if (currentIndex == 0) {
                        previousButton.setEnabled(false);  // Disable previous button at the first node
                    }
                }
            }
        });

        // Add buttons to the button panel
        buttonPanel.add(previousButton);
        buttonPanel.add(nextButton);

        // Add the button panel to the bottom of the frame
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Allow the close button to work
        frame.setVisible(true);
    }

    // Method to update the currently displayed node (state, cost, depth, parent)
    public void updateNode(Node newNode) {
        this.currentNode = newNode;
        repaint();  // Repaint the panel to reflect the updated node
    }

    // Paint the current state


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Get the current dimensions of the screen (panel)
        int screenWidth = getWidth();
        int screenHeight = getHeight();

        if (currentNode != null) {

            int ellipseWidth = screenWidth / 2;
            int ellipseHeight = screenHeight / 4;

            // Calculate positions for parent and current nodes
            int currentX = (screenWidth / 2) - (ellipseWidth / 2);  // Center the current node horizontally
            int currentY = (screenHeight / 2);  // Current node below the parent

            int parentX = (screenWidth / 2) - (ellipseWidth / 2);  // Center the parent node horizontally
            int parentY = (screenHeight / 2) - (ellipseHeight + 50);  // Parent node above the current node

            // Draw the parent node if available
            if (currentNode.parent != null) {
                drawNodeCircle(g, currentNode.parent, parentX, parentY, ellipseWidth, ellipseHeight);
                g.drawString("Parent", parentX - ellipseWidth - 10, parentY + ellipseHeight / 2);  // Label the parent node
            }

            // Draw the current node
            drawNodeCircle(g, currentNode, currentX, currentY, ellipseWidth, ellipseHeight);
            g.drawString("Current", currentX - ellipseWidth - 10, currentY + ellipseHeight / 2);  // Label the current node

            // Draw a line connecting the parent and current node
            if (currentNode.parent != null) {
                g.setColor(Color.BLACK);
                g.drawLine(parentX + ellipseWidth / 2, parentY + ellipseHeight, currentX + ellipseWidth / 2, currentY);
            }
        }
    }

    // Method to draw an ellipse representing a node and the state inside (from before)
    private void drawNodeCircle(Graphics g, Node node, int x, int y, int ellipseWidth, int ellipseHeight) {
        // Draw the ellipse for the node
        g.setColor(Color.BLACK);
        g.drawOval(x, y, ellipseWidth, ellipseHeight);

        // Center the bottles inside the ellipse
        int numBottles = node.state.bottles.length;
        int bottleWidth = 50;  // Fixed width for each bottle
        int bottleHeight = 100;  // Fixed height for each bottle

        // Calculate spacing between bottles inside the ellipse
        int bottleSpacing = (ellipseWidth - numBottles * bottleWidth) / (numBottles + 1);

        // Draw the bottles inside the ellipse
        int bottleStartX = x + bottleSpacing;  // Start drawing bottles with some padding from the left
        int bottleY = y + (ellipseHeight / 2) - (bottleHeight / 2);  // Center the bottles vertically in the ellipse

        for (int i = 0; i < numBottles; i++) {
            Bottle bottle = node.state.bottles[i];
            drawBottle(g, bottleStartX + i * (bottleWidth + bottleSpacing), bottleY, bottle);
        }

        // Draw node information (cost, depth, operator) next to the ellipse
        drawNodeInfo(g, node, x + ellipseWidth + 20, y + 40);  // Adjust position to the right of the ellipse
    }

    // Method to draw a single bottle with water layers
    private void drawBottle(Graphics g, int x, int y, Bottle bottle) {
        int bottleHeight = 100;  // Height of the bottle
        int bottleWidth = 50;    // Width of the bottle
        int waterHeight = bottleHeight / bottle.layers.length;  // Height of each water layer

        // Draw the bottle (rectangle)
        g.setColor(Color.BLACK);
        g.drawRect(x, y, bottleWidth, bottleHeight);

        // Draw water layers inside the bottle from top to bottom
        for (int i = 0; i < bottle.layers.length; i++) {
            char layer = bottle.layers[i];
            Color color = mapLayerToColor(layer);  // Map character to color
            g.setColor(color);
            if (layer != 'e') {  // Only fill if the layer is not empty
                g.fillRect(x, y + i * waterHeight, bottleWidth, waterHeight);
            }
        }
    }

    // Method to display node information: cost, depth, and operator (action taken)
    private void drawNodeInfo(Graphics g, Node node, int x, int y) {
        // Set a larger, clearer font
        g.setFont(new Font("Arial", Font.BOLD, 24));

        // Display cost, depth, and operator info outside the ellipse
        g.setColor(Color.BLACK);
        g.drawString("Cost: " + node.cost, x, y);
        g.drawString("Depth: " + node.depth, x, y + 20);
        if (node.operator != null) {
            g.drawString("Operator: " + node.operator, x, y + 40);
        }
    }

    // Method to map a bottle layer to a color
    private Color mapLayerToColor(char layer) {
        switch (layer) {
            case 'r':
                return Color.RED;
            case 'y':
                return Color.YELLOW;
            case 'b':
                return Color.BLUE;
            case 'g':
                return Color.GREEN;
            case 'e':
                return Color.WHITE;  // 'e' represents empty
            default:
                return Color.GRAY;   // For any undefined layers
        }
    }

}

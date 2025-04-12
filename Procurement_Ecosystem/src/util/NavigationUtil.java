package util;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

/**
 * NavigationUtil is a helper utility designed to manage panel navigation using a {@link CardLayout}.
 * <p>
 * It keeps track of panel navigation history using a stack structure, allowing you to:
 * <ul>
 *   <li>Switch between panels by name</li>
 *   <li>Go back to the previously visited panel (like a "Back" button)</li>
 *   <li>Ensure panels are not duplicated in the layout</li>
 * </ul>
 *
 * <p>This utility is useful for Swing applications that use {@code CardLayout} to simulate
 * multi-page workflows or dynamic screen transitions.
 *
 * <h3>Example usage:</h3>
 * <pre>{@code
 * JPanel mainPanel = new JPanel(new CardLayout());
 * NavigationUtil nav = new NavigationUtil(mainPanel);
 *
 * nav.showCard(homePanel, "Home");
 * nav.showCard(settingsPanel, "Settings");
 * nav.goBack(); // Returns to "Home"
 * }</pre>
 *
 * @author tisaac
 */
public class NavigationUtil {
    private final JPanel workArea;
    private final Stack<String> history; // Stores visited panels

    public NavigationUtil(JPanel workArea) {
        this.workArea = workArea;
        this.history = new Stack<>();
    }

    /**
     * Displays the specified panel in the CardLayout and tracks the navigation history.
     *
     * @param panel the JPanel to display
     * @param name  the unique identifier for this panel in the CardLayout
     */
    public void showCard(JPanel panel, String name) {
        // Add to history only if the last visited panel is different
        if (history.isEmpty() || !history.peek().equals(name)) {
            history.push(name);
        }

        // Check if the panel already exists
        if (!panelExists(name)) {
            workArea.add(name, panel);
        }

        // Force CardLayout to refresh
        workArea.revalidate();  // Ensures new components are recognized
        workArea.repaint();     // Forces UI to redraw

//        System.out.println("show card stack size: " + history.size());

        // Show the requested panel
        CardLayout ly = (CardLayout) workArea.getLayout();
        ly.show(workArea, name);
    }

    /**
     * Navigates back to the previously visited panel, if available.
     * <p>
     * Does nothing if there is only one panel in the history.
     * Logs an error message if no previous panel is found.
     */
    public void goBack() {
//        System.out.println("stack size: " + history.size());
        if (history.size() > 1) {
            history.pop();
            String prePanel = history.peek();

            workArea.revalidate();
            workArea.repaint();

            CardLayout ly = (CardLayout) workArea.getLayout();
            ly.show(workArea, prePanel);
        } else {
            System.out.println("sth goes wrong, no previous panel found");
        }
    }

    /**
     * Checks whether a panel with the given name already exists in the CardLayout.
     *
     * @param name the name of the panel to check
     * @return true if the panel exists, false otherwise
     */
    private boolean panelExists(String name) {
        for (java.awt.Component comp : workArea.getComponents()) {
            if (name.equals(comp.getName())) {
                return true;
            }
        }
        return false;
    }
}

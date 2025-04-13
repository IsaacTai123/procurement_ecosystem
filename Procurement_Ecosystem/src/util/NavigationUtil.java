package util;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

/**
 * {@code NavigationUtil} is a singleton helper utility for managing panel navigation in Swing applications using {@link CardLayout}.
 * <p>
 * It maintains a navigation history using a stack structure, enabling features like:
 * <ul>
 *   <li>Switching between panels by name</li>
 *   <li>Returning to the previous panel (simulating a "Back" button)</li>
 *   <li>Preventing duplicate panels from being added to the layout</li>
 * </ul>
 * <p>
 * This utility is ideal for multi-page workflows where panels are dynamically displayed using {@code CardLayout}.
 *
 * <h3>Usage Example:</h3>
 * <pre>{@code
 * // Initialize once in your main frame
 * NavigationUtil.init(mainPanel);
 *
 * // Retrieve and use anywhere in your app
 * NavigationUtil nav = NavigationUtil.getInstance();
 * nav.showCard(homePanel, "Home");
 * nav.showCard(settingsPanel, "Settings");
 * nav.goBack(); // Returns to "Home"
 * }</pre>
 *
 * <p><strong>Note:</strong> {@code init()} must be called once before calling {@code getInstance()}.
 * Otherwise, {@code getInstance()} will return {@code null} or throw an error if guarded.
 *
 * @author tisaac
 */
public class NavigationUtil {
    private static NavigationUtil instance;
    private final JPanel workArea;
    private final Stack<String> history; // Stores visited panels

    private NavigationUtil(JPanel workArea) {
        this.workArea = workArea;
        this.history = new Stack<>();
    }

    public static void init(JPanel workArea) {
        if (instance == null) {
            instance = new NavigationUtil(workArea);
        }
    }

    public static NavigationUtil getInstance() {
        return instance;
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

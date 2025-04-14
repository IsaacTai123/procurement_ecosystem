package util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.function.Function;
import java.util.List;

/**
 * @author tisaac
 */
public class UIUtil {

    /**
     * Clears all rows from the specified JTable while keeping the column structure intact.
     *
     * @param table the JTable to clear
     */
    public static void clearTable(JTable... tables) {
        for (JTable t : tables) {
            DefaultTableModel model = (DefaultTableModel) t.getModel();
            model.setRowCount(0);
        }
    }

    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showInfo(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void clearTextFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }

    public static void clearPasswordFields(JPasswordField... fields) {
        for (JPasswordField field : fields) {
            field.setText("");
        }
    }

    public static void setEnabled(boolean enabled, JComponent... components) {
        for (JComponent comp : components) {
            comp.setEnabled(enabled);
        }
    }

    /**
     * Populates a JComboBox with items from a data list.
     *
     * @param <T>            the type of the item in the list
     * @param comboBox       the JComboBox to populate
     * @param dataList       the list of data items
     * @param labelMapper    function to convert each item into a display string
     * @param placeholder    an optional first item to show as placeholder (can be null to skip)
     */
    public static <T> void populateComboBox(JComboBox<String> comboBox, List<T> dataList,
                                            Function<T, String> labelMapper, String placeholder) {
        comboBox.removeAllItems();

        if (placeholder != null) {
            comboBox.addItem(placeholder);
        }

        dataList.stream()
                .map(labelMapper)
                .forEach(comboBox::addItem);
    }

    /**
     * Reloads a JTable with new data.
     *
     * @param <T>         the type of data item
     * @param table       the JTable to reload
     * @param dataList    the list of data items to display
     * @param rowMapper   a function that maps each data item to a table row (Object[])
     */
    public static <T> void reloadTable(JTable table, List<T> dataList, Function<T, Object[]> rowMapper) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear existing rows

        dataList.stream()
                .map(rowMapper)
                .forEach(model::addRow);
    }

    /**
     * Converts the selected item in a JComboBox<String> into the corresponding enum value.
     *
     * @param <E>        the enum type
     * @param comboBox   the JComboBox containing string values of the enum
     * @param enumClass  the class of the enum to convert to
     * @return the matching enum constant, or {@code null} if the selection is empty
     * @throws RuntimeException if the selected string does not match any enum constant
     */
    public static <E extends Enum<E>> E getSelectedEnum(JComboBox<String> comboBox, Class<E> enumClass) {
        String selected = (String) comboBox.getSelectedItem();
        if (selected == null || selected.isBlank()) {
            return null;
        }

        try {
            return Enum.valueOf(enumClass, selected);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid enum value: " + selected);
        }
    }

    /**
     * Populates a JComboBox<String> with the names of all constants from the specified enum type.
     * An optional placeholder item can be added at the top of the list.
     *
     * @param <E>         the enum type
     * @param comboBox    the JComboBox to populate
     * @param enumClass   the class of the enum whose constants will be listed
     * @param placeholder an optional label (e.g., "-- Select --") shown as the first item;
     *                    pass {@code null} to skip adding a placeholder
     */
    public static <E extends Enum<E>> void populateComboBoxFromEnum(
            JComboBox<String> comboBox,
            Class<E> enumClass,
            String placeholder) {

        comboBox.removeAllItems();

        if (placeholder != null) {
            comboBox.addItem(placeholder);
        }

        for (E e : enumClass.getEnumConstants()) {
            comboBox.addItem(e.name());
        }
    }

    /**
     * Resets the selection of one or more JComboBox components to their first item (index 0).
     * <p>
     * This is useful when clearing or reinitializing forms where combo boxes need to be reset
     * to their default or placeholder option.
     *
     * @param comboBoxes one or more JComboBox components to reset
     */
    public static void resetComboBoxes(JComboBox<?>... comboBoxes) {
        for (JComboBox<?> box : comboBoxes) {
            if (box.getItemCount() > 0) {
                box.setSelectedIndex(0); // Set to the first item
            }
        }
    }
}

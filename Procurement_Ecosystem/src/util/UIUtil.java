package util;

import common.Result;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import javax.swing.text.html.Option;
import java.awt.*;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
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

    public static void setTextFields(Map<JTextComponent, String> fieldValueMap) {
        for (Map.Entry<JTextComponent, String> entry : fieldValueMap.entrySet()) {
            if (entry.getKey() != null) {
                entry.getKey().setText(entry.getValue());
            }
        }
    }

    public static void setLabelText(Map<JLabel, String> labelMap) {
        for (var entry : labelMap.entrySet()) {
            if (entry.getKey() != null) {
                entry.getKey().setText(entry.getValue());
            }
        }
    }

    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showWarning(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    public static void showInfo(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void clearTextComponents(JTextComponent... components) {
        for (JTextComponent component : components) {
            component.setText("");
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

    private static void setComponentVisibility(boolean visible, JComponent... components) {
        for (JComponent comp : components) {
            if (comp != null) {
                comp.setVisible(visible);
            }
        }
    }

    public static void show(JComponent... components) {
        setComponentVisibility(true, components);
    }

    public static void hide(JComponent... components) {
        setComponentVisibility(false, components);
    }

    public static void setEnterpriseTitle(JLabel lb, String enterpriseName) {
        if (lb == null) return;
        String safeEnterpriseName = enterpriseName == null ? "" : enterpriseName;
        String safeTitle = lb.getText() == null ? "" : lb.getText();
        lb.setText(safeEnterpriseName + ": " + safeTitle);
    }

    /**
     * Updates the text of a JLabel with flexible append or prepend options.
     *
     * @param label        the JLabel to update
     * @param newText      the text to add
     * @param mode         how the new text should be applied (prepend, append, or replace)
     * @param separator    optional separator (e.g., ": ", " - ") used in append/prepend mode
     */
    public static void adjustLabelText(JLabel label, String newText, AppendMode mode, String separator) {
        if (label == null) return;

        String safeNewText = newText == null ? "" : newText;
        String currentText = label.getText() == null ? "" : label.getText();
        String sep = separator == null ? "" : separator;

        switch (mode) {
            case PREPEND -> label.setText(safeNewText + sep + currentText);
            case APPEND -> label.setText(currentText + sep + safeNewText);
            case REPLACE -> label.setText(safeNewText);
        }
    }

    public enum AppendMode {
        PREPEND,
        APPEND,
        REPLACE
    }

    public static void toggleComponentsEnabled(JComponent... components) {
        for (JComponent comp : components) {
            if (comp != null) {
                comp.setEnabled(!comp.isEnabled());
            }
        }
    }

    public static void toggleComponentsEnabled(boolean enable, JComponent... components) {
        for (JComponent comp : components) {
            if (comp != null) {
                comp.setEnabled(enable);
            }
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

    /**
     * Retrieves the value of a selected row from a specified column in a JTable.
     * <p>
     * If no row is selected, or if the selected row has a null value in the given column,
     * an informational dialog is shown and {@link Optional#empty()} is returned.
     *
     * @param table        The JTable to retrieve the value from.
     * @param columnIndex  The index of the column from which to extract the value.
     * @param parent       The parent component for displaying the information dialog.
     * @param infoMessage  The message to display if no row is selected.
     * @return An {@link Optional} containing the string value from the selected cell,
     *         or {@link Optional#empty()} if the row is not selected or the cell is null.
     */
    public static Optional<String> getSelectedTableValue(JTable table, int columnIndex, Component parent, String infoMessage) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            showWarning(parent, infoMessage);
            return Optional.empty();
        }
        Object value = table.getValueAt(selectedRow, columnIndex);
        if (value == null) {
            showWarning(parent, "Selected row has no value in column " + columnIndex);
            return Optional.empty();
        }
        return Optional.of(value.toString());
    }

    /**
     * Retrieves the original object from a selected row and column in a JTable.
     * <p>
     * If no row is selected, or the cell is null, an informational dialog is shown and {@link Optional#empty()} is returned.
     *
     * @param table       The JTable to retrieve the object from.
     * @param columnIndex The column index to extract the value from.
     * @param parent      The parent component to show the dialog.
     * @param infoMessage The message to show if nothing is selected.
     * @param <T>         The expected type of the object.
     * @return An {@link Optional} containing the object, or empty if not found or invalid.
     */
    public static <T> Optional<T> getSelectedTableObject(JTable table, int columnIndex, Class<T> clazz, Component parent, String infoMessage) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            showWarning(parent, infoMessage);
            return Optional.empty();
        }
        Object value = table.getValueAt(selectedRow, columnIndex);
        if (value == null) {
            showWarning(parent, "Selected row has no value in column " + columnIndex);
            return Optional.empty();
        }

        if (clazz.isInstance(value)) {
            return Optional.of(clazz.cast(value));
        } else {
            showError(parent, "Unexpted type in selected cell. Expected: " + clazz.getSimpleName() + ", but got: " + value.getClass().getSimpleName());
            return Optional.empty();
        }
    }

    /**
     * Retrieves the selected object from a JTable and performs a specified action if the selection is valid.
     * <p>
     * This method builds upon {@link #getSelectedTableObject(JTable, int, Class, Component, String)} and simplifies
     * the flow where an operation (represented as a {@link Function}) is performed on the selected object.
     * If no valid selection is made, a failure {@link Result} is returned and the action is not executed.
     *
     * @param table         The JTable containing selectable rows.
     * @param columnIndex   The column index from which to retrieve the object.
     * @param clazz         The expected class type of the object.
     * @param parent        The parent component for displaying dialogs.
     * @param warningMessage The message to show if no valid selection is made.
     * @param action        A function that takes the selected object and returns a {@link Result} indicating the outcome.
     * @param <T>           The type of the object expected from the table.
     * @return A {@link Result} representing the outcome of the operation, or a failure result if no valid selection is found.
     */
    public static <T> Result<Void> withSelectedTableObject(JTable table, int columnIndex, Class<T> clazz, Component parent, String warningMessage, Function<T, Result<Void>> action) {
        Optional<T> selected = getSelectedTableObject(table, columnIndex, clazz, parent, warningMessage);
        return selected.map(action)
                .orElse(ResultUtil.failure("No valid selection"));
    }

    /**
     * Registers an ActionListener to a JComboBox that triggers when a user selects an item.
     * The selected item is passed to the given Consumer as a String.
     *
     * @param comboBox The JComboBox to attach the listener to.
     * @param onSelect A Consumer that handles the selected item string.
     */
    public static void registerComboBoxListener(JComboBox<?> comboBox, Consumer<String> onSelect) {
        comboBox.addActionListener(e -> {
            Object selectedItem = comboBox.getSelectedItem();
            if (selectedItem != null) {
                onSelect.accept(selectedItem.toString());
            }
        });
    }
}

package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ComboBoxEditor;
import javax.swing.JTextField;

/**
 * Custom editor.
 */
public class CustomEditor implements ComboBoxEditor, ActionListener {
    private final JTextField editorComponent;
    private Object currentItem;
    private ActionListener actionListener;

    public CustomEditor() {
        final int textFieldColumns = 20;
        this.editorComponent = new JTextField(textFieldColumns);
        editorComponent.setBackground(Color.WHITE);
        editorComponent.addActionListener(this);
    }

    @Override
    public Component getEditorComponent() {
        return editorComponent;
    }

    @Override
    public void setItem(Object anObject) {
        currentItem = anObject;
        // Display the item in the text field
        if (anObject != null) {
            editorComponent.setText(anObject.toString());
        }
        else {
            editorComponent.setText("");
        }
    }

    @Override
    public Object getItem() {
        // Return the text field's content as the new item
        return editorComponent.getText();
    }

    @Override
    public void selectAll() {

    }

    @Override
    public void addActionListener(ActionListener l) {
        this.actionListener = l;
    }

    @Override
    public void removeActionListener(ActionListener l) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

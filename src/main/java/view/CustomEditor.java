package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomEditor implements ComboBoxEditor, ActionListener{
    private final JTextField editorComponent;
    private Object currentItem;
    private ActionListener actionListener;

    public CustomEditor() {
        this.editorComponent = new JTextField(20);
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
        editorComponent.setText(anObject != null ? anObject.toString() : "");
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

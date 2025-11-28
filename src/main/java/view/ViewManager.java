package view;

import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.*;

public class ViewManager extends JPanel implements PropertyChangeListener {

    private final CardLayout cardLayout;
    private final ViewManagerModel viewManagerModel;

    public ViewManager(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
        this.viewManagerModel.addPropertyChangeListener(this);

        this.cardLayout = new CardLayout();
        setLayout(cardLayout);
        this.setPreferredSize(new Dimension(720, 500));
    }

    public void addView(JPanel view, String viewName) {
        add(view, viewName);
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String activeView = (String) evt.getNewValue();
        cardLayout.show(this, activeView);
        revalidate();
        repaint();
    }

}

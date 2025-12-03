package view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

/**
 * View manager to manage all the views.
 */
public class ViewManager extends JPanel implements PropertyChangeListener {

    private final CardLayout cardLayout;
    private final interface_adapter.ViewManagerModel viewManagerModel;

    public ViewManager(interface_adapter.ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
        this.viewManagerModel.addPropertyChangeListener(this);

        this.cardLayout = new CardLayout();
        setLayout(cardLayout);
        final Dimension viewSize = new Dimension(870, 550);
        this.setPreferredSize(viewSize);
    }

    /**
     * Adds a view to be managed.
     * @param view view to be added.
     * @param viewName view name of the view to be added.
     */
    public void addView(JPanel view, String viewName) {
        add(view, viewName);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final String activeView = (String) evt.getNewValue();
        cardLayout.show(this, activeView);
        revalidate();
        repaint();
    }

}

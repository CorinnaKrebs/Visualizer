package com.threedimensionalloadingcvrp.vrp;

import com.threedimensionalloadingcvrp.Presenter;
import javafx.scene.control.CheckBoxTreeItem;

/**
 * The Presenter for the Customer tree.
 */
public class CustomerTreePresenter extends Presenter {

    /** The view to control */
    private final CustomerTree view;

    /**
     * Instantiates a new Customer Tree presenter.
     *
     * @param view the view
     */
    public CustomerTreePresenter(final CustomerTree view) {
        this.view = view;
    }

    /**
     * Initializes the View.
     */
    public void init() {
        // Iterate through Tree
        for (var child : view.getRoot().getChildren()) {
            // Connect Visibility of Customer Pin with the CheckBox of the Tree
            if (child.getChildren().isEmpty()) {
                CheckBoxTreeItem<String> item = (CheckBoxTreeItem<String>) child;
                item.selectedProperty().addListener(event ->
                        model.getCustomerModel().getCustomerNode(Integer.parseInt(item.getValue())).getNode().setVisible(item.isSelected()));
            }
        }

    }
}

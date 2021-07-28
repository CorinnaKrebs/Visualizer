package com.threedimensionalloadingcvrp.vrp;

import com.threedimensionalloadingcvrp.Presenter;
import com.threedimensionalloadingcvrp.vrp.model.TourNode;
import javafx.scene.control.CheckBoxTreeItem;

/**
 * The type Tour tree presenter.
 */
public class TourTreePresenter extends Presenter {

    /** The view to control */
    private final TourTree view;

    /**
     * Instantiates a new Tour tree presenter.
     *
     * @param view the view
     */
    public TourTreePresenter(final TourTree view) {
        this.view = view;
    }

    /**
     * Initializes the View.
     */
    public void init() {
        // Update visibility of tree items.
        for (final var child : view.getRoot().getChildren()) {
            if (child.getChildren().isEmpty()) {
                CheckBoxTreeItem<String> item = (CheckBoxTreeItem<String>) child;
                int tourId = Integer.parseInt(item.getValue().substring(0, item.getValue().indexOf(":")));
                item.selectedProperty().addListener(event -> {
                    TourNode tourNode = model.getTourModel().getTourNode(tourId);
                    tourNode.getNode().setVisible(item.isSelected());
                    for (final var node : tourNode.getPaths()) {
                        node.setVisible(item.isSelected());
                    }
                });
            }
        }
    }
}

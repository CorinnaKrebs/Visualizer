package com.threedimensionalloadingcvrp.vrp;

import com.threedimensionalloadingcvrp.validator.model.Solution;
import com.threedimensionalloadingcvrp.validator.model.Tour;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import lombok.Getter;

/**
 * The Tour tree.
 */
@Getter
public class TourTree extends TreeView<String> {

    /**
     * Instantiates a new Tour Tree.
     *
     * @param solution the solution Data
     */
    public TourTree(final Solution solution) {
        super();

        // Create Root Node
        final CheckBoxTreeItem<String> root = new CheckBoxTreeItem<>("Tours");
        root.setExpanded(true);
        root.setSelected(true);

        // Create and Add Sub Nodes (Customers)
        for (final Tour tour : solution.getTours()) {
            CheckBoxTreeItem<String> item = new CheckBoxTreeItem<>(tour.getId() + ":" + tour.getCustomer_ids().toString());
            item.setExpanded(true);
            item.setSelected(true);
            root.getChildren().add(item);
        }

        setRoot(root);
        setCellFactory(CheckBoxTreeCell.forTreeView());
        setPrefHeight(100);
    }
}

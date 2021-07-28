package com.threedimensionalloadingcvrp.vrp;

import com.threedimensionalloadingcvrp.validator.model.Instance;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import lombok.Getter;

/**
 * The Customer tree.
 */
@Getter
public class CustomerTree extends TreeView<String> {

    /**
     * Instantiates a new Customer Tree.
     *
     * @param instance the instance Data
     */
    public CustomerTree(final Instance instance) {

        // Create Root Node
        final CheckBoxTreeItem<String> root = new CheckBoxTreeItem<>("Customers");
        root.setExpanded(false);
        root.setSelected(true);

        // Create and Add Sub Nodes (Customers)
        for (var customer : instance.getCustomers()) {
            CheckBoxTreeItem<String> item = new CheckBoxTreeItem<>(String.valueOf(customer.getId()));
            item.setSelected(true);
            root.getChildren().add(item);
        }

        setRoot(root);
        setCellFactory(CheckBoxTreeCell.forTreeView());
        setPrefHeight(200);
    }
}

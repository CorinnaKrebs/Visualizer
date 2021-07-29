package com.threedimensionalloadingcvrp.clp.model;

import com.threedimensionalloadingcvrp.Model;
import com.threedimensionalloadingcvrp.clp.Vehicle3D;
import com.threedimensionalloadingcvrp.validator.model.Item;
import com.threedimensionalloadingcvrp.validator.model.Tour;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import lombok.Data;

/**
 * The Item Model containing necessary information about items.
 */
@Data
public class ItemModel {

    /** HashMap linking TourId to List of Item Nodes */
    private final ObservableMap<Integer, ObservableList<ItemNode>> tourItemsMap;

    /** Current Index of the displayed Tour */
    private ObservableValue<Integer> currentIndex;

    /** The current Vehicle Group containing the Item Nodes */
    private Vehicle3D vehicle3D;

    /**
     * Instantiates the Item model and fills data.
     *
     * @param model the model
     */
    public ItemModel(final Model model) {
        // Create empty HashMap
        tourItemsMap = FXCollections.observableHashMap();

        // Fill HashMap and create Item Nodes
        for (final Tour tour : model.getSolution().getTours()) {
            ObservableList<ItemNode> list = FXCollections.observableArrayList();
            for (int i = 0; i < tour.getItem_ids().size(); ++i) {
                int itemId = tour.getItem_ids().get(i);
                Item item = model.getInstance().getItems().get(itemId);
                ItemNode node = new ItemNode(item, i, model.getCustomerColors().get(item.getCustomer_id()));
                list.add(node);
            }
            tourItemsMap.put(tour.getId(), list);
        }

        // Create Value
        currentIndex = new SimpleObjectProperty<>(tourItemsMap.keySet().stream().findFirst().get());
        vehicle3D = new Vehicle3D(model.getInstance().getVehicle().getL(), model.getInstance().getVehicle().getW(),
                model.getInstance().getVehicle().getH());
    }

    /**
     * Gets the current list of ItemNodes.
     *
     * @return the current list
     */
    public ObservableList<ItemNode> getCurrentList() {
        return tourItemsMap.get(currentIndex.getValue());
    }

    /**
     * Gets an ItemNode by item Id.
     *
     * @param itemId the itemId of the ItemNode to be found
     * @return the found ItemFound
     */
    public ItemNode getItemNode(final int itemId) {
        // Iterate trough HashMap
        for (final var tour : tourItemsMap.entrySet()) {
            for (final var node : tour.getValue()) {
                if (node.getItemId() == itemId) {
                    return node;
                }
            }
        }
        return null;
    }
}

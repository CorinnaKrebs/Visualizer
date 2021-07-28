package com.threedimensionalloadingcvrp.vrp.model;

import com.threedimensionalloadingcvrp.validator.model.Tour;
import javafx.scene.Node;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * The Tour Node.
 */
@Data
public class TourNode {

    /** The Tour Data */
    private Tour tour;

    /** The Node of the Vehicle */
    private Node node;

    /** List of Lines between Customers */
    private List<Node> paths;

    /**
     * Instantiates a new Tour node.
     *
     * @param tour the tour
     */
    public TourNode(final Tour tour) {
        this.tour = tour;
        this.paths = new ArrayList<>();
    }
}

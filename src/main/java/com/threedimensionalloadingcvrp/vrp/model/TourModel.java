package com.threedimensionalloadingcvrp.vrp.model;

import com.threedimensionalloadingcvrp.Model;
import com.threedimensionalloadingcvrp.validator.model.Tour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.Data;

/**
 * The Tour Model.
 */
@Data
public class TourModel {

    /** List of Tour Nodes */
    private final ObservableMap<Integer, TourNode> tourNodes;

    /**
     * Instantiates a new Tour model.
     *
     * @param model     the model
     * @param depotNode the depot node
     */
    public TourModel(final Model model, final CustomerNode depotNode) {
        tourNodes = FXCollections.observableHashMap();
        for (final Tour tour : model.getSolution().getTours()) {
            TourNode tourNode = new TourNode(tour);

            // Create Symbol
            final Circle circle = new Circle();
            circle.setRadius(10);
            circle.setFill(Color.BLUE);

            // Set Position of Node in Dept
            circle.setLayoutX(depotNode.getX());
            circle.setLayoutY(depotNode.getY());

            // Create ToolTip
            Tooltip.install(circle, new Tooltip("Tour: " + tour.getId()));

            // Save Data
            tourNode.setNode(circle);
            tourNodes.put(tour.getId(), tourNode);
        }
    }


    /**
     * Gets tour node.
     *
     * @param id the id
     * @return the tour node
     */
    public TourNode getTourNode(final int id) {
        return tourNodes.get(id);
    }
}

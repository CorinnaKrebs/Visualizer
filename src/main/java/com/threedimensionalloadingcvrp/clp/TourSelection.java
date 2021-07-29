package com.threedimensionalloadingcvrp.clp;

import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import lombok.Getter;

/**
 * The Tour Selection.
 */
@Getter
public class TourSelection extends Pane {

    /** Menu to select the current tour to be displayed */
    private final ChoiceBox<Integer> tourSelection;

    /**
     * Instantiates a new Tour selection.
     */
    public TourSelection() {
        super();
        tourSelection = new ChoiceBox<>();
        getChildren().add(tourSelection);
    }
}

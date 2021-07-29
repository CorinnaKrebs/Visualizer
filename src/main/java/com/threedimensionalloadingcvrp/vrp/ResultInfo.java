package com.threedimensionalloadingcvrp.vrp;


import com.threedimensionalloadingcvrp.validator.model.Solution;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

/**
 * The Pane giving the Infos for the Result.
 */
public class ResultInfo extends VBox {

    /** The Label for the number of used vehicles */
    private final Label labelVehicles;

    /** The Label for the Total Travel Distance */
    private final Label labelDistance;

    /** The Label for the Total Time of the Tour */
    private final Label labeTime;

    /**
     * Instantiates a new Result info.
     *
     * @param solution the solution Data
     */
    public ResultInfo(final Solution solution) {
        super();

        labelVehicles = new Label("Used Vehicles: " + solution.getTours().size());
        labelDistance = new Label ("Total Distance: " + solution.getTotal_travel_distance());
        labeTime = new Label("Total Time: " + solution.getTotal_travel_time());

        Separator separator = new Separator();

        getChildren().addAll(separator, labelVehicles, labelDistance, labeTime);
    }
}

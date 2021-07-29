package com.threedimensionalloadingcvrp.vrp;

import javafx.scene.control.Label;
import javafx.scene.shape.Polygon;
import lombok.Data;

/**
 * The Presenter for the Item Customer to update the slider and the button.
 */
@Data
public class CustomerSliderPresenter {

    /** The view to control */
    private CustomerSlider view;

    /**
     * Instantiates a new Customer slider presenter.
     *
     * @param view the view
     */
    public CustomerSliderPresenter(CustomerSlider view) {
        this.view = view;
    }

    /**
     * Initializes the View.
     *
     * @param max the total time of the tours.
     */
    public void init(int max) {
        view.getSlider().setMin(0);
        view.getSlider().setMax(max);
        view.getSlider().setValue(max);
        view.getSlider().setBlockIncrement(1);
        view.getSlider().setMajorTickUnit(5);
        view.getSlider().setShowTickLabels(true);
        view.getSlider().setShowTickMarks(true);
        view.getSlider().setSnapToTicks(true);
    }

    /**
     * Sets play button graphic.
     *
     * @param play the current state of the animation
     */
    public void setPlayButtonGraphic(boolean play) {
        if (play) {
            // Create Play Polygon
            Polygon triangle = new Polygon();
            triangle.getPoints().addAll(
                    0.0, 0.0,
                    5.0, 5.0,
                    0.0, 10.0
            );
            view.getPlayButton().setGraphic(triangle);
        } else {
            // Create Pause Symbol
            view.getPlayButton().setGraphic(new Label("||"));
        }
    }
}

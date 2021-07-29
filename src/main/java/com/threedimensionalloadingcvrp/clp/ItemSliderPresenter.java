package com.threedimensionalloadingcvrp.clp;

import com.threedimensionalloadingcvrp.Presenter;
import javafx.scene.control.Label;
import javafx.scene.shape.Polygon;
import lombok.Data;

/**
 * The Presenter for the Item Slider to update the slider and the button.
 */
@Data
public class ItemSliderPresenter extends Presenter {

    /** The view to control */
    private ItemSlider view;

    /**
     * Instantiates a new Item Slider Presenter.
     *
     * @param view the view
     */
    public ItemSliderPresenter(final ItemSlider view) {
        this.view = view;
    }

    /**
     * Initializes the View.
     */
    public void init() {
        setPlayButtonGraphic(true);
    }

    /**
     * Update the Slider.
     *
     * @param max the number of items in the tour
     */
    public void update(final int max) {
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
    public void setPlayButtonGraphic(final boolean play) {
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

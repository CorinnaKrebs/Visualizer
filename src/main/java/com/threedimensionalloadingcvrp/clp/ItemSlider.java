package com.threedimensionalloadingcvrp.clp;

import com.threedimensionalloadingcvrp.App;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import lombok.Getter;

/**
 * The Item slider.
 */
@Getter
public class ItemSlider extends Pane {

    /** The Button to Start / Pause the Animation */
    private final Button playButton;

    /** The Slider to indicate the current packing procedure */
    private final Slider slider;

    /**
     * Instantiates a new Item Slider.
     */
    public ItemSlider() {
        super();
        HBox hBox = new HBox();
        slider = new Slider();
        playButton = new Button();

        hBox.getChildren().addAll(playButton, slider);
        getChildren().add(hBox);
    }
}

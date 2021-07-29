package com.threedimensionalloadingcvrp.vrp;

import com.threedimensionalloadingcvrp.App;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import lombok.Getter;

/**
 * The Customer slider.
 */
@Getter
public class CustomerSlider extends Pane {

    /** The Button to Start / Pause the Animation */
    private final Button playButton;

    /** The Slider to indicate the current routing procedure */
    private final Slider slider;

    /**
     * Instantiates a new Customer Slider.
     */
    public CustomerSlider() {
        super();
        HBox hBox = new HBox();
        slider = new Slider();
        slider.setPrefWidth(App.WIN_WIDTH * 0.99);
        slider.setPadding(new Insets(0, 19, 0, 20));
        playButton = new Button();

        hBox.getChildren().addAll(playButton, slider);
        getChildren().add(hBox);
    }
}

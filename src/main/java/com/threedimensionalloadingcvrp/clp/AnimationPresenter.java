package com.threedimensionalloadingcvrp.clp;

import com.threedimensionalloadingcvrp.Presenter;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * The Animation presenter.
 */
public class AnimationPresenter extends Presenter {

    /** The Timeline for handling animations */
    private static Timeline timeline;

    /** The connected Presenter for the Item Slider */
    private final ItemSliderPresenter itemSliderPresenter;

    /**
     * Instantiates a new Animation presenter.
     *
     * @param itemSliderPresenter the item slider presenter
     */
    public AnimationPresenter(final ItemSliderPresenter itemSliderPresenter) {
        this.itemSliderPresenter = itemSliderPresenter;
    }

    /**
     * Initializes the View.
     */
    public void init() {

        // Starts Animation with Initialization
        itemSliderPresenter.getView().getPlayButton().setOnMouseClicked(event -> {
            animateItems((int) itemSliderPresenter.getView().getSlider().getValue());
        });

        itemSliderPresenter.getView().getSlider().setOnMouseClicked(event -> {
            itemSliderPresenter.setPlayButtonGraphic(true);
            timeline.pause();
        });
    }

    /**
     * Animate the items visibility.
     *
     * @param startIndex the index to start the animation.
     */
    public void animateItems(final int startIndex) {
        // The Number of Items in the Tour
        double max = itemSliderPresenter.getView().getSlider().getMax() + 1;

        // Initiate Animation
        AtomicInteger start = new AtomicInteger(startIndex);
        timeline = new Timeline();

        // Update the Slider Value
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100), e -> {
            itemSliderPresenter.getView().getSlider().setValue(start.getAndIncrement());
       }));

        // Change the Symbol of the Play Button in the end of the animation
        timeline.setOnFinished(e -> itemSliderPresenter.setPlayButtonGraphic(true));

        // End Value
       timeline.setCycleCount((int) max);

       // Start the Animation
       timeline.play();
    }
}

package com.threedimensionalloadingcvrp.clp;

import com.threedimensionalloadingcvrp.Presenter;
import com.threedimensionalloadingcvrp.clp.model.ItemNode;
import com.threedimensionalloadingcvrp.support.SavePresenter;
import javafx.collections.transformation.FilteredList;
import lombok.Data;

import java.util.function.Predicate;

/**
 * The CLP presenter.
 */
@Data
public class CLPPresenter extends Presenter {

    /** The entire CLP Window */
    private final CLPWindow stage;

    /** The Presenter for handling the Animations */
    private final AnimationPresenter animationPresenter;

    /** The Presenter for handling the Tour Selection */
    private final TourSelectionPresenter tourSelectionPresenter;

    /** The Presenter for handling the Item Slider Actions */
    private final ItemSliderPresenter itemSliderPresenter;

    /** The Presenter for handling the Table showing Item Data */
    private final ItemTablePresenter itemTablePresenter;

    /** The Presenter for handling the Vehicle */
    private final Vehicle3DPresenter vehicle3DPresenter;

    /** The Presenter for handling the 3D Scene */
    private final Scene3DPresenter scene3DPresenter;

    /** The Presenter for saving the 3D Scene */
    private final SavePresenter savePresenter;

    /**
     * Instantiates a new Clp presenter.
     *
     * @param stage the stage
     */
    public CLPPresenter(final CLPWindow stage) {
        this.stage = stage;

        tourSelectionPresenter = new TourSelectionPresenter(stage.getTourSelection());
        tourSelectionPresenter.init();

        vehicle3DPresenter = new Vehicle3DPresenter();
        vehicle3DPresenter.init();

        scene3DPresenter = new Scene3DPresenter(stage.getSubScene());
        scene3DPresenter.init();

        itemSliderPresenter = new ItemSliderPresenter(stage.getItemSlider());
        itemSliderPresenter.init();

        animationPresenter = new AnimationPresenter(itemSliderPresenter);
        animationPresenter.init();

        itemTablePresenter = new ItemTablePresenter(stage.getItemTable());
        itemTablePresenter.init();

        savePresenter = new SavePresenter(stage.getButtonSave(), stage.getSubScene());
        savePresenter.init();
    }

    /**
     * Initializes the View.
     */
    public void init() {
        updateByTourSelection();
        updateVisibilityBySlider();
        itemSliderPresenter.update(model.getItemModel().getCurrentList().size());
        animationPresenter.animateItems(0);
        itemSliderPresenter.getView().getSlider().prefWidthProperty().bind(stage.widthProperty().multiply(0.92));
        itemTablePresenter.getView().getTable().prefWidthProperty().bind(stage.widthProperty().multiply(0.98));
        scene3DPresenter.getView().widthProperty().bind(stage.widthProperty().multiply(0.98));
    }

    /**
     * Update by tour selection.
     */
    public void updateByTourSelection() {
        stage.getTourSelection().getTourSelection().getSelectionModel()
                .selectedIndexProperty().addListener((observableValue, number, number2) -> {
                    Integer value = stage.getTourSelection().getTourSelection().getItems().get((Integer) number2);
            var list = model.getItemModel().getTourItemsMap().get(value);
            stage.getItemTable().getTable().setItems(list);
            itemSliderPresenter.update(list.size());
            vehicle3DPresenter.updateVehicle(list);
        });
    }

    /**
     * Update the Visibility of the Items through the Slider.
     */
    public void updateVisibilityBySlider() {
        stage.getItemSlider().getSlider().valueProperty().addListener((observable, oldValue, newValue) -> {

            // Get the Current Item Node List
            var list = model.getItemModel().getCurrentList();

            // Set all Items "invisible" (= only Line Stroke)
            for (var item : list) item.setVis(false);

            // Get the list of items which should be visible
            FilteredList<ItemNode> items = new FilteredList<>(list);
            Predicate<ItemNode> visibleItems = i -> i.getSequenceIndex() <= newValue.intValue();
            items.setPredicate(visibleItems);

            // Update the visibility
            for (var item : items) item.setVis(true);

            // Refresh the View
            vehicle3DPresenter.updateVehicle(list);
        });
    }
}

package com.threedimensionalloadingcvrp.clp;

import com.threedimensionalloadingcvrp.Presenter;
import javafx.collections.FXCollections;
import javafx.util.StringConverter;

/**
 * The type Tour selection presenter.
 */
public class TourSelectionPresenter extends Presenter {

    /** The view to control */
    private final TourSelection view;

    /**
     * Instantiates a new Tour selection presenter.
     *
     * @param view the view
     */
    public TourSelectionPresenter(TourSelection view) {
        this.view = view;
    }

    /**
     * Initializes the View.
     */
    public void init() {
        view.getTourSelection().setItems(FXCollections.observableArrayList(model.getItemModel().getTourItemsMap().keySet()));
        view.getTourSelection().setConverter(this.choiceBoxString());

        // Bind to update the Index automatically
        view.getTourSelection().valueProperty().bind(model.getItemModel().getCurrentIndex());
    }

    /**
     * Generates the String for displaying tours.
     *
     * @return the String Converter
     */
    private StringConverter<Integer> choiceBoxString() {
        return new StringConverter<>() {
            @Override
            public String toString(final Integer integer) {
                return "Tour " + integer.toString();
            }

            @Override
            public Integer fromString(final String s) {
                return null;
            }
        };
    }
}

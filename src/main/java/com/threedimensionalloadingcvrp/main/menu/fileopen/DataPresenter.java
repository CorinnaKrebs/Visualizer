package com.threedimensionalloadingcvrp.main.menu.fileopen;

import com.threedimensionalloadingcvrp.Presenter;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

/**
 * The type Data presenter.
 */
public class DataPresenter extends Presenter {

    /** The view to control */
    private final DataPane view;

    private final Button button;

    /**
     * Instantiates a new Data presenter.
     *
     * @param view   the view
     * @param button the button
     */
    public DataPresenter(final DataPane view, final Button button) {
        this.view = view;
        this.button = button;
    }

    /**
     * Initializes the View.
     */
    public void init() {
        // Set Basic Constraint Set
        view.getCheckCLP().setSelected(true);
        view.getCheckVRP().setSelected(true);
        view.getCheckLoadingConstraints().setSelected(true);
        view.getCheckRoutingConstraints().setSelected(true);

        button.addEventHandler(ActionEvent.ACTION, event -> {
            model.setIsCLP(view.getCheckCLP().isSelected());
            model.setIsVRP(view.getCheckVRP().isSelected());
            model.setCheckLoadingConstraints(view.getCheckLoadingConstraints().isSelected());
            model.setCheckRoutingConstraints(view.getCheckRoutingConstraints().isSelected());
        });

    }
}

package com.threedimensionalloadingcvrp.main.menu.fileopen;

import com.threedimensionalloadingcvrp.Presenter;
import com.threedimensionalloadingcvrp.validator.model.ConstraintSet;
import com.threedimensionalloadingcvrp.validator.model.Stacking;
import com.threedimensionalloadingcvrp.validator.model.UnloadingSequence;
import com.threedimensionalloadingcvrp.validator.model.VerticalStability;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

/**
 * The Presenter for the Constraint Data.
 */
public class ConstraintPresenter extends Presenter {

    /** The view to control */
    private final LoadingConstraintPane loadingPane;

    /** The view to control */
    private final RoutingConstraintPane routingPane;

    /** The Start Button */
    private final Button button;

    /**
     * Instantiates a new Constraint presenter.
     *
     * @param loadingPane   the Constraint Pane
     * @param button the Start button
     */
    public ConstraintPresenter(final RoutingConstraintPane routingPane, final LoadingConstraintPane loadingPane,
                               final Button button) {
        this.routingPane = routingPane;
        this.loadingPane = loadingPane;
        this.button = button;
    }

    /**
     * Initializes the View.
     */
    public void init() {

        // Set Loading Constraint Set
        routingPane.getCheckSD().setSelected(false);
        routingPane.getCheckTW().setSelected(false);

        // Set Basic Constraint Set
        loadingPane.getCheckRotation().setSelected(true);
        loadingPane.getCheckCapacity().setSelected(true);
        loadingPane.getCheckReachability().setSelected(false);
        loadingPane.getCheckAxleWeights().setSelected(false);
        loadingPane.getCheckBalancing().setSelected(false);

        // Set Standard Parameters
        loadingPane.getTextAlpha().setText("0.75");
        loadingPane.getTextLambda().setText("5");
        loadingPane.getTextBalancedPart().setText("0.7");
        loadingPane.getChoiceUSequence().setValue(UnloadingSequence.LIFO);
        loadingPane.getChoiceVStability().setValue(VerticalStability.MinimalSupport);
        loadingPane.getChoiceStacking().setValue(Stacking.Fragility);

        // Hide parameter alpha if no Vertical Stability
        loadingPane.getChoiceVStability().setOnAction(event -> {
            boolean visibility = loadingPane.getChoiceVStability().getValue() != VerticalStability.none;
            loadingPane.getLabelAlpha().setVisible(visibility);
            loadingPane.getTextAlpha().setVisible(visibility);
        });

        // Show Lambda Parameter if Reachability is activated
        loadingPane.getLabelLambda().visibleProperty().bind(loadingPane.getCheckReachability().selectedProperty());
        loadingPane.getTextLambda().visibleProperty().bind(loadingPane.getCheckReachability().selectedProperty());

        // Show Balanced part parameter if Balanced Load is activated
        loadingPane.getLabelBalancedPart().visibleProperty().bind(loadingPane.getCheckBalancing().selectedProperty());
        loadingPane.getTextBalancedPart().visibleProperty().bind(loadingPane.getCheckBalancing().selectedProperty());

        // Add Click Action for Button
        button.addEventHandler(ActionEvent.ACTION, event -> getConstraintSet());
    }

    /**
     * Evaluates the Form and sets the constraint set.
     */
    public void getConstraintSet() {
        // Read the Data
        boolean timeWindows     = routingPane.getCheckTW().isSelected();
        boolean splitDelivery   = routingPane.getCheckSD().isSelected();

        boolean rotation        = loadingPane.getCheckRotation().isSelected();
        boolean capacity        = loadingPane.getCheckCapacity().isSelected();
        boolean reachability    = loadingPane.getCheckReachability().isSelected();
        boolean axleWeights     = loadingPane.getCheckAxleWeights().isSelected();
        boolean balancing       = loadingPane.getCheckBalancing().isSelected();
        VerticalStability verticalStability = loadingPane.getChoiceVStability().getValue();
        UnloadingSequence unloadingSequence = loadingPane.getChoiceUSequence().getValue();
        Stacking stacking = loadingPane.getChoiceStacking().getValue();

        float alpha = Float.parseFloat(loadingPane.getTextAlpha().getText());
        int   lambda = Integer.parseInt(loadingPane.getTextLambda().getText());
        float balancedPart = Float.parseFloat(loadingPane.getTextBalancedPart().getText());

        // Create new Constraint Set
        ConstraintSet constraintSet = new ConstraintSet(rotation, capacity, unloadingSequence,
                verticalStability, stacking, reachability, axleWeights, balancing,
                alpha, lambda, balancedPart, timeWindows, splitDelivery);

        // Save the Constraint Set
        model.setConstraintSet(constraintSet);
    }
}

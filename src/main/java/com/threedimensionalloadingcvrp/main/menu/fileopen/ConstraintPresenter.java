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
    private final ConstraintPane view;

    /** The Start Button */
    private final Button button;

    /**
     * Instantiates a new Constraint presenter.
     *
     * @param view   the Constraint Pane
     * @param button the Start button
     */
    public ConstraintPresenter(final ConstraintPane view, final Button button) {
        this.view = view;
        this.button = button;
    }

    /**
     * Initializes the View.
     */
    public void init() {

        // Set Basic Constraint Set
        view.getCheckRotation().setSelected(true);
        view.getCheckCapacity().setSelected(true);
        view.getCheckReachability().setSelected(false);
        view.getCheckAxleWeights().setSelected(false);
        view.getCheckBalancing().setSelected(false);

        // Set Standard Parameters
        view.getTextAlpha().setText("0.75");
        view.getTextLambda().setText("5");
        view.getTextBalancedPart().setText("0.7");
        view.getChoiceUSequence().setValue(UnloadingSequence.LIFO);
        view.getChoiceVStability().setValue(VerticalStability.MinimalSupport);
        view.getChoiceStacking().setValue(Stacking.Fragility);

        // Hide parameter alpha if no Vertical Stability
        view.getChoiceVStability().setOnAction(event -> {
            boolean visibility = view.getChoiceVStability().getValue() != VerticalStability.none;
            view.getLabelAlpha().setVisible(visibility);
            view.getTextAlpha().setVisible(visibility);
        });

        // Show Lambda Parameter if Reachability is activated
        view.getLabelLambda().visibleProperty().bind(view.getCheckReachability().selectedProperty());
        view.getTextLambda().visibleProperty().bind(view.getCheckReachability().selectedProperty());

        // Show Balanced part parameter if Balanced Load is activated
        view.getLabelBalancedPart().visibleProperty().bind(view.getCheckBalancing().selectedProperty());
        view.getTextBalancedPart().visibleProperty().bind(view.getCheckBalancing().selectedProperty());

        // Add Click Action for Button
        button.addEventHandler(ActionEvent.ACTION, event -> getConstraintSet());
    }

    /**
     * Evaluates the Form and sets the constraint set.
     */
    public void getConstraintSet() {
        // Read the Data
        boolean rotation = view.getCheckRotation().isSelected();
        boolean capacity = view.getCheckCapacity().isSelected();
        boolean reachability = view.getCheckReachability().isSelected();
        boolean axleWeights  = view.getCheckAxleWeights().isSelected();
        boolean balancing    = view.getCheckBalancing().isSelected();
        VerticalStability verticalStability = view.getChoiceVStability().getValue();
        UnloadingSequence unloadingSequence = view.getChoiceUSequence().getValue();
        Stacking stacking = view.getChoiceStacking().getValue();

        float alpha = Float.parseFloat(view.getTextAlpha().getText());
        int   lambda = Integer.parseInt(view.getTextLambda().getText());
        float balancedPart = Float.parseFloat(view.getTextBalancedPart().getText());

        // Create new Constraint Set
        ConstraintSet constraintSet = new ConstraintSet(rotation, capacity, unloadingSequence,
                verticalStability, stacking, reachability, axleWeights, balancing,
                alpha, lambda, balancedPart);

        // Save the Constraint Set
        model.setConstraintSet(constraintSet);
    }
}

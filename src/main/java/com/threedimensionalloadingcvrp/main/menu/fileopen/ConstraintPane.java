package com.threedimensionalloadingcvrp.main.menu.fileopen;

import com.threedimensionalloadingcvrp.validator.model.Stacking;
import com.threedimensionalloadingcvrp.validator.model.UnloadingSequence;
import com.threedimensionalloadingcvrp.validator.model.VerticalStability;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The Pane for Constraints.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ConstraintPane extends TitledPane {

    /** Label for Alpha Parameter */
    private final Label labelAlpha;

    /** Label for Lambda Parameter */
    private final Label labelLambda;

    /** Label for Balanced Part Parameter */
    private final Label labelBalancedPart;

    /** Checkbox for the Consideration of the Rotation Constraint */
    private final CheckBox checkRotation;

    /** Checkbox for the Consideration of the Load Capacity Constraint */
    private final CheckBox checkCapacity;

    /** Checkbox for the Consideration of the Axle Weights Constraint */
    private final CheckBox checkAxleWeights;

    /** Checkbox for the Consideration of the Rechability Constraint */
    private final CheckBox checkReachability;

    /** Checkbox for the Consideration of the Balanced Load Constraint */
    private final CheckBox checkBalancing;

    /** ChoiceBox to select the Unloading Sequence Constraint */
    private final ChoiceBox<UnloadingSequence> choiceUSequence;

    /** ChoiceBox to select the Vertical Stability Constraint */
    private final ChoiceBox<VerticalStability> choiceVStability;

    /** ChoiceBox to Select the Stacking Constraint */
    private final ChoiceBox<Stacking>          choiceStacking;

    /** Textfield to insert the value for the Alpha Parameter */
    private final TextField textAlpha;

    /** Textfield to insert the value for the Lambda Parameter */
    private final TextField textLambda;

    /** Textfield to insert the value for the Balanced Part Parameter */
    private final TextField textBalancedPart;

    /**
     * Instantiates a new Constraint pane.
     */
    public ConstraintPane() {
        super();
        // TitledPane -> ScrollPane -> GridPane
        GridPane gridPane = new GridPane();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane);
        setContent(scrollPane);

        this.setText("Loading Constraints");

        // Counter to set nodes to correct row
        int row = 0;

        // Rotation
        Label labelRotation = new Label("Rotation of Items:");
        checkRotation = new CheckBox();
        gridPane.add(labelRotation, 0, row);
        gridPane.add(checkRotation, 1, row++);

        // Load Capacity
        Label labelCapacity = new Label("Load Capacity:");
        checkCapacity = new CheckBox();
        gridPane.add(labelCapacity, 0, row);
        gridPane.add(checkCapacity, 1, row++);

        // Unloading Sequence
        Label labelUSequence = new Label("Unloading Sequence:");
        choiceUSequence = new ChoiceBox<>();
        choiceUSequence.getItems().addAll(UnloadingSequence.values());
        gridPane.add(labelUSequence,  0, row);
        gridPane.add(choiceUSequence, 1, row++);

        // Vertical Stability
        Label labelVStability = new Label("Vertical Stability:");
        choiceVStability = new ChoiceBox<>();
        choiceVStability.getItems().addAll(VerticalStability.values());
        gridPane.add(labelVStability, 0, row);
        gridPane.add(choiceVStability, 1, row);

        labelAlpha = new Label("Support Parameter (alpha):");
        textAlpha = new TextField("");
        gridPane.add(labelAlpha, 2, row);
        gridPane.add(textAlpha,  3, row++);

        // Stacking
        Label labelStacking= new Label("Stacking:");
        choiceStacking = new ChoiceBox<>();
        choiceStacking.getItems().addAll(Stacking.values());
        gridPane.add(labelStacking, 0, row);
        gridPane.add(choiceStacking, 1, row++);

        // Reachability
        Label labelReachability = new Label("Reachability");
        checkReachability = new CheckBox();
        gridPane.add(labelReachability, 0, row);
        gridPane.add(checkReachability, 1, row);

        labelLambda = new Label("Reachability Parameter (lambda):");
        textLambda = new TextField("");
        gridPane.add(labelLambda, 2, row);
        gridPane.add(textLambda,  3, row++);

        // Axle Weights
        Label labelAxleWeights = new Label("Axle Weights");
        checkAxleWeights = new CheckBox();
        gridPane.add(labelAxleWeights, 0, row);
        gridPane.add(checkAxleWeights,  1, row++);

        // Balanced Loading
        Label labelBalancing  = new Label("Balanced Loading");
        checkBalancing = new CheckBox();
        gridPane.add(labelBalancing, 0, row);
        gridPane.add(checkBalancing, 1, row);

        labelBalancedPart = new Label("Part of Balancing Loading:");
        textBalancedPart  = new TextField("");
        gridPane.add(labelBalancedPart, 2, row);
        gridPane.add(textBalancedPart,  3, row);

        // Set Pref Width
        int prefWidth = 90;
        choiceStacking.setPrefWidth(prefWidth);
        choiceVStability.setPrefWidth(prefWidth);
        choiceUSequence.setPrefWidth(prefWidth);
        textAlpha.setPrefWidth(prefWidth);
        textLambda.setPrefWidth(prefWidth);
        textBalancedPart.setPrefWidth(prefWidth);

        gridPane.setHgap(40);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
    }

}

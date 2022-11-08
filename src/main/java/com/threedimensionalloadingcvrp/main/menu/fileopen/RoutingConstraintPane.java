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
public class RoutingConstraintPane extends TitledPane {

    /** Checkbox for the Consideration of the Time Windows Constraint */
    private final CheckBox checkTW;

    /** Checkbox for the Consideration of the Split Delivery Constraint */
    private final CheckBox checkSD;

    /**
     * Instantiates a new Constraint pane.
     */
    public RoutingConstraintPane() {
        super();
        // TitledPane -> ScrollPane -> GridPane
        GridPane gridPane = new GridPane();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane);
        setContent(scrollPane);

        this.setText("Routing Constraints");

        // Counter to set nodes to correct row
        int row = 0;

        // Rotation
        Label labelTW = new Label("Time Windows Check:");
        checkTW = new CheckBox();
        gridPane.add(labelTW, 0, row);
        gridPane.add(checkTW, 1, row++);

        // Load Capacity
        Label labelSD = new Label("Split Delivery:");
        checkSD = new CheckBox();
        gridPane.add(labelSD, 0, row);
        gridPane.add(checkSD, 1, row++);

        // Set Pref Width
        int prefWidth = 90;
        gridPane.setHgap(40);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
    }
}

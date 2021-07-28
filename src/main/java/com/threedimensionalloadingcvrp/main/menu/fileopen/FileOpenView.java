package com.threedimensionalloadingcvrp.main.menu.fileopen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import lombok.Data;

/**
 * The type File open view.
 */
@Data
public class FileOpenView extends VBox {

    /** The Pane to insert the Data Files */
    private final DataPane dataPane;

    /** The Pane to define the Constraint Set */
    private final ConstraintPane constraintsPane;

    /** The Start Button */
    private final Button button;

    /**
     * Instantiates a new File Open View.
     */
    public FileOpenView() {
        super();

        // Create Start Button
        button = new Button("Start!");
        button.setAlignment(Pos.CENTER);

        // Create Data Pane
        dataPane = new DataPane();
        DataPresenter dataPresenter = new DataPresenter(dataPane, button);
        dataPresenter.init();

        // Create Constraint Pane
        constraintsPane = new ConstraintPane();
        ConstraintPresenter constraintPresenter = new ConstraintPresenter(constraintsPane, button);
        constraintPresenter.init();

        getChildren().addAll(dataPane, constraintsPane, button);

        setPadding(new Insets(10, 10, 10, 10));
    }
}


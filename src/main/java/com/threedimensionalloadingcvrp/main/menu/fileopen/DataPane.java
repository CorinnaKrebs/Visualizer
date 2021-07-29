package com.threedimensionalloadingcvrp.main.menu.fileopen;

import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import lombok.Data;

/**
 * The type Data pane.
 */
@Data
public class DataPane extends TitledPane {

    /** Checkbox for the (De-)Activation of the CLP View */
    private final CheckBox checkCLP;

    /** Checkbox for the (De-)Activation of the VRP View */
    private final CheckBox checkVRP;

    /** Checkbox for the Check of Loading Constraints */
    private final CheckBox checkLoadingConstraints;

    /** Checkbox for the Check of Routing Constraints */
    private final CheckBox checkRoutingConstraints;

    /** Pane to select Instance File */
    private final SelectionPane instancePane;

    /** Pane to select Solution File */
    private final SelectionPane solutionPane;

    /**
     * Instantiates a new Data pane.
     */
    public DataPane() {
        super();
        this.setText("Data");
        ScrollPane scrollPane = new ScrollPane();
        GridPane gridPane = new GridPane();
        scrollPane.setContent(gridPane);
        setContent(scrollPane);

        // Counter to set nodes to correct row
        int row = 0;

        Label captionProblem = new Label("Problem Variant");
        captionProblem.setStyle("-fx-font-weight: bold");
        gridPane.add(captionProblem, 0, row++, 2, 1);

        checkCLP = new CheckBox("Container Loading Problem");
        gridPane.add(checkCLP, 0, row);

        checkLoadingConstraints = new CheckBox("Check Loading Constraints");
        gridPane.add(checkLoadingConstraints, 1, row++);

        checkVRP = new CheckBox("Vehicle Routing Problem");
        gridPane.add(checkVRP, 0, row);

        checkRoutingConstraints = new CheckBox("Check Routing Constraints");
        gridPane.add(checkRoutingConstraints, 1, row++);

        Label captionFiles = new Label("File Selection");
        captionFiles.setStyle("-fx-font-weight: bold");
        gridPane.add(captionFiles, 0, row++, 2, 1);

        // Create FileSelection Captions
        Label captionInstance = new Label("Instance File");
        gridPane.add(captionInstance, 0, row);
        Label captionSolution = new Label("Solution File");
        gridPane.add(captionSolution, 1, row++);

        instancePane = new SelectionPane();
        SelectionPresenter instancePresenter = new SelectionPresenter(instancePane, FileSelection.INSTANCE);
        instancePresenter.init();
        gridPane.add(instancePane, 0, row);

        // Create FileSelection for Solution File
        solutionPane = new SelectionPane();
        SelectionPresenter solutionPresenter = new SelectionPresenter(solutionPane, FileSelection.SOLUTION);
        solutionPresenter.init();
        gridPane.add(solutionPane, 1, row);

        gridPane.setHgap(40);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
    }
}

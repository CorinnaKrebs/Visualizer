package com.threedimensionalloadingcvrp.main.start;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type Start view.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StartView extends VBox {

    /** The Caption Text */
    private final Label caption;

    /** The Content Text */
    private final Label content;


    /**
     * Instantiates a new Start view.
     */
    public StartView() {
        super();
        caption = new Label();
        content = new Label();

        caption.setStyle("-fx-font-weight: bold");
        setSpacing(10);
        setPadding(new Insets(20, 20, 20, 20));
        getChildren().addAll(caption, content);

        caption.setText("Home");
        content.setText("Welcome to the Visualization Tool \n" +
                "for the combined Vehicle Routing Problem (VRP) with the Container Loading Problem (CLP)!\n" +
                "Therefore, suitable for the 3L-CVRP and its extension with Time Windows (3L-VRPTW).\n" +
                "Just open the files and get it started.\n\n" +
                "Have fun!");
    }
}

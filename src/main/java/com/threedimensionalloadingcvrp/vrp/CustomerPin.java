package com.threedimensionalloadingcvrp.vrp;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

/**
 * The Customer Pin Symbol.
 */
public class CustomerPin extends Group {
    /**
     * Instantiates a new Customer Pin.
     *
     * @param color  the color of the pin
     * @param header the text for the header
     */
    public CustomerPin(Color color, String header) {
        super();

        // Create a Polygon => Triangle
        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(
                -10.0, 0.0,
                10.0, 0.0,
                0.0, 20.0);

        // Create outer Circle
        Circle circle1 = new Circle();
        circle1.setRadius(9.5);

        // Create inner Circle
        Circle circle2 = new Circle();
        circle2.setRadius(7);
        circle2.setFill(Color.WHITE);
        circle2.setStroke(Color.BLACK);
        circle2.setCenterY(-1);

        // The Label for the Header
        Label label = new Label(header);
        label.setTranslateX(-6);
        label.setTranslateY(-10);
        label.setTextFill(Color.BLACK);

        // Combine nodes
        Shape union = Shape.union(polygon, circle1);
        union.setFill(color);
        union.setStroke(Color.BLACK);

        getChildren().addAll(union, circle2, label);
    }
}

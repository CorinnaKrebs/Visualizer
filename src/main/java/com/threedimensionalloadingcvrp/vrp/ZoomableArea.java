package com.threedimensionalloadingcvrp.vrp;

import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import lombok.Data;

/**
 * The Zoomable Area.
 */
@Data
public class ZoomableArea extends Pane {
    /** The zoomable Group */
    private Group group;

    /** The Scale Transform */
    private Scale scale;

    /**
     * Instantiates a new Zoomable area.
     */
    public ZoomableArea() {
        group = new Group();
        scale = new Scale();
        group.getTransforms().add(scale);
        getChildren().add(group);
    }
}



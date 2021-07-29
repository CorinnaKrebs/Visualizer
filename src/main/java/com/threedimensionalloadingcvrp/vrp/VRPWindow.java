package com.threedimensionalloadingcvrp.vrp;

import com.threedimensionalloadingcvrp.App;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Data;

/**
 * The VRP window.
 */
@Data
public class VRPWindow extends Stage {

    /** Content Pane (Left, Center, Bottom) */
    private BorderPane content;

    /** The Center Pane (shows the Tours) */
    private ZoomableArea centerPane;

    /** The Tree for Customers */
    private CustomerTree customerTree;

    /** The Tree for Tours */
    private TourTree tourTree;

    /** The Pane for Result Infos */
    private ResultInfo resultInfo;

    /** The Slider for the Tour Process */
    private CustomerSlider customerSlider;

    /** Gantt Diagram for the Tour Process */
    private DiagramTourGantt diagramTourGantt;

    /** Button to save the 3D Scene */
    private final Button buttonSave;

    /**
     * Instantiates a new Vrp window.
     */
    public VRPWindow() {
        super();
        content = new BorderPane();
        customerSlider = new CustomerSlider();
        buttonSave = new Button();
        content.setRight(buttonSave);

        // Start Scene
        setTitle("VRP View");
        Scene scene = new Scene(content, App.WIN_WIDTH, App.WIN_HEIGHT);
        this.setScene(scene);
    }


}

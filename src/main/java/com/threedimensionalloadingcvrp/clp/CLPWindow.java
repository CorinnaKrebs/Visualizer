package com.threedimensionalloadingcvrp.clp;

import com.threedimensionalloadingcvrp.App;
import com.threedimensionalloadingcvrp.Presenter;
import com.threedimensionalloadingcvrp.support.SavePresenter;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import lombok.Getter;

/**
 * The Container Loading Window.
 */
@Getter
public class CLPWindow extends Stage {

    /** The entire Layout of the Window */
    private final BorderPane content;

    /** The 3D SubScene */
    private final SubScene subScene;

    /** The Table containing all item data */
    private final ItemTable itemTable;

    /** Slider to visualize the packing process */
    private final ItemSlider itemSlider;

    /** Menu to select the Tour to be displayed */
    private final TourSelection tourSelection;

    /** Button to save the 3D Scene */
    private final Button buttonSave;

    /**
     * Creates a new CLP window.
     *
     * @param vehicle3D the 3D Vehicle
     */
    public CLPWindow(final Vehicle3D vehicle3D) {
        super();
        content = new BorderPane();

        // The 3D View must be a SubScene
        subScene = new SubScene(vehicle3D, App.WIN_WIDTH * 0.9, App.WIN_HEIGHT / 2.0, true,
                SceneAntialiasing.BALANCED);
        PerspectiveCamera camera = new PerspectiveCamera();
        camera.setNearClip(0.00001);
        camera.setFarClip(10000.0);
        subScene.setCamera(camera);
        subScene.setDepthTest(DepthTest.ENABLE);

        // Layout
        tourSelection = new TourSelection();
        itemSlider = new ItemSlider();
        itemTable = new ItemTable();

        // Top
        HBox hbox = new HBox();
        buttonSave = new Button();
        hbox.getChildren().addAll(tourSelection, buttonSave);
        hbox.prefWidthProperty().bind(this.widthProperty());
        content.setTop(hbox);

        // Center
        content.setCenter(subScene);

        // Bottom
        VBox vBox = new VBox();
        vBox.getChildren().addAll(itemSlider, itemTable);
        content.setBottom(vBox);

        // Start Scene
        setTitle("CLP View");
        Scene scene = new Scene(content, App.WIN_WIDTH, App.WIN_HEIGHT, true, SceneAntialiasing.BALANCED);
        this.setScene(scene);
    }
}

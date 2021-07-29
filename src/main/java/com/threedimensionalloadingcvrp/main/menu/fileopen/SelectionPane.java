package com.threedimensionalloadingcvrp.main.menu.fileopen;

import com.threedimensionalloadingcvrp.App;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type Selection pane.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SelectionPane extends Pane {

    /** The Button to open File Selection Window */
    private Button fileButton;

    /** The Field to show the File Path */
    private TextArea textPath;

    /** The Button to remove the file */
    private Button crossButton;

    /** The Layout for the selection process */
    private VBox selectionLayout;

    /** The Layout showing selection success */
    private VBox successLayout;

    /**
     * Instantiates a new Selection Pane.
     * Layouts are instantiated to switch easily.
     */
    public SelectionPane() {
        super();
        initSelection();
        initSuccess();
        setSelectionPane();
    }

    /**
     * Instantiates the Selection Layout.
     */
    private void initSelection() {
        selectionLayout = new VBox();
        selectionLayout.setAlignment(Pos.CENTER);
        resetSelectionLayout();

        Image image = new Image("file:src/main/resources/uploadCloud.png");
        ImageView imageView = new ImageView(image);

        Label label = new Label("Drag & Drop");
        fileButton = new Button("or select a file");

        selectionLayout.getChildren().addAll(imageView, label, fileButton);
    }

    /**
     * Instantiates the Success Layout.
     */
    private void initSuccess() {
        HBox hBox = new HBox();
        successLayout = new VBox();

        textPath = new TextArea();
        textPath.setWrapText(true);
        textPath.setDisable(true);
        textPath.setMaxWidth(App.WIN_WIDTH / 4.0);
        textPath.setMaxHeight(20);

        Image image = new Image("file:src/main/resources/cross.png");
        crossButton = new Button();
        crossButton.setGraphic(new ImageView((image)));

        hBox.getChildren().addAll(textPath, crossButton);
        successLayout.getChildren().add(hBox);
    }

    /**
     * Sets selection pane.
     */
    public void setSelectionPane() {
        getChildren().clear();
        getChildren().add(selectionLayout);
    }

    /**
     * Sets success pane.
     */
    public void setSuccessPane() {
        getChildren().clear();
        getChildren().add(successLayout);
    }

    /**
     * Reset the border style for the selection layout.
     */
    public void resetSelectionLayout() {
        selectionLayout.setStyle("-fx-border-style: dashed");
    }

}

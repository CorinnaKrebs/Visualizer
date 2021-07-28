package com.threedimensionalloadingcvrp.support;

import com.threedimensionalloadingcvrp.Presenter;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * The Save Presenter.
 */
public class SavePresenter extends Presenter {

    /** The Button to save */
    private final Button button;

    /** The Node to export */
    private final Node group;

    /**
     * Instantiates a new Menu presenter.
     *
     * @param button the save button
     * @param group the group to export
     */
    public SavePresenter(final Button button, final Node group) {
        this.button = button;
        this.group = group;
    }

    /**
     * Initializes the Button.
     */
    public void init() {
        Image image = new Image("file:src/main/resources/download.png");
        button.setGraphic(new ImageView((image)));
        button.setOnAction(event -> saveNode());
    }

    /**
     * Saves the Node.
     */
    private void saveNode() {
        WritableImage image = group.snapshot(new SnapshotParameters(), null);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG File", "*.png"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            } catch (IOException ignored) {}
        }
    }
}

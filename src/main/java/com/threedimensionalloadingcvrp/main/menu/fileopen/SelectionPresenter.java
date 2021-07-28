package com.threedimensionalloadingcvrp.main.menu.fileopen;

import com.threedimensionalloadingcvrp.Presenter;
import com.threedimensionalloadingcvrp.validator.Read;
import com.threedimensionalloadingcvrp.validator.model.Instance;
import com.threedimensionalloadingcvrp.validator.model.Solution;
import javafx.event.ActionEvent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import lombok.Data;

import java.io.File;

/**
 * The Presenter for the Selection Pane.
 */
@Data
public class SelectionPresenter extends Presenter {

    /** The view to control */
    private final SelectionPane view;

    /** Type of the File */
    private final FileSelection fileType;

    /**
     * Instantiates a new Selection presenter.
     *
     * @param view          the view
     * @param fileSelection the type of the selected File (Instance / Solution)
     */
    public SelectionPresenter(SelectionPane view, FileSelection fileSelection) {
        this.view = view;
        this.fileType = fileSelection;
    }

    /**
     * Initializes the View.
     */
    public void init() {
        view.getFileButton().setOnAction(this::actionFileChooser);
        view.getCrossButton().setOnAction(event -> view.setSelectionPane());
        view.getSelectionLayout().setOnDragOver(event -> mouseDragOver(event, view.getSelectionLayout()));
        view.getSelectionLayout().setOnDragDropped(event -> mouseDragDropped(event, view.getSelectionLayout()));
        view.getSelectionLayout().setOnDragExited(event -> view.resetSelectionLayout());
    }

    /**
     * Open File Chooser Window.
     */
    private void actionFileChooser(final ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setTitle("Open File");
        File file = fileChooser.showOpenDialog(null);
        if (checkFile(file)) {
            receivedFile(file);
        }
    }

    /**
     * Consume dragged file.
     */
    private void mouseDragDropped(final DragEvent event, Pane pane) {
        final Dragboard db = event.getDragboard();
        if (db.hasFiles()) {
            if (checkFile(db.getFiles().get(0))) {
                File file = event.getDragboard().getFiles().get(0);
                event.setDropCompleted(true);
                event.consume();
                receivedFile(file);
            }
        }
    }

    /**
     * Set the Style for Drag Over.
     */
    private void mouseDragOver(final DragEvent event, Pane pane) {
        final Dragboard db = event.getDragboard();
        if (db.hasFiles()) {
            if (checkFile(db.getFiles().get(0))) {
                pane.setStyle("-fx-background-color: #35a903");
                event.acceptTransferModes(TransferMode.COPY);
                return;
            }
        }
        pane.setStyle("-fx-background-color: #FA0707");
    }

    /**
     * Check Acceptance of File.
     */
    private boolean checkFile(final File file) {
        final boolean feasible = file.getPath().toLowerCase().endsWith(".txt");
        final String path = file.getPath();

        // try to read the instance file
        if (feasible && fileType == FileSelection.INSTANCE)
            try {
                Instance instance = Read.readInstanceFile(path);
                model.setInstance(instance);
                return true;
            } catch (Exception e) {
                //System.out.println("Could not read Instance File");
                return false;
            }

        // try to read the solution file
        if (feasible && fileType == FileSelection.SOLUTION) {
            try {
                Solution solution = Read.readSolutionFile(path, model.getInstance());
                model.setSolution(solution);
                return true;
            } catch (Exception e) {
                //System.out.println("Could not read Solution File");
                return false;
            }
        }
        return false;
    }

    /**
     * Update Layout w.r.t received file.
     */
    private void receivedFile(File file) {
        if (file != null) {
            if (checkFile(file)) {
                view.setSuccessPane();
                view.getTextPath().setText(file.getPath());
                if (fileType == FileSelection.INSTANCE) model.setInstanceFile(file);
                if (fileType == FileSelection.SOLUTION) model.setSolutionFile(file);
            }
        }
    }
}

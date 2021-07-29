package com.threedimensionalloadingcvrp.clp;

import com.threedimensionalloadingcvrp.App;
import com.threedimensionalloadingcvrp.Presenter;
import com.threedimensionalloadingcvrp.clp.model.ItemNode;
import javafx.application.Platform;
import javafx.scene.control.Control;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import lombok.Data;

/**
 * The presenter for the Item Table.
 */
@Data
public class ItemTablePresenter extends Presenter {

    /** The view to control */
    private final ItemTable view;

    /**
     * Instantiates a new Item Table presenter.
     *
     * @param view the view
     */
    public ItemTablePresenter(final ItemTable view) {
        this.view = view;
    }

    /**
     * Initializes the View.
     */
    public void init() {
        initTable();
        markItemDoubleClick();
    }

    /**
     * Selects the Row in the Table when Double Clicking on an item.
     * Adds Listener to all Item Nodes to check for Double Click.
     */
    private void markItemDoubleClick() {
        // All Tours
        for (var tour : model.getItemModel().getTourItemsMap().values()) {
            // All Item Nodes
            for (var itemNode : tour) {
                // Check Double Click
                itemNode.setOnMouseClicked(mouseEvent -> {
                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                        if(mouseEvent.getClickCount() == 2) {
                            // Search for Entry in Item Table
                            Platform.runLater(() -> {
                                // Reset the selection of rows
                                view.getTable().requestFocus();
                                view.getTable().getSelectionModel().clearSelection();
                                for (int i = 0; i < view.getTable().getItems().size(); ++i) {
                                    // Select the corresponding Row
                                    if (view.getTable().getItems().get(i).getItemId() == itemNode.getItemId()) {
                                        view.getTable().getSelectionModel().select(i);
                                        view.getTable().getFocusModel().focus(i);
                                        return;
                                    }
                                }
                            });
                        }
                    }
                });
            }
        }
    }

    /**
     * Initializes the Table Columns,
     * links the Item Properties to the Columns
     */
    private void initTable() {
        int cellHeight = 25;

        view.getTable().setEditable(true);
        view.getTable().setFixedCellSize(cellHeight);
        view.getTable().setMaxHeight(6 * cellHeight);
        view.getTable().setColumnResizePolicy(javafx.scene.control.TableView.UNCONSTRAINED_RESIZE_POLICY);
        view.getTable().setPrefWidth(App.WIN_WIDTH);

        view.getTable().getItems().setAll(model.getItemModel().getTourItemsMap().get(1));
        view.getTable().getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        view.getColumnId().setCellValueFactory(i -> i.getValue().itemIdProperty().asObject());
        view.getColumnTypeId().setCellValueFactory(i -> i.getValue().typeIdProperty().asObject());
        view.getColumnCustomerId().setCellValueFactory(i -> i.getValue().customerIdProperty().asObject());
        view.getColumnLength().setCellValueFactory(i -> i.getValue().lProperty().asObject());
        view.getColumnWidth().setCellValueFactory(i -> i.getValue().wProperty().asObject());
        view.getColumnHeight().setCellValueFactory(i -> i.getValue().hProperty().asObject());
        view.getColumnMass().setCellValueFactory(i -> i.getValue().massProperty().asObject());
        view.getColumnFragile().setCellValueFactory(i -> i.getValue().fragileProperty().asObject());
        view.getColumnLBS().setCellValueFactory(i -> i.getValue().lbsProperty().asObject());
        view.getColumnX().setCellValueFactory(i -> i.getValue().xProperty().asObject());
        view.getColumnY().setCellValueFactory(i -> i.getValue().yProperty().asObject());
        view.getColumnZ().setCellValueFactory(i -> i.getValue().zProperty().asObject());

        view.getColumnVisibility().setCellValueFactory(i -> i.getValue().visProperty());
        view.getColumnVisibility().setCellFactory(CheckBoxTableCell.forTableColumn(view.getColumnVisibility()));
        view.getColumnVisibility().setEditable(true);

        view.getColumnFeasible().setCellValueFactory(i -> i.getValue().feasibleProperty().asObject());
        view.getColumnError().setCellValueFactory(i -> i.getValue().errorProperty());

        view.getColumnColor().setCellValueFactory(i -> i.getValue().colorProperty());
        setColumnColor(view.getColumnColor());
        setFeasibleBackground(view.getColumnFeasible());

        // Remove Error and Feasibility Columns if Loading Constraints should not be checked
        if (!model.getCheckLoadingConstraints()) {
            view.getTable().getColumns().removeAll(view.getColumnFeasible(), view.getColumnError());
        }
    }

    /**
     * Removes the Color String and sets the background color of the cell instead.
     *
     * @param column the Column to set the Color
     */
    private void setColumnColor(TableColumn<ItemNode, Color> column) {
        column.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Color color, boolean empty) {
                super.updateItem(color, empty);

                // No Color at errors
                if (color == null || empty) {
                    setText("");
                    setStyle("");
                } else {
                    // Set the Background of the Cell according to the item & customer color
                    String hex = String.format( "#%02X%02X%02X",
                            (int)( color.getRed() * 255 ),
                            (int)( color.getGreen() * 255 ),
                            (int)( color.getBlue() * 255 ) );
                    setStyle("-fx-background-color:" + hex);
                }
            }
        });
    }

    /**
     * Set the Background Color of the Cell according to the Feasibility.
     *
     * @param column the Column to set the Color
     */
    private void setFeasibleBackground(final TableColumn<ItemNode, Boolean> column) {
        column.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean feasible, boolean empty) {
                super.updateItem(feasible, empty);

                // Feasible or unknown
                if (feasible == null || feasible) {
                    setStyle("-fx-background-color: #006400");

                // Infeasible
                } else {
                    setStyle("-fx-background-color: #8E1600");
                }
            }
        });
    }
}

package com.threedimensionalloadingcvrp.clp;

import com.threedimensionalloadingcvrp.clp.model.ItemNode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ItemTable extends Pane {

    /** The table. */
    private TableView<ItemNode> table;

    /** Table Column showing Item id. */
    private TableColumn<ItemNode, Integer> columnId;

    /** Table Column showing ItemType Id. */
    private TableColumn<ItemNode, Integer> columnTypeId;

    /** Table Column showing Customer Id. */
    private TableColumn<ItemNode, Integer> columnCustomerId;

    /** Table Column showing Item Length. */
    private TableColumn<ItemNode, Integer> columnLength;

    /** Table Column showing Item Width. */
    private TableColumn<ItemNode, Integer> columnWidth;

    /** Table Column showing Item Height. */
    private TableColumn<ItemNode, Integer> columnHeight;

    /** Table Column showing Item Mass. */
    private TableColumn<ItemNode, Double> columnMass;

    /** Table Column showing Item Fragility. */
    private TableColumn<ItemNode, Boolean> columnFragile;

    /** Table Column showing Item Load Bearing Strength. */
    private TableColumn<ItemNode, Double> columnLBS;

    /** Table Column showing x-Position of Item. */
    private TableColumn<ItemNode, Integer> columnX;

    /** Table Column showing y-Position of Item. */
    private TableColumn<ItemNode, Integer> columnY;

    /** Table Column showing z-Position of Item. */
    private TableColumn<ItemNode, Integer> columnZ;

    /** Table Column displaying used Color of Item. */
    private TableColumn<ItemNode, Color>  columnColor;

    /** Table Column setting the visibility of the Item. */
    private TableColumn<ItemNode, Boolean>  columnVisibility;

    /** Table Column showing the feasibility of the Item Position. */
    private TableColumn<ItemNode, Boolean>  columnFeasible;

    /** Table Column showing the violated constraints. */
    private TableColumn<ItemNode, String>  columnError;

    public ItemTable() {
        super();
        table = new TableView<>();

        columnId = new TableColumn<>("Id");
        columnTypeId = new TableColumn<>("Type Id");
        columnCustomerId = new TableColumn<>("Customer Id");
        columnLength = new TableColumn<>("Length");
        columnWidth = new TableColumn<>("Width");
        columnHeight = new TableColumn<>("Height");
        columnMass = new TableColumn<>("Mass");
        columnFragile = new TableColumn<>("Fragile");
        columnLBS = new TableColumn<>("LBS");
        columnX = new TableColumn<>("x");
        columnY = new TableColumn<>("y");
        columnZ = new TableColumn<>("z");
        columnColor = new TableColumn<>("Color");
        columnVisibility = new TableColumn<>("Visible");
        columnFeasible = new TableColumn<>("Feasible");
        columnError = new TableColumn<>("Errors");

        table.getColumns().addAll(columnVisibility, columnId, columnTypeId, columnCustomerId,
                columnLength, columnWidth, columnHeight, columnMass, columnFragile, columnLBS,
                columnX, columnY, columnZ, columnColor, columnFeasible, columnError);

        this.getChildren().add(table);
    }
}

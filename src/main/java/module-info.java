module com.threedimensionalloadingcvrp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires lombok;
    requires com.threedimensionalloadingcvrp.validator;

    opens com.threedimensionalloadingcvrp to javafx.fxml;
    exports com.threedimensionalloadingcvrp;
}
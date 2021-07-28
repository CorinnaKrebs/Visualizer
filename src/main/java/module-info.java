module com.threedimensionalloadingcvrp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires lombok;
    requires validator;

    opens com.threedimensionalloadingcvrp to javafx.fxml;
    exports com.threedimensionalloadingcvrp;
}
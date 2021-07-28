package com.threedimensionalloadingcvrp.clp;

import com.threedimensionalloadingcvrp.App;
import com.threedimensionalloadingcvrp.Presenter;
import com.threedimensionalloadingcvrp.clp.model.ItemNode;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The type Vehicle 3 d presenter.
 */
@Getter
@NoArgsConstructor
public class Vehicle3DPresenter extends Presenter {

    /**
     * Initialize the View.
     */
    public void init() {
        updateVehicle(model.getItemModel().getCurrentList());
    }

    /**
     * Update the Vehicles and its containing Item Nodes.
     *
     * @param list the list of Item Nodes
     */
    public void updateVehicle(final ObservableList<ItemNode> list) {
        var view = model.getItemModel().getVehicle3D();
        view.getChildren().clear();
        view.getChildren().add(view.getBox());
        view.getChildren().addAll(list);
        resetView();
    }

    /**
     * Reset the View (Rotation, Scale Factor).
     */
    public void resetView() {
        var view = model.getItemModel().getVehicle3D();
        view.getRx().setAngle(30.0);
        view.getRy().setAngle(-140);
        view.getRz().setAngle(0);

        double scaleFactorY = App.WIN_HEIGHT / (3.0 * model.getInstance().getVehicle().getL());
        double scaleFactorX = App.WIN_WIDTH / (3.0 * model.getInstance().getVehicle().getW());
        double scaleFactor = Math.min(scaleFactorX, scaleFactorY);

        view.getScale().setX(scaleFactor);
        view.getScale().setY(scaleFactor);
        view.getScale().setZ(scaleFactor);
    }
}

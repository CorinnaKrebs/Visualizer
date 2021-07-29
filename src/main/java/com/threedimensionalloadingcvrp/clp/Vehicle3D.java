package com.threedimensionalloadingcvrp.clp;

import com.threedimensionalloadingcvrp.App;
import javafx.scene.Group;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import lombok.Data;

/**
 * The 3D Vehicle.
 */
@Data
public class Vehicle3D extends Group {

    /** The Box representing the vehicle */
    private Box box;

    /** The Rotation along x-axis. */
    private Rotate rx;

    /** The Rotation along y-axis. */
    private Rotate ry;

    /** The Rotation along z-axis. */
    private Rotate rz;

    /** The Scale Transform. */
    public Scale scale;

    /**
     * Instantiates a new 3D Vehicle.
     *
     * @param length the length of the cargo space
     * @param width  the width of the cargo space
     * @param height the height of the cargo space
     */
    public Vehicle3D(final int length, final int width, final int height) {
        super();
        rx = new Rotate();
        ry = new Rotate();
        rz = new Rotate();
        rx.setAxis(Rotate.X_AXIS);
        ry.setAxis(Rotate.Y_AXIS);
        rz.setAxis(Rotate.Z_AXIS);
        scale = new Scale();
        getTransforms().addAll(rx, rz, ry, scale);

        box = new Box();
        box.setTranslateZ(length * 0.5); // x
        box.setTranslateX(-width * 0.5); // y
        box.setTranslateY(-height * 0.5); // z
        box.setDrawMode(DrawMode.LINE); // Only Lines

        box.setDepth(length);
        box.setWidth(width);
        box.setHeight(height);

        this.setTranslateX(App.WIN_WIDTH / 2.0);
        this.setTranslateY(App.WIN_HEIGHT / 4.0);
    }
}

package com.threedimensionalloadingcvrp.clp;

import com.threedimensionalloadingcvrp.Presenter;
import javafx.scene.SubScene;
import lombok.Data;

/**
 * The Presenter for the 3D SubScene.
 */
@Data
public class Scene3DPresenter extends Presenter {

    /** The view to control */
    private final SubScene view;

    /** New Mouse x-Position */
    private static double mousePosX;

    /** New Mouse y-Position */
    private static double mousePosY;

    /** Old Mouse x-Position */
    private static double mouseOldX;

    /** Old Mouse y-Position */
    private static double mouseOldY;

    /**
     * Instantiates a new Scene3D presenter.
     *
     * @param view the view
     */
    public Scene3DPresenter(final SubScene view) {
        this.view = view;
    }

    /**
     * Initialize the View.
     */
    public void init() {
        initSubScene();
    }

    /**
     * Add Mouse Events to the 3D SubScene.
     */
    private void initSubScene() {
        var stack = model.getItemModel().getVehicle3D();

        // Save Mouse Position when Start Pressing
        view.setOnMousePressed(event -> {
            mousePosX = event.getX();
            mousePosY = event.getY();
            mouseOldX = event.getX();
            mouseOldY = event.getY();
        });

        // Scroll => Scale the Stack
        view.setOnScroll(event -> {
            double factor = event.getDeltaY() < 0 ? 0.75 : 1.25;
            double newScale = stack.getScale().getX() * factor;
            stack.getScale().setX(newScale);
            stack.getScale().setY(newScale);
            stack.getScale().setZ(newScale);
        });

        view.setOnMouseDragged(event -> {
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
            mousePosX = event.getX();
            mousePosY = event.getY();
            double mouseDeltaX = mousePosX - mouseOldX;
            double mouseDeltaY = mousePosY - mouseOldY;

            // Primary Button => Move the Stack
            if (event.isPrimaryButtonDown()) {
                stack.setTranslateX(stack.getTranslateX() + mouseDeltaX);
                stack.setTranslateY(stack.getTranslateY() + mouseDeltaY);
            }

            // Secondary Button => Rotate the Stack around origin
            else if (event.isSecondaryButtonDown()) {
                stack.getRx().setAngle(stack.getRx().getAngle() + mouseDeltaY);
                stack.getRy().setAngle(stack.getRy().getAngle() - mouseDeltaX);
            }
        });
    }
}

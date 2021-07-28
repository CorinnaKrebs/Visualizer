package com.threedimensionalloadingcvrp;

import com.threedimensionalloadingcvrp.main.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {
    /**
     * The constant representing the window width.
     */
    public static final int WIN_WIDTH = 1024;
    /**
     * The constant representing the window height.
     */
    public static final int WIN_HEIGHT = 600;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */

    // Main Function
    public static void main(String[] args) {
        launch();
    }

    public void start(Stage stage) {
        stage.setTitle("Visualization of 3L-CVRP");

        // Create Start View
        MainView view = new MainView();
        Presenter.model = new Model();

        // Create Scene
        Scene scene = new Scene(view, App.WIN_WIDTH, App.WIN_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

}
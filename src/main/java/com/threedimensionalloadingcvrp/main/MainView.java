package com.threedimensionalloadingcvrp.main;

import com.threedimensionalloadingcvrp.main.menu.MenuPane;
import com.threedimensionalloadingcvrp.main.menu.MenuPresenter;
import com.threedimensionalloadingcvrp.main.start.StartView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * The Main view.
 */
public class MainView extends BorderPane {

    /** The Menu */
    private final MenuPane menu;

    /** The Content */
    private final Pane content;

    /**
     * Instantiates a new Main view.
     */
    public MainView() {
        super();
        menu = new MenuPane();
        MenuPresenter menuPresenter = new MenuPresenter(menu, this);
        menuPresenter.init();

        content = new StartView();

        setTop(menu);
        setCenter(content);
    }
}

package com.threedimensionalloadingcvrp.main.menu;

import com.threedimensionalloadingcvrp.Presenter;
import com.threedimensionalloadingcvrp.main.MainView;
import com.threedimensionalloadingcvrp.main.menu.fileopen.FileOpenPresenter;
import com.threedimensionalloadingcvrp.main.menu.fileopen.FileOpenView;

/**
 * The type Menu presenter.
 */
public class MenuPresenter extends Presenter {

    /** The menu */
    private final MenuPane menuPane;

    /** The view to control */
    private final MainView mainView;

    /**
     * Instantiates a new Menu presenter.
     *
     * @param menuPane the menu pane
     * @param mainView the main view
     */
    public MenuPresenter(final MenuPane menuPane, final MainView mainView) {
        this.menuPane = menuPane;
        this.mainView = mainView;
    }

    /**
     * Initializes the View.
     */
    public void init() {
        menuPane.getStartItemOpen().setOnAction (event -> handleItemOpen());
        menuPane.getStartItemClose().setOnAction(event -> handleItemClose());
    }

    /**
     * Close Application.
     */
    private void handleItemClose() {
        System.exit(1);
    }

    /**
     * Change view to FileOpen.
     */
    private void handleItemOpen() {
        FileOpenView view = new FileOpenView();
        FileOpenPresenter fileOpenPresenter = new FileOpenPresenter(view);
        fileOpenPresenter.init();
        mainView.setCenter(view);
    }
}

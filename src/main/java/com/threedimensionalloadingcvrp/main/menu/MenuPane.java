package com.threedimensionalloadingcvrp.main.menu;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The Menu pane.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MenuPane extends MenuBar {

    /** The Start Menu */
    private Menu    menuStart;

    /** The SubItem of Start to Open/Select the Files */
    private MenuItem startItemOpen;

    /** The SubItem of Start to Close the Application */
    private MenuItem startItemClose;

    /**
     * Instantiates a new Menu pane.
     */
    public MenuPane() {
        super();

        menuStart = new Menu("Start");

        // create Start Menu Items
        startItemOpen = new MenuItem("Open");
        startItemClose = new MenuItem("Close");

        // add Start Menu items
        menuStart.getItems().add(startItemOpen);
        menuStart.getItems().add(startItemClose);

        // add menu to menubar
        getMenus().addAll(menuStart);
    }
}

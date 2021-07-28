package com.threedimensionalloadingcvrp.vrp;

import com.threedimensionalloadingcvrp.Model;
import com.threedimensionalloadingcvrp.Presenter;
import com.threedimensionalloadingcvrp.support.SavePresenter;
import com.threedimensionalloadingcvrp.support.Support;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * The VRP presenter.
 */
public class VRPPresenter extends Presenter {

    /** The entire VRP stage */
    private final VRPWindow stage;

    /** The Presenter for handling the Animations */
    private AnimationPresenter animationPresenter;

    /** The Presenter for handling the Customer Slider Actions */
    private final CustomerSliderPresenter customerSliderPresenter;

    /** The Presenter for handling the Tour Tree */
    private TourTreePresenter tourTreePresenter;

    /** The Presenter for handling the Customer Tree */
    private CustomerTreePresenter customerTreePresenter;

    /** The Presenter for saving the 3D Scene */
    private SavePresenter savePresenter;

    /** New Mouse x-Position */
    private static double mousePosX;

    /** New Mouse y-Position */
    private static double mousePosY;

    /** Old Mouse x-Position */
    private static double mouseOldX;

    /** Old Mouse y-Position */
    private static double mouseOldY;

    /**
     * Instantiates a new VRP presenter.
     *
     * @param stage the stage
     */
    public VRPPresenter(final VRPWindow stage) {
        this.stage = stage;
        customerSliderPresenter = new CustomerSliderPresenter(stage.getCustomerSlider());
    }

    /**
     * Initializes the View.
     *
     * @param error the error messages for routing constraints
     */
    public void init(String error) {
        Support.calcArrivalTimes(model);

        initCenter();
        initLeftMenu(error);
        initBottomPane();
        customerSliderPresenter.init((int) model.getSolution().getTotal_travel_time());

        tourTreePresenter = new TourTreePresenter(stage.getTourTree());
        tourTreePresenter.init();

        customerTreePresenter = new CustomerTreePresenter(stage.getCustomerTree());
        customerTreePresenter.init();

        savePresenter = new SavePresenter(stage.getButtonSave(), stage.getContent().getCenter());
        savePresenter.init();

        animationPresenter = new AnimationPresenter(stage, customerSliderPresenter);
        animationPresenter.flyInCustomers();

        customerSliderPresenter.getView().getSlider().prefWidthProperty().bind(stage.widthProperty().multiply(0.98));
    }

    /**
     * Initializes the Center Content.
     */
    private void initCenter() {
        ZoomableArea pane = new ZoomableArea();

        // Add all Customer Pins
        for (final var customerNode : model.getCustomerModel().getCustomerNodes().values()) {
            pane.getGroup().getChildren().add(customerNode.getNode());
        }

        // Add all Tour Nodes
        for (final var tourNode : model.getTourModel().getTourNodes().values()) {
            pane.getGroup().getChildren().add(tourNode.getNode());
        }
        stage.setCenterPane(pane);
        stage.getContent().setCenter(pane);
        pane.setStyle("-fx-background-color:transparent;");
        addMouseEvents(stage.getCenterPane().getGroup());
    }

    /**
     * Adds Mouse Events.
     * @param stack the stack to add the mouse events.
     */
    private void addMouseEvents(final Group stack) {
        // Save Mouse Position when Start Pressing
        stack.setOnMousePressed(event -> {
            mousePosX = event.getX();
            mousePosY = event.getY();
            mouseOldX = event.getX();
            mouseOldY = event.getY();
        });

        // Scroll => Scale the Stack
        stage.getCenterPane().setOnScroll(event -> {
            double factor = event.getDeltaY() < 0 ? 0.75 : 1.25;
            double newScale = stage.getCenterPane().getScale().getX() * factor;
            stage.getCenterPane().getScale().setX(newScale);
            stage.getCenterPane().getScale().setY(newScale);
            stage.getCenterPane().getScale().setZ(newScale);
        });

        stack.setOnMouseDragged(event -> {
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
            mousePosX = event.getX();
            mousePosY = event.getY();
            double mouseDeltaX = mousePosX - mouseOldX;
            double mouseDeltaY = mousePosY - mouseOldY;
            mouseDeltaX = mouseDeltaX < 0 ? Math.max(-200, mouseDeltaX) : Math.min(200, mouseDeltaX);
            mouseDeltaY = mouseDeltaY < 0 ? Math.max(-200, mouseDeltaY) : Math.min(200, mouseDeltaY);

            // Primary Button => Move the Stack
            if (event.isPrimaryButtonDown()) {
                stack.setTranslateX(stack.getTranslateX() + mouseDeltaX);
                stack.setTranslateY(stack.getTranslateY() + mouseDeltaY);
            }
        });
    }

    /**
     * Initializes the Left Menu
     * @param error the error messages
     */
    private void initLeftMenu(final String error) {
        ScrollPane pane = new ScrollPane();
        pane.setFitToWidth(true);
        VBox vBox = new VBox();
        pane.setContent(vBox);

        // Create Tour Tree
        var tourTree = new TourTree(model.getSolution());
        stage.setTourTree(tourTree);
        vBox.getChildren().add(tourTree);

        // Create Customer Tree
        var customerTree = new CustomerTree(model.getInstance());
        stage.setCustomerTree(customerTree);
        vBox.getChildren().add(customerTree);

        // Create Result Info
        VBox resultInfos = new ResultInfo(model.getSolution());
        vBox.getChildren().add(resultInfos);

        // Add Feasibility Info
        Label labelError = new Label();
        if (model.getCheckRoutingConstraints()) {
            labelError.setText("Routes Feasibility: " + model.getSolution().isFeasible());
            if (!model.getSolution().isFeasible())
                Tooltip.install(labelError, new Tooltip(error));
            vBox.getChildren().add(labelError);
        }

        stage.getContent().setLeft(pane);
    }

    /**
     * Initializes Bottom Pane.
     */
    private void initBottomPane() {
        HBox hBox = new HBox();

        // Initialize Bottom and Slider
        stage.getCustomerSlider().getPlayButton().setOnAction(event -> animationPresenter.buttonPlayClick());
        customerSliderPresenter.setPlayButtonGraphic(false);
        hBox.getChildren().setAll(stage.getCustomerSlider());

        // Initialize Gantt Chart
        VBox vBox = new VBox();
        GanttChart<Number, String> chart = DiagramTourGantt.create(model);
        HBox legend = DiagramTourGantt.createLegend();
        vBox.getChildren().addAll(hBox, chart, legend);

        stage.getContent().setBottom(vBox);
    }
}
package com.threedimensionalloadingcvrp.vrp;

import com.threedimensionalloadingcvrp.Presenter;
import com.threedimensionalloadingcvrp.support.Support;
import com.threedimensionalloadingcvrp.validator.model.Tour;
import com.threedimensionalloadingcvrp.vrp.model.CustomerNode;
import com.threedimensionalloadingcvrp.vrp.model.TourNode;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicInteger;

import static com.threedimensionalloadingcvrp.support.Support.getCurrent;
import static com.threedimensionalloadingcvrp.support.Support.getNext;
import static javafx.animation.Animation.Status.RUNNING;

/**
 * The type Animation presenter.
 */
public class AnimationPresenter extends Presenter {

    /** The entire VRP Window */
    private final VRPWindow stage;

    /** The Timeline for handling animations */
    private static Timeline timeline;

    /** The connected Presenter for the Customer Slider */
    private final CustomerSliderPresenter customerSliderPresenter;

    /**
     * Instantiates a new Animation presenter.
     *
     * @param stage                   the stage
     * @param customerSliderPresenter the customer slider presenter
     */
    public AnimationPresenter(VRPWindow stage, CustomerSliderPresenter customerSliderPresenter) {
        this.stage = stage;
        this.customerSliderPresenter = customerSliderPresenter;

        // Starts Animation with Initialization
        customerSliderPresenter.getView().getSlider()
                .valueProperty().addListener(event -> updateTours((int) customerSliderPresenter.getView().getSlider().getValue()));

        customerSliderPresenter.getView().getSlider().setOnMouseClicked(event -> {
            customerSliderPresenter.setPlayButtonGraphic(true);
            timeline.pause();
        });
    }

    /**
     * The Customer are flying into the stage.
     */
    void flyInCustomers() {
        // Animate Intro
        timeline = new Timeline();

        // The y-Position of each Customer is shifted by 30 units.
        // This animation moves each customer by one unit per animation step.
        // The total number of animation steps is 30 (= cycle count)
        // to move each Customer to the correct position.
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(50),
                e -> model.getCustomerModel().getCustomerNodes().values().forEach(CustomerNode::incrementY)));
        timeline.setCycleCount(30);

        // After flying in the customers, the Animation of the Tours is started.
        timeline.setOnFinished(e -> animateTours(0));
        timeline.play();
    }

    /**
     * Animate tours.
     *
     * @param startTime the start time
     */
    void animateTours(final int startTime) {
        // Animate Intro
        AtomicInteger start = new AtomicInteger(startTime);
        timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(50), e -> {
            // Calculate the current lines
            updateTours(start.getAndIncrement());
            // Update the Slider to match with TimeLine
            customerSliderPresenter.getView().getSlider().setValue(start.get());
        }));

        // Total Time of Animation corresponds to the total travel time of the tour
        timeline.setCycleCount((int) model.getSolution().getTotal_travel_time());

        // Change the Symbol of the Play Button in the end of the animation
        timeline.setOnFinished(e -> customerSliderPresenter.setPlayButtonGraphic(true));

        // Start Animation
        timeline.play();

    }

    /**
     * Update tours.
     *
     * @param currentTime the current time
     */
    void updateTours(final int currentTime) {
        for (TourNode tourNode : model.getTourModel().getTourNodes().values()) {
            final int currentCustomerIndex = getCurrentCustomerIndex(tourNode.getTour(), currentTime);
            setCurrentPosition(tourNode,  currentCustomerIndex, currentTime);
            createPreviousLines(tourNode, currentCustomerIndex);
        }
    }

    /**
     * Gets the index in the sequence of the current customer for a specific time.
     *
     * @param tour the tour for which the position should be determined
     * @param currentTime the current time to get the position
     */
    private int getCurrentCustomerIndex(final Tour tour, final int currentTime) {
        for (int i = -1; i < tour.getCustomer_ids().size(); ++i) {
            final CustomerNode customer = getCurrent(model.getCustomerModel(), tour, i);
            final CustomerNode next = getNext(model.getCustomerModel(), tour, i);
            if (currentTime >= customer.getArrivalTime() && currentTime < next.getArrivalTime()) {
                return i;
            }
        }
        return tour.getCustomer_ids().size() -1;
    }

    /**
     * Sets the current position of the vehicle and creates lines.
     *
     * @param tourNode the vehicle node (tour node)
     * @param currentCustomerIndex the index of the current customer
     * @param currentTime the current time
     */
    private void setCurrentPosition(final TourNode tourNode, final int currentCustomerIndex, final int currentTime) {
        // Determine the current Customer and the next Customer in the Tour
        final CustomerNode currentNode = getCurrent(model.getCustomerModel(), tourNode.getTour(), currentCustomerIndex);
        final CustomerNode nextNode = getNext(model.getCustomerModel(), tourNode.getTour(), currentCustomerIndex);

        // Determine the Departure Time for the Current Customer
        double departureTime = currentNode.getCustomer().getId() == 0 ?
                currentNode.getCustomer().getServiceTime() : currentNode.getDepartureTime();

        double x, y;

        // Check if the Vehicle is at the customer location
        if (currentTime <= departureTime) {
            // Set the Vehicle Node over the Customer Pin
            x = currentNode.getX();
            y = currentNode.getY();

        // the vehicle is on the way to the next customer
        } else {
            // Calculate the current Position between two Customers
            final double distance = Support.getDistance(currentNode.getCustomer(), nextNode.getCustomer());
            final double difference = currentTime - departureTime;
            final double part = Math.min(difference / distance, 1);
            x = (1 - part) * currentNode.getX() + part * nextNode.getX();
            y = (1 - part) * currentNode.getY() + part * nextNode.getY();
        }

        // Delete all Nodes
        stage.getCenterPane().getGroup().getChildren().removeAll(tourNode.getPaths());
        stage.getCenterPane().getGroup().getChildren().remove(tourNode.getNode());
        tourNode.getPaths().clear();

        // Set Vehicle Position
        tourNode.getNode().setLayoutX(x);
        tourNode.getNode().setLayoutY(y);
        stage.getCenterPane().getGroup().getChildren().add(tourNode.getNode());

        // Create Line to Vehicle
        final Line line1 = new Line(x, y, currentNode.getX(), currentNode.getY());
        tourNode.getPaths().add(line1);
        stage.getCenterPane().getGroup().getChildren().add(0, line1);
    }

    /**
     * Creates all lines of the already visited customers.
     *
     * @param tourNode the vehicle node (tour node)
     * @param currentCustomerIndex the index of the current customer
     */
    private void createPreviousLines(final TourNode tourNode, final int currentCustomerIndex) {
        for (int i = -1; i < currentCustomerIndex; ++i) {
            final CustomerNode currentNode = getCurrent(model.getCustomerModel(), tourNode.getTour(), i);
            final CustomerNode nextNode = getNext(model.getCustomerModel(), tourNode.getTour(), i);
            final double x1 = currentNode.getX();
            final double y1 = currentNode.getY();
            final double x2 = nextNode.getX();
            final double y2 = nextNode.getY();
            final Line line2 = new Line(x1, y1, x2, y2);
            tourNode.getPaths().add(line2);
            stage.getCenterPane().getGroup().getChildren().add(0, line2);
        }
    }

    /**
     * Play Button.
     */
    void buttonPlayClick() {
        if (timeline.getStatus() == RUNNING) {
            customerSliderPresenter.setPlayButtonGraphic(true);
            timeline.pause();
        } else {
            customerSliderPresenter.setPlayButtonGraphic(false);
            animateTours((int) customerSliderPresenter.getView().getSlider().getValue());
        }
    }

}

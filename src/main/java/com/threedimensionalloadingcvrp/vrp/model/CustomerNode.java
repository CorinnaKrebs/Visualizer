package com.threedimensionalloadingcvrp.vrp.model;

import com.threedimensionalloadingcvrp.validator.model.Customer;
import javafx.scene.Node;
import lombok.Data;

/**
 * The Customer node.
 */
@Data
public class CustomerNode {

    /** The Customer Data */
    private Customer customer;

    /** The Waiting Time */
    private int waitingTime;

    /** The Arrival Time */
    private int arrivalTime;

    /** The Departure Time */
    private int departureTime;

    /** The Customer Pin (Node) */
    private Node node;

    /**
     * Instantiates a new Customer node.
     *
     * @param customer the customer
     */
    public CustomerNode(Customer customer) {
        this.customer = customer;
    }

    /**
     * Sets y based on the bottommost edge.
     *
     * @param y the y
     */
    public void setY(double y) {
        node.setLayoutY(y - 20);
    }

    /**
     * Increment y by one.
     */
    public void incrementY() {
        setY(getY() + 1);
    }

    /**
     * Gets y based on the bottommost edge.
     *
     * @return the y
     */
    public double getY() {
        return node.getLayoutY() + 20;
    }

    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(double x) {
        node.setLayoutX(x);
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public double getX() {
        return node.getLayoutX();
    }
}

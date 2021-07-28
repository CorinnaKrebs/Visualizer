package com.threedimensionalloadingcvrp.vrp.model;

import com.threedimensionalloadingcvrp.Model;
import com.threedimensionalloadingcvrp.validator.model.Customer;
import com.threedimensionalloadingcvrp.vrp.CustomerPin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import lombok.Data;

/**
 * The type Customer model.
 */
@Data
public class CustomerModel {
    private final ObservableMap<Integer, CustomerNode> customerNodes;

    /** The minimum x-coordinate of all customers */
    private int customer_minX;

    /** The minimum y-coordinate of all customers */
    private int customer_minY;

    /** The maximum x-coordinate of all customers */
    private int customer_maxX;

    /** The maximum y-coordinate of all customers */
    private int customer_maxY;

    /** The minimum x-coordinate for the content position */
    private int window_minX = 30;

    /** The maximum x-coordinate for the content position */
    private int window_maxX = 500;

    /** The minimum y-coordinate for the content position */
    private int window_minY = 30;

    /** The maxmimum y-coordinate for the content position */
    private int window_maxY = 350;

    /**
     * Instantiates a new Customer model.
     *
     * @param model the model
     */
    public CustomerModel(final Model model) {
        customer_minX = Integer.MAX_VALUE;
        customer_minY = Integer.MAX_VALUE;
        customer_maxX = 0;
        customer_maxY = 0;

        // Determine minimum and maximum Coordinates
        for (final Customer customer : model.getInstance().getCustomers()) {
            customer_minX = Math.min((int) customer.getX(), customer_minX);
            customer_minY = Math.min((int) customer.getY(), customer_minY);
            customer_maxX = Math.max((int) customer.getX(), customer_maxX);
            customer_maxY = Math.max((int) customer.getY(), customer_maxY);
        }

        customerNodes = FXCollections.observableHashMap();

        // Create Customer Nodes
        for (final Customer customer : model.getInstance().getCustomers()) {
            CustomerNode customerNode = new CustomerNode(customer);

            final String strToolTip = "Id: " + customer.getId() + ";\n"
                    + "x: " + customer.getX() + ";\n"
                    + "y: " + customer.getY() + ";\n"
                    + "Items: " + customer.getItem_ids().size() + "\n";

            final String strCustomerId = String.valueOf(customer.getId());
            final Color color = model.getCustomerColors().get(customer.getId());

            final CustomerPin pin = new CustomerPin(color, strCustomerId);
            Tooltip.install(pin, new Tooltip(strToolTip));

            customerNode.setNode(pin);
            customerNode.setX(getXCoordinate(customer.getX()));
            customerNode.setY(getYCoordinate(customer.getY()) - 30);
            customerNodes.put(customer.getId(), customerNode);
        }
    }

    /**
     * Gets x-layout-coordinate for a given customer x-coordinate.
     *
     * @param x the current x-coordinate
     */
    private double getXCoordinate(final double x) {
        return (window_maxX - window_minX) * (x - customer_minX) / (customer_maxX - customer_minX) + window_minX;
    }

    /**
     * Gets y-layout-coordinate for a given customer y-coordinate.
     *
     * @param y the current y-coordinate
     */
    private double getYCoordinate(final double y) {
        return (window_maxY - window_minY) * (y - customer_minY) / (customer_maxY - customer_minY) + window_minY;
    }

    /**
     * Gets customer node.
     *
     * @param id the id
     * @return the customer node
     */
    public CustomerNode getCustomerNode(final int id) {
        return customerNodes.get(id);
    }
}

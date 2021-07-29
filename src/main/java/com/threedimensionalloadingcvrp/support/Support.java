package com.threedimensionalloadingcvrp.support;

import com.threedimensionalloadingcvrp.Model;
import com.threedimensionalloadingcvrp.validator.model.Customer;
import com.threedimensionalloadingcvrp.validator.model.Tour;
import com.threedimensionalloadingcvrp.vrp.model.CustomerModel;
import com.threedimensionalloadingcvrp.vrp.model.CustomerNode;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * A Support Class.
 */
public class Support {
    /**
     * Gets the euclidean distance between two customers.
     *
     * @param customer1 the customer 1
     * @param customer2 the customer 2
     * @return the distance
     */
    public static int getDistance(final Customer customer1, final Customer customer2) {
        double result = sqrt(pow((customer1.getX() - customer2.getX()), 2)  + pow((customer1.getY() - customer2.getY()), 2));
        return (int) Math.ceil(result);
    }

    /**
     * Gets the next Customer in the Tour.
     *
     * @param customerModel the customer model
     * @param tour          the tour
     * @param i             the current index
     * @return the next customer
     */
    public static CustomerNode getNext(final CustomerModel customerModel, final Tour tour, final int i) {
        // Get next Customer
        if (i + 1 < tour.getCustomer_ids().size()) {
            int customerId = tour.getCustomer_ids().get(i + 1);
            return customerModel.getCustomerNode(customerId);
        }
        // Last Customer is Depot
        return customerModel.getCustomerNode(0);
    }

    /**
     * Gets the current Customer in the Tour.
     *
     * @param customerModel the customer model
     * @param tour          the tour
     * @param i             the current index
     * @return the current customer
     */
    public static CustomerNode getCurrent(CustomerModel customerModel, Tour tour, int i) {
        if (i == -1 || i == tour.getCustomer_ids().size()) {
            return customerModel.getCustomerNode(0);
        }
        int currentId = tour.getCustomer_ids().get(i);
        return customerModel.getCustomerNode(currentId);
    }

    /**
     * Calculates and saves arrival times per customer.
     *
     * @param model the model
     */
    public static void calcArrivalTimes(final Model model) {
        // Calculate Time and Distance
        int totalTime = 0;
        int totalDistance = 0;
        CustomerModel customerModel = model.getCustomerModel();

        // Iterate trough Tour
        for (final Tour tour : model.getSolution().getTours()) {
            int time = 0;
            for (int i = -1; i < tour.getCustomer_ids().size(); ++i) {
                CustomerNode currentNode = getCurrent(customerModel, tour, i);
                CustomerNode nextNode = getNext(customerModel, tour, i);

                // Travel to Next Customer
                final int distance = Support.getDistance(currentNode.getCustomer(), nextNode.getCustomer());
                time += distance;

                // Check Opening Time Window, Calculate Waiting Time
                final int waiting = time < nextNode.getCustomer().getReadyTime() ? nextNode.getCustomer().getReadyTime() - time : 0;
                nextNode.setWaitingTime(waiting);

                time += waiting;

                // Set Arrival Time
                nextNode.setArrivalTime(time);

                // Add Service Times
                time += nextNode.getCustomer().getServiceTime();

                // Set Departure Time
                nextNode.setDepartureTime(time);

                // Update Total Time
                totalTime = Math.max(totalTime, time);
                totalDistance += distance;
            }
        }

        // Save Data
        customerModel.getCustomerNode(0).setArrivalTime(0);
        model.getSolution().setTotal_travel_time(totalTime);
    }
}
package com.threedimensionalloadingcvrp.vrp;


import com.threedimensionalloadingcvrp.Model;
import com.threedimensionalloadingcvrp.support.Support;
import com.threedimensionalloadingcvrp.validator.model.Tour;
import com.threedimensionalloadingcvrp.vrp.model.CustomerNode;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * The Tour Gantt Diagram.
 */
public class DiagramTourGantt {

    /**
     * Create Gantt Chart.
     *
     * @param model the model
     * @return the Gantt Chart
     */
    public static GanttChart<Number, String> create(Model model) {
        final int totalTime = (int) model.getSolution().getTotal_travel_time();
        final int tick = totalTime / 20;
        final GanttChart<Number,String> chart = new GanttChart<>(new NumberAxis(0, totalTime, tick), new CategoryAxis());

        chart.getYAxis().setTickLabelGap(0);
        chart.setPrefHeight(100);

        // Legend is created outside
        chart.setLegendVisible(false);

        // Create the Tour DataSeries
        for (Tour tour : model.getSolution().getTours()) {
            String name = "Tour " + tour.getId();
            XYChart.Series<Number, String> series = new XYChart.Series<>();

            CustomerNode lastNode = model.getCustomerModel().getCustomerNode(0);
            series.getData().add(new XYChart.Data<>(0, name,
                    new GanttChart.AdditionalData(lastNode.getCustomer().getServiceTime(),
                            getColor(TimeStatus.SERVICETIME), "0")));

            // Create TimeStatus Rectangles per Customer
            int x = lastNode.getCustomer().getServiceTime();
            for (int customer_id : tour.getCustomer_ids()) {
                String id = String.valueOf(customer_id);
                final CustomerNode customerNode = model.getCustomerModel().getCustomerNode(customer_id);
                final int distance = Support.getDistance(lastNode.getCustomer(), customerNode.getCustomer());

                series.getData().add(new XYChart.Data<>(x, name, new GanttChart.AdditionalData(distance, getColor(TimeStatus.TRAVELLING), id)));
                x += distance;

                series.getData().add(new XYChart.Data<>(x, name, new GanttChart.AdditionalData(customerNode.getWaitingTime(), getColor(TimeStatus.WAITING), id)));
                x += customerNode.getWaitingTime();

                series.getData().add(new XYChart.Data<>(x, name, new GanttChart.AdditionalData(customerNode.getCustomer().getServiceTime(), getColor(TimeStatus.SERVICETIME), id)));
                x += customerNode.getCustomer().getServiceTime();

                lastNode = customerNode;
            }

            // Add Rectangle to Depot
            CustomerNode depotNode = model.getCustomerModel().getCustomerNode(0);
            final int distance = Support.getDistance(lastNode.getCustomer(), depotNode.getCustomer());
            series.getData().add(new XYChart.Data<>(x, name, new GanttChart.AdditionalData(distance, getColor(TimeStatus.TRAVELLING), "0")));
            chart.getData().add(series);
        }

        return chart;
    }

    /**
     * Create the legend layout.
     *
     * @return the legend layout
     */
    public static HBox createLegend() {
        HBox hBox = new HBox();
        for (final TimeStatus status : TimeStatus.values()) {
            hBox.getChildren().addAll(createLegendItem(status));
        }
        hBox.setSpacing(20);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    /**
     * Create a legend item / entry.
     *
     * @return the legend entry
     */
    private static HBox createLegendItem(final TimeStatus status) {
        Rectangle rectangle = new Rectangle();
        rectangle.setX(0);
        rectangle.setY(0);
        rectangle.setWidth(30);
        rectangle.setHeight(15);
        rectangle.setFill(Color.web(getColor(status)));

        String str = status.toString().toLowerCase();
        Label label = new Label(str);
        HBox group = new HBox();
        group.getChildren().addAll(rectangle, label);
        return group;
    }

    /**
     * Gets the color for the TimeStatus.
     *
     * @return the color string
     */
    private static String getColor(final TimeStatus status) {
        switch (status) {
            case TRAVELLING:
                return "#c0c0c0"; // silver

            case WAITING: // Rot
                return "#ff0000";

            case SERVICETIME: // Green
                return "#00ff00";
        }
        return "black";
    }
}
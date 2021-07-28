package com.threedimensionalloadingcvrp.vrp;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.beans.NamedArg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The Gantt chart.
 * Most Methods are based on the original implementation for the Bubble Chart.
 *
 * @param <X> the x parameter
 * @param <Y> the y parameter
 */
public class GanttChart<X,Y> extends XYChart<X,Y> {

    /**
     * Additional data class.
     */
    @Getter
    @Setter
    public static class AdditionalData {
        /**
         * The Length / Duration of one Block.
         */
        public int length;
        /**
         * The Color of the Block.
         */
        public String color;
        /**
         * The Label of the Block.
         */
        public String label;

        /**
         * Instantiates a new Additional data.
         *
         * @param duration the duration
         * @param color    the color
         * @param label    the label
         */
        public AdditionalData(final int duration, final String color, final String label) {
            super();
            this.length = duration;
            this.color = color;
            this.label = label;
        }
    }

    /** The Fixed height of the Block. */
    private final double blockHeight = 10;

    /**
     * Instantiates a new Gantt chart.
     *
     * @param xAxis the x axis
     * @param yAxis the y axis
     */
    public GanttChart(@NamedArg("xAxis") Axis<X> xAxis, @NamedArg("yAxis") Axis<Y> yAxis) {
        this(xAxis, yAxis, FXCollections.<Series<X, Y>>observableArrayList());
    }

    /**
     * Instantiates a new Gantt chart.
     *
     * @param xAxis the x axis
     * @param yAxis the y axis
     * @param data  the data
     */
    public GanttChart(@NamedArg("xAxis") Axis<X> xAxis, @NamedArg("yAxis") Axis<Y> yAxis, @NamedArg("data") ObservableList<Series<X,Y>> data) {
        super(xAxis, yAxis);
        if (!(xAxis instanceof ValueAxis && yAxis instanceof CategoryAxis)) {
            throw new IllegalArgumentException("Axis type incorrect, X and Y should both be NumberAxis");
        }
        setData(data);
    }


    /**
     * Gets the color.
     *
     * @param object the object to get the color
     * @return the color string
     */
    private static String getColor(Object object) {
        return ((AdditionalData) object).getColor();
    }

    /**
     * Gets the duration.
     *
     * @param object the object to get the duration
     * @return the duration
     */
    public static double getDuration(Object object) {
        return ((AdditionalData) object).getLength();
    }

    /**
     * Gets the Label.
     *
     * @param object the object to get the text
     * @return the text
     */
    public static String getLabel(Object object) {
        return ((AdditionalData) object).getLabel();
    }

    @Override protected void layoutPlotChildren() {

        for (Series<X,Y> series : getData()) {

            Iterator<Data<X,Y>> iter = getDisplayedDataIterator(series);
            while(iter.hasNext()) {
                Data<X,Y> item = iter.next();
                double x = getXAxis().getDisplayPosition(item.getXValue());
                double y = getYAxis().getDisplayPosition(item.getYValue());
                if (!Double.isNaN(x) && !Double.isNaN(y) && getDuration(item.getExtraValue()) > 0) {
                    Node block = item.getNode();
                    Rectangle rectangle;
                    if (block instanceof StackPane) {
                        StackPane region = (StackPane)item.getNode();
                        if (region.getShape() == null) {
                            rectangle = new Rectangle( getDuration( item.getExtraValue()), blockHeight);
                        } else if (region.getShape() instanceof Rectangle) {
                            rectangle = (Rectangle)region.getShape();
                        } else {
                            return;
                        }

                        // Calculate Scale Factor
                        double width = getDuration (item.getExtraValue()) * ((getXAxis() instanceof NumberAxis) ? Math.abs(((NumberAxis)getXAxis()).getScale()) : 1);
                        double height = blockHeight * ((getYAxis() instanceof NumberAxis) ? Math.abs(((NumberAxis)getYAxis()).getScale()) : 1);

                        // Set Dimensions
                        rectangle.setWidth(width);
                        rectangle.setHeight(height);
                        y -= blockHeight / 2.0;

                        // Add ToolTip for Rectangles
                        String label = getLabel(item.getExtraValue());
                        if (label != null) {
                            Tooltip tooltip = new Tooltip("Customer: " + label + "\nDuration: " + GanttChart.getDuration(item.getExtraValue()));
                            Tooltip.install(region, tooltip);
                        }

                        region.setShape(null);
                        region.setShape(rectangle);
                        region.setScaleShape(false);
                        region.setCenterShape(false);
                        region.setCacheShape(false);

                        block.setLayoutX(x);
                        block.setLayoutY(y);
                    }
                }
            }
        }
    }

    @Override
    protected void dataItemAdded(Series<X, Y> series, int itemIndex, Data<X, Y> item) {
        Node block = createRectangle(item);
        getPlotChildren().add(block);

    }

    @Override
    protected void dataItemRemoved(Data<X, Y> data, Series<X, Y> series) {
        Node node = data.getNode();
        if (this.shouldAnimate()) {
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(500.0D), node);
            fadeTransition.setToValue(0.0D);
            fadeTransition.setOnFinished(e -> {
                this.getPlotChildren().remove(node);
                this.removeDataItemFromDisplay(series, data);
                node.setOpacity(1.0D);
            });
            fadeTransition.play();
        } else {
            this.getPlotChildren().remove(node);
            this.removeDataItemFromDisplay(series, data);
        }
    }

    @Override
    protected void dataItemChanged(Data<X, Y> data) {

    }

    @Override
    protected void seriesAdded(Series<X, Y> series, int seriesIndex) {
        for(Data item : series.getData()) {
            Node node = this.createRectangle(item);
            if (this.shouldAnimate()) {
                node.setOpacity(0.0D);
                this.getPlotChildren().add(node);
                FadeTransition fadeTransition = new FadeTransition(Duration.millis(500.0D), node);
                fadeTransition.setToValue(1.0D);
                fadeTransition.play();
            } else {
                this.getPlotChildren().add(node);
            }
        }

    }

    @Override
    protected void seriesRemoved(Series<X, Y> series) {
        if (this.shouldAnimate()) {
            ParallelTransition pathTransition = new ParallelTransition();
            pathTransition.setOnFinished(e -> this.removeSeriesFromDisplay(series));
            Iterator iter = series.getData().iterator();

            while(iter.hasNext()) {
                Data item = (Data)iter.next();
                Node node = item.getNode();
                FadeTransition fadeTransition = new FadeTransition(Duration.millis(500.0D), node);
                fadeTransition.setToValue(0.0D);
                fadeTransition.setOnFinished(e -> {
                    this.getPlotChildren().remove(node);
                    node.setOpacity(1.0D);
                });
                pathTransition.getChildren().add(fadeTransition);
            }

            pathTransition.play();
        } else {
            Iterator iter = series.getData().iterator();

            while(iter.hasNext()) {
                Data item = (Data)iter.next();
                Node node = item.getNode();
                this.getPlotChildren().remove(node);
            }

            this.removeSeriesFromDisplay(series);
        }
    }

    private static double getDoubleValue(Object var0, double var1) {
        return !(var0 instanceof Number) ? var1 : ((Number)var0).doubleValue();
    }


    private Node createRectangle(final Data<X,Y> item) {

        Node container = item.getNode();

        if (container == null) {
            container = new StackPane();
            item.setNode(container);
        }

        container.setStyle("-fx-background-color: " + getColor( item.getExtraValue()));

        return container;
    }

    @Override
    protected void updateAxisRange() {
        Axis xAxis = this.getXAxis();
        Axis yAxis = this.getYAxis();
        ArrayList xData = null;
        ArrayList yData = null;
        if (xAxis.isAutoRanging()) {
            xData = new ArrayList();
        }

        if (yAxis.isAutoRanging()) {
            yData = new ArrayList();
        }

        boolean xc = xAxis instanceof CategoryAxis;
        boolean yc = yAxis instanceof CategoryAxis;
        if (xData != null || yData != null) {
            Iterator iter = this.getData().iterator();

            while(iter.hasNext()) {
                Series series = (Series)iter.next();
                Iterator data = series.getData().iterator();

                while(data.hasNext()) {
                    Data item = (Data)data.next();
                    if (xData != null) {
                        if (xc) {
                            xData.add(item.getXValue());
                        } else {
                            xData.add(item.getXValue());
                            xData.add(xAxis.toRealValue(xAxis.toNumericValue(item.getXValue()) + getDuration(item.getExtraValue())));
                        }
                    }

                    if (yData != null) {
                        if (yc) {
                            yData.add(item.getYValue());
                        } else {
                            yData.add(yAxis.toRealValue(yAxis.toNumericValue(item.getYValue()) + getDoubleValue(item.getExtraValue(), 0.0D)));
                            yData.add(yAxis.toRealValue(yAxis.toNumericValue(item.getYValue()) - getDoubleValue(item.getExtraValue(), 0.0D)));
                        }
                    }
                }
            }

            if (xData != null) {
                xAxis.invalidateRange(xData);
            }

            if (yData != null) {
                yAxis.invalidateRange(yData);
            }
        }

    }
}
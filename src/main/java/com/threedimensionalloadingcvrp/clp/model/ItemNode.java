package com.threedimensionalloadingcvrp.clp.model;

import com.threedimensionalloadingcvrp.validator.model.Item;
import javafx.beans.property.*;
import javafx.scene.DepthTest;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;

/**
 * The Item Node Class.
 */
public class ItemNode extends Box {

    /** The unique Id of the Item. */
    private final SimpleIntegerProperty itemId;

    /** The type Id of the Item. */
    private final SimpleIntegerProperty typeId;

    /** The customer Id of the Item. */
    private final SimpleIntegerProperty customerId;

    /** The length of the Item. */
    private final SimpleIntegerProperty length;

    /** The width of the Item. */
    private final SimpleIntegerProperty width;

    /** The height of the Item. */
    private final SimpleIntegerProperty height;

    /** The mass of the Item. */
    private final SimpleDoubleProperty mass;

    /** The fragility flag of the Item. */
    private final SimpleBooleanProperty fragile;

    /** The load bearing strength of the Item. */
    private final SimpleDoubleProperty lbs;

    /** The x-coordinate of the Item. */
    private final SimpleIntegerProperty x;

    /** The y-coordinate of the Item. */
    private final SimpleIntegerProperty y;

    /** The z-coordinate of the Item. */
    private final SimpleIntegerProperty z;

    /** The color of the Item. */
    private Property<Color> color;

    /** The visibility of the Item. */
    private final SimpleBooleanProperty vis;

    /** The feasibility of the Item position. */
    private final SimpleBooleanProperty feasible;

    /** The violated constraints due to the Item position. */
    private final SimpleStringProperty error;

    /** The index of the loading sequence. */
    private final SimpleIntegerProperty sequenceIndex;

    /**
     * Instantiates a new Item node.
     * Properties are necessary for Item Table
     * and for event updates.
     *
     * @param item    the original item
     * @param indexId the index id
     * @param color   the color
     */
    public ItemNode(final Item item, final Integer indexId, Color color) {
        super();
        this.itemId = new SimpleIntegerProperty(item.getId());
        this.typeId = new SimpleIntegerProperty(item.getType_id());
        this.customerId = new SimpleIntegerProperty(item.getCustomer_id());
        this.length = new SimpleIntegerProperty(!item.isRotated() ? item.getL() : item.getW());
        this.width  = new SimpleIntegerProperty(!item.isRotated() ? item.getW() : item.getL());
        this.height = new SimpleIntegerProperty(item.getH());
        this.mass = new SimpleDoubleProperty(round(item.getMass(), 2));
        this.fragile = new SimpleBooleanProperty(item.isFragile());
        this.lbs = new SimpleDoubleProperty(round(item.getLbs(), 4));

        this.x = new SimpleIntegerProperty(item.getMin().getX());
        this.y = new SimpleIntegerProperty(item.getMin().getY());
        this.z = new SimpleIntegerProperty(item.getMin().getZ());

        this.color = new SimpleObjectProperty<>(color);
        this.vis = new SimpleBooleanProperty(true);
        this.feasible = new SimpleBooleanProperty(true);
        this.error = new SimpleStringProperty("");

        this.sequenceIndex = new SimpleIntegerProperty(indexId);
        this.setDepthTest(DepthTest.ENABLE);

        setDepth(length.getValue());
        setWidth(width.getValue());
        setHeight(height.getValue());

        setPosition();
        setMaterial();

        // Listener to change the visibility of the Box
        this.visProperty().addListener((observable, oldvalue, newvalue) -> {
            if (newvalue) this.setDrawMode(DrawMode.FILL);
            else this.setDrawMode(DrawMode.LINE);
        });

    }

    private double round(double number, int places) {
        final double factor = Math.pow(10, places + 1);
        return Math.round(number * factor) / factor;
    }


    /**
     * Sets the Position of the 3D box,
     * migrating the coordinates to the JavaFx 3D Coordinate System.
     */
    public void setPosition() {
        this.setTranslateZ(x.getValue() + this.length.getValue() * 0.5); // x
        this.setTranslateX(-y.getValue() - this.width.getValue() * 0.5); // y
        this.setTranslateY(-z.getValue() - this.height.get() * 0.5); // z
    }

    /**
     * Sets the Material and Color of the 3D Box.
     */
    public void setMaterial() {
        // Create and set Material
        PhongMaterial material = new PhongMaterial(color.getValue());
        this.setMaterial(material);
    }

    /**
     * Updates the Material and Color of the 3D Box.
     * @param color the new color to set
     */
    public void updateMaterial(final Color color) {
        this.color = new SimpleObjectProperty<>(color);
        setMaterial();
    }

    /**
     * Gets the item id.
     *
     * @return the item id
     */
    public int getItemId() {
        return itemId.get();
    }

    /**
     * The Item Id Property.
     *
     * @return Item Id Property.
     */
    public SimpleIntegerProperty itemIdProperty() {
        return itemId;
    }


    /**
     * The Item TypeId Property
     *
     * @return Item TypeId Property
     */
    public SimpleIntegerProperty typeIdProperty() {
        return typeId;
    }


    /**
     * The Customer Id Property.
     *
     * @return Customer Id Property
     */
    public SimpleIntegerProperty customerIdProperty() {
        return customerId;
    }


    /**
     * The Length Property
     *
     * @return the Length Property
     */
    public SimpleIntegerProperty lProperty() {
        return length;
    }


    /**
     * The Width Property
     *
     * @return the Width Property
     */
    public SimpleIntegerProperty wProperty() {
        return width;
    }


    /**
     * The Height Property
     *
     * @return the Height Property
     */
    public SimpleIntegerProperty hProperty() {
        return height;
    }

    /**
     * The Mass Property
     *
     * @return the Mass Property
     */
    public SimpleDoubleProperty massProperty() {
        return mass;
    }

    /**
     * The fragility flag
     *
     * @return the is fragile
     */
    public boolean isFragile() {
        return fragile.get();
    }

    /**
     * The Fragile property
     *
     * @return Fragile Property
     */
    public SimpleBooleanProperty fragileProperty() {
        return fragile;
    }

    /**
     * Gets the load bearing strength value.
     *
     * @return the lbs value
     */
    public double getLbs() {
        return lbs.get();
    }

    /**
     * The LBS Property
     *
     * @return LBS property
     */
    public SimpleDoubleProperty lbsProperty() {
        return lbs;
    }

    /**
     * Gets the original x-coordinate.
     *
     * @return the x-coordinate
     */
    public int getX() {
        return x.get();
    }

    /**
     * The x-coordinate Property.
     *
     * @return the x-coordinate property
     */
    public SimpleIntegerProperty xProperty() {
        return x;
    }

    /**
     * Gets the original y-coordinate
     *
     * @return the y-coordinate
     */
    public int getY() {
        return y.get();
    }

    /**
     * the y-coordinate Property
     *
     * @return the y-coordinate property
     */
    public SimpleIntegerProperty yProperty() {
        return y;
    }

    /**
     * Gets the original z-coordinate.
     *
     * @return the z-coordinate
     */
    public int getZ() {
        return z.get();
    }

    /**
     * The z-coordinate property.
     *
     * @return the z-coordinate property
     */
    public SimpleIntegerProperty zProperty() {
        return z;
    }

    /**
     * The Color Property.
     *
     * @return the color property
     */
    public Property<Color> colorProperty() {
        return color;
    }


    /**
     * The visibility property.
     *
     * @return the visibility property.
     */
    public SimpleBooleanProperty visProperty() {
        return vis;
    }

    /**
     * Sets the visibility value.
     *
     * @param vis the new visibility flag
     */
    public void setVis(final boolean vis) {
        this.vis.set(vis);
    }

    /**
     * The feasibility flag (false = non-fragile, true = fragile).
     *
     * @return fragility flag
     */
    public boolean isFeasible() {
        return feasible.get();
    }

    /**
     * Feasible property simple boolean property.
     *
     * @return the simple boolean property
     */
    public SimpleBooleanProperty feasibleProperty() {
        return feasible;
    }

    /**
     * Sets the feasibility of the item placement.
     *
     * @param feasible the feasibility
     */
    public void setFeasible(boolean feasible) {
        this.feasible.set(feasible);
    }

    /**
     * Gets the error messages to the item placement.
     *
     * @return the error text
     */
    public String getError() {
        return error.get();
    }

    /**
     * Add error message.
     *
     * @param addError the additional string to append
     */
    public void addError(String addError) {
        error.set(error.get() + addError + "\n");
    }

    /**
     * The Error Property.
     *
     * @return the error property.
     */
    public SimpleStringProperty errorProperty() {
        return error;
    }

    /**
     * Gets the index of the packing sequence.
     *
     * @return the sequence index.
     */
    public int getSequenceIndex() {
        return sequenceIndex.get();
    }


}

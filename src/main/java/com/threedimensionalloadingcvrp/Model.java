package com.threedimensionalloadingcvrp;

import com.threedimensionalloadingcvrp.clp.model.ItemModel;
import com.threedimensionalloadingcvrp.validator.model.ConstraintSet;
import com.threedimensionalloadingcvrp.validator.model.Instance;
import com.threedimensionalloadingcvrp.validator.model.Solution;
import com.threedimensionalloadingcvrp.vrp.model.CustomerModel;
import com.threedimensionalloadingcvrp.vrp.model.TourModel;
import javafx.scene.paint.Color;
import lombok.Data;

import java.io.File;
import java.util.ArrayList;

@Data
public class Model {

    /** The Instance Data */
    private Instance instance;

    /** The Constraint Set */
    private ConstraintSet constraintSet;

    /** The Solution Data */
    private Solution solution;

    /** Problem is VRP */
    private Boolean isVRP;

    /** Problem is CLP */
    private Boolean isCLP;

    /** Check Routing Constraints (0: false) */
    private Boolean checkRoutingConstraints;

    /** Check Loading Constraints (0: false) */
    private Boolean checkLoadingConstraints;

    /** The Instance File */
    private File instanceFile;

    /** The Solution File */
    private File solutionFile;

    /** List containing the color for each customer */
    private ArrayList<Color> customerColors;

    /** The Data for the Tours */
    private TourModel tourModel;

    /** The Data for Customers */
    private CustomerModel customerModel;

    /** The Data for Items */
    private ItemModel itemModel;
}

package com.threedimensionalloadingcvrp.main.menu.fileopen;

import com.threedimensionalloadingcvrp.Presenter;
import com.threedimensionalloadingcvrp.clp.CLPPresenter;
import com.threedimensionalloadingcvrp.clp.CLPWindow;
import com.threedimensionalloadingcvrp.clp.model.ItemModel;
import com.threedimensionalloadingcvrp.clp.model.ItemNode;
import com.threedimensionalloadingcvrp.validator.Read;
import com.threedimensionalloadingcvrp.validator.model.*;
import com.threedimensionalloadingcvrp.vrp.VRPPresenter;
import com.threedimensionalloadingcvrp.vrp.VRPWindow;
import com.threedimensionalloadingcvrp.vrp.model.CustomerModel;
import com.threedimensionalloadingcvrp.vrp.model.TourModel;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import static com.threedimensionalloadingcvrp.validator.constraints.Loading.*;
import static com.threedimensionalloadingcvrp.validator.constraints.Routing.*;

/**
 * The type File open presenter.
 */
public class FileOpenPresenter extends Presenter {

    /** The view to control */
    private final FileOpenView view;

    /**
     * Instantiates a new File open presenter.
     *
     * @param view the view
     */
    public FileOpenPresenter(final FileOpenView view) {
        this.view = view;
    }


    /**
     * Initializes the View.
     */
    public void init() {
        view.getButton().setOnAction(this::actionStart);
        view.getDataPane().getCheckRoutingConstraints().selectedProperty().addListener((observableValue, oldValue, newValue)
                -> view.getRoutingPane().setVisible(newValue));
        view.getDataPane().getCheckLoadingConstraints().selectedProperty().addListener((observableValue, oldValue, newValue)
                -> view.getLoadingPane().setVisible(newValue));
    }

    /**
     * Actions when clicking on "Start" Button.
     * Read Files, create Colors, open Windows.
     *
     * @param actionEvent the fired click event
     */
    private void actionStart(final ActionEvent actionEvent) {

        // Read Instance and Soultion File
        File instanceFile = model.getInstanceFile();
        File solutionFile = model.getSolutionFile();
        if (instanceFile != null && solutionFile != null) {

            // Close Current Window
            view.getButton().getScene().getWindow().hide();


            // Create Colors for Customers
            HashSet<Color> colorArrayList = new HashSet<>();
            Random random = new Random();
            for (int i = 0; i < model.getInstance().getCustomers().size(); ++i) {
                colorArrayList.add(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            }
            model.setCustomerColors(new ArrayList<>(colorArrayList));

            // Open the CLP and/or VRP Window
            createCLPWindow();
            createVRPWindow();
        }
    }

    /**
     * Create the VRP Window.
     */
    private void createVRPWindow() {
        if (model.getIsVRP()) {
            CustomerModel customerModel = new CustomerModel(model);
            model.setCustomerModel(customerModel);

            TourModel tourModel = new TourModel(model, customerModel.getCustomerNode(0));
            model.setTourModel(tourModel);

            // Check the Routing Constraints
            StringBuilder builder = new StringBuilder();
            if (model.getCheckRoutingConstraints()) {
                Solution solution = model.getSolution();
                Instance instance = model.getInstance();
                final boolean msg = false;

                if (!checkTimeWindows(solution, instance, model.getConstraintSet().isTimeWindows(), msg)) {
                    builder.append("TimeWindows exceeded");
                }

                if (!checkCapacities(solution, model.getConstraintSet().isLoad_capacity(), instance, msg)) {
                    builder.append("Capacities exceeded");
                }

                if (!checkDispatchedCustomers(solution, model.getConstraintSet().isSplit(), instance, msg)) {
                    builder.append("Not all Customers are dispatched");
                }

                if (!checkDispatchedItems(solution, instance, msg)) {
                    builder.append("Not all Items are dispatched");
                }

                if (!checkUsedVehicles(solution, instance, msg)) {
                    builder.append("Number of used vehicles exceeds number of available vehicles");
                }

                if (!checkCost(solution, instance, msg)) {
                    builder.append("Cost calculation incorrect");
                }
            }
            String error = builder.toString();
            model.getSolution().setFeasible(error.isEmpty());

            VRPWindow vrpWindow = new VRPWindow();
            VRPPresenter vrpPresenter = new VRPPresenter(vrpWindow);
            vrpPresenter.init(error);

            vrpWindow.show();
        }
    }

    /**
     * Create the CLP Window.
     */
    private void createCLPWindow() {
        if (model.getIsCLP()) {
            ItemModel itemModel = new ItemModel(model);

            // Check the Loading Constraints
            if (model.getCheckLoadingConstraints()) {
                Instance instance = model.getInstance();
                ConstraintSet cSet = model.getConstraintSet();
                final int scaleFactor = getScaleFactor(instance.getVehicle().getH());
                for (Tour tour : model.getSolution().getTours()) {
                    // Necessary for some constraints beforehand
                    getRelevantItems(tour, tour.getItem_ids().size(), model.getInstance());

                    final int endPos = tour.getItem_ids().size();
                    for (final int item_id : tour.getItem_ids()) {
                        Item currentItem = instance.getItems().get(item_id);
                        ItemNode itemNode = itemModel.getItemNode(item_id);
                        boolean msg = false;

                        // Save Error Message per Item
                        if (!checkVehicleWalls(currentItem, instance.getVehicle(), msg))
                            itemNode.addError("overlaps with vehicle walls");

                        if (!checkMaxCoordinates(currentItem, msg))
                            itemNode.addError("maximum coordinates incorrect");

                        if (!checkDimensions(currentItem, msg))
                            itemNode.addError("dimensions incorrect");

                        if (!checkOverlapping(currentItem, tour, endPos, instance, msg))
                            itemNode.addError("overlaps with another item");

                        if (!checkAxleWeights(currentItem, tour, cSet.isAxle_weights(), instance, msg))
                            itemNode.addError("causes overload of at least one axle");

                        if (!checkBalancedLoading(currentItem, tour, cSet.isBalanced_loading(), cSet.getBalanced_part(), instance, msg))
                            itemNode.addError("causes unbalanced loading");

                        if (!checkUnloadingSequence(currentItem, tour, endPos, cSet.getUSequence(), instance, msg))
                            itemNode.addError("unloading sequence not obeyed");

                        if (!checkReachability(currentItem, tour, cSet.getLambda() * scaleFactor, cSet.isReachability(), instance, msg))
                            itemNode.addError("not reachable");

                        if (!checkVStability(currentItem, cSet.getVStability(), cSet.getAlpha(), instance, msg))
                            itemNode.addError("vertical not stable");

                        if (!checkStacking(currentItem, tour, endPos, cSet.getStacking(), instance, msg))
                            itemNode.addError("stacking not given");

                        if (!itemNode.getError().isEmpty() || !itemNode.getError().equals("")) {
                            itemNode.setFeasible(false);
                            itemNode.updateMaterial(Color.RED);
                            model.getSolution().setFeasible(false);
                        }
                    }
                }
            }

            model.setItemModel(itemModel);

            CLPWindow clpWindow = new CLPWindow(itemModel.getVehicle3D());
            CLPPresenter clpPresenter = new CLPPresenter(clpWindow);
            clpPresenter.init();
            clpWindow.show();
        }
    }
}

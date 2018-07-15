
package View_Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.util.Optional;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;

import javafx.scene.*;
import javafx.stage.*;


import Model.Inventory;
import Model.Part;
import Model.Product;
     
/**
 * FXML Main Screen Controller class
 *
 * @author Mothy
 */
public class MainScreenController implements Initializable {

    //TN: part and product to be modified - will populate if selected
    private static Part modifyPartSelectionMain;
    private static Product modifyProductSelection;

    //TN: elements of the Main Part Table
    @FXML
    private TableView<Part> mainPartTable;
    
    @FXML
    private TableColumn<Part, Integer> mainPartIDColumn;
    
    @FXML
    private TableColumn<Part, String> mainPartNameColumn;
    
    @FXML
    private TableColumn<Part, Integer> mainPartInvLevelColumn;
    
    @FXML
    private TableColumn<Part, Double> mainPartCostColumn;
    
    //TN: elements of the Main Product Table
    @FXML
    private TableView<Product> mainProductTable;
    
    @FXML
    private TableColumn<Product, Integer> mainProductIDColumn;
    
    @FXML
    private TableColumn<Product, String> mainProductNameColumn;
    
    @FXML
    private TableColumn<Product, Integer> mainProductInvLevelColumn;
    
    @FXML
    private TableColumn<Product, Double> mainProductPriceColumn;
    
    //TN: Main Part ID Text Field
    @FXML
    private TextField mainPartIDSearchField;
    
    //TN: Main Product ID Text Field 
    @FXML
    private TextField mainProductIDSearchField;   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        setModifiedPart(null);
        setModifiedProduct(null);
        //TN: initialize parts columns:
        mainPartIDColumn.setCellValueFactory(cellData -> 
                new SimpleIntegerProperty(cellData.getValue().getPartID()).asObject());
        mainPartNameColumn.setCellValueFactory(cellData -> 
                new SimpleStringProperty(cellData.getValue().getPartName()));
        mainPartInvLevelColumn.setCellValueFactory(cellData -> 
                new SimpleIntegerProperty(cellData.getValue().getPartInStock()).asObject());
        mainPartCostColumn.setCellValueFactory(cellData -> 
                new SimpleDoubleProperty(cellData.getValue().getPartPrice()).asObject());
        //TN: initialize products column
        mainProductIDColumn.setCellValueFactory(cellData -> 
                new SimpleIntegerProperty(cellData.getValue().getProductID()).asObject());
        mainProductNameColumn.setCellValueFactory(cellData -> 
                new SimpleStringProperty(cellData.getValue().getProductName()));
        mainProductInvLevelColumn.setCellValueFactory(cellData -> 
                new SimpleIntegerProperty(cellData.getValue().getProductInStock()).asObject());
        mainProductPriceColumn.setCellValueFactory(cellData -> 
                new SimpleDoubleProperty(cellData.getValue().getProductPrice()).asObject());
        //TN: populate both tables wioth any exitsing data:
        populateMainPageTables();
    }    
       
    //TN: switch to parts screen (add or modify): 
    public void partsScreenSwitch(ActionEvent event) throws IOException {
        Parent loader = FXMLLoader.load(getClass().getResource("PartsScreen.fxml"));
        Scene partsScreen = new Scene(loader);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(partsScreen);
        window.show();
    }  
    
    //TN: switch to product screen (add or modify): 
    public void productsScreenSwitch(ActionEvent event) throws IOException {
        Parent loader = FXMLLoader.load(getClass().getResource("ProductsScreen.fxml"));
        Scene partsScreen = new Scene(loader);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(partsScreen);
        window.show();
    }
    
    //TN: method to populate both tables - used on page launch
    public void populateMainPageTables() {
        mainPartTable.setItems(Inventory.getAllParts());
        mainProductTable.setItems(Inventory.getAllProducts());
    }
    
    //TN: add part button action (switch to add part screen):
    @FXML
    void handleAddPart(ActionEvent event) throws IOException {        
        modifyPartSelectionMain = null;
        partsScreenSwitch(event);
    }

    //TN: add product button action (switch to add product screen):
    @FXML
    void handleAddProduct(ActionEvent event) throws IOException {
        modifyProductSelection = null;
        productsScreenSwitch(event);
    }

    //TN: modify part button action (switch to modify part screen).  Sets
    //"modifyPartSelectionMain" variable if selected:
    @FXML
    void handleModifyPart(ActionEvent event) throws IOException {
        modifyPartSelectionMain = mainPartTable.getSelectionModel().getSelectedItem();
        setModifiedPart(modifyPartSelectionMain);
        partsScreenSwitch(event);
    }

    //TN: setter to set part to current selection:
    public void setModifiedPart(Part modifyPartSelectionMain) {
        MainScreenController.modifyPartSelectionMain = modifyPartSelectionMain;
    }
    
    //TN: getter to return selected part
    public static Part getModifiedPart() {
        return modifyPartSelectionMain;
    }
    
    //TN: setter to set part to current selection:
    public void setModifiedProduct(Product modifyProductSelection) {
        MainScreenController.modifyProductSelection = modifyProductSelection;
    }
    
    //TN: getter to return selected part
    public static Product getModifiedProduct() {
        return modifyProductSelection;
    }
    
    //TN: modify product button action (switch to modify product screen):
    @FXML
    void handleModifyProduct(ActionEvent event) throws IOException {
        modifyProductSelection = mainProductTable.getSelectionModel().getSelectedItem();
        setModifiedProduct(modifyProductSelection);
        productsScreenSwitch(event);
    }
 
    //TN: delete part button actions:
    @FXML
    void handleDeletePart(ActionEvent event){
        modifyPartSelectionMain = mainPartTable.getSelectionModel().getSelectedItem();
	Alert alert = new Alert(AlertType.CONFIRMATION);
	alert.setTitle("DELETE");
	alert.setHeaderText("Confirm deletion of " + modifyPartSelectionMain.getPartName() 
                + " by clicking OK");
	Optional<ButtonType> result = alert.showAndWait();
	
        if (result.get() == ButtonType.OK){
		Inventory.deletePart(modifyPartSelectionMain.getPartID());
	}
    }
    
    //TN: delete product button actions.  Satisfies "preventing the user from 
    //deleting a product that has a part assigned to it" requirement:
    @FXML
    void handleDeleteProduct() {
        modifyProductSelection = mainProductTable.getSelectionModel().getSelectedItem();
        int partCount = modifyProductSelection.getProductsPartCount();
        
            if (partCount == 0) { 
                Alert productDeletionAlert = new Alert(Alert.AlertType.WARNING);
                productDeletionAlert.setTitle("DELETE");
                productDeletionAlert.setHeaderText("Confirm deletion of " 
                        + modifyProductSelection.getProductName() + " by clicking OK");
                
                Optional<ButtonType> result = productDeletionAlert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Inventory.removeProduct(modifyProductSelection.getProductID());
                }
            }
            
            else {
                Alert hasAssociatedPartsAlert = new Alert(Alert.AlertType.WARNING);
                hasAssociatedPartsAlert.setTitle("UNABLE TO DELETE");
                hasAssociatedPartsAlert.setHeaderText(modifyProductSelection.getProductName()
                        + " has associated parts.");
                hasAssociatedPartsAlert.setContentText("You must remove associated"
                        + " parts before deleting.");

                hasAssociatedPartsAlert.showAndWait();
            }
    } 

    //TN: parts search actions.
    @FXML
    void handleSearchPart() {
	Part foundPart = Inventory.lookupPart(Integer.parseInt(mainPartIDSearchField.getText()));
            
        if (foundPart != null) {
                    ObservableList<Part> searchResults = 
                            FXCollections.observableArrayList();
                    searchResults.add(foundPart);
                    mainPartTable.setItems(searchResults);
            } else {
                    Alert partNotFoundAlert = new Alert(AlertType.INFORMATION);
                        partNotFoundAlert.setTitle("PART NOT FOUND");
                        partNotFoundAlert.setHeaderText("Part does not exist in database");
                        partNotFoundAlert.showAndWait();
                }
    }
   
    //TN: products search actions.  Returns all when search initiated with no
    //specified parameters:
    @FXML
    void handleSearchProduct() {
	String enteredProductID = mainProductIDSearchField.getText();
        Product foundProduct = Inventory.lookupProduct(Integer.parseInt(enteredProductID));
                        
	if (enteredProductID.isEmpty()){
		mainProductTable.setItems(Inventory.getAllProducts());
	}
                    
            else if (foundProduct != null) {
                    ObservableList<Product> searchResults = 
                            FXCollections.observableArrayList();
                    searchResults.add(foundProduct);
                    mainProductTable.setItems(searchResults);
                } else {
                    Alert productNotFoundAlert = new Alert(AlertType.INFORMATION);
                    productNotFoundAlert.setTitle("PRODUCT NOT FOUND");
                    productNotFoundAlert.setHeaderText("Product does not exist in database");
                    productNotFoundAlert.showAndWait();
            }
    }
   
    //TN: exit button action:
    @FXML
    void handleExitMain() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("EXIT");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Click OK to proceed");
        Optional<ButtonType> result = alert.showAndWait();
 
        if (result.get() == ButtonType.OK){
            System.exit(0);
        }
    }
    
}

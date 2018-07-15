
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
 * FXML Products Screen Controller class
 *
 * @author Mothy
 */
public class ProductsScreenController implements Initializable {

    private static Product selectedModifyProduct;
    private boolean isNewProduct;
    
    //TN: this products associated parts
    private ObservableList<Part> productAssociatedParts = 
            FXCollections.observableArrayList();
    
    //TN: elements of the Available Parts Table:
    @FXML
    private TableView<Part> productScreenAllPartsTable;
    
    @FXML
    private TableColumn<Part, Integer> productAllPartsIDColumn;
    
    @FXML
    private TableColumn<Part, String> productAllPartsPartNameColumn; 
    
    @FXML
    private TableColumn<Part, Integer> productAllPartsInvLevelColumn;
    
    @FXML
    private TableColumn<Part, Double> productAllPartsPriceColumn;
    
    //TN: elements of the Current Product Part Inventory Table:
    @FXML
    private TableView<Part> productScreenCurrentPartsTable;
    
    @FXML
    private TableColumn<Part, Integer> productCurrentPartIDColumn;
    
    @FXML
    private TableColumn<Part, String> productCurrentPartNameColumn;
    
    @FXML
    private TableColumn<Part, Integer> productCurrentInvLevelColumn;
    
    @FXML
    private TableColumn<Part, Double> productCurrentPriceColumn;
    
    //TN: Product Screen Header Label:
    @FXML
    private Label productScreenHeader;

    //TN: Product screen search text:
    @FXML
    private TextField productScreenSearchText;
    
    //TN: Product Screen ID Text Field:
    @FXML
    private TextField productScreenIDText;
    
    //TN: Product Screen Name Text Field: 
    @FXML
    private TextField productScreenNameText;  
    
    //TN: Product Screen Inventory Text Field: 
    @FXML
    private TextField productScreenInventoryText;
    
    //TN: Product Screen Price Text Field: 
    @FXML
    private TextField productScreenPriceText;
    
    //TN: Product Screen Max Text Field: 
    @FXML
    private TextField productScreenMaxText;
    
    //TN: Product Screen Min Text Field: 
    @FXML
    private TextField productScreenMinText;
    
    public ProductsScreenController() {
        this.selectedModifyProduct = MainScreenController.getModifiedProduct();
        }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //TN: Defaults to Add Product on screen launch if no prpduct was selected 
        //TN: to modify:
        if (selectedModifyProduct == null) {
            productScreenHeader.setText("Inventory Management System : Add Product");
            isNewProduct = true;
            int newProductID = Inventory.getNewProductID();
            productScreenIDText.setText(Integer.toString(newProductID));
        }
        //TN: Sets header and populates part data for modification:
        else{
            productScreenHeader.setText("Inventory Management System : Modify Product");
            isNewProduct = false;
            productScreenIDText.setText(Integer.toString(selectedModifyProduct.getProductID()));
            productScreenNameText.setText(selectedModifyProduct.getProductName());
            productScreenInventoryText.setText(Integer.toString(selectedModifyProduct.getProductInStock()));
            productScreenPriceText.setText(Double.toString(selectedModifyProduct.getProductPrice()));
            productScreenMaxText.setText(Integer.toString(selectedModifyProduct.getProductMax()));
            productScreenMinText.setText(Integer.toString(selectedModifyProduct.getProductMin()));
            //TN: populates associated parts list with existing associated parts:
            productAssociatedParts = selectedModifyProduct.getAssociatedParts();
        }

        //TN: initialize all parts table columns:
        productAllPartsIDColumn.setCellValueFactory(cellData -> 
                new SimpleIntegerProperty(cellData.getValue().getPartID()).asObject());
        productAllPartsPartNameColumn.setCellValueFactory(cellData -> 
                new SimpleStringProperty(cellData.getValue().getPartName()));
        productAllPartsInvLevelColumn.setCellValueFactory(cellData -> 
                new SimpleIntegerProperty(cellData.getValue().getPartInStock()).asObject());
        productAllPartsPriceColumn.setCellValueFactory(cellData -> 
                new SimpleDoubleProperty(cellData.getValue().getPartPrice()).asObject());
        //TN: initialize associated parts table column
        productCurrentPartIDColumn.setCellValueFactory(cellData -> 
                new SimpleIntegerProperty(cellData.getValue().getPartID()).asObject());
        productCurrentPartNameColumn.setCellValueFactory(cellData -> 
                new SimpleStringProperty(cellData.getValue().getPartName()));
        productCurrentInvLevelColumn.setCellValueFactory(cellData -> 
                new SimpleIntegerProperty(cellData.getValue().getPartInStock()).asObject());
        productCurrentPriceColumn.setCellValueFactory(cellData -> 
                new SimpleDoubleProperty(cellData.getValue().getPartPrice()).asObject());
        //TN: populates both tables:
        populateProductScreenAllPartsTable();
        populateProductScreenAssociatedPartsTable();
        
    }    
    
    //TN: populates this products associated parts table:
    public void populateProductScreenAllPartsTable() {
        productScreenAllPartsTable.setItems(Inventory.getAllParts());
    }
    
    //TN: populates this products associated parts table:
    public void populateProductScreenAssociatedPartsTable() {
        productScreenCurrentPartsTable.setItems(productAssociatedParts);
    }
    
    //TN: function to add total cost of associated parts:
    private double getCostOfParts() {
		double partCost = 0;
		ObservableList<Part> partsContained = productAssociatedParts;
		for(Part part : partsContained) {
			partCost += part.getPartPrice();
		}
		return partCost;
	}
    
    //TN: add part button action:
    @FXML
    void handleProductAddPart(ActionEvent event) {
        Part selectedPart = productScreenAllPartsTable.getSelectionModel().getSelectedItem();
        productAssociatedParts.add(selectedPart);
        populateProductScreenAllPartsTable();        
    }    
    
    //TN: delete button actions: 
    @FXML
    void handleProductDeletePart(ActionEvent event) {
        Part selectedPart = productScreenCurrentPartsTable.getSelectionModel().getSelectedItem();
        productAssociatedParts.remove(selectedPart);
        populateProductScreenAllPartsTable(); 
    }  
    
    //TN: parts search actions.  Returns all when search initiated with no
    //specified parameters: 
    @FXML
    void handleSearchParts(ActionEvent event) throws IOException {
        String enteredPartID = productScreenSearchText.getText();
        Part searchedPart = Inventory.lookupPart(Integer.parseInt(enteredPartID));
        
        if (enteredPartID.isEmpty()){
		productScreenAllPartsTable.setItems(Inventory.getAllParts());
    	}
            else if (searchedPart != null) {
                    ObservableList<Part> searchResults = 
                            FXCollections.observableArrayList();
                    searchResults.add(searchedPart);
                    productScreenAllPartsTable.setItems(searchResults);
            } else {
                    Alert partNotFoundAlert = new Alert(AlertType.INFORMATION);
                        partNotFoundAlert.setTitle("PART NOT FOUND");
                        partNotFoundAlert.setHeaderText("Part does not exist in database");
                        partNotFoundAlert.showAndWait();
                }
    }  
    
    @FXML
    void handleProductSave(ActionEvent event) throws IOException {
  
        String productID = productScreenIDText.getText();
        String productName = productScreenNameText.getText();
        String productInventory = productScreenInventoryText.getText();
        String productPrice = productScreenPriceText.getText();
        String productMin = productScreenMinText.getText();
        String productMax = productScreenMaxText.getText();
        int inventoryMax = Integer.parseInt(productMax);
        int inventoryMin = Integer.parseInt(productMin);
        int inventoryTotal = Integer.parseInt(productInventory);
        Double partsTotal = getCostOfParts();
               
        //TN: if statement to handle following requirements:
        // •  ensuring that a product must always have at least one part
            if (productAssociatedParts.isEmpty()){
                        Alert partNotFoundAlert = new Alert(AlertType.INFORMATION);
                        partNotFoundAlert.setTitle("NO ASSOCIATED PARTS");
                        partNotFoundAlert.setHeaderText("You must add a part to "
                                + "the product to save");
                        partNotFoundAlert.showAndWait();
        //TN: if statement to handle following requirements:
        // •  ensuring that a product must have a name, price, and inventory level 
            } else if (productName.isEmpty() || productPrice.isEmpty() || productInventory.isEmpty()) {
                        Alert missingDataAlert = new Alert(AlertType.INFORMATION);
                        missingDataAlert.setTitle("MISSING REQUIRED DATA");
                        missingDataAlert.setHeaderText("Ensure Name, Price and "
                                + "Inventory have been specified");
                        missingDataAlert.showAndWait();
            } else if (partsTotal > (Double.parseDouble(productPrice))) {            
                        Alert costHigherThanPriceAlert = new Alert(AlertType.INFORMATION);
                        costHigherThanPriceAlert.setTitle("BAD COST/PRICE DATA");
                        costHigherThanPriceAlert.setHeaderText("Price can not be "
                                + "lower than sum of part costs");
                        costHigherThanPriceAlert.showAndWait();
        //TN: if statement to handle following requirements:
        // •  entering an inventory value greater than the maximum value for a 
        //    part or product, or lower than the minimum value for a part or product
        // •  preventing the minimum field from having a value above the maximum field
        // •  preventing the maximum field from having a value below the minimum field
            } else if (inventoryMax < inventoryMin || inventoryMax < inventoryTotal 
                    || inventoryTotal < inventoryMin) {
                        Alert inventoryProblemAlert = new Alert(AlertType.INFORMATION);
                        inventoryProblemAlert.setTitle("INVENTORY PROBLEM");
                        inventoryProblemAlert.setHeaderText("Invalid specified "
                                + "inventory parameters");
                        inventoryProblemAlert.setContentText("Must adhere to: "
                                + "Min Inventory  <   Total Inventory   <   Max Inventory ");
                        inventoryProblemAlert.showAndWait();    
                } else {
                    Product thisProduct = new Product();
                    thisProduct.setProductID(Integer.parseInt(productID));
                    thisProduct.setProductName(productName);
                    thisProduct.setProductPrice(Double.parseDouble(productPrice));
                    thisProduct.setProductInStock(inventoryTotal);
                    thisProduct.setProductMin(inventoryMin);
                    thisProduct.setProductMax(inventoryMax);
                    thisProduct.setAssociatedParts(productAssociatedParts);
                                                     
                        if (isNewProduct == true) {
                           Inventory.addProduct(thisProduct); 
                        }else{
                           Inventory.updateProduct(selectedModifyProduct, thisProduct);
                        }

                Parent modifyProductCancel = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(modifyProductCancel);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
                }
    }  
    
    //TN: cancel button action:
    @FXML
    void handleProductCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("CANCEL");
        alert.setHeaderText("Are you sure you want to cancel?");
        alert.setContentText("Click OK to proceed");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Parent loader = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(loader);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
        
    }  
       
}


package View_Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import java.util.Optional;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

import javafx.scene.*;
import javafx.stage.*;

import Model.Inventory;
import Model.Part;
import Model.InhousePart;
import Model.OutsourcedPart;

/**
 * FXML Parts Screen Controller class
 *
 * @author Mothy
 */
public class PartsScreenController implements Initializable {

    private static Part selectedModifyPart;
    private boolean isInHouse;
    private boolean isNewPart;

    //TN: Part Screen Header Label:
    @FXML
    private Label partPageHeader;
    
    //TN: Part Screen In-House Radio Button:
    @FXML
    private RadioButton partPageInHouseRadio;
    
    //TN: Part Screen Outsourced Radio Button:
    @FXML
    private RadioButton partPageOutsourcedRadio;

    //TN: Part Screen ID Text Field:
    @FXML
    private TextField partPageIDText;
    
    //TN: Part Screen Name Text Field: 
    @FXML
    private TextField partPageNameText;  
    
    //TN: Part Screen Inventory Text Field: 
    @FXML
    private TextField partPageInventoryText;
    
    //TN: Part Screen Price Text Field: 
    @FXML
    private TextField partPagePriceText;
    
    //TN: Part Screen Max Text Field: 
    @FXML
    private TextField partPageMaxText;
    
    //TN: Part Screen Min Text Field: 
    @FXML
    private TextField partPageMinText;
    
    //TN: Part Screen Machine or Company Text Field: 
    @FXML
    private TextField partPageMachineOrCompText;
    
    //TN: Part Screen Machine or Company Label:
    @FXML
    private Label partPageMachineOrCompLabel;
    
    public PartsScreenController() {
        //TN: retrieves the selected part from the Main page (if selected):
        selectedModifyPart = MainScreenController.getModifiedPart();
        }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //TN: Defaults to Add Part on screen launch if no part was selected 
        //TN: to modify:
        if (selectedModifyPart == null) {
            partPageHeader.setText("Inventory Management System : Add Part");
            isNewPart = true;
            int newPartID = Inventory.getNewPartID();
            partPageIDText.setText(Integer.toString(newPartID));
            isInHouse = true;
            partPageMachineOrCompLabel.setText("Machine ID");
        }
        //TN: Sets header and populates part data for modification:
        else{
            partPageHeader.setText("Inventory Management System : Modify Part");
            isNewPart = false;
            partPageIDText.setText(Integer.toString(selectedModifyPart.getPartID()));
            partPageNameText.setText(selectedModifyPart.getPartName());
            partPageInventoryText.setText(Integer.toString(selectedModifyPart.getPartInStock()));
            partPagePriceText.setText(Double.toString(selectedModifyPart.getPartPrice()));
            partPageMinText.setText(Integer.toString(selectedModifyPart.getPartMin()));
            partPageMaxText.setText(Integer.toString(selectedModifyPart.getPartMax()));
            //TN: Sets dynamic label depending on what part type is being modified:
            if (selectedModifyPart instanceof InhousePart) {
                partPageMachineOrCompText.setText(Integer.toString
                        (((InhousePart) selectedModifyPart).getMachineID()));
                partPageMachineOrCompLabel.setText("Machine ID");
                partPageInHouseRadio.setSelected(true);

            } else {
                partPageMachineOrCompText.setText((
                        (OutsourcedPart) selectedModifyPart).getCompanyName());
                partPageMachineOrCompLabel.setText("Company Name");
                partPageOutsourcedRadio.setSelected(true);
            }
        }
    }

    //TN: Changes dynamic label when In-House radio button selected:
    @FXML
    void handleInHouseSelection(ActionEvent event) {
        isInHouse = true;
        partPageMachineOrCompLabel.setText("Machine ID");
    }
    
    //TN: Changes dynamic label when In-House radio button selected:
    @FXML
    void handleOutsourcedSelection(ActionEvent event) {
        isInHouse = false;
        partPageMachineOrCompLabel.setText("Company ID");
    }

    //TN: Save button actions:
    @FXML
    void handlePartPageSave(ActionEvent event) throws IOException {
        String partID = partPageIDText.getText();
        String partName = partPageNameText.getText();
        String partInventory = partPageInventoryText.getText();
        String partPrice = partPagePriceText.getText();
        String partMin = partPageMinText.getText();
        String partMax = partPageMaxText.getText();
        String partMachineOrComp = partPageMachineOrCompText.getText();
        int inventoryMax = Integer.parseInt(partMax);
        int inventoryMin = Integer.parseInt(partMin);
        int inventoryTotal = Integer.parseInt(partInventory);        
            
            //TN: if statement to handle following requirements:
            // •  entering an inventory value greater than the maximum value for 
            //    a part or product, or lower than the minimum value for a part or product
            // •  preventing the minimum field from having a value above the maximum field
            // •  preventing the maximum field from having a value below the minimum field  
            
        if (inventoryMax < inventoryMin || inventoryMax < inventoryTotal 
                || inventoryTotal < inventoryMin) {
                Alert inventoryProblemAlert = new Alert(Alert.AlertType.INFORMATION);
                inventoryProblemAlert.setTitle("INVENTORY PROBLEM");
                inventoryProblemAlert.setHeaderText("Invalid specified inventory parameters");
                inventoryProblemAlert.setContentText("Must adhere to: Min Inventory  "
                        + "<   Total Inventory   <   Max Inventory ");
                inventoryProblemAlert.showAndWait(); 
            //TN: Checks if part is in-house, if so sets in-house values
            //if not sets outsource values.  Adds a new part if a new
            //part transaction was started, if not modifies an existing part
                } else if (isInHouse == true) {
                    InhousePart thisInhousePart = new InhousePart();
                    thisInhousePart.setPartID(Integer.parseInt(partID));
                    thisInhousePart.setPartName(partName);
                    thisInhousePart.setPartPrice(Double.parseDouble(partPrice));
                    thisInhousePart.setPartInStock(inventoryTotal);
                    thisInhousePart.setPartMin(inventoryMin);
                    thisInhousePart.setPartMax(inventoryMax);
                    thisInhousePart.setMachineID(Integer.parseInt(partMachineOrComp));
                    
                        if (isNewPart == true) {
                           Inventory.addPart(thisInhousePart); 
                        }else{
                           Inventory.updatePart(selectedModifyPart, thisInhousePart);
                        }
                
                Parent modifyProductCancel = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(modifyProductCancel);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
                
                } else {
                    OutsourcedPart thisOutsourcedPart = new OutsourcedPart();
                    thisOutsourcedPart.setPartID(Integer.parseInt(partID));
                    thisOutsourcedPart.setPartName(partName);
                    thisOutsourcedPart.setPartPrice(Double.parseDouble(partPrice));
                    thisOutsourcedPart.setPartInStock(inventoryTotal);
                    thisOutsourcedPart.setPartMin(inventoryMin);
                    thisOutsourcedPart.setPartMax(inventoryMax);
                    thisOutsourcedPart.setCompanyName(partMachineOrComp);
                                        
                        if (isNewPart == true) {
                           Inventory.addPart(thisOutsourcedPart); 
                        }else{
                           Inventory.updatePart(selectedModifyPart, thisOutsourcedPart);
                        }
                        
                Parent modifyProductCancel = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(modifyProductCancel);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();               
                }
    }
    //TN: Cancel button action (launches main screen on confirmation):
    @FXML
    void handlePartPageCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("CANCEL");
        alert.setHeaderText("Are you sure you want to cancel?");
        alert.setContentText("Click OK to proceed");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Parent partsCancel = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(partsCancel);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }
}

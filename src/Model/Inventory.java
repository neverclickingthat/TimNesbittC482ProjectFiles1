package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * "Inventory" class per assignment UML
 * @author Tim Nesbitt
 */

public class Inventory {

    public Inventory() {
        }

    public static int partCounter;
    public static int productCounter;
    //TN: observable list for product inventory:
    private final static ObservableList<Product> 
            productsTable = FXCollections.observableArrayList();
    
    //TN: observable list for part inventory:
    private final static ObservableList<Part> 
            partsTable = FXCollections.observableArrayList();

    //TN: Adds a new product to the products table:
    public static void addProduct(Product newProduct){
        productsTable.add(newProduct);
        productCounter++;
    }
    
    //TN: Removes a product from the products table using the product ID:
    public static boolean removeProduct(int productID) {
        for (Product product : productsTable) {
            if (product.getProductID() == productID) {
                productsTable.remove(product);
                return true;
            }
        }
        return false;
    }
    
    //TN: Looks up a product using the products ID in the products table:
    public static Product lookupProduct(int productID) {
        for (Product product : productsTable) {
            if (product.getProductID() == productID) {
                return product;
            }
        }
        return null;
    }
    
    //TN: Updates a product in the products table using the product ID:
    public static void updateProduct(Product oldValues, Product updatedProduct) {
        int index = productsTable.indexOf(oldValues);
        productsTable.set(index, updatedProduct);
    }
        
    //TN: Returns all products:
    public static ObservableList<Product> getAllProducts() {
        return productsTable;
    }    
            
    //TN: Adds a new part to the parts table:
    public static void addPart(Part newPart){
        partsTable.add(newPart);
        partCounter++;
    }
     
    //TN: Deletes a part from the table using the part ID:
    public static boolean deletePart(int partID) {
        for (Part part : partsTable) {
            if (part.getPartID() == partID) {
                partsTable.remove(part);
                return true;
            }
        }
        return false;
    }
    
    //TN: Looks up a part using the part ID from the parts table:
    public static Part lookupPart(int partID) {
        for (Part part : partsTable) {
            if (part.getPartID() == partID) {
                return part;
            }
        }
        return null;
    }
    
    //TN: Updates a part in the parts table using the part ID:
    public static void updatePart(Part oldVaules, Part updatedPart) {
        int index = partsTable.indexOf(oldVaules);
        partsTable.set(index, updatedPart);
   }

    //TN: Returns all parts:
    public static ObservableList<Part> getAllParts() {
        return partsTable;
    }
    
    //TN: Returns a new unique Part ID
    public static int getNewPartID() {
        return partCounter + 100;        
    }
    
    //TN: Returns a new unique Product ID
    public static int getNewProductID() {
        return productCounter + 3000;       
    }
}

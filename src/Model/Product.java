
package Model;

/**
 * "Product" class per assignment UML
 * @author Tim Nesbitt
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    
    //TN: array list to store associated parts:
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    
    //TN: product ID:
    private int productID;
    //TN: product name:
    private String name;
    //TN: product price:
    private double price;
    //TN: inventory level of product:
    private int inStock;
    //TN: product inventory minumum:
    private int min;
    //TN: product inventory maximum:
    private int max;
    
   
    public Product() {
    }
    
    //TN: setter to set pprouct name:
    public void setProductName(String name) {
        this.name = name;
    }
       
    //TN: getter to return product name:
    public String getProductName() {
        return name;
    }
    
    //TN: setter to set product price:
    public void setProductPrice(double price) {
        this.price = price;
    }

    //TN: getter to return product price:
    public double getProductPrice() {
        return price;
    }

    //TN: setter to set current inventory level for product:
    public void setProductInStock(int inStock) {
        this.inStock = inStock;
    }
    
    //TN: setter to set associated parts:
    public void setAssociatedParts(ObservableList<Part> productAssociatedParts) {
        this.associatedParts = productAssociatedParts;
    }
    
    //TN: getter to return current inventory level for product:
    public int getProductInStock() {
        return inStock;
    }
    
    //TN: setter to set minimum inventory level for product:
    public void setProductMin(int min) {
        this.min = min;
    }

    //TN: getter to return minimum inventory level for product:
    public int getProductMin() {
        return min;
    }

    //TN: setter to set maximum inventory level for product:
    public void setProductMax(int max) {
        this.max = max;
    }

    //TN: getter to return maximum inventory level for product:
    public int getProductMax() {
        return max;
    }
    
    //TN: setter to add associated part to product:
    public void addAssociatedPart(Part associatedPart) {
        this.associatedParts.add(associatedPart);
    }
    
    //TN: setter to remove an associated part from product:
    public boolean removeAssociatedPart(int partID) {
        for (Part part : associatedParts) {
            if (part.getPartID() == partID) {
                associatedParts.remove(part);
                return true;
            }
        }
        return false;
    }

    //TN: getter to return an associated part if it exists, otherwise null:
    public Part lookupAssociatedPart(int partID) {
        for (Part part : associatedParts) {
            if (part.getPartID() == partID) {
                return part;
            }
        }
        return null;
    }
    
    //TN: returns all associated parts:
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }
    
    //TN: setter to set product ID:
    public void setProductID(int productID) {
        this.productID = productID;
    }
    
    //TN: getter to get product ID:
    public int getProductID() {
        return productID;
    }
    
    //TN: getter to return part count (used to prevebnt deletion of products
    //with associated parts:
    public int getProductsPartCount() {
        return associatedParts.size();
    }
}
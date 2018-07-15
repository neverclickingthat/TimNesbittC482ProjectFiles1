
package Model;

/**
 * "OutsourcedPart" class per assignment UML
 * @author Mothy
 */

public class OutsourcedPart extends Part {

    //TN: company name for outsourced part:
    private String companyName;
    
    //TN: getter to return company name for outsourcd part:
    public String getCompanyName() {
        return companyName;
    }
        
    //TN: setter to set company name for outsourced part:
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}

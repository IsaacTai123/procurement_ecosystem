package directory;

import enums.EnterpriseType;
import model.ecosystem.Enterprise;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tisaac
 */
public class EnterpriseDirectory {

    private final List<Enterprise> enterprisesList;

    public EnterpriseDirectory() {
        this.enterprisesList = new ArrayList<>();
    }

    public Enterprise createEnterprise(String name, EnterpriseType type) {
        Enterprise enterprise = new Enterprise(name, type);
        enterprisesList.add(enterprise);
        return enterprise;
    }

    public List<Enterprise> getEnterprisesList() {
        return enterprisesList;
    }
    
    public Enterprise getEnterpriseByName(String name) {
        
        for(Enterprise enterprise: enterprisesList){
            if (enterprise.getName() == name) {
                return enterprise;
            }
        
        }
        
        return null;
    
    }
}

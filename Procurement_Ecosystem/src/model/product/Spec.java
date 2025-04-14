package model.product;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tisaac
 */
public class Spec {
    private String modelNumber;
    private String color;
    private String size;
    private String material;
    private String category;
    private String additionalNotes;
    private String specId;
    private String type;
    private Map<String, String> details;

    public Spec(String modelNumber, String color, String size, String material, String category, String additionalNotes, String specId, String type) {
        this.modelNumber = modelNumber;
        this.color = color;
        this.size = size;
        this.material = material;
        this.category = category;
        this.additionalNotes = additionalNotes;
        this.specId = specId;
        this.type = type;
        this.details = new HashMap<>();
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public String getColor() {
        return color;
    }

    public String getSize() {
        return size;
    }

    public String getMaterial() {
        return material;
    }

    public String getCategory() {
        return category;
    }
    
    public void addDetail(String key, String value) {
        details.put(key, value);
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public String getType() {
        return type;
    }

    public String getSpecId() {
        return specId;
    }
}

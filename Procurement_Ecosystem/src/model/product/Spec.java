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
    private String remarks;

    public Spec(String modelNumber, String color, String size, String material, String category, String remarks) {
        this.modelNumber = modelNumber;
        this.color = color;
        this.size = size;
        this.material = material;
        this.category = category;
        this.remarks = remarks;
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

    public String getRemarks() {
        return remarks;
    }
}

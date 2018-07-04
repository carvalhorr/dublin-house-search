package data;

import java.math.BigDecimal;

public class PropertyInfo {

    private String id;

    private String name;
    private String url;
    private String price;
    private String additionalInfo;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public String toString() {
        return "PropertyInfo{" +
                "id='" + id + "\'" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", price=" + price +
                ", addtionalInfo=" + additionalInfo +
                '}';
    }

}

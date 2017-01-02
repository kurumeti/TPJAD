package model;

import javax.faces.bean.ManagedBean;
import javax.faces.context.Flash;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue
    private int productId;
    private String productName;
    private String productDescription;
    private String productCategory;
    private float productPrice;
    private int productQuantity;
    private String productImageFileName;

    public Product() {
    }

    public Product(String productName, String productDescription, String productCategory, float productPrice, int productQuantity, String productImageFileName) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productImageFileName = productImageFileName;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductImageFileName() {
        return productImageFileName;
    }

    public void setProductImageFileName(String productImageFileName) {
        this.productImageFileName = productImageFileName;
    }

    @Override
    public String toString() {
        return productName + " (" + productCategory +"): " + productDescription + "; Price: " + productPrice + "; Available: " + productQuantity;
    }
}

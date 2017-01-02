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
    return productName + " (" + productCategory + "): " + productDescription + "; Price: " + productPrice + "; Available: " + productQuantity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Product product = (Product) o;

    if (productId != product.productId) return false;
    if (Float.compare(product.productPrice, productPrice) != 0) return false;
    if (productQuantity != product.productQuantity) return false;
    if (productName != null ? !productName.equals(product.productName) : product.productName != null) return false;
    if (productDescription != null ? !productDescription.equals(product.productDescription) : product.productDescription != null)
      return false;
    if (productCategory != null ? !productCategory.equals(product.productCategory) : product.productCategory != null)
      return false;
    return productImageFileName != null ? productImageFileName.equals(product.productImageFileName) : product.productImageFileName == null;
  }

  @Override
  public int hashCode() {
    int result = productId;
    result = 31 * result + (productName != null ? productName.hashCode() : 0);
    result = 31 * result + (productDescription != null ? productDescription.hashCode() : 0);
    result = 31 * result + (productCategory != null ? productCategory.hashCode() : 0);
    result = 31 * result + (productPrice != +0.0f ? Float.floatToIntBits(productPrice) : 0);
    result = 31 * result + productQuantity;
    result = 31 * result + (productImageFileName != null ? productImageFileName.hashCode() : 0);
    return result;
  }
}

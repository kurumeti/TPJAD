package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ProductToOrder")
public class ProductToOrder {
  @Id
  @GeneratedValue
  private int ptoId;
  private int productId;
  private int orderId;
  private int productQuantity;

  public ProductToOrder() {
  }

  public ProductToOrder(int productId, int orderId, int productQuantity) {
    this.productId = productId;
    this.orderId = orderId;
    this.productQuantity = productQuantity;
  }

  public int getPtoId() {
    return ptoId;
  }

  public void setPtoId(int ptoId) {
    this.ptoId = ptoId;
  }

  public int getProductId() {
    return productId;
  }

  public void setProductId(int productId) {
    this.productId = productId;
  }

  public int getOrderId() {
    return orderId;
  }

  public void setOrderId(int orderId) {
    this.orderId = orderId;
  }

  public int getProductQuantity() {
    return productQuantity;
  }

  public void setProductQuantity(int productQuantity) {
    this.productQuantity = productQuantity;
  }

  @Override
  public String toString() {
    return "productId=" + productId +
        ", orderId=" + orderId +
        ", productQuantity=" + productQuantity +
        '}';
  }
}

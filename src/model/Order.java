package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Orders")
public class Order {
  @Id
  @GeneratedValue
  private int orderId;
  private String clientName;
  private String clientAddress;
  private BigDecimal orderTotal;
  private Date orderDate;

  public Order() {
  }

  public Order(String clientName, String clientAddress, BigDecimal orderTotal, Date orderDate) {
    this.clientName = clientName;
    this.clientAddress = clientAddress;
    this.orderTotal = orderTotal;
    this.orderDate = orderDate;
  }

  public int getOrderId() {
    return orderId;
  }

  public void setOrderId(int orderId) {
    this.orderId = orderId;
  }

  public String getClientName() {
    return clientName;
  }

  public void setClientName(String clientName) {
    this.clientName = clientName;
  }

  public String getClientAddress() {
    return clientAddress;
  }

  public void setClientAddress(String clientAddress) {
    this.clientAddress = clientAddress;
  }

  public BigDecimal getOrderTotal() {
    return orderTotal;
  }

  public void setOrderTotal(BigDecimal orderTotal) {
    this.orderTotal = orderTotal;
  }

  public Date getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(Date orderDate) {
    this.orderDate = orderDate;
  }

  @Override
  public String toString() {
    return clientName + "; " + ", " + clientAddress + "; Total: " + orderTotal + "; Date: " + orderDate;
  }
}

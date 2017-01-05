package beans;

import model.Product;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ManagedBean(name = "detailsBean")
@SessionScoped
public class DetailsBean {
  private Product product;
  private List<String> order;
  private int quantity;

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
    this.quantity = 0;
  }

  public String getName() {
    return product.getProductName();
  }

  public String getImage() {
    return product.getProductImageFileName();
  }

  public float getAvailableQuantity() {
    return product.getProductQuantity();
  }

  public List<String> getDetails() {
    List<String> details = new ArrayList<>();
    Collections.addAll(details, product.getProductDescription().split(";"));
    return details;
  }

  public float getPrice() {
    return product.getProductPrice();
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}

package beans;

import model.Product;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "detailsBean")
@ViewScoped
public class DetailsBean {
  static Product product;

  public static Product getProduct() {
    return product;
  }

  public static void setProduct(Product product) {
    DetailsBean.product = product;
  }

  public String getName() {
    return product.getProductName();
  }

  public String getImage() {
    return product.getProductImageFileName();
  }
}

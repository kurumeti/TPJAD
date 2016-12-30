package beans;

import model.Product;

import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "product")
public class ProductBean implements Serializable {
  private static List<Product> products;
  static {
    products = new ArrayList<>();
    products.add(new Product("Reaper's Scythe", "Perfect for collecting the souls of those who pun.", BigDecimal.valueOf(10), 1, ""));
  }

  public static List<Product> getProducts() {
    return products;
  }
}

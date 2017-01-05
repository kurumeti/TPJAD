package beans;

import ctrl.ClientController;
import model.Product;
import repo.OrderRepo;
import repo.ProductRepo;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "cartBean")
@SessionScoped
public class CartBean {

  //When I did this, only God and I knew what I was doing.
  //Now, only God knows.
  private Map<Product, Integer> cart = new HashMap<>();

  public void addToCart(Product product, int quantity) {
    System.out.println(quantity);
    if (cart == null) {
      cart = new HashMap<>();
    }

    if(isValidQuantity(product, quantity)) {
      if (cart.get(product) != null) {
        cart.put(product, quantity + cart.get(product));
      } else {
        cart.put(product, quantity);
      }
    } else {
      FacesContext.getCurrentInstance().addMessage("addForm:quantity", new FacesMessage("Quantity cannot be greater than the available quantity."));
    }
  }

  public boolean isValidQuantity(Product product, int quantity) {
    if(cart.get(product) != null) {
      return quantity + cart.get(product) <= product.getProductQuantity();
    } else {
      return quantity <= product.getProductQuantity();
    }
  }

  public float getTotalPrice() {
    float rez = 0;
    for (Product p : cart.keySet()) {
      rez += p.getProductPrice() * cart.get(p);
    }
    return rez;
  }

  public List<String> getOrder() {
    List<String> order = new ArrayList<>();
    for (Product p : cart.keySet()) {
      order.add(p.getProductId() + "#" + cart.get(p));
    }
    return order;
  }

  public List<Map.Entry<Product, Integer>> getCart() {
    return new ArrayList<>(cart.entrySet());
  }

  public void remove(Product product) {
    if(cart.get(product) != null) {
      cart.remove(product);
    }
  }
}

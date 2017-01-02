package beans;

import ctrl.ClientController;
import repo.OrderRepo;
import repo.ProductRepo;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;

@ManagedBean(name = "orderBean")
@SessionScoped
public class OrderBean {
  private String name;
  private String address;
  private String phone;
  private String email;
  private List<String> order;

  @EJB
  private ProductRepo productRepo;
  @EJB
  private OrderRepo orderRepo;

  private ClientController controller;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setOrder(List<String> order) {
    this.order = order;
  }

  public void completeOrder() {
    if(controller == null) {
      controller = new ClientController(productRepo, orderRepo);
    }

  }
}

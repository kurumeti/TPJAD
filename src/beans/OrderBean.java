package beans;

import ctrl.ClientController;
import model.Order;
import repo.OrderRepo;
import repo.ProductRepo;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "orderBean")
@SessionScoped
public class OrderBean {
  private String name;
  private String address;
  private String phone;
  private String email;
  private List<String> order;
  private float totalPrice;

  private EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysql");
  private EntityManager entityManager;
  private ProductRepo productRepo;
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

  public void setTotalPrice(float totalPrice) {
    this.totalPrice = totalPrice;
  }

  public void completeOrder() throws Exception {
    if (entityManager == null) {
      entityManager = factory.createEntityManager();
    }
    if (productRepo == null) {
      productRepo = new ProductRepo(entityManager);
    }
    if (orderRepo == null) {
      orderRepo = new OrderRepo(entityManager);
    }
    if (controller == null) {
      controller = new ClientController(productRepo, orderRepo);
    }

    Order orderInfo = new Order(name, address, totalPrice, new Date());

    controller.saveOrder(order, orderInfo);
    FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
  }

  public void clickListener(List<String> order, float totalPrice) {
    this.order = order;
    this.totalPrice = totalPrice;
  }

  public void cancelOrder() {
    FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
  }
}

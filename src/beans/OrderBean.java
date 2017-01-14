package beans;

import ctrl.ClientController;
import model.Order;
import model.OrderValidator;
import repo.OrderRepo;
import repo.ProductRepo;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
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

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void completeOrder() throws Exception {
        if (controller == null) {
            controller = new ClientController(productRepo, orderRepo);
        }
        try {
            Order orderInfo = new Order(name, address, totalPrice, new Date());
            OrderValidator validator = new OrderValidator();
            if (validator.validate(orderInfo)) {
                controller.saveOrder(order, orderInfo);
                FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
                //redirect to index
                FacesContext.getCurrentInstance().getExternalContext().redirect("searchPage.xhtml");
            } else {
                if (name.isEmpty())
                    FacesContext.getCurrentInstance().addMessage("orderForm:clientName", new FacesMessage("Client name cannot be empty or longer than 100 characters!"));
                if (address.isEmpty())
                    FacesContext.getCurrentInstance().addMessage("orderForm:clientAddress", new FacesMessage("Client address cannot be empty or longer than 255 characters!"));
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage("orderForm:clientName", new FacesMessage("Something went wrong with saving the order. Please try again!"));
        }
    }

    public void clickListener(List<String> order, float totalPrice) {
        this.order = order;
        this.totalPrice = totalPrice;
    }

    public void cancelOrder() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }
}

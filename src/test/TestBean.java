package test;

import ctrl.ClientController;
import model.Order;
import model.Product;
import repo.OrderRepo;
import repo.ProductRepo;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.*;

@ManagedBean(name = "testBean")
@SessionScoped
public class TestBean {
  @EJB
  private ProductRepo productRepo;
  @EJB
  private OrderRepo orderRepo;

  private ClientController controller;
  private Map<String, String> testResults;

  private void initCtrl() {
    if (controller == null) {
      controller = new ClientController(productRepo, orderRepo);
    }
  }

  private String testGetAllProducts() {
    initCtrl();
    String rez = "";
    try {
      List<Product> list = controller.getAllProducts();
      if (list != null && list.size() > 0) {
        rez = "Pass";
      }
    } catch (Exception ex) {
      rez = ex.getMessage();
    }
    return rez;
  }

  private String testGetFilteredProducts() {
    initCtrl();
    String rez = "";
    try {
      List<Product> list = controller.getFilteredProducts("Laptop");
      if (list != null && list.size() > 0) {
        rez = "Pass";
      }
    } catch (Exception ex) {
      rez = ex.getMessage();
    }
    return rez;
  }

  private String testGetAllCategories() {
    initCtrl();
    String rez = "";
    try {
      List<String> list = controller.getAllCategories();
      if (list != null && list.size() > 0) {
        rez = "Pass";
      }
    } catch (Exception ex) {
      rez = ex.getMessage();
    }
    return rez;
  }

  private String testSaveOrder() {
    initCtrl();
    String rez = "Pass";
    try {
      controller.saveOrder(Collections.singletonList("1#1"), new Order("test", "test", 1, new Date()));
      controller.revertLastOrder();
    } catch (Exception ex) {
      rez = ex.getMessage();
    }
    return rez;
  }

  public List<Map.Entry<String, String>> getResults() {
    if (testResults == null) {
      testResults = new HashMap<>();
    }
    testResults.clear();
    testResults.put("Get all products", testGetAllProducts());
    testResults.put("Get filtered products", testGetFilteredProducts());
    testResults.put("Get all categories", testGetAllCategories());
    testResults.put("Save order", testSaveOrder());
    return new ArrayList<>(testResults.entrySet());
  }
}

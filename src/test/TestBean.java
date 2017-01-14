package test;

import ctrl.ClientController;
import model.Order;
import model.Product;
import repo.OrderRepo;
import repo.ProductRepo;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.*;

@ManagedBean(name = "testBean")
@SessionScoped
public class TestBean {
  private EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysql_test");
  private EntityManager entityManager;
  private ProductRepo productRepo;
  private OrderRepo orderRepo;

  private ClientController controller;
  private Map<String, String> testResults;

  private void initResources() {
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
  }

  private String testGetAllProducts() {
    initResources();
    String rez = "";
    try {
      List<Product> list = controller.getAllProducts();
      if (list != null && list.size() == 1) {
        rez = "Pass";
      } else {
        rez = "No products found";
      }
    } catch (Exception ex) {
      rez = ex.getMessage();
    }
    return rez;
  }

  private String testGetFilteredProducts() {
    initResources();
    String rez = "";
    try {
      List<Product> list = controller.getFilteredProducts("test");
      if (list != null && list.size() == 1 && (list.get(0).getProductName().contains("test") || list.get(0).getProductDescription().contains("test"))) {
        rez = "Pass";
      } else {
        rez = "No products found for keyword test";
      }
    } catch (Exception ex) {
      rez = ex.getMessage();
    }
    return rez;
  }

  private String testGetAllCategories() {
    initResources();
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
    initResources();
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

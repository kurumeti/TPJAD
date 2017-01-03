package beans;

import ctrl.ClientController;
import repo.OrderRepo;
import repo.ProductRepo;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "searchBean")
@SessionScoped
public class SearchBean implements Serializable {
  @EJB
  private ProductRepo productRepo;
  @EJB
  private OrderRepo orderRepo;

  private ClientController controller;
  private Map<String, Object> categoryMap;

  private String category;

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public Map<String, Object> getCategoryMap() {
    if(categoryMap == null) {
      categoryMap = new LinkedHashMap<>();
    }
    if(controller == null) {
      controller = new ClientController(productRepo, orderRepo);
    }
    categoryMap.put("All", "All");
    List<String> list = controller.getAllCategories();
    for (String s : list) {
      categoryMap.put(s,s);
    }
    return categoryMap;
  }
}

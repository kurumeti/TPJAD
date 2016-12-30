package beans;

import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

@ManagedBean(name = "category")
public class CategoryBean implements Serializable {
  private static Map<String, Object> categoryMap;
  static {
    categoryMap = new LinkedHashMap<>();
    //TODO: load from db
    categoryMap.put("Cat1", "Cat1"); //label, value
    categoryMap.put("Cat2", "Cat2");
    categoryMap.put("Cat3", "Cat3");
  }

  private String category;

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public static Map<String, Object> getCategoryMap() {
    return categoryMap;
  }
}

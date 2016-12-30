package beans;

import model.Product;
import utils.PaginationHelper;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "productBean")
@ViewScoped
public class ProductBean implements Serializable {

  private static final long serialVersionUID = 1L;
  private static List<Product> products;
  static {
    products = new ArrayList<>();
    //TODO: db things
    products.add(new Product("Staff of Moses", "Can split things. Especially red, watery ones.", BigDecimal.valueOf(100), 1, ""));
    products.add(new Product("Caduceus", "Best used when buying or selling things", BigDecimal.valueOf(25), 1, ""));
    products.add(new Product("Helmet of Hades", "Invisibility is still in fashion.", BigDecimal.valueOf(80), 1, ""));
    products.add(new Product("Winged Sandals of Perseus", "Walking is such a drag.", BigDecimal.valueOf(75), 1, ""));
    products.add(new Product("Trident", "Curiously, it also causes earthquakes.", BigDecimal.valueOf(120), 1, ""));
  }

  private PaginationHelper pagination;
  private int selectedItemIndex;
  private DataModel dataModel = null;

  public PaginationHelper getPagination() {

    if (pagination == null) {

      pagination = new PaginationHelper(3) {
        @Override
        public int getItemsCount() {
          return products.size();
        }

        @Override
        public DataModel<Product> createPageDataModel() {
          System.out.println(getPageItemIndex());
          return new ListDataModel<>(products.subList(getPageItemIndex(), Math.min(getPageItemIndex() + getPageSize(), products.size())));
        }
      };
    }
    return pagination;
  }

  private void recreateModel() {
    dataModel = null;
  }

  public void next() {
    getPagination().nextPage();
    recreateModel();
  }

  public void previous() {
    getPagination().previousPage();
    recreateModel();
  }

  public DataModel getDataModel() {
    if (dataModel == null) {
      dataModel = getPagination().createPageDataModel();
    }
    return dataModel;
  }
}

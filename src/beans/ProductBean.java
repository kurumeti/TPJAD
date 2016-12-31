package beans;

import ctrl.ClientController;
import model.Product;
import repo.OrderRepo;
import repo.ProductRepo;
import utils.PaginationHelper;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "productBean")
@SessionScoped
public class ProductBean implements Serializable {

  @EJB
  private ProductRepo productRepo;
  @EJB
  private OrderRepo orderRepo;

  private List<Product> products = null;
  private ClientController controller;

  private PaginationHelper pagination;
  private int selectedItemIndex;
  private DataModel dataModel = null;

  public PaginationHelper getPagination() {
    if(controller == null) {
      controller = new ClientController(productRepo, orderRepo);
    }

    if(products == null) {
      products = controller.getAllProducts();
    }

    if (pagination == null) {

      pagination = new PaginationHelper(10) {
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

  public void clickListener() {
    int id = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
    for (Product p : products) {
      if (p.getProductId() == id) {
        DetailsBean.setProduct(p);
      }
    }
  }
}

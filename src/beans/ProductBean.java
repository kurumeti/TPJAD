package beans;

import ctrl.ClientController;
import model.Product;
import repo.OrderRepo;
import repo.ProductRepo;
import utils.PaginationHelper;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "productBean")
@SessionScoped
public class ProductBean implements Serializable {
    private ClientController controller;
    private ProductRepo productRepo;
    private OrderRepo orderRepo;

    private static final long serialVersionUID = 1L;
    private static List<Product> products;

    static {
        products = new ArrayList<>();
        //TODO: db things
        products.add(new Product("Staff of Moses", "Can split things. Especially red, watery ones.", "Moses", 100, 1, ""));
        products.add(new Product("Caduceus", "Best used when buying or selling things", "Category unknown", 25, 1, ""));
        products.add(new Product("Helmet of Hades", "Invisibility is still in fashion.", "Fashion", 80, 1, ""));
        products.add(new Product("Winged Sandals of Perseus", "Walking is such a drag.", "No it is not", 75, 1, ""));
        products.add(new Product("Trident", "Curiously, it also causes earthquakes.", "Did not know that", 120, 1, ""));
    }

    private PaginationHelper pagination;
    private int selectedItemIndex;
    private DataModel dataModel = null;

    public PaginationHelper getPagination() {
        if (controller == null) {
            controller = new ClientController(productRepo, orderRepo);
        }

        if (products == null) {
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

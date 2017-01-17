package beans;

import ctrl.ClientController;
import model.Product;
import repo.OrderRepo;
import repo.ProductRepo;
import utils.PaginationHelper;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ManagedBean(name = "productBean")
@SessionScoped
public class ProductBean implements Serializable {
    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysql");
    private EntityManager entityManager;
    private ProductRepo productRepo;
    private OrderRepo orderRepo;

    private List<Product> products = null;
    private ClientController controller;

    private PaginationHelper pagination;
    private DataModel dataModel = null;

    private String keyword;
    private String category;

    private boolean sortAscending = true;

    //sort by product name
    public void sortByName() {
        if (sortAscending) {
            //ascending order
            Collections.sort(products, (o1, o2) -> o1.getProductName().compareTo(o2.getProductName()));
            sortAscending = false;
        } else {
            //descending order
            Collections.sort(products, (o1, o2) -> o2.getProductName().compareTo(o1.getProductName()));
            sortAscending = true;
        }
        recreateModel();
    }

    //sort by product name
    public void sortByPrice() {
        if (sortAscending) {
            //ascending order
            Collections.sort(products, (o1, o2) -> {
                float price1 = o1.getProductPrice(), price2 = o2.getProductPrice();
                return price1 > price2 ? 1 : price1 < price2 ? -1 : 0;
            });
            sortAscending = false;
        } else {
            //descending order
            Collections.sort(products, (o1, o2) -> {
                float price1 = o1.getProductPrice(), price2 = o2.getProductPrice();
                return price2 > price1 ? 1 : price2 < price1 ? -1 : 0;
            });
            sortAscending = true;
        }
        recreateModel();
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public PaginationHelper getPagination() {
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

    private void recreatePagination() {
        pagination = null;
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

    public void clickListener(DetailsBean detailsBean) {
        int id = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
        for (Product p : products) {
            if (p.getProductId() == id) {
                detailsBean.setProduct(p);
            }
        }
    }

    public void search() {
        if ("".equals(keyword)) {
            products = controller.getAllProducts();
        } else {
            products = controller.getFilteredProducts(keyword);
        }
        if (!category.equals("All")) {
            products = products.stream().filter(x -> x.getProductCategory().equals(category)).collect(Collectors.toList());
        }

        recreatePagination();
        recreateModel();
    }
}

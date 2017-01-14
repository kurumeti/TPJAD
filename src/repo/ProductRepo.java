package repo;

import model.Product;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ProductRepo {
//  @PersistenceContext(unitName = "mysql")
  private EntityManager entityManager;

  public ProductRepo() {
  }

  public ProductRepo(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  //gets all the products in the db that have a quantity > 0
  public List<Product> getAllProducts() {
    List<Product> productList = null;
    try {
      TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p WHERE p.productQuantity > 0", Product.class);
      productList = query.getResultList();
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
    return productList;
  }

  //gets the product with the given id
  public Product getProductById(int productId) {
    Product product = null;
    try {
      TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p WHERE p.productId=" + productId, Product.class);
      product = query.getSingleResult();
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
    return product;
  }

  //gets the products that contain searchString in either name or description
  public List<Product> getProductByNameOrDescription(String searchString) {
    List<Product> productList = null;
    try {
      TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p WHERE p.productQuantity > 0 " +
          "AND (p.productDescription LIKE '%" + searchString + "%' OR p.productName LIKE '%" + searchString + "%')", Product.class);
      productList = query.getResultList();
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
    return productList;
  }

  //updates the given product when an order is finalized by reducing the available quantity
  public Product updateProduct(Product p) throws Exception {
    try {
      EntityTransaction t = entityManager.getTransaction();
      t.begin();
      Product rez = entityManager.merge(p);
      t.commit();
      return rez;
    } catch (IllegalArgumentException | TransactionRequiredException ex) {
      throw new Exception(ex.getMessage());
    }
  }

  public List<String> getCategories() {
    List<String> rez = new ArrayList<>();
    try {
      TypedQuery<String> query = entityManager.createQuery("SELECT distinct p.productCategory FROM Product p", String.class);
      rez = query.getResultList();
    }catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
    return rez;
  }
}

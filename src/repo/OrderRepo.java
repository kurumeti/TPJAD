package repo;

import model.Order;
import model.OrderValidator;
import model.ProductToOrder;

import javax.ejb.Stateless;
import javax.persistence.*;

@Stateless
public class OrderRepo {
//  @PersistenceContext(unitName = "mysql")
  private EntityManager entityManager;
  private OrderValidator orderValidator = new OrderValidator();

  public OrderRepo() {
  }

  public OrderRepo(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public Order addOrder(Order o) throws Exception {
    try {
      if (orderValidator.validate(o)) {
        EntityTransaction t = entityManager.getTransaction();
        t.begin();
        entityManager.persist(o);
        //flush needed to get the new id
        entityManager.flush();
        t.commit();
        return o;
      } else throw new Exception("Incorrect order arguments!");
    } catch (EntityExistsException | IllegalArgumentException | TransactionRequiredException ex) {
      throw new Exception(ex.getMessage());
    }
  }

  public void addProductToOrder(ProductToOrder pto) throws Exception {
    try {
      EntityTransaction t = entityManager.getTransaction();
      t.begin();
      entityManager.persist(pto);
      entityManager.flush();
      t.commit();
    } catch (EntityExistsException | IllegalArgumentException | TransactionRequiredException ex) {
      throw new Exception(ex.getMessage());
    }
  }

  public Order getLastOrder() throws Exception {
    Order rez = null;
    try {
      TypedQuery<Order> query = entityManager.createQuery("SELECT o from Order o order by o.orderDate desc", Order.class);
      rez = query.setMaxResults(1).getResultList().get(0);
    } catch (EntityExistsException | IllegalArgumentException | TransactionRequiredException ex) {
      throw new Exception(ex.getMessage());
    }
    return rez;
  }

  public ProductToOrder getLastProductToOrder() throws Exception {
    ProductToOrder rez = null;
    try {
      TypedQuery<ProductToOrder> query = entityManager.createQuery("SELECT pto from ProductToOrder pto order by pto.ptoId desc ", ProductToOrder.class);
      rez = query.setMaxResults(1).getResultList().get(0);
    } catch (EntityExistsException | IllegalArgumentException | TransactionRequiredException ex) {
      throw new Exception(ex.getMessage());
    }
    return rez;
  }

  public void deleteOrder(int orderId) throws Exception {
    try {
      Order order = getOrderById(orderId);
      entityManager.remove(order);
    } catch (EntityExistsException | IllegalArgumentException | TransactionRequiredException ex) {
      throw new Exception(ex.getMessage());
    }
  }

  private Order getOrderById(int orderId) {
    TypedQuery<Order> query = entityManager.createQuery("SELECT o from Order o where o.orderId = " + orderId, Order.class);
    return query.getSingleResult();
  }

  public void deleteProductToOrder(int productToOrderId) throws Exception {
    try {
      ProductToOrder productToOrder = getProductToOrderById(productToOrderId);
      entityManager.remove(productToOrder);
    } catch (EntityExistsException | IllegalArgumentException | TransactionRequiredException ex) {
      throw new Exception(ex.getMessage());
    }
  }

  private ProductToOrder getProductToOrderById(int productToOrderId) {
    TypedQuery<ProductToOrder> query = entityManager.createQuery("SELECT pto from ProductToOrder pto where pto.ptoId = " + productToOrderId, ProductToOrder.class);
    return query.getSingleResult();
  }
}

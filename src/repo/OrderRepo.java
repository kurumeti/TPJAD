package repo;

import model.Order;
import model.OrderValidator;
import model.ProductToOrder;

import javax.persistence.*;

public class OrderRepo {
    @PersistenceContext(unitName = "mysql")
    private EntityManager entityManager;
    private OrderValidator orderValidator;

    public Order addOrder(Order o) throws Exception {
        try {
            if (orderValidator.validate(o)) {
                entityManager.persist(o);
                //flush needed to get the new id
                entityManager.flush();
                return o;
            } else throw new Exception("Incorrect order arguments!");
        } catch (EntityExistsException | IllegalArgumentException | TransactionRequiredException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void addProductToOrder(ProductToOrder pto) throws Exception {
        try {
            entityManager.persist(pto);
        } catch (EntityExistsException | IllegalArgumentException | TransactionRequiredException ex) {
            throw new Exception(ex.getMessage());
        }
    }
}

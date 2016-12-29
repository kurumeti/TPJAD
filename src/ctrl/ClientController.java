package ctrl;

import model.Order;
import model.Product;
import model.ProductToOrder;
import repo.OrderRepo;
import repo.ProductRepo;

import java.util.List;

public class ClientController {
    private ProductRepo productRepo;
    private OrderRepo orderRepo;

    public ClientController(ProductRepo productRepo, OrderRepo orderRepo) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
    }

    public List<Product> getAllProducts() {
        return productRepo.getAllProducts();
    }

    public List<Product> getFilteredProducts(String searchString) {
        return productRepo.getProductByNameOrDescription(searchString);
    }

    //orderItems: <productId#quantity>
    //clientOrderInfo: client info
    public void saveOrder(List<String> orderItems, Order clientOrderInfo) throws Exception {
        try {
            //save client order info
            Order o = orderRepo.addOrder(clientOrderInfo);
            for (String s : orderItems) {
                String[] data = s.split("#");
                Product p = productRepo.getProductById(Integer.parseInt(data[0]));
                if (p == null) {
                    throw new Exception("Cannot find ordered product!");
                }
                ProductToOrder pto = new ProductToOrder();
                pto.setOrderId(o.getOrderId());
                pto.setProductId(p.getProductId());
                int quantity = Integer.parseInt(data[1]);
                int originalQuantity = p.getProductQuantity();
                if (quantity <= 0) {
                    throw new Exception("Ordered quantity cannot be zero or negative!");
                }
                if(originalQuantity<quantity){
                    throw new Exception("Cannot order more items than available!");
                }
                pto.setProductQuantity(quantity);
                //insert pto
                orderRepo.addProductToOrder(pto);
                //update product quantity from pto
                p.setProductQuantity(originalQuantity-quantity);
                productRepo.updateProduct(p);
            }
        } catch (Exception ex) {
            throw new Exception("Incorrect order arguments!");
        }
    }
}

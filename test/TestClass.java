import model.Product;
import org.junit.jupiter.api.Test;
import repo.ProductRepo;

import javax.ejb.embeddable.EJBContainer;
import java.util.List;

//@RunWith(Arquillian.class)
public class TestClass {
//    @Deployment
//    public static Archive<?> createDeployment() {
//        return ShrinkWrap.create(WebArchive.class, "test.war")
//                .addPackage(Product.class.getPackage()) // Add classes
//                .addPackage(OrderRepo.class.getPackage()) // Add more classes
//                .addPackage(ProductRepo.class.getPackage()) // Add more classes
//                .addPackage(ClientController.class.getPackage()) // Add more classes
//                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
//                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
//    }

//    @EJB
//    private ProductRepo productRepo;
//    @EJB
//    private OrderRepo orderRepo;
//    @EJB
//    private ClientController clientController;

    @Test
    public void testGetAllProducts() throws Exception {
        System.out.println("get all products");
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        ProductRepo instance = (ProductRepo) container.getContext().lookup("java:global/classes/ProductRepo");
        List<Product> result = instance.getAllProducts();
        for (Product p : result) {
            if (p.getProductQuantity() <= 0) assert false;
        }
        assert true;
        container.close();
    }

//    // Calls ctrl to return all the products from the db.
//    // test passed if: all the products have quantity > 0.
//    @test
//    public void testSearchPage() {
//        List<Product> allProducts = clientController.getAllProducts();
//        for (Product p : allProducts) {
//            if (p.getProductQuantity() <= 0) assert false;
//        }
//        assert true;
//    }
//
//    // Calls ctrl to return only the products that contain a given keyword in their name or description.
//    // test case 1: filter by empty => test passed if all products are returned
//    // test case 2: filter by an existing keyword => test passed if all the products contain the given keyword
//    // test case 3: filter by a nonexistent keyword => test passed if nothing is returned
//    @test
//    public void testFiltering() {
//        String keyword1 = "", keyword2 = "Laptop", keyword3 = "zzzzz";
//        List<Product> allProducts = clientController.getAllProducts();
//
//        List<Product> filtered1 = clientController.getFilteredProducts(keyword1);
//        if (filtered1.size() != allProducts.size()) assert false;
//
//        List<Product> filtered2 = clientController.getFilteredProducts(keyword2);
//        if (filtered2.size() != allProducts.size()) assert false;
//        else
//            for (Product p : filtered2) {
//                if (!(p.getProductName().contains(keyword2) || p.getProductDescription().contains(keyword2)))
//                    assert false;
//            }
//
//        List<Product> filtered3 = clientController.getFilteredProducts(keyword3);
//        if (filtered3.size() != 0) assert false;
//
//        assert true;
//    }
//
//    // Requests the product with id 1 and checks to see if the name corresponds with the returned result.
//    // Expected result: the product with id 1 is returned with all the correct detail fields (name, description, price, quantity)
//    @test
//    public void testViewProductDetails() {
//        Product p = productRepo.getProductById(1);
//        String name = "Laptop ASUS A540LJ-XX612D Procesor Intel® Core™ i3-5005U 2.00GHz, Broadwell™, 15.6\", 4GB, 500GB, DVD-RW, nVIDIA® GeForce® 920M 2GB, Free DOS, Chocolate Black";
//        if (p.getProductName().equals(name)) assert true;
//
//        assert false;
//    }
//
//    // Requests the contents of the shopping cart
//    // test case 1: if the cart is empty, an empty list is returned, no exception is thrown
//    // test case 2: if the cart contains something, the complete and correct contents are returned
//    @test
//    public void testViewCart() {
//        CartBean cartBean = new CartBean();
//        if (cartBean.getCart().size() != 0) assert false;
//
//        Product p = productRepo.getProductById(1);
//        cartBean.addToCart(p, 1);
//        if (cartBean.getCart().size() != 1) assert false;
//
//        assert true;
//    }
//
//    // Adds a product to the shopping cart.
//    // test case 1: adds an existing number of products (between 1 and available quantity) => test passed if product is added
//    // test case 2: adds a negative or 0 number of products => test fails and nothing is added
//    // test case 3: adds more than the available quantity for the product => test fails and nothing is added
//    // test case 4: adds a char as the product quantity => test fails and nothing is added
//    @test
//    public void testAddToCart() {
//        CartBean cartBean = new CartBean();
//        Product p = productRepo.getProductById(1);
//
//        cartBean.addToCart(p, 1);
//        if (cartBean.getCart().size() != 1) assert false;
//
//        cartBean.addToCart(p, p.getProductQuantity());
//        if (cartBean.getCart().size() != 2) assert false;
//
//        p = productRepo.getProductById(2);
//        cartBean.addToCart(p, 0);
//        if (cartBean.getCart().size() != 2) assert false;
//
//        cartBean.addToCart(p, -1);
//        if (cartBean.getCart().size() != 2) assert false;
//
//        cartBean.addToCart(p, p.getProductQuantity() + 1);
//        if (cartBean.getCart().size() != 2) assert false;
//
//        cartBean.addToCart(p, 'a');
//        if (cartBean.getCart().size() != 2) assert false;
//
//        assert true;
//    }
//
//    // Deletes a product from the shopping cart.
//    // test passed if the selected product and only the selected product is removed from the cart.
//    @test
//    public void testDeleteFromCart() {
//        CartBean cartBean = new CartBean();
//        Product p = productRepo.getProductById(1);
//        cartBean.addToCart(p, 1);
//        cartBean.remove(p);
//        if (cartBean.getCart().size() != 0) assert false;
//        assert true;
//    }
//
//    // Finalize order by filling in client details and saving the order in the database.
//    // Also updates the available quantity for the ordered products.
//    // test case 1: all the user information given is correct => test passed and insert and update operations are completed correctly
//    // test case 2: string values are either null, or the floating point value is null or a char=> test fails and the info is requested again
//    @test
//    public void testFinalizeOrder() {
//        CartBean cartBean = new CartBean();
//        Product p = productRepo.getProductById(1);
//        cartBean.addToCart(p, 1);
//        OrderBean orderBean = new OrderBean();
//        orderBean.setAddress("Cluj, nr 123");
//        orderBean.setEmail("mail@mail.com");
//        orderBean.setName("Client1");
//        orderBean.setPhone("12345");
//        orderBean.setTotalPrice(p.getProductPrice());
//        orderBean.setOrder(cartBean.getOrder());
//        try {
//            orderBean.completeOrder();
//            if (productRepo.getProductById(1).getProductQuantity() != p.getProductQuantity() - 1) assert false;
//        } catch (Exception ex) {
//            assert false;
//        }
//
//        orderBean.setPhone("");
//        orderBean.setTotalPrice(p.getProductPrice());
//        orderBean.setOrder(cartBean.getOrder());
//        try {
//            orderBean.completeOrder();
//        } catch (Exception ex) {
//            assert true;
//        }
//
//        orderBean.setPhone("12345");
//        orderBean.setTotalPrice('a');
//        orderBean.setOrder(cartBean.getOrder());
//        try {
//            orderBean.completeOrder();
//        } catch (Exception ex) {
//            assert true;
//        }
//
//        assert true;
//    }
//
//    // Cancel the order either from the cart page, or from the finalize order page.
//    // Clears the shopping cart from all products.
//    @test
//    public void testCancelOrder() {
//        CartBean cartBean = new CartBean();
//        OrderBean orderBean = new OrderBean();
//        Product p = productRepo.getProductById(1);
//        cartBean.addToCart(p, 1);
//        orderBean.setOrder(cartBean.getOrder());
//        orderBean.cancelOrder();
//        if (cartBean.getCart().size() != 0) assert false;
//
//        assert true;
//    }
}

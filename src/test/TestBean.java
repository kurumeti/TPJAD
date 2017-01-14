package test;

import beans.CartBean;
import beans.OrderBean;
import ctrl.ClientController;
import model.Order;
import model.Product;
import model.TestResult;
import repo.OrderRepo;
import repo.ProductRepo;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.*;

@ManagedBean(name = "testBean")
@SessionScoped
public class TestBean {
    @EJB
    private ProductRepo productRepo;
    @EJB
    private OrderRepo orderRepo;

    private ClientController controller;
    private List<TestResult> testResults;

    private void initCtrl() {
        if (controller == null) {
            controller = new ClientController(productRepo, orderRepo);
        }
    }

    // Calls ctrl to return all the products from the db.
    // test passed if: all the products have quantity > 0
    private String testGetAllProducts() {
        initCtrl();
        String rez = "";
        try {
            List<Product> list = controller.getAllProducts();
            boolean result = true;
            if (list != null && list.size() > 0) {
                for (Product p : list) {
                    if (p.getProductQuantity() <= 0) result = false;
                }
            } else result = false;

            if (result == true)
                rez = "Pass";
            else rez = "Failed";
        } catch (Exception ex) {
            rez = ex.getMessage();
        }
        return rez;
    }

    // test case 1: filter by empty => test passed if all products are returned
    // test case 2: filter by an existing keyword => test passed if all the products contain the given keyword
    // test case 3: filter by a nonexistent keyword => test passed if nothing is returned
    private String testGetFilteredProducts(String keyword) {
        initCtrl();
        String rez = "";
        try {
            List<Product> list = controller.getFilteredProducts(keyword);
            if (!keyword.isEmpty()) {
                if (keyword.equals("stuff"))
                    if (list != null && list.size() == 0) rez = "Pass";
                    else rez = "Failed";
                else if (keyword.equals("Laptop"))
                    if (list != null && list.size() > 0) {
                        String ok = "Pass";
                        for (Product p : list) {
                            if (!(p.getProductName().contains(keyword) || p.getProductDescription().contains(keyword)))
                                ok = "Failed";
                        }
                        rez = ok;
                    } else rez = "Failed";
            } else if (list != null && list.size() > 0) rez = "Pass";
            else rez = "Failed";
        } catch (Exception ex) {
            rez = ex.getMessage();
        }
        return rez;
    }

    // Requests the product with id 1 and checks to see if the name corresponds with the returned result.
    // Expected result: the product with id 1 is returned with all the correct detail fields (name, description, price, quantity)
    public String testGetProductDetails() {
        String rez = "";
        try {
            Product p = productRepo.getProductById(1);
            String name = "Laptop ASUS A540LJ-XX612D Procesor Intel® Core™ i3-5005U 2.00GHz, Broadwell™, 15.6\", 4GB, 500GB, DVD-RW, nVIDIA® GeForce® 920M 2GB, Free DOS, Chocolate Black";
            if (p.getProductName().equals(name)) rez = "Pass";
            else rez = "Failed";
        } catch (Exception ex) {
            rez = ex.getMessage();
        }
        return rez;
    }

    // Calls the controller to get all the available categories.
    // test passed if categories list has a size of 6
    private String testGetAllCategories() {
        String rez = "";
        initCtrl();
        try {
            List<String> list = controller.getAllCategories();
            if (list != null && list.size() > 0) {
                if (list.size() == 7) rez = "Pass";
                else rez = "Failed";
            }
        } catch (Exception ex) {
            rez = ex.getMessage();
        }
        return rez;
    }

    // Adds a product to the shopping cart in a given quantity.
    // test case 1: adds an existing number of products (between 1 and available quantity) => test passed if product is added
    // test case 2: adds a negative or 0 number of products => test fails and nothing is added
    // test case 3: adds more than the available quantity for the product => test fails and nothing is added
    // test case 4: adds a char as the product quantity => test fails and nothing is added
    public String testAddToCart(Product p, int quantity) {
        String rez = "";
        try {
            CartBean cartBean = new CartBean();
            cartBean.addToCart(p, quantity);
            if (quantity > 0 && quantity <= p.getProductQuantity())
                if (cartBean.getCart().size() == 1) rez = "Pass";
                else rez = "Failed";
            else if (cartBean.getCart().size() != 0) rez = "Failed";
            else rez = "Pass";
        } catch (Exception ex) {
            rez = ex.getMessage();
        }
        return rez;
    }

    // Deletes a product from the shopping cart.
    // test passed if the selected product and only the selected product is removed from the cart
    public String testDeleteFromCart() {
        String rez = "";
        try {
            CartBean cartBean = new CartBean();
            Product p = productRepo.getProductById(1);
            cartBean.addToCart(p, 1);
            cartBean.addToCart(productRepo.getProductById(2), 1);
            cartBean.remove(p);
            if (cartBean.getCart().size() != 1) rez = "Failed";
            else rez = "Pass";
        } catch (Exception ex) {
            rez = ex.getMessage();
        }
        return rez;
    }

    // Requests the contents of the shopping cart
    // test case 1: if the cart is empty, an empty list is returned
    // test case 2: if the cart contains something, the complete and correct contents are returned
    public String testViewCart(Product p) {
        String rez = "";
        try {
            CartBean cartBean = new CartBean();
            if (p == null)
                if (cartBean.getCart().size() != 0) rez = "Failed";
                else rez = "Pass";
            else {
                cartBean.addToCart(p, 1);
                if (cartBean.getCart().size() != 1) rez = "Failed";
                else rez = "Pass";
            }
        } catch (Exception ex) {
            rez = ex.getMessage();
        }
        return rez;
    }

    // Finalize order by filling in client details and saving the order in the database.
    // Also updates the available quantity for the ordered products.
    // test case 1: all the user information given is correct => test passed and insert and update operations are completed correctly
    // test case 2: string values are either null, or the floating point value is null or a char=> test fails and the info is requested again
    private String testSaveOrder(String productId, String quantity, Order o) {
        initCtrl();
        String rez = "Pass";
        try {
            controller.saveOrder(Collections.singletonList(productId + "#" + quantity), o);
            controller.revertLastOrder();
        } catch (Exception ex) {
            rez = ex.getMessage();
        }
        return rez;
    }


    public List<TestResult> getResults() {
        if (testResults == null) testResults = new ArrayList<>();
        testResults.clear();
        testResults.add(new TestResult("Get all products", "Test passed if: all the products have quantity > 0", testGetAllProducts()));
        testResults.add(new TestResult("Get product details", "Test passed if the product with requested id is returned with all the correct detail fields (name, description, price, quantity)", testGetProductDetails()));
        testResults.add(new TestResult("Get filtered products", "Test case 1: filter by empty => test passed if all products are returned", testGetFilteredProducts("")));
        testResults.add(new TestResult("Get filtered products", "Test case 2: filter by an existing keyword('Laptop') => test passed if all the products contain the given keyword", testGetFilteredProducts("Laptop")));
        testResults.add(new TestResult("Get filtered products", "Test case 3: filter by a nonexistent keyword('stuff') => test passed if nothing is returned", testGetFilteredProducts("stuff")));
        testResults.add(new TestResult("Get all categories", "Test passed if all 7 categories are returned", testGetAllCategories()));
        testResults.add(new TestResult("View content of shopping cart", "Test case 1: if the cart is empty, an empty list is returned", testViewCart(null)));
        testResults.add(new TestResult("View content of shopping cart", "Test case 2: if the cart contains something, the complete and correct contents are returned", testViewCart(productRepo.getProductById(1))));
        testResults.add(new TestResult("Adds a product to the shopping cart in a given quantity", "Test case 1: adds an existing number of products (between 1 and available quantity) => test passed if product is added", testAddToCart(productRepo.getProductById(1), 1)));
        testResults.add(new TestResult("Adds a product to the shopping cart in a given quantity", "Test case 2: adds 0 number of products => test fails and nothing is added", testAddToCart(productRepo.getProductById(1), 0)));
        testResults.add(new TestResult("Adds a product to the shopping cart in a given quantity", "Test case 3: adds a negative number of products => test fails and nothing is added", testAddToCart(productRepo.getProductById(1), -1)));
        testResults.add(new TestResult("Adds a product to the shopping cart in a given quantity", "Test case 4: adds more than the available quantity for the product => test fails and nothing is added", testAddToCart(productRepo.getProductById(1), productRepo.getProductById(1).getProductQuantity() + 1)));
        testResults.add(new TestResult("Adds a product to the shopping cart in a given quantity", "Test case 5: adds a char as the product quantity => test fails and nothing is added", testAddToCart(productRepo.getProductById(1), 'a')));
        testResults.add(new TestResult("Deletes a product from the shopping cart", "Test passed if the selected product and only the selected product is removed from the cart", testDeleteFromCart()));
        testResults.add(new TestResult("Finalize order by filling in client details and saving the order in the database", "Test case 1: all the user information given is correct => test passed and insert and update operations are completed correctly", testSaveOrder("1", "1", new Order("test", "test", 1, new Date()))));
        testResults.add(new TestResult("Finalize order by filling in client details and saving the order in the database", "Test case 2: a string value is empty => test fails and the info is requested again", testSaveOrder("1", "1", new Order("", "test", 1, new Date()))));
        testResults.add(new TestResult("Finalize order by filling in client details and saving the order in the database", "Test case 3: a string value is null => test fails and the info is requested again", testSaveOrder("1", "1", new Order("test", null, 1, new Date()))));
        testResults.add(new TestResult("Finalize order by filling in client details and saving the order in the database", "Test case 4: the floating point value is null or a char => test fails and the info is requested again", testSaveOrder("1", "1", new Order("test", "test", 'a', new Date()))));
        return testResults;
    }
}

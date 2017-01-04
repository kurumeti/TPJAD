package model;

public class ProductValidator {
    public boolean validate(Product p) {
        if (p == null)
            return false;
        if (p.getProductName().isEmpty() || p.getProductName().length() < 1)
            return false;
        if (p.getProductDescription().isEmpty() || p.getProductDescription().length() < 1)
            return false;
        if (p.getProductQuantity() < 1)
            return false;
        if (p.getProductImageFileName().isEmpty() || (p.getProductImageFileName().length() < 1 || p.getProductImageFileName().length() > 254))
            return false;
        return true;
    }
}

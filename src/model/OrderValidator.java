package model;

public class OrderValidator {
    public boolean validate(Order o) {
        if (o == null)
            return false;
        if (o.getClientName().isEmpty() || (o.getClientName().length() < 1 || o.getClientName().length() > 100))
            return false;
        if (o.getClientAddress().isEmpty() || (o.getClientAddress().length() < 1 || o.getClientAddress().length() > 254))
            return false;
        if (o.getOrderTotal() <= 0)
            return false;
        return true;
    }
}

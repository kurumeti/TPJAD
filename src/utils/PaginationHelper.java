package utils;

import model.Product;

import javax.faces.model.DataModel;

public abstract class PaginationHelper {
    private int pageSize;
    private int page = 0;

    public PaginationHelper(int pageSize) {
        this.pageSize = pageSize;
    }

    public abstract int getItemsCount();

    public abstract DataModel<Product> createPageDataModel();

    public int getPageItemIndex() {
        return page * pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageLastItem() {
        int i = getPageItemIndex() + pageSize - 1;
        int count = getItemsCount() - 1;
        if (i > count) {
            i = count;
        }
        if (i < 0) {
            i = 0;
        }
        return i;
    }

    public boolean isHasNextPage() {
        return (page + 1) * pageSize + 1 <= getItemsCount();
    }

    public void nextPage() {
        if (isHasNextPage()) {
            page++;
        }
    }

    public boolean isHasPreviousPage() {
        return page > 0;
    }

    public void previousPage() {
        if (isHasPreviousPage()) {
            page--;
        }
    }

    public int getPage() {
        return page;
    }
}

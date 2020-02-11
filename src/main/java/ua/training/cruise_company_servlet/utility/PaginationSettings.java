package ua.training.cruise_company_servlet.utility;

public class PaginationSettings {
    private int pageSize;
    private long currentPageNumber;

    public PaginationSettings(int pageSize, long currentPageNumber) {
        if (pageSize <= 0) {
            throw new IllegalArgumentException("pageSize has to be positive number.");
        }
        if (currentPageNumber < 0) {
            throw new IllegalArgumentException("currentPageNumber couldn't be a negative number.");
        }
        this.pageSize = pageSize;
        this.currentPageNumber = currentPageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getCurrentPageNumber() {
        return currentPageNumber;
    }

    @Override
    public String toString() {
        return "PaginationSettings{" +
                "pageSize=" + pageSize +
                ", currentPageNumber=" + currentPageNumber +
                '}';
    }
}

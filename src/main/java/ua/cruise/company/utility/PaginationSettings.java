package ua.cruise.company.utility;

public class PaginationSettings {
    private int pageSize;
    private long currentPageNumber;

    public PaginationSettings(int pageSize, long currentPageNumber) {
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

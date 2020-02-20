package ua.cruise.company.utility;

import java.util.List;

public class Page<T> {
    private long totalElements;
    private long currentPageNumber;
    private int size;
    private List<T> content;

    public Page(PaginationSettings settings) {
        currentPageNumber = settings.getCurrentPageNumber();
        size = settings.getPageSize();
    }

    public Page(List<T> content, PaginationSettings settings, long totalElements) {
        this(settings);
        this.content = content;
        this.totalElements = totalElements;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public long getTotalPages() {
        return (long) Math.ceil((double) totalElements / (double) size);
    }

    public long getCurrentPageNumber() {
        return currentPageNumber;
    }

    public int getSize() {
        return size;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Page{" +
                "totalElements=" + totalElements +
                ", currentPageNumber=" + currentPageNumber +
                ", size=" + size +
                ", content=" + content +
                '}';
    }
}

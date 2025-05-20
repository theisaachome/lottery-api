package com.highway.lottery.common.dto;

import java.util.List;

public record APIListResponse<T>(
        boolean success,
        Meta meta,
        List<T> data,
        String message
) {
    public static record Meta(
            int page,
            int limit,
            int totalPages,
            long totalElements,
            boolean isLast
    ) {}
}

//
//
//class Meta{
//    private int page;
//    private int limit;
//    private int totalPages;
//    private int totalElements;
//
//
//    public Meta(int page, int limit, int totalPages, int totalElements) {
//        this.page = page;
//        this.limit = limit;
//        this.totalPages = totalPages;
//        this.totalElements = totalElements;
//    }
//
//    public int getPage() {
//        return page;
//    }
//
//    public void setPage(int page) {
//        this.page = page;
//    }
//
//    public int getLimit() {
//        return limit;
//    }
//
//    public void setLimit(int limit) {
//        this.limit = limit;
//    }
//
//    public int getTotalPages() {
//        return totalPages;
//    }
//
//    public void setTotalPages(int totalPages) {
//        this.totalPages = totalPages;
//    }
//
//    public int getTotalElements() {
//        return totalElements;
//    }
//
//    public void setTotalElements(int totalElements) {
//        this.totalElements = totalElements;
//    }
//}

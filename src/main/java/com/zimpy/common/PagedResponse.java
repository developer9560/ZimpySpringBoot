package com.zimpy.common;

import org.springframework.data.domain.Page;

import java.util.List;

public class PagedResponse<T> {

    private List<T> data;
    private PaginationMeta meta;

    public PagedResponse(Page<T> page) {
        this.data = page.getContent();
        this.meta = new PaginationMeta(page);
    }

    // getters

    public List<T> getData() {
        return data;
    }

    public PaginationMeta getMeta() {
        return meta;
    }
}

class PaginationMeta {

    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean last;

    public PaginationMeta(Page<?> page) {
        this.page = page.getNumber();
        this.size = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.last = page.isLast();
    }
}

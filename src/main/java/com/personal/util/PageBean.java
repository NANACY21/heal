package com.personal.util;

/**
 * @author 李箎
 */
public class PageBean {
    //当前页
    private int currentPage;
    //每页显示的条数
    private int pageSize;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}

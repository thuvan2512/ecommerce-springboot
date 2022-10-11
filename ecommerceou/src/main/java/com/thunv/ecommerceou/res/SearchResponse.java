package com.thunv.ecommerceou.res;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public class SearchResponse {
    @Getter @Setter
    private Integer totalObject;
    @Getter @Setter
    private Integer pageSize;
    @Getter @Setter
    private Integer totalPage;
    @Getter @Setter
    private Integer currentPage;
    @Getter @Setter
    private Integer totalObjectInCurrentPage;
    @Getter @Setter
    private List<Object> listResult;

    public SearchResponse() {
    }

    public SearchResponse(Integer totalPage,Integer pageSize, Integer totalObject, Integer currentPage, Integer totalObjectInCurrentPage, List<Object> listResult) {
        this.totalPage = totalPage;
        this.pageSize = pageSize;
        this.totalObject = totalObject;
        this.currentPage = currentPage;
        this.totalObjectInCurrentPage = totalObjectInCurrentPage;
        this.listResult = listResult;
    }
}

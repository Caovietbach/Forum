package org.example.forum.response.pagination;

import lombok.Data;

@Data
public class PostListResponse {
    private long totalElements;
    private int totalPages;
    private int size;
    private Object content;

    public PostListResponse(long totalElements, int totalPages, int size, Object content) {
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.size = size;
        this.content = content;
    }
}

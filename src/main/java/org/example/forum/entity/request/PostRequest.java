package org.example.forum.entity.request;

import lombok.Data;

@Data
public class PostRequest {

    private Long accountId;

    private String title;
}

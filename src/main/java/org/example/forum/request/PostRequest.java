package org.example.forum.request;

import lombok.Data;

@Data
public class PostRequest {

    private Long accountId;

    private String title;
}

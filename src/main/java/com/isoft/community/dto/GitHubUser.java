package com.isoft.community.dto;

import lombok.Data;

@Data
public class GitHubUser {
    private String bio;
    private long id;
    private String name;
    private String avatar_url;
}

package com.isoft.community.dto;

public class GitHubUser {
    private String bio;
    private long id;
    private String name;

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getBio() {
        return bio;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public GitHubUser() {
    }

    public GitHubUser(String bio, long id, String name) {
        this.bio = bio;
        this.id = id;
        this.name = name;
    }
}

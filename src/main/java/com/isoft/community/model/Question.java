package com.isoft.community.model;

public class Question {
    private Integer id;
    private String title;
    private String description;
    private long gmt_create;
    private long gmt_modified;
    private String tag;
    private Integer creator;
    private Integer view_count;
    private Integer like_count;
    private Integer comment_count;

    public Question() {
    }

    public Question(Integer id, String title, String description, long gmt_create, long gmt_modified, String tag, Integer creator, Integer view_count, Integer like_count, Integer comment_count) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.gmt_create = gmt_create;
        this.gmt_modified = gmt_modified;
        this.tag = tag;
        this.creator = creator;
        this.view_count = view_count;
        this.like_count = like_count;
        this.comment_count = comment_count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getGmt_create() {
        return gmt_create;
    }

    public void setGmt_create(long gmt_create) {
        this.gmt_create = gmt_create;
    }

    public long getGmt_modified() {
        return gmt_modified;
    }

    public void setGmt_modified(long gmt_modified) {
        this.gmt_modified = gmt_modified;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Integer getView_count() {
        return view_count;
    }

    public void setView_count(Integer view_count) {
        this.view_count = view_count;
    }

    public Integer getLike_count() {
        return like_count;
    }

    public void setLike_count(Integer like_count) {
        this.like_count = like_count;
    }

    public Integer getComment_count() {
        return comment_count;
    }

    public void setComment_count(Integer comment_count) {
        this.comment_count = comment_count;
    }
}

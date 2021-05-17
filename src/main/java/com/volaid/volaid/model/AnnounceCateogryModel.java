package com.volaid.volaid.model;

import javax.validation.constraints.NotNull;

public class AnnounceCateogryModel {

    private Long id;

    @NotNull
    private String categoryName;

    @NotNull
    private String categoryIcon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon;
    }
}

package edu.osu.myapplication.Data;

import java.util.List;

public class Store {
    private String storeId;
    private String storeName;
    private List<Category> categories;

    public Store(){}

    public Store(String storeName, String storeId){
        this.storeName = storeName;
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public List<Category> getCategories() {
        return categories;
    }
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}

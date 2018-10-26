package edu.osu.myapplication.Data;

public class Category {

    /*generating random keys::
    String key = database.getReference("//ObjectName i.e Category// ").push().getKey();
    */
    private String categoryID;
    private String categoryName;
    public Category(){
    }
    public Category(String categoryName, String categoryID){
        this.categoryName = categoryName;
        this.categoryID = categoryID;
    }

    public String getcategoryID() { return categoryID; }
    public String getCategoryName() { return categoryName; }

    public void setCategoryName(String categoryName) {this.categoryName = categoryName;}
}

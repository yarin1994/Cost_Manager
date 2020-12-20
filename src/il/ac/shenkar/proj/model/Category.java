package il.ac.shenkar.proj.model;

public class Category {
    private String name;


    public Category(String name) {
        setCategory(name);
    }

    public String getCategory() {
        return this.name;
    }

    public void setCategory(String category) {
        this.name = category;
    }
}

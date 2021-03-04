package il.ac.shenkar.proj.model;


/**
 * Category class - Holds only the name of the category.
*/
public class Category {
    private String name;

    @Override
    public String toString() {
        return (name);
    }

    /**
     * Category C'tor
     * @param name - the value we send with this function is the name of the new category
     */
    public Category(String name) {
        setCategory(name);
    }

    /**
     * Getter
    */
    public String getCategory() {
        return this.name;
    }
    /**
     * Setter
    */
    public void setCategory(String category) {
        this.name = category;
    }
}

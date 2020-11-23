package app.entity;

public class Product extends BaseEntity {

    private String name;
    private String description;

    public Product(int id, String name, String description) {
        super(id);

        this.description = description;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

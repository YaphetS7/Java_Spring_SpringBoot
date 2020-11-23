package app.entity;

public class Price extends BaseEntity {

    private int productId;
    private int price;

    public Price(int id, int productId, int price) {
        super(id);

        this.productId = productId;
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

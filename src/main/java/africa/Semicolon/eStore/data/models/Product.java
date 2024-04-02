package africa.Semicolon.eStore.data.models;

public final class Product {
    private int id;
    private String name;
    private double price;
    private String description;
    private ProductCategory category;
    private int quantity;

    public Product(int id, String name, double price, String description, ProductCategory category, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        String format = "%n\t%s%n\t%s%n\t%s%n\t₦%,.2f%n\tID: %d";

        return String.format(format, name, description, category, price, id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        return (obj instanceof Product product) && this.id == product.id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }
}

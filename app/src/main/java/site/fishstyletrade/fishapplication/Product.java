package site.fishstyletrade.fishapplication;

import java.io.Serializable;

public class Product implements Comparable<Product>, Serializable {

    //DB products_description
    Integer products_id;
    Integer language_id; //1
    String products_name;
    String products_description;
    //DB products
    Integer products_quantity;
    String products_image;
    Double products_price;
    String products_weight;
    String products_weight_unit;
    Integer products_type; //1
    Integer products_status;
    Integer is_current;
    Integer products_slug;
    //DB products_to_categories
    Integer categories_id;
    //DB inventory
    Double purchase_price;
    Integer stock;
    String stock_type;

    public Product(Integer products_id, Integer language_id, String products_name, String products_description, Integer products_quantity, String products_image, Double products_price, String products_weight, String products_weight_unit, Integer products_type, Integer products_status, Integer is_current, Integer products_slug, Integer categories_id, Double purchase_price, Integer stock, String stock_type) {
        this.products_id = products_id;
        this.language_id = language_id;
        this.products_name = products_name;
        this.products_description = products_description;
        this.products_quantity = products_quantity;
        this.products_image = products_image;
        this.products_price = products_price;
        this.products_weight = products_weight;
        this.products_weight_unit = products_weight_unit;
        this.products_type = products_type;
        this.products_status = products_status;
        this.is_current = is_current;
        this.products_slug = products_slug;
        this.categories_id = categories_id;
        this.purchase_price = purchase_price;
        this.stock = stock;
        this.stock_type = stock_type;
    }

    public Integer getProducts_id() {
        return products_id;
    }

    @Override
    public int compareTo(Product o) {
        return this.getProducts_id().compareTo(o.getProducts_id());
    }
}

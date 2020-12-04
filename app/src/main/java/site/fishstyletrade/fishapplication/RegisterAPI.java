package site.fishstyletrade.fishapplication;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface RegisterAPI {



    //products_description
    @FormUrlEncoded
    @POST("/public/mobile/edit_products_description.php")
    void editToProductsDescription(
            @Field("products_id") Integer products_id,
            @Field("products_name") String products_name,
            @Field("products_description") String products_description,
            Callback<Response> callback);

    @FormUrlEncoded
    @POST("/public/mobile/insert_products_description.php")
    void addToProductsDescription(
            @Field("products_id") Integer products_id,
            @Field("language_id") Integer language_id,
            @Field("products_name") String products_name,
            @Field("products_description") String products_description,
            Callback<Response> callback);



    //products
    @FormUrlEncoded
    @POST("/public/mobile/edit_products.php")
    void editToProducts(
            @Field("products_id") Integer products_id,
            @Field("products_quantity") Integer products_quantity,
            @Field("products_price") Double products_price,
            @Field("products_weight") String products_weight,
            @Field("products_weight_unit") String products_weight_unit,
            @Field("products_status") Integer products_status,
            Callback<Response> callback);

    @FormUrlEncoded
    @POST("/public/mobile/insert_products.php")
    void addToProducts(
            @Field("products_id") Integer products_id,
            @Field("products_quantity") Integer products_quantity,
            @Field("products_image") String products_image,
            @Field("products_price") Double products_price,
            @Field("products_weight") String products_weight,
            @Field("products_weight_unit") String products_weight_unit,
            @Field("products_type") Integer products_type,
            @Field("products_status") Integer products_status,
            @Field("is_current") Integer is_current,
            @Field("products_slug") Integer products_slug,
            Callback<Response> callback);



    //products_to_categories
    @FormUrlEncoded
    @POST("/public/mobile/edit_products_to_categories.php")
    void editToProductsToCategories(
            @Field("products_id") Integer products_id,
            @Field("categories_id") Integer categories_id,
            Callback<Response> callback);

    @FormUrlEncoded
    @POST("/public/mobile/insert_products_to_categories.php")
    void addToProductsToCategories(
            @Field("products_id") Integer products_id,
            @Field("categories_id") Integer categories_id,
            Callback<Response> callback);



    //inventory
    @FormUrlEncoded
    @POST("/public/mobile/edit_inventory.php")
    void updateToInventory(
            @Field("products_id") Integer products_id,
            @Field("purchase_price") Double purchase_price,
            @Field("stock") Integer stock,
            Callback<Response> callback);

    @FormUrlEncoded
    @POST("/public/mobile/insert_inventory.php")
    void addToInventory(
            @Field("products_id") Integer products_id,
            @Field("purchase_price") Double purchase_price,
            @Field("stock") Integer stock,
            @Field("stock_type") String stock_type,
            Callback<Response> callback);
}
package com.example.project_android.Retrofit;

import com.example.project_android.Model.*;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface APIUser {


    @FormUrlEncoded
    @POST("/register/add/{role}")
    Call<User> addUser(@Path("role") int role,
                       @Field("fullName") String fullName,
                       @Field("phoneNumber") String phoneNumber,
                       @Field("passW") String passW,
                       @Field("otp") String otp_code);

    @POST("/register/verify-otp")
    Call<ResponseBody> verifyOTP(@Query("phone") String phone, @Query("otp") String otp);


    @POST("/register/resend-otp")
    Call<ResponseBody> resendOTP(@Query("phone") String phone, @Query("otp") String newOtp);

    @POST("/register/login")
    Call<ResponseBody> login(@Query("phone") String phone, @Query("password") String password);

    @POST("/register/google-signin")
    Call<ResponseBody> googleSignIn(@Query("email") String email, @Query("fullname") String fullname);

    @GET("/register/check-email")
    Call<ResponseBody> checkEmail(@Query("email") String email);

    @GET("/dish/listByRestaurant")
    Call<List<Dish>> listRestaurantDish(@Query("restaurant") int restaurantId);


    @GET("/dish/listByGroup")
    Call<List<Dish>> listGroupDish(@Query("groupdish") String groupdish);

    @GET("/dish/listDish")
    Call<List<Dish>> listDish();

    @GET("/groupdish/list")
    Call<List<GroupDish>> listGroupDish();

    @GET("/dish/listLimit10")
    Call<List<Dish>> listLimit10();

    @GET("/dish/listDishGoiY")
    Call<List<Dish>> listDishGoiY();

    @GET("/dish/searchDish")
    Call<List<Dish>> searchDish(@Query("name") String name);


    @GET("/dish/listGroupDishSearch")
    Call<List<Dish>> listGroupDishSearch(@Query("groupdish") String groupdish, @Query("name") String name);


    @Multipart
    @POST("/dish/add")
    Call<Dish> addDish(@Part("name") RequestBody name,
                       @Part("price") RequestBody price,
                       @Part("quantity") RequestBody quantity,
                       @Part("describe") RequestBody describe,
                       @Part("id_restaurant") RequestBody id_restaurant,
                       @Part("id_group") RequestBody id_group,
                       @Part MultipartBody.Part image);

    @Multipart
    @POST("/groupdish/add")
    Call<Dish> groupdish(@Part("name") RequestBody name,
                         @Part MultipartBody.Part image);


    @GET("/dish/detail")
    Call<Dish> detailDish(@Query("id") int id);

    @POST("/voucherRestaurant/add")
    Call<Voucher_restaurant> addRes(@Query("name") String name,
                                    @Query("quantity") int quantity,
                                    @Query("price") Double price,
                                    @Query("expiry") LocalDateTime expiry,
                                    @Query("id_fk_restaurant") int id_fk_restaurant
    );

    @POST("/voucherSystem/add")
    Call<VoucherSystem> addSys(@Query("name") String name,
                               @Query("quantity") int quantity,
                               @Query("price") Double price,
                               @Query("expiry") LocalDateTime expiry,
                               @Query("id_fk") int id_fk
    );

    @GET("/voucherRestaurant/listRequest")
    Call<List<Voucher_restaurant>> listVoucherRequest(@Query("id_fk") int id_fk);

    @GET("/voucherRestaurant/listRequestAll")
    Call<List<Voucher_restaurant>> listRequestAll();


    @GET("/voucherRestaurant/list")
    Call<List<Voucher_restaurant>> listVoucher(@Query("id_fk") int id_fk);

    @DELETE("/voucherRestaurant/delete/{id}")
    Call<ResponseBody> deleteVoucher(@Path("id") int id);


    @POST("/voucherRestaurant/active")
    Call<String> activeVocher(@Query("id") int id);


    @Multipart
    @PUT("/dish/update")
    Call<Dish> updateDish(
            @Part("name") RequestBody name,
            @Part("price") RequestBody price,
            @Part("quantity") RequestBody quantity,
            @Part("describe") RequestBody describe,
            @Part("id_restaurant") RequestBody id_restaurant,
            @Part("id_group") RequestBody id_group,
            @Part MultipartBody.Part image);

    @Multipart
    @PUT("/dish/update")
    Call<ResponseBody> updateDish(@Part("id") RequestBody id,
                                  @Part("name") RequestBody name,
                                  @Part("price") RequestBody price,
                                  @Part("quantity") RequestBody quantity,
                                  @Part("describe") RequestBody describe,
                                  @Part("id_fk_restaurant") RequestBody id_fk_restaurant,
                                  @Part("id_fk_group_dish") RequestBody id_fk_group_dish,
                                  @Part MultipartBody.Part imageFile);


    @Multipart
    @PUT("/dish/update1")
    Call<ResponseBody> updateDish1(@Part("id") RequestBody id,
                                   @Part("name") RequestBody name,
                                   @Part("price") RequestBody price,
                                   @Part("quantity") RequestBody quantity,
                                   @Part("describe") RequestBody describe,
                                   @Part("id_fk_restaurant") RequestBody id_fk_restaurant,
                                   @Part("id_fk_group_dish") RequestBody id_fk_group_dish);


    @POST("/register/reset")
    Call<ResponseBody> reset(@Query("phone") String phone, @Query("password") String password);

    @GET("/register/checkPassword")
    Call<Boolean> checkPassword(@Query("id_customer") int id_customer,
                                @Query("password") String password);


    @GET("/register/findByEmail")
    Call<Integer> findIdByEmail(@Query("email") String email);

    @POST("/register/changePassword")
    Call<Integer> changePassword(@Query("id_customer") int id_customer, @Query("password") String password);


    @POST("/cart/add")
    Call<String> addDishToCart(@Query("id_customer") int id_customer,
                               @Query("id_restaurant") int id_restaurant,
                               @Query("id_dish") int id_dish,
                               @Query("quantity") int quantity
    );


    @GET("/dish/getIdRestaurant")
    Call<Integer> getIdRestaurant(@Query("id") int id);


    @GET("/cart/listDishInCart")
    Call<List<OrderDetail>> listDishInCart(@Query("id_customer") int id_customer);

    @POST("/cart/addMore")
    Call<String> addMore(@Query("id_order") String id_order,
                         @Query("id_dish") int id_dish);

    @POST("/cart/minus")
    Call<String> minus(@Query("id_order") String id_order,
                       @Query("id_dish") int id_dish);


    @POST("/cart/delete")
    Call<String> deleteCart(@Query("id") int id);


    @GET("/restaurant/listAll")
    Call<List<Restaurant>> listAllRestaurant(@Query("id_customer") int id_customer);


    @GET("/restaurant/listAll1")
    Call<List<Restaurant>> listAllRestaurant1(@Query("id_customer") int id_customer);

    @GET("/restaurant/listAllRestaurantAdmin")
    Call<List<Restaurant>> listAllRestaurantAdmin();


    @GET("/restaurant/listAllSearch")
    Call<List<Restaurant>> listAllRestaurantSearch(@Query("id_customer") int id_customer, @Query("name") String name);

    @GET("/cart/getNameRestaurant")
    Call<Restaurant> getNameRestaurant(@Query("id_customer") int id_customer);


    @GET("/voucherSystem/listVoucherSystemOfCustomer")
    Call<List<VoucherSystem>> listVoucherSystemOfCustomer(@Query("id_customer") int id_customer);

    @GET("/voucherSystem/listVoucherSystemOfCustomer1")
    Call<List<VoucherSystem>> listVoucherSystemOfCustomer1(@Query("id_customer") int id_customer);

    @GET("/voucherRestaurant/listVoucherRestaurantOfCustomerInCart")
    Call<List<Voucher_restaurant>> listVoucherRestaurantOfCustomerInCart(@Query("id_customer") int id_customer,
                                                                         @Query("id_res") int id_res);


    @POST("/cart/applyVoucherSys")
    Call<String> applyVoucherSys(@Query("oder_id") String oder_id,
                                 @Query("voucher_id") int voucher_id);

    @POST("/cart/applyVoucherRes")
    Call<String> applyVoucherRes(@Query("oder_id") String oder_id,
                                 @Query("voucher_id") int voucher_id);

    @GET("/cart/getOrderId")
    Call<Map<String, String>> getOrderId(@Query("id_customer") int id_customer);

    @GET("/cart/getSumVoucher")
    Call<Map<String, Double>> getSumVoucher(@Query("oder_id") String oder_id);

    @GET("/order/list")
    Call<List<SellingOrder>> getAllOrderOfRestaurant(@Query("id_restaurant") int id_restaurant);

    @GET("/order/listHis")
    Call<List<SellingOrder>> getAllOrderOfRestaurantHis(@Query("id_restaurant") int id_restaurant);

    @GET("/order/listAccept")
    Call<List<SellingOrder>> getAllOrderAcceptOfRestaurant(@Query("id_restaurant") int id_restaurant);

    @GET("/order/listDetail")
    Call<List<OrderDetail>> getDetailOrderOfRestaurant(@Query("order_id") String order_id);


    @GET("/voucherRestaurant/listVoucherRestaurantAdd")
    Call<List<Voucher_restaurant>> listVoucherRestaurantAdd();


    @GET("/voucherSystem/listVoucherSystemAdd")
    Call<List<VoucherSystem>> listVoucherSystemAdd();

    @GET("/voucherSystem/listVoucherSystemManager")
    Call<List<VoucherSystem>> listVoucherSystemManager();


    @POST("/voucherRestaurant/getVoucher")
    Call<String> getVoucherRes(@Query("id_customer") int id_customer,
                               @Query("id_voucher") int id_voucher);


    @POST("/voucherSystem/getVoucher")
    Call<String> getVoucherSys(@Query("id_customer") int id_customer,
                               @Query("id_voucher") int id_voucher);


    @GET("/voucherRestaurant/check")
    Call<Boolean> checkRes(@Query("id_customer") int id_customer, @Query("id_voucher") int id_voucher);

    @GET("/voucherSystem/check")
    Call<Boolean> checkSys(@Query("id_customer") int id_customer, @Query("id_voucher") int id_voucher);


    @GET("/voucherRestaurant/listVoucherRestaurantOfCustomer")
    Call<List<Voucher_restaurant>> listVoucherRestaurantOfCustomer(@Query("id_customer") int id_customer);


    @GET("/restaurant/detail")
    Call<Restaurant> detailOfRestaurant(@Query("id_customer") int id_customer, @Query("id_res") int id_res);


    @GET("/voucherRestaurant/checkExpiry")
    Call<Boolean> checkExpiry(@Query("id_voucher") int id_voucher);


    @GET("/voucherRestaurant/checkQuantity")
    Call<Boolean> checkQuantity(@Query("id_customer") int id_customer, @Query("id_voucher") int id_voucher);

    @GET("/voucherSystem/checkExpiry")
    Call<Boolean> checkExpiry1(@Query("id_voucher") int id_voucher);


    @GET("/voucherSystem/checkQuantity")
    Call<Boolean> checkQuantity1(@Query("id_customer") int id_customer, @Query("id_voucher") int id_voucher);


    @GET("/voucherRestaurant/checkExpiryAndQuantity")
    Call<Integer> checkExpiryAndQuantity(@Query("id_customer") int id_customer, @Query("id_voucher") int id_voucher);

    @GET("/voucherSystem/checkExpiryAndQuantity")
    Call<Integer> checkExpiryAndQuantity1(@Query("id_customer") int id_customer, @Query("id_voucher") int id_voucher);


    @POST("/cart/confirm")
    Call<String> confirm(@Query("oder_id") String oder_id,
                         @Query("payment") int payment,
                         @Query("latitude") Double latitude,
                         @Query("longitude") Double longitude);

    @GET("/order/listOrder")
    Call<List<SellingOrder>> listOrder(@Query("id_customer") int id_customer);

    @GET("/order/listOrderDelivering")
    Call<List<SellingOrder>> listOrderDelivering(@Query("id_customer") int id_customer);

    @GET("/order/listOrderHistory")
    Call<List<SellingOrder>> listOrderHistory(@Query("id_customer") int id_customer);

    @DELETE("/cart/cancel")
    Call<ResponseBody> cancel(@Query("oder_id") String oder_id);

    @POST("/cart/accept")
    Call<ResponseBody> accept(@Query("oder_id") String oder_id);

    @POST("/cart/done")
    Call<ResponseBody> done(@Query("oder_id") String oder_id);

    @POST("/cart/naptien")
    Call<ResponseBody> naptien(@Query("id") int id,
                               @Query("tien") double tien);

    @POST("/cart/trutien")
    Call<ResponseBody> trutien(@Query("id") int id,
                               @Query("tien") double tien);


    @POST("/order/ratingCus")
    Call<ResponseBody> ratingCus(@Query("orderId") String orderId,
                                 @Query("id_fk_restaurant") int id_fk_restaurant,
                                 @Query("ratingRes") double ratingRes,
                                 @Query("id_fk_shiper") int id_fk_shiper,
                                 @Query("ratingShiper") double ratingShiper
    );

    @POST("/cart/ok")
    Call<ResponseBody> ok(@Query("oder_id") String oder_id, @Query("shiper") int shiper);


    @POST("/cart/hoantat")
    Call<ResponseBody> hoantat(@Query("oder_id") String oder_id, @Query("shiper") int shiper, @Query("money") double money);


    @GET("/infor")
    Call<User> getInfor(@Query("id_user") int id_user);

    @GET("/infor/getMoney")
    Call<String[]> getMoney(@Query("id_user") int id_user);

    @GET("/infor/sodu")
    Call<Double> sodu(@Query("id_user") int id_user);

    @GET("/infor/checkStatus")
    Call<Integer> checkStatus(@Query("id_user") int id_user);

    @GET("/infor/checkPhoneAndAddress")
    Call<Integer> checkPhoneAndAddress(@Query("id_user") int id_user);

    @GET("/infor/checkNameAndAddress")
    Call<Integer> checkNameAndAddress(@Query("id_user") int id_user);


    @GET("/infor/tonghoadon")
    Call<Double> tonghoadon(@Query("orderId") String orderId);


    @POST("/infor/changePhoneNumber")
    Call<Integer> changePhoneNumber(@Query("id_user") int id_user,
                                    @Query("phone") String phone);

    @POST("/infor/changeAddress")
    Call<Integer> changeAddress(@Query("id_user") int id_user,
                                @Query("address") String address);

    @POST("/infor/updateNameRes")
    Call<Integer> updateNameRes(@Query("id_user") int id_user,
                                @Query("name") String name);


    @POST("/infor/changeName")
    Call<Integer> changeName(@Query("id_user") int id_user,
                             @Query("name") String name);

    @POST("/infor/changeEmailOk")
    Call<Integer> changeEmail(@Query("id_user") int id_user,
                              @Query("email") String email);

    @POST("/email/changeEmail")
    Call<String> sendChangeEmailNotification(@Body Map<String, Object> emailData);

    @Multipart
    @POST("/infor/change")
    Call<Integer> change(
            @Part("id_user") RequestBody id_user,
            @Part("gender") RequestBody gender,
            @Part MultipartBody.Part image
    );

    @Multipart
    @POST("/infor/updateImage")
    Call<Integer> updateImage(
            @Part("id_user") RequestBody id_user,
            @Part MultipartBody.Part image
    );


    @Multipart
    @POST("/infor/changeTemp")
    Call<Integer> changeTemp(
            @Part("id_user") RequestBody id_user,
            @Part("gender") RequestBody gender
    );

    @GET("/infor/getPositionRestaurant")
    Call<double[]> getPositionRestaurant(
            @Query("order_id") String order_id
    );

    @GET("/infor/getInforOrder")
    Call<String[]> getInforOrder(
            @Query("order_id") String order_id
    );

    @GET("/infor/getInforOrderCus")
    Call<String[]> getInforOrderCus(
            @Query("order_id") String order_id
    );


    @POST("/cart/delivering")
    Call<ResponseBody> delivering(@Query("oder_id") String oder_id);


    @Multipart
    @POST("/infor/updatePosition")
    Call<Integer> updatePosition(
            @Part("id_user") int id_user,
            @Part("latitude") Double latitude,
            @Part("longtitude") Double longtitude
    );


    @GET("/admin/listAllShiper")
    Call<List<Shiper>> listAllShiper();

    @GET("/admin/listAllShiperRequest")
    Call<List<Shiper>> listAllShiperRequest();

    @POST("/admin/active")
    Call<String> activeShiper(@Query("id") int id);


    @GET("/infor/getNameRes")
    Call<String> getNameRes(@Query("id_user") int id_user);


}



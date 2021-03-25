package com.afrifanom.inventoryapp.communication;

import com.afrifanom.inventoryapp.models.LoginModel;
import com.afrifanom.inventoryapp.models.ResponseModel;
import com.afrifanom.inventoryapp.models.ShoeModel;
import com.afrifanom.inventoryapp.models.ShoesResponseModel;
import com.afrifanom.inventoryapp.models.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("/users/add")
    Call<ResponseModel> createUser(@Body UserModel userModel);

    @POST("/users/login")
    Call<ResponseModel> login(@Body LoginModel loginModel);

    @GET("/shoes/get")
    @Headers("Content-Type:application/json")
    Call<ShoesResponseModel> getShoes();

    @POST("/shoes/add")
    Call<ResponseModel> addShoes(@Body ShoeModel shoeModel);

    @DELETE("/shoes/delete")
    @Headers("Content-Type:application/json")
    Call<ResponseModel> deleteShoe(@Query("shoeId") String shoeId, @Query("id") String id);

    @PUT("/shoes/modify")
    Call<ResponseModel> modifyShoe(@Body ShoeModel shoeModel);


}

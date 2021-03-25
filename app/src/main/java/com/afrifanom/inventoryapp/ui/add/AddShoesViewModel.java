package com.afrifanom.inventoryapp.ui.add;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.afrifanom.inventoryapp.communication.ApiInterface;
import com.afrifanom.inventoryapp.communication.RetrofitClientInstance;
import com.afrifanom.inventoryapp.communication.UrlClass;
import com.afrifanom.inventoryapp.models.ResponseModel;
import com.afrifanom.inventoryapp.models.ShoeModel;
import com.afrifanom.inventoryapp.models.UserModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddShoesViewModel extends ViewModel {

    private MutableLiveData<ResponseModel> apiResponse;

    public LiveData<ResponseModel> addShoes(String brand, String size, String color, String price) {
        if (apiResponse == null) {
            apiResponse = new MutableLiveData<>();
        }
        save(brand, size, color, price);

        return apiResponse;
    }


    public void save(String brand, String size, String color, String price) {

        System.out.println(UrlClass.baseUrl + UrlClass.ADD_SHOES);

        System.out.println("AddShoesViewModel");
        System.out.println(brand);
        System.out.println(size);
        System.out.println(color);
        System.out.println(price);

        try {

            ShoeModel shoeModel = new ShoeModel(brand, size, color, price);
            ApiInterface apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);

            System.out.println(shoeModel.toString());

            Call<ResponseModel> call = apiService.addShoes(shoeModel);

            call.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                    System.out.println("response: " + response);

                    if (response.body() != null) {
                        apiResponse.postValue(response.body());
                    } else {
                        apiResponse.postValue(null);
                    }

                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
//                apiResponse.postValue(response.body().toString());
                    t.printStackTrace();
                    System.out.println(t.getMessage());
                }
            });

        } catch (Exception ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

    }





}
package com.afrifanom.inventoryapp.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.afrifanom.inventoryapp.R;
import com.afrifanom.inventoryapp.communication.ApiInterface;
import com.afrifanom.inventoryapp.communication.RetrofitClientInstance;
import com.afrifanom.inventoryapp.communication.UrlClass;
import com.afrifanom.inventoryapp.models.LoginModel;
import com.afrifanom.inventoryapp.models.ResponseModel;
import com.afrifanom.inventoryapp.models.UserModel;
import com.afrifanom.inventoryapp.utilities.UtilityManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<ResponseModel> apiResponse;

    public LiveData<ResponseModel> login(String userId, String password) {
        if (apiResponse == null) {
            apiResponse = new MutableLiveData<>();
        }
        userLogin(userId, password);

        return apiResponse;
    }



    public void userLogin(String userId, String password) {

        System.out.println(UrlClass.baseUrl + UrlClass.LOGIN);

        LoginModel loginModel = new LoginModel(userId, password);
        ApiInterface apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);

        Call<ResponseModel> call = apiService.login(loginModel);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                if (response.body() != null) {
                    System.out.println(response.toString());
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


    }
}
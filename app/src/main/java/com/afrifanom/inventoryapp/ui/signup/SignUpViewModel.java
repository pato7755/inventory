package com.afrifanom.inventoryapp.ui.signup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.afrifanom.inventoryapp.communication.ApiInterface;
import com.afrifanom.inventoryapp.communication.RetrofitClientInstance;
import com.afrifanom.inventoryapp.communication.UrlClass;
import com.afrifanom.inventoryapp.models.ResponseModel;
import com.afrifanom.inventoryapp.models.UserModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpViewModel extends ViewModel {

    private MutableLiveData<ResponseModel> apiResponse;

    public LiveData<ResponseModel> signUp(String firstName, String lastName, String userId, String password, String role) {
        if (apiResponse == null) {
            apiResponse = new MutableLiveData<>();
        }
        saveUser(firstName, lastName, userId, password, role);

        return apiResponse;
    }


    public void saveUser(String firstName, String lastName, String userId, String password, String role) {

        System.out.println(UrlClass.baseUrl + UrlClass.USERS);

        try {

            UserModel userModel = new UserModel(firstName, lastName, userId, password, role);
            ApiInterface apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);

            System.out.println(userModel.toString());

            Call<ResponseModel> call = apiService.createUser(userModel);

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
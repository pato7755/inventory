package com.afrifanom.inventoryapp.ui.view;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.afrifanom.inventoryapp.adapters.ShoesAdapter;
import com.afrifanom.inventoryapp.application.AfrifaApplication;
import com.afrifanom.inventoryapp.communication.ApiInterface;
import com.afrifanom.inventoryapp.communication.RetrofitClientInstance;
import com.afrifanom.inventoryapp.communication.UrlClass;
import com.afrifanom.inventoryapp.models.ResponseModel;
import com.afrifanom.inventoryapp.models.ShoesResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewShoesViewModel extends ViewModel {

    private MutableLiveData<ShoesResponseModel> apiResponse = new MutableLiveData<>();;
    private MutableLiveData<Integer> deleteResponse;
    private ResponseModel apiDeleteResponse;

//    private ShoesAdapter shoesAdapter = new ShoesAdapter();

    public LiveData<ShoesResponseModel> getShoes() {
        if (apiResponse == null) {
            apiResponse = new MutableLiveData<>();
        }

        fetchShoes();

        return apiResponse;
    }





    public void fetchShoes() {

        System.out.println(UrlClass.baseUrl + UrlClass.GET_SHOES);

        try {

            ApiInterface apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);

            Call<ShoesResponseModel> call = apiService.getShoes();

            call.enqueue(new Callback<ShoesResponseModel>() {
                @Override
                public void onResponse(Call<ShoesResponseModel> call, Response<ShoesResponseModel> response) {

                    System.out.println("response: " + response.body());

                    if (response.body() != null) {
                        apiResponse.postValue(response.body());
                    } else {
                        apiResponse.postValue(null);
                    }

                }

                @Override
                public void onFailure(Call<ShoesResponseModel> call, Throwable t) {
//                apiResponse.postValue(response.body().toString());
                    t.printStackTrace();
                    System.out.println(t.getMessage());
                }
            });

        } catch (Exception ex) {
            System.out.println("exception: " + ex.getMessage());
            ex.printStackTrace();
        }

    }

    public void deleteShoe(String shoeId, String userId, int position) {

        System.out.println(UrlClass.baseUrl + UrlClass.DELETE_SHOES);

        try {

            ApiInterface apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);

            Call<ResponseModel> call = apiService.deleteShoe(shoeId, userId);

            call.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                    System.out.println("response: " + response.body());

                    if (response.body() != null) {
                        apiDeleteResponse = response.body();

                            if (response.body().getResponseCode().equals("100")) {
                                Toast.makeText(AfrifaApplication.getContext(), response.body().getResponseMessage(), Toast.LENGTH_LONG).show();
//                                deleteResponse.postValue(position);

//                                shoesAdapter.removeAt(position);
                            } else {
                                Toast.makeText(AfrifaApplication.getContext(), response.body().getResponseMessage(), Toast.LENGTH_LONG).show();
                            }




                    } else {
                        apiDeleteResponse = null;
                    }

                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
//                apiResponse.postValue(response.body().toString());
                    t.printStackTrace();
                    System.out.println(t.getMessage());
                }

            });

//            return apiDeleteResponse;

        } catch (Exception ex) {
            System.out.println("exception: " + ex.getMessage());
            ex.printStackTrace();
        }

//        return apiDeleteResponse;

    }


}
package com.afrifanom.inventoryapp.ui.view;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afrifanom.inventoryapp.R;
import com.afrifanom.inventoryapp.adapters.ShoesAdapter;
import com.afrifanom.inventoryapp.communication.CheckInternetConnection;
import com.afrifanom.inventoryapp.utilities.ErrorDialogInterface;

import java.util.Objects;

public class ViewShoesFragment extends Fragment implements ErrorDialogInterface {

    CheckInternetConnection checkInternetConnection = new CheckInternetConnection();
    private ViewShoesViewModel viewShoesViewModel;
    LinearLayoutManager linearLayoutManager;
    ShoesAdapter adapter;
    View root;

    ProgressBar progressBar;
    RecyclerView recyclerView;
    TextView noResultsTextView;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewShoesViewModel =
                ViewModelProviders.of(this).get(ViewShoesViewModel.class);
        root = inflater.inflate(R.layout.fragment_view_shoes, container, false);

        initViews();

        adapter = new ShoesAdapter(null, getActivity());

//        viewShoesViewModel.getPositionAfterDelete();

        callApi();

        return root;
    }


    public void initViews() {

        progressBar = root.findViewById(R.id.progress_circular);
        recyclerView = root.findViewById(R.id.recyclerview);
        noResultsTextView = root.findViewById(R.id.no_results_textview);

    }

    public void callApi(){

        if (!checkInternetConnection.isNetworkAvailable(getActivity())) {
            showAlertDialog(getString(R.string.oops), getString(R.string.no_internet_connection), getString(R.string.cancel), getActivity());
        } else {

            progressBar.setVisibility(View.VISIBLE);

            viewShoesViewModel.getShoes().observe(getViewLifecycleOwner(), result -> {

                progressBar.setVisibility(View.GONE);

                if (result != null) {

                    if (result.getResponseCode().equals("100")) {

                        linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(linearLayoutManager);

                        // set dividers in recyclerview
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            recyclerView.addItemDecoration(new DividerItemDecoration(requireActivity(), linearLayoutManager.getOrientation()));
                        }

                        adapter = new ShoesAdapter(result.getData(), getActivity());
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);

                    } else {
                        noResultsTextView.setVisibility(View.INVISIBLE);
                    }

                } else {
                    showErrorDialog(getString(R.string.oops), getString(R.string.something_went_wrong), getString(R.string.ok), getActivity());
                }

            });


        }

    }


    @Override
    public void showAlertDialog(String title, String message, String
            positiveButtonText, Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonText, (dialogInterface, id) -> {
                    dialogInterface.dismiss();

                })
                .create()
                .show();

    }

    @Override
    public void showErrorDialog(String title, String message, String
            positiveButtonText, Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonText, (dialogInterface, id) -> {
                    dialogInterface.dismiss();
                })
                .create()
                .show();

    }


}
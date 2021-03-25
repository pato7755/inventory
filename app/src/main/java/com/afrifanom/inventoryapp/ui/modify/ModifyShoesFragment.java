package com.afrifanom.inventoryapp.ui.modify;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.afrifanom.inventoryapp.R;
import com.afrifanom.inventoryapp.communication.CheckInternetConnection;
import com.afrifanom.inventoryapp.utilities.ErrorDialogInterface;

import static android.view.View.GONE;

public class ModifyShoesFragment extends Fragment implements ErrorDialogInterface {

    CheckInternetConnection checkInternetConnection = new CheckInternetConnection();
    private ModifyShoesViewModel modifyShoesViewModel;
    View root;

    ProgressBar progressBar;
    EditText brandEditText;
    EditText colorEditText;
    EditText priceEditText;
    EditText sizeEditText;
    Button confirmButton;

    String id = "";
    String brand = "";
    String color = "";
    String size = "";
    String price = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        modifyShoesViewModel =
                ViewModelProviders.of(this).get(ModifyShoesViewModel.class);
        root = inflater.inflate(R.layout.fragment_modify_shoe, container, false);

        if (getArguments() != null) {
            id = getArguments().getString("id");
            brand = getArguments().getString("brand");
            color = getArguments().getString("color");
            size = getArguments().getString("size");
            price = getArguments().getString("price");
        }

        initViews();

        return root;
    }


    public void initViews() {

        progressBar = root.findViewById(R.id.progress_circular);
        brandEditText = root.findViewById(R.id.brand_edittext);
        sizeEditText = root.findViewById(R.id.size_edittext);
        colorEditText = root.findViewById(R.id.color_edittext);
        priceEditText = root.findViewById(R.id.price_edittext);
        confirmButton = root.findViewById(R.id.confirm_button);

        brandEditText.setText(brand);
        sizeEditText.setText(size);
        colorEditText.setText(color);
        priceEditText.setText(price);

        confirmButton.setOnClickListener(view -> {

            brand = brandEditText.getText().toString();
            size = sizeEditText.getText().toString();
            color = colorEditText.getText().toString();
            price = priceEditText.getText().toString();

            if (brand.length() == 0) {
                brandEditText.setError("Enter shoe brand");
            } else if (size.length() == 0) {
                sizeEditText.setError("Enter shoe size");
            } else if (color.length() == 0) {
                colorEditText.setError("Enter shoe color");
            } else if (price.length() == 0) {
                priceEditText.setError("Enter price");
            } else {

                if (!checkInternetConnection.isNetworkAvailable(getActivity())) {
                    showAlertDialog(getString(R.string.oops), getString(R.string.no_internet_connection), getString(R.string.cancel), getActivity());
                } else {

                    progressBar.setVisibility(View.VISIBLE);

                    modifyShoesViewModel.modifyShoe(id, brand, size, color, price).observe(getViewLifecycleOwner(), result -> {

                        progressBar.setVisibility(GONE);

                        if (result != null) {

                            if (result.getResponseCode().equals("100")) {
                                showAlertDialog("Successful", result.getResponseMessage(), getString(R.string.ok), getActivity());
                            } else {
                                showErrorDialog(getString(R.string.oops), result.getResponseMessage(), getString(R.string.ok), getActivity());
                            }

                        } else {
                            showErrorDialog(getString(R.string.oops), getString(R.string.something_went_wrong), getString(R.string.ok), getActivity());
                        }


                    });

                }
            }

        });


    }



    @Override
    public void showAlertDialog(String title, String message, String
            positiveButtonText, Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonText, (dialogInterface, id) -> {
                    dialogInterface.dismiss();

                    brandEditText.setText("");
                    sizeEditText.setText("");
                    colorEditText.setText("");
                    priceEditText.setText("");

                    brand = "";
                    color = "";
                    size = "";
                    price = "";

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
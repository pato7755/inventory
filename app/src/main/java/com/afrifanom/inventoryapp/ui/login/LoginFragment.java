package com.afrifanom.inventoryapp.ui.login;

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
import androidx.navigation.Navigation;

import com.afrifanom.inventoryapp.R;
import com.afrifanom.inventoryapp.application.AfrifaApplication;
import com.afrifanom.inventoryapp.communication.CheckInternetConnection;
import com.afrifanom.inventoryapp.utilities.ErrorDialogInterface;
import com.afrifanom.inventoryapp.utilities.UtilityManager;

import java.util.Objects;

import static android.view.View.GONE;

public class LoginFragment extends Fragment implements ErrorDialogInterface {

    CheckInternetConnection checkInternetConnection = new CheckInternetConnection();
    UtilityManager utilityManager = new UtilityManager();

    private LoginViewModel loginViewModel;
    View root;
    EditText userIdEditText;
    EditText passwordEditText;
    Button loginButton;
    ProgressBar progressBar;

    private String password = "";
    private String userId = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        loginViewModel =
                ViewModelProviders.of(this).get(LoginViewModel.class);
        root = inflater.inflate(R.layout.login, container, false);

        initViews();

        return root;
    }


    public void initViews() {

        progressBar = root.findViewById(R.id.progress_circular);
        userIdEditText = root.findViewById(R.id.user_id_edittext);
        passwordEditText = root.findViewById(R.id.password_edittext);
        loginButton = root.findViewById(R.id.login_button);

        loginButton.setOnClickListener(view -> {

            userId = userIdEditText.getText().toString();
            password = passwordEditText.getText().toString();


            if (userId.length() == 0) {
                userIdEditText.setError("Enter your user ID");
            } else if (password.length() == 0) {
                passwordEditText.setError("Enter a new password");
            } else {

                System.out.println("userId: " + userId);
                System.out.println("password: " + password);

                if (!checkInternetConnection.isNetworkAvailable(getActivity())) {
                    showAlertDialog(getString(R.string.oops), getString(R.string.no_internet_connection), getString(R.string.cancel), getActivity());
                } else {

                    progressBar.setVisibility(View.VISIBLE);

                    loginViewModel.login(userId, password).observe(getViewLifecycleOwner(), result -> {

                        progressBar.setVisibility(GONE);

                        if (result != null) {

                            if (result.getResponseCode().equals("100")) {
                                System.out.println("LOGIN SUCCESSFUL");
                                utilityManager.setPreferences("USER_ID", userId);
                                Navigation.findNavController(root).navigate(R.id.action_nav_login_to_nav_add_shoes);
//                                showAlertDialog("Congratulations", result.getResponseMessage(), getString(R.string.ok), getActivity());
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
//                    Navigation.findNavController(root).navigate(R.id.action_navigation_albums_to_navigation_tracks, bundle);
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


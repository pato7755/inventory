package com.afrifanom.inventoryapp.ui.signup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.afrifanom.inventoryapp.R;
import com.afrifanom.inventoryapp.communication.CheckInternetConnection;
import com.afrifanom.inventoryapp.models.ResponseModel;
import com.afrifanom.inventoryapp.utilities.ErrorDialogInterface;
import com.afrifanom.inventoryapp.utilities.UtilityManager;

import static android.view.View.GONE;

public class SignUpFragment extends Fragment implements ErrorDialogInterface, View.OnClickListener {

    CheckInternetConnection checkInternetConnection = new CheckInternetConnection();

    private SignUpViewModel signUpViewModel;
    View root;
    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText userIdEditText;
    EditText passwordEditText;
    EditText confirmPasswordEditText;
    RadioButton managerRadioButton;
    RadioButton cashierRadioButton;
    RadioGroup radioGroup;
    TextView loginTextView;
    Button signUpButton;
    ProgressBar progressBar;

    private String password = "";
    private String confirmPassword = "";
    private String firstName = "";
    private String lastName = "";
    private String userId = "";
    private String userRole = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        signUpViewModel =
                ViewModelProviders.of(this).get(SignUpViewModel.class);
        root = inflater.inflate(R.layout.signup, container, false);

        initViews();

        return root;
    }


    @SuppressLint("ClickableViewAccessibility")
    public void initViews() {

        progressBar = root.findViewById(R.id.progress_circular);
        firstNameEditText = root.findViewById(R.id.first_name_edittext);
        lastNameEditText = root.findViewById(R.id.last_name_edittext);
        userIdEditText = root.findViewById(R.id.user_id_edittext);
        passwordEditText = root.findViewById(R.id.password_edittext);
        confirmPasswordEditText = root.findViewById(R.id.confirm_password_edittext);
        radioGroup = root.findViewById(R.id.radiogroup);
        managerRadioButton = root.findViewById(R.id.manager_radio_button);
        cashierRadioButton = root.findViewById(R.id.cashier_radio_button);
        signUpButton = root.findViewById(R.id.signup_button);
        loginTextView = root.findViewById(R.id.login_textview);

        managerRadioButton.setOnClickListener(this);
        cashierRadioButton.setOnClickListener(this);

//
//
//        radioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
//
//            onRadioButtonClick(i);
//
//        });

        loginTextView.setOnTouchListener((view, motionEvent) -> {

            Navigation.findNavController(root).navigate(R.id.action_nav_signup_to_nav_login);

            return false;
        });

        signUpButton.setOnClickListener(view -> {

            userId = userIdEditText.getText().toString();
            firstName = firstNameEditText.getText().toString();
            lastName = lastNameEditText.getText().toString();
            password = passwordEditText.getText().toString();
            confirmPassword = confirmPasswordEditText.getText().toString();


            if (firstName.length() == 0) {
                firstNameEditText.setError("Enter your first name");
            } else if (lastName.length() == 0) {
                lastNameEditText.setError("Enter your last name");
            } else if (userId.length() == 0) {
                userIdEditText.setError("Enter your user ID");
            } else if (password.length() == 0) {
                passwordEditText.setError("Enter a new password");
            } else if (confirmPassword.length() == 0) {
                confirmPasswordEditText.setError("Confirm your new password");
            } else if (!password.equals(confirmPassword)) {
                confirmPasswordEditText.setError("Passwords do not match");
            } else if (userRole.length() == 0) {
                Toast.makeText(getActivity(), getString(R.string.select_user_role), Toast.LENGTH_LONG).show();
            } else {

                System.out.println("firstName: " + firstName);
                System.out.println("lastName: " + lastName);
                System.out.println("userId: " + userId);
                System.out.println("password: " + password);
                System.out.println("confirmPassword: " + confirmPassword);
                System.out.println("userRole: " + userRole);

                if (!checkInternetConnection.isNetworkAvailable(getActivity())) {
                    showAlertDialog(getString(R.string.oops), getString(R.string.no_internet_connection), getString(R.string.cancel), getActivity());
                } else {

                    progressBar.setVisibility(View.VISIBLE);

                    signUpViewModel.signUp(firstName, lastName, userId, password, userRole).observe(getViewLifecycleOwner(), result -> {

                        progressBar.setVisibility(GONE);

                        if (result != null) {

                            if (result.getResponseCode().equals("100")) {
                                showAlertDialog("Congratulations", result.getResponseMessage(), getString(R.string.ok), getActivity());
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
                    Navigation.findNavController(root).navigate(R.id.action_nav_signup_to_nav_login);
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

    public void onRadioButtonSelected(View view) {

        if (view.getId() == R.id.manager_radio_button) {

            userRole = UtilityManager.MANAGER;

        } else {

            userRole = UtilityManager.CASHIER;

        }
        System.out.println("selected role: " + userRole);
    }

    @Override
    public void onClick(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        if (view.getId() == R.id.manager_radio_button) {

            userRole = UtilityManager.MANAGER;

        } else {

            userRole = UtilityManager.CASHIER;

        }
        System.out.println("selected role: " + userRole);

    }
}


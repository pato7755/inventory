package com.afrifanom.inventoryapp.utilities;

import android.content.Context;

public interface ErrorDialogInterface {

    void showAlertDialog(String title, String message, String positiveButtonText, final Context context);

    void showErrorDialog(String title, String message, String positiveButtonText, final Context context);

}

package com.example.victor.pruebaactores;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by Victor on 31/01/2017.
 */
public class AlertDialogFragment extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();//obtiene la actividad
        AlertDialog.Builder builder = new AlertDialog.Builder(context)//se crea el alert y mensajes
                .setTitle("Oops, sorry...")
                .setMessage("Try again")
                .setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        return dialog;
    }
}

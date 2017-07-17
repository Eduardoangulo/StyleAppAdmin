package com.styleapp.styleappadm.fragments;

/**
 * Created by Luis on 04/07/2017.
 */
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.styleapp.styleappadm.R;

public class ServiceDialog extends DialogFragment {

    public void setmListener(ServiceDialogListener mListener) {
        this.mListener = mListener;
    }

    public interface ServiceDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
    private ServiceDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view= inflater.inflate(R.layout.service_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view)
                .setMessage("Aprobar nuevo servicio")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogPositiveClick(ServiceDialog.this);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegativeClick(ServiceDialog.this);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
package com.beoks.goaltree;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class CustomBottomAppDialog extends BottomSheetDialog {
    MainActivity mainActivity;
    public CustomBottomAppDialog(@NonNull Context context) {
        super(context);
        mainActivity=(MainActivity)context;
    }
}

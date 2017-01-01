package com.zjy.simpleweather.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.zjy.simpleweather.R;

/**
 * Created by 极速蜗牛 on 2016/12/24 0024.
 */

public class FragmentDialog_send extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = View.inflate(getActivity(), R.layout.fg_dialog_sent, null);
        builder.setView(view);
        return builder.create();
    }
}

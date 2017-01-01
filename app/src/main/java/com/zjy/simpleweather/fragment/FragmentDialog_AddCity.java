package com.zjy.simpleweather.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.zjy.simpleweather.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 极速蜗牛 on 2016/12/24 0024.
 */

public class FragmentDialog_AddCity extends DialogFragment {
    private EditText editText;
    private Button button;
    private View view;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(getActivity());
        view = View.inflate(getActivity(), R.layout.fg_dialog_citychoice, null);
        builder.setView(view);
        setdata();
        dialog = builder.create();
        return dialog;
    }
    private Context mcontext;
    public void setContext(Context context){
        mcontext = context;
    }

    private String str;
    public void setdata(){

        editText = (EditText) view.findViewById(R.id.ed_city);
        button = (Button) view.findViewById(R.id.bt_city);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str = editText.getText().toString();
                AddCityFragmentWeather addfg = new AddCityFragmentWeather();
                addfg.setCityName(str,mcontext);
                if (listener != null){
                    listener.getcity(addfg,str);
                }
                dialog.dismiss();
            }
        });
    }
    private onListener listener;
    public void setOnListener(onListener listener){
        this.listener = listener;
    }

    public interface onListener{
        public void getcity(AddCityFragmentWeather addfg,String city_str);
    }

}

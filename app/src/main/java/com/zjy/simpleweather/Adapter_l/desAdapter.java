package com.zjy.simpleweather.Adapter_l;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjy.simpleweather.R;
import com.zjy.simpleweather.dataBean.WeatherDataBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 极速蜗牛 on 2016/12/29 0029.
 */

public class desAdapter extends BaseAdapter {

    private WeatherDataBean dataBean;
    Context context;

    private int[] code = new int[]{
      R.drawable.air,R.drawable.life,R.drawable.che,
            R.drawable.dress,R.drawable.cold,R.drawable.sport,
            R.drawable.tour,R.drawable.sun
    };


    public desAdapter(WeatherDataBean dataBean, Context context) {
        this.dataBean = dataBean;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public Object getItem(int position) {
        return dataBean;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.suggestion, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();

        switch (position){
            case 0:
                holder.imageView.setImageResource(code[0]);
                holder.tvDes.setText(dataBean.getHeWeather5().get(0).getSuggestion().getAir().getTxt());
                break;
            case 1:
                holder.imageView.setImageResource(code[1]);
                holder.tvDes.setText(dataBean.getHeWeather5().get(0).getSuggestion().getComf().getTxt());
                break;
            case 2:
                holder.imageView.setImageResource(code[2]);
                holder.tvDes.setText(dataBean.getHeWeather5().get(0).getSuggestion().getCw().getTxt());
                break;
            case 3:
                holder.imageView.setImageResource(code[3]);
                holder.tvDes.setText(dataBean.getHeWeather5().get(0).getSuggestion().getDrsg().getTxt());
                break;
            case 4:
                holder.imageView.setImageResource(code[4]);
                holder.tvDes.setText(dataBean.getHeWeather5().get(0).getSuggestion().getFlu().getTxt());
                break;
            case 5:
                holder.imageView.setImageResource(code[5]);
                holder.tvDes.setText(dataBean.getHeWeather5().get(0).getSuggestion().getSport().getTxt());
                break;
            case 6:
                holder.imageView.setImageResource(code[6]);
                holder.tvDes.setText(dataBean.getHeWeather5().get(0).getSuggestion().getTrav().getTxt());
                break;
            case 7:
                holder.imageView.setImageResource(code[7]);
                holder.tvDes.setText(dataBean.getHeWeather5().get(0).getSuggestion().getUv().getTxt());
                break;
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.tv_des)
        TextView tvDes;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

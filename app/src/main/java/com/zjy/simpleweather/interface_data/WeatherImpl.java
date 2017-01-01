package com.zjy.simpleweather.interface_data;

import com.zjy.simpleweather.dataBean.WeatherDataBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 极速蜗牛 on 2016/12/23 0023.
 */

public interface WeatherImpl {
    /**
     * http://v.juhe.cn/基本地址
     * weather/index?
     * format=2&cityname=%E8%8B%8F%E5%B7%9E&key=6f9c8db34a74a276aa6a9a7c809962fe
     *
     */
    @GET("v5/weather?")
   Call<WeatherDataBean>getWeather(@Query("city") String cityname,@Query("key")String key);
}

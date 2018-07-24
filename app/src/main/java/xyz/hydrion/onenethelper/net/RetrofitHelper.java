package xyz.hydrion.onenethelper.net;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hydrion on 2018/7/23.
 */

public class RetrofitHelper {
    private static Retrofit sInstance = null;

    public static Retrofit getInstance(){
        if (sInstance == null){
            synchronized (RetrofitHelper.class){
                if (sInstance == null){
                    sInstance = new Retrofit.Builder()
                            .baseUrl("http://api.heclouds.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return sInstance;
    }
}

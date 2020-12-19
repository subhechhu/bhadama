package com.subhechhu.bhadama.Networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

//    private static Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl("https://api.locationiq.com/v1/autocomplete.php")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build();

    public static <S> S cteateService(Class<S> serviceClass) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.locationiq.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(serviceClass);
    }
}

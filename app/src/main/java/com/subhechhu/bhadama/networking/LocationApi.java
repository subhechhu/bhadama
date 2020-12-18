package com.subhechhu.bhadama.networking;


import com.subhechhu.bhadama.model.LocationModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LocationApi {
    @GET("autocomplete.php")
    Call<List<LocationModel>> getLocationList(@Query("q") String newsSource,
                                              @Query("key") String apiKey,
                                              @Query("countrycodes") String countryCode);
}

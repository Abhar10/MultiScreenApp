package com.abhar.android.multiscreenapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class ApiClient to fetch data from URL
 */
public class ApiClient {
    private static final String BASE_URL="http://jsonplaceholder.typicode.com";
    private static Retrofit retrofit = null;

    /**
     * Method to create Retrofit object
     * @return Retrofit object
     */
    public static Retrofit getClient()
    {
        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}

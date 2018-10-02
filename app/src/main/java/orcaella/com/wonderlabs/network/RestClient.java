package orcaella.com.wonderlabs.network;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import orcaella.com.wonderlabs.BuildConfig;
import orcaella.com.wonderlabs.model.DateModel;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class RestClient {
    private static ApiService apiService;
    private static final int CONNECTION_TIME_OUT = 120;

    public static ApiService getClient() {
        if (apiService == null) {
            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                    .readTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.HOSTNAME)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
            apiService = retrofit.create(ApiService.class);
        }

        return apiService;
    }

    public interface ApiService {
        @GET(ApiUrl.GET_LIST_DATES)
        Call<List<DateModel>> getListDate();

    }
}



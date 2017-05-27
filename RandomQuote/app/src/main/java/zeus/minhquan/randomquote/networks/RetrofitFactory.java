package zeus.minhquan.randomquote.networks;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by QuanT on 5/27/2017.
 */

public class RetrofitFactory {
    public static RetrofitFactory retrofitFactory = new RetrofitFactory();
    private static Retrofit retrofit;

    private RetrofitFactory() {
        retrofit = new Retrofit.Builder().baseUrl("https://a-server.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitFactory getInstence() {
        return retrofitFactory;
    }

    public static <ServiceClass> ServiceClass createService(Class<ServiceClass> serviceClass) {
        return retrofit.create(serviceClass);
    }
}

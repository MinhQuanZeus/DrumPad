package zeus.minhquan.randomquote.networks.services;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import zeus.minhquan.randomquote.networks.RegisterRequest;
import zeus.minhquan.randomquote.networks.RegisterResponse;

/**
 * Created by QuanT on 5/27/2017.
 */

public interface LoginService {
    @POST("login")
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);
}

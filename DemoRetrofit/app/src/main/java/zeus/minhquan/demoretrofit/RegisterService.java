package zeus.minhquan.demoretrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by QuanT on 5/23/2017.
 */

public interface RegisterService {
    @POST("register")
    Call<RegisterResponse>register(@Body RegisterRequest registerRequest);
}

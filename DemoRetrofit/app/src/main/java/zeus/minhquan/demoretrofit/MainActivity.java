package zeus.minhquan.demoretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText ed_username, ed_password;
    Button btn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed_password = (EditText) findViewById(R.id.ed_password);
        ed_username = (EditText)findViewById(R.id.ed_username);
        btn_register = (Button)findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://a-server.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RegisterService registerService = retrofit.create(RegisterService.class);
        registerService.register(new RegisterRequest(ed_username.getText().toString(),ed_password.getText().toString())).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(response.code()==200) {
                    RegisterResponse registerResponse = response.body();
                    if(registerResponse.getCode()==1){
                        Toast.makeText(MainActivity.this,"Resgisted",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this,"User already exists",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Please connect network",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

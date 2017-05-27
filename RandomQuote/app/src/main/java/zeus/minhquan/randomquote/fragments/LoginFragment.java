package zeus.minhquan.randomquote.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zeus.minhquan.randomquote.R;
import zeus.minhquan.randomquote.activities.MainActivity;
import zeus.minhquan.randomquote.networks.RegisterRequest;
import zeus.minhquan.randomquote.networks.RegisterResponse;
import zeus.minhquan.randomquote.networks.RetrofitFactory;
import zeus.minhquan.randomquote.networks.services.LoginService;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";
    private final String prefname = "my_data";
    private static final int REQUEST_SIGNUP = 0;
    private EditText _usernameText;
    private EditText _passwordText;
    private Button _loginButton;
    private TextView _signupLink;
    private CheckBox _saveLoginInfomationCb;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        _usernameText = (EditText) view.findViewById(R.id.input_email);
        _passwordText = (EditText) view.findViewById(R.id.input_password);
        _loginButton = (Button) view.findViewById(R.id.btn_login);
        _signupLink = (TextView) view.findViewById(R.id.link_signup);
        _saveLoginInfomationCb = (CheckBox) view.findViewById(R.id.cb_remember);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO Start the Signup activity
                ((MainActivity) getActivity()).changeFragment(new RegisterFragment(), true);
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        savingPreferences();
    }

    @Override
    public void onResume() {
        super.onResume();
        restoringPreferences();
    }

    public void signIn() {
        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();
        LoginService loginService = RetrofitFactory.getInstence().createService(LoginService.class);
        loginService.register(new RegisterRequest(username, password)).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.code() == 200) {
                    RegisterResponse registerResponse = response.body();
                    if (registerResponse.getCode() == 1) {

                        Toast.makeText(getActivity(), registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        onLoginSuccess();
                    } else {
                        Toast.makeText(getActivity(), registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Please connect network", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        _loginButton.setEnabled(true);

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.
        signIn();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                    //    onLoginSuccess();
                        //onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        ((MainActivity) getActivity()).changeFragment(new QuoteFragment().setUsername(_usernameText.getText().toString()), false);
        //      finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getContext(), "Login failed", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        if (username.isEmpty() || username.length() < 3) {
            _usernameText.setError("must be at least 4 characters");
            valid = false;
        } else if (!username.matches("^[a-zA_Z0-9_.]{3,15}$")) {
            _usernameText.setError("contain letters, numbers, periods and underscores only");
            valid = false;
        } else {
            _usernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    public void savingPreferences() {
        SharedPreferences pre = this.getActivity().getSharedPreferences(prefname, MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        String user = _usernameText.getText().toString();
        String pwd = _passwordText.getText().toString();
        boolean bchk = _saveLoginInfomationCb.isChecked();
        if (!bchk) {
            editor.clear();
        } else {
            editor.putString("user", user);
            editor.putString("pwd", pwd);
            editor.putBoolean("checked", bchk);
        }
        editor.commit();
    }

    public void restoringPreferences()
    {
        SharedPreferences pre=this.getActivity().getSharedPreferences(prefname,MODE_PRIVATE);
        boolean bchk=pre.getBoolean("checked", false);
        if(bchk)
        {
            String user=pre.getString("user", "");
            String pwd=pre.getString("pwd", "");
            _usernameText.setText(user);
            _passwordText.setText(pwd);
        }
        _saveLoginInfomationCb.setChecked(bchk);
    }

}

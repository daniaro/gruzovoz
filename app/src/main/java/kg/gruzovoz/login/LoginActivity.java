package kg.gruzovoz.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import kg.gruzovoz.BaseActivity;
import kg.gruzovoz.R;

public class LoginActivity extends AppCompatActivity implements LoginContract.LoginView {

    Button loginButton;
    TextInputLayout phoneInputLayout;
    TextInputLayout passwordInputLayout;
    TextInputEditText phoneEditText;
    TextInputEditText passwordEditText;
    LoginPresenter presenterL = new LoginPresenter(this);
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        initViews();

        sharedPreferences = getApplicationContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        phoneEditText.setText("+996");
        Selection.setSelection(phoneEditText.getText(), phoneEditText.getText().length());

        phoneEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().startsWith("+996")) {
                    phoneEditText.setText("+996");
                    Selection.setSelection(phoneEditText.getText(), phoneEditText.getText().length());

                }

            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (passwordEditText.getText().length() > 0) {
                    passwordInputLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(getClass().getName(), passwordEditText.getText().toString());
                Log.e(getClass().getName(), phoneEditText.getText().toString());
                presenterL.login(phoneEditText.getText().toString(), passwordEditText.getText().toString());
            }
        });

    }

    private void initViews(){
        loginButton = findViewById(R.id.loginButton);
        passwordEditText = findViewById(R.id.passwordEditText);
        phoneEditText = findViewById(R.id.loginEditText);
        phoneInputLayout = findViewById(R.id.phone_text_field);
        passwordInputLayout = findViewById(R.id.password_text_field);
    }

    @Override
    public void showLoginError() {
        passwordInputLayout.setError(getString(R.string.shr_error_password));
        passwordEditText.setText("");
    }

    @Override
    public void showErrorToast() {
        Toast.makeText(this,getString(R.string.no_internet) , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addAuthToken(String authToken) {
        editor.putString("authToken", authToken).commit();
        startActivity(new Intent(LoginActivity.this, BaseActivity.class));
        finish();
    }

    @Override
    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}

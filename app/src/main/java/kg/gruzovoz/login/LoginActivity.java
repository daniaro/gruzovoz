package kg.gruzovoz.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import kg.gruzovoz.BaseActivity;
import kg.gruzovoz.R;
import kg.gruzovoz.models.Login;

public class LoginActivity extends AppCompatActivity implements LoginContract.LoginView{

    Button loginButton;
    EditText phoneEditText;
    EditText passwordEditText;
    public Login login;
    LoginPresenter presenterL = new LoginPresenter(this);
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        loginButton = findViewById(R.id.loginButton);
        passwordEditText = findViewById(R.id.passwordEditText);
        phoneEditText = findViewById(R.id.loginEditText);

        sharedPreferences = getApplicationContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

         //login = new Login(phoneEditText.getText().toString(),passwordEditText.getText().toString());

//        Login login = new Login("+996551234567","trueadminpass");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenterL.login(phoneEditText.getText().toString(), passwordEditText.getText().toString());
            }
        });

    }


    @Override
    public void showErrorToast() {
        Toast.makeText(this, "Неверные данные, попробуйте снова", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void addAuthToken(String authToken) {
        editor.putString("authToken", authToken).commit();
        startActivity(new Intent(LoginActivity.this, BaseActivity.class));
        finish();
    }
}

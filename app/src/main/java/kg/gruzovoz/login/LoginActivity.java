package kg.gruzovoz.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import kg.gruzovoz.R;
import kg.gruzovoz.models.Login;

public class LoginActivity extends AppCompatActivity implements LoginContract.LoginView{

    Button loginButton;
    TextInputLayout phoneEditText;
    TextInputLayout passwordEditText;
    public Login login;
    LoginPresenter presenterL = new LoginPresenter(this);
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        loginButton = findViewById(R.id.loginButton);
        passwordEditText = findViewById(R.id.password_text_field);
        phoneEditText = findViewById(R.id.phone_text_field);

        sharedPreferences = getApplicationContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

         //login = new Login(phoneEditText.getText().toString(),passwordEditText.getText().toString());

//        Login login = new Login("+996551234567","trueadminpass");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenterL.login();
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
    }


}

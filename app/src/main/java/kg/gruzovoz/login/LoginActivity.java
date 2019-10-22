package kg.gruzovoz.login;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import kg.gruzovoz.R;

public class LoginActivity extends AppCompatActivity implements LoginContract.LoginScreenView{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
    }
}

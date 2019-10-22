package kg.gruzovoz.login;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import kg.gruzovoz.R;

public class LoginScreenActivity extends AppCompatActivity implements LoginScreenContract.LoginScreenView{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
    }
}

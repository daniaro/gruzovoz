package kg.gruzovoz.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import kg.gruzovoz.BaseActivity;
import kg.gruzovoz.R;
import kg.gruzovoz.models.Login;

public class LoginActivity extends AppCompatActivity implements LoginContract.LoginView {

    Button loginButton;
    TextInputLayout phoneInputLayout;
    TextInputLayout passwordInputLayout;
    TextInputEditText phoneEditText;
    TextInputEditText passwordEditText;
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

        phoneInputLayout = findViewById(R.id.phone_text_field);
        passwordInputLayout = findViewById(R.id.password_text_field);

        passwordEditText.addTextChangedListener(textWatcher);

        sharedPreferences = getApplicationContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //login = new Login(phoneEditText.getText().toString(),passwordEditText.getText().toString());

//        Login login = new Login("+996551234567","trueadminpass");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
                Log.e(getClass().getName(), passwordEditText.getText().toString());
                Log.e(getClass().getName(), phoneEditText.getText().toString());
                //presenterL.login(passwordEditText.getText().toString(), phoneEditText.getText().toString());
                presenterL.login(phoneEditText.getText().toString(), passwordEditText.getText().toString());
            }
        });

    }



    @Override
    public void showLoginError() {
        passwordInputLayout.setError(getString(R.string.shr_error_password));
        passwordEditText.setText("");


    }

    @Override
    public void showErrorToast(){
        //        Toast.makeText(this, "Неверные данные, попробуйте снова", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void addAuthToken(String authToken) {
        editor.putString("authToken", authToken).commit();
        startActivity(new Intent(LoginActivity.this, BaseActivity.class));
        finish();
    }

    @Override
    public void registerUser() {
        if (TextUtils.isEmpty(phoneEditText.getText().toString().trim())) {
            phoneInputLayout.setError(getString(R.string.enter_phone_number));
            //Toast.makeText(this, "Введите номер телефона", Toast.LENGTH_SHORT).show();
            return;
        }
//        if (TextUtils.isEmpty(passwordEditText.getText().toString().trim())) {
//            passwordEditText.setError(getString(R.string.enter_password));
//            //Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show();
//            return;
//        }

    }

    TextWatcher textWatcher = new TextWatcher(){

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

//    public void ShowHidePass(View view){
//
//        if(view.getId()==R.id.show_pass_btn){
//
//            if(edit_password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
//                ((ImageView(view)).setImageResource(R.drawable.hide_password);
//
//                //Show Password
//                edit_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//            }
//            else{
//                ((ImageView)(view)).setImageResource(R.drawable.show_password);
//
//                //Hide Password
//                edit_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
//
//            }
//        }
//    }


}

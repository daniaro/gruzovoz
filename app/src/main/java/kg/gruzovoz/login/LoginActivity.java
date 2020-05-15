package kg.gruzovoz.login;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import kg.gruzovoz.BaseActivity;
import kg.gruzovoz.R;
import kg.gruzovoz.models.FirebaseUserData;

public class LoginActivity extends AppCompatActivity implements LoginContract.LoginView {

    Button loginButton;
    TextInputLayout phoneInputLayout;
    TextInputLayout passwordInputLayout;
    TextInputEditText phoneEditText;
    TextInputEditText passwordEditText;
    LoginPresenter presenterL = new LoginPresenter(this);
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @SuppressLint({"CommitPrefEdits", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        initViews();
        initSharedPref();

        initBattaryOptimithtion();
        initAutoStart();
        initNotificationAcces();

        phoneEditText.setText("+996");
        Selection.setSelection(phoneEditText.getText(), Objects.requireNonNull(phoneEditText.getText()).length());

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
                if (Objects.requireNonNull(passwordEditText.getText()).length() > 0) {
                    passwordInputLayout.setError(null);
                    loginButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        loginButton.setOnClickListener(view -> {
            presenterL.login(phoneEditText.getText().toString(), Objects.requireNonNull(passwordEditText.getText()).toString());
            loginButton.setEnabled(false);
        });


    }

    private void initSharedPref() {
        sharedPreferences = getApplicationContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private void initViews(){
        loginButton = findViewById(R.id.loginButton);
        passwordEditText = findViewById(R.id.passwordEditText);
        phoneEditText = findViewById(R.id.loginEditText);
        phoneInputLayout = findViewById(R.id.phone_text_field);
        passwordInputLayout = findViewById(R.id.password_text_field);
    }


    @Override
    public void addAuthToken(String authToken) {
        editor.putString("authToken", authToken).commit();
        startActivity(new Intent(LoginActivity.this, BaseActivity.class));
        finish();
    }


    @Override
    public void registerFirebaseUser(FirebaseUserData firebaseUserData) {
        String fbUserName = firebaseUserData.getUsername();
        String fbToken = firebaseUserData.getFirebaseToken();
        Long fbUserId = firebaseUserData.getId();

        FirebaseAuth.getInstance().signInWithCustomToken(fbToken);
        editor.putString("fbUserName", fbUserName).commit();
        editor.putLong("fbUserId", fbUserId).commit();


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
    public void showAlreadySignedToast() {
        passwordEditText.setText("");
        Toast.makeText(this,getString(R.string.already_signed) , Toast.LENGTH_LONG).show();
    }


    @Override
    public void notAuthorized() {
        Toast.makeText(this,"Dы не авторизованы",Toast.LENGTH_SHORT);
    }


    @Override
    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

//    @Override
    public void initAutoStart() {
        if (Build.MANUFACTURER.equals("Xiaomi") || Build.MANUFACTURER.equals("xiaomi") ) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
            builder.setTitle("Важно для Xiaomi")
                    .setMessage("Для корректной работы приложения, включите AutoStart")
                    .setCancelable(false)
                    .setNegativeButton(R.string.cancel, null)
                    .setPositiveButton(getString(R.string.ok), (dialog, which) -> {
                        try {
                            setResult(RESULT_OK);
                            Intent intent = new Intent();
                            intent.setComponent(new ComponentName(
                                    "com.miui.securitycenter",
                                    "com.miui.permcenter.autostart.AutoStartManagementActivity"
                            ));
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {

                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();

        } else if (Build.BRAND.equals("Honor")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
            builder.setTitle("Важно для Honor")
                    .setMessage("Чтобы не пропустить уведомления о новых сообщених, включите AutoStart")
                    .setCancelable(false)
                    .setNegativeButton(R.string.cancel, null)
                    .setPositiveButton(getString(R.string.ok), (dialog, which) -> {
                        try {
                            Intent intent = new Intent();
                            intent.setComponent(new ComponentName(
                                    "com.huawei.systemmanager",
                                    "com.huawei.systemmanager.optimize.process.ProtectActivity"
                            ));
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {

                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }


    public void initBattaryOptimithtion(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent();
            String packageName = getPackageName();
            PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
            assert pm != null;
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                Log.e("packegename",packageName);
                startActivity(intent);
            }
        }
    }


    public void initNotificationAcces(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        builder.setTitle("Важно для Грузовоза")
                .setMessage("Чтобы не пропустить уведомления о новых сообщених, нужно их настроить")
                .setCancelable(false)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(getString(R.string.ok), (dialog, which) -> {
                    try {
                        setResult(RESULT_OK);
                        Intent intent = new Intent();
                        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");

                        //for Android 5-7
                        intent.putExtra("app_package", getPackageName());
                        intent.putExtra("app_uid", getApplicationInfo().uid);

                        // for Android 8 and above
                        intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());

                        startActivity(intent);
                    } catch (ActivityNotFoundException ignored) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}

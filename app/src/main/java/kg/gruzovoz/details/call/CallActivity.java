package kg.gruzovoz.details.call;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import kg.gruzovoz.R;

public class CallActivity extends AppCompatActivity implements CallContract.CallView {

    private static final int  REQUEST_CALL = 1;

    Button callButton;
    Button allOrdersButton;
    TextView phoneNumberTextView;
    CallContract.CallPresenter presenter;
    TextView detailedAdress;

    String phoneNumber;
    String detailedAdressIntent;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        phoneNumber = getIntent().getStringExtra("phoneNumber");
        initViews();

        sharedPreferences = getApplicationContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE);

        detailedAdressIntent = sharedPreferences.getString("detailed_adress", null);
        detailedAdress.setText(detailedAdressIntent);
        Log.e("setViewInfo: call str", detailedAdressIntent);



        presenter = new CallPresenter(this);
        presenter.parsePhoneNumber(phoneNumber);
    }

    private void initViews() {
        phoneNumberTextView = findViewById(R.id.phone_numberTextView);
        detailedAdress = findViewById(R.id.detailed_address);
        callButton = findViewById(R.id.callButton);
        callButton.setOnClickListener(view -> makePhoneCall());
        allOrdersButton = findViewById(R.id.allOrdersButton);
        allOrdersButton.setOnClickListener(view -> {
            onBackPressed();
            finish();
        });
    }

    public void makePhoneCall(){
        String phoneNumber = getIntent().getStringExtra("phoneNumber");
        assert phoneNumber != null;
        if (phoneNumber.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(CallActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CallActivity.this,
                        new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial  = "tel:" + phoneNumber;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                finish();
            }
        } else {
            Toast.makeText(CallActivity.this, R.string.call_unavailable,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CALL) {

            if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            } else {
                Toast.makeText(CallActivity.this,"Невозможно позвонить, пожалуйста свяжитесь с диспетчером",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        phoneNumberTextView.setText(phoneNumber);
    }
}

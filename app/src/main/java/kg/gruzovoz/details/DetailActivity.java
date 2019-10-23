package kg.gruzovoz.details;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import kg.gruzovoz.R;

public class DetailActivity extends AppCompatActivity {

    Button acceptButton;
    Button finishButton;
    Button callButton;
    DetailPresenter presenter = new DetailPresenter();
    ImageView closeIcon;

    private static final int  REQUEST_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        acceptButton = findViewById(R.id.accept);
        finishButton = findViewById(R.id.finish);
        callButton = findViewById(R.id.callButton2);
        closeIcon = findViewById(R.id.close_icon);

//        if (acceptButton.isPressed()){
//            acceptButton.setVisibility(View.GONE);
//            finishButton.setVisibility(View.VISIBLE);
//            callButton.setVisibility(View.VISIBLE);
//        }
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO NUJNO ETO SDELAT'
            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent acceptIntent = new Intent(DetailActivity.this, CallActivity.class);
                startActivity(acceptIntent);
                finish();
                acceptButton.setVisibility(View.GONE);
                finishButton.setVisibility(View.VISIBLE);
                callButton.setVisibility(View.VISIBLE);
            }
        });

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall();
            }
        });

        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
    }


    public void makePhoneCall(){
        //get phone number from api , now let in be string var
        String phoneNumber = presenter.getPhoneNumber();
        if (phoneNumber.trim().length() >0){
            if (ContextCompat.checkSelfPermission(DetailActivity.this,
                    Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(DetailActivity.this,
                        new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + phoneNumber;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                finish();
            }
        } else {
            Toast.makeText(DetailActivity.this,"Невозможно позвонить, пожалуйста свяжитесь с диспетчером",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CALL){

            if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            }else {
                Toast.makeText(DetailActivity.this,"Невозможно позвонить, пожалуйста свяжитесь с диспетчером",Toast.LENGTH_LONG).show();

            }
        }
    }
}

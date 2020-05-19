package kg.gruzovoz.details;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import kg.gruzovoz.R;
import kg.gruzovoz.details.call.CallActivity;
import kg.gruzovoz.login.LoginActivity;
import kg.gruzovoz.models.Results;

public class DetailActivity extends AppCompatActivity implements DetailContract.DetailView {

    private DetailContract.DetailPresenter presenter;

    Button acceptButton;
    Button finishButton;
    Button callButton;
    ImageView closeIcon;

    TextView carTypeTextView;
    TextView shortAddressTextView;
    TextView detailedAddressTextView;
    TextView paymentTextView;
    TextView cargoTypeTextView;
    TextView commentTextView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private static final int REQUEST_CALL = 1;
    private Results results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        results = (Results) getIntent().getSerializableExtra("order");
        presenter = new DetailPresenter(this);

        initViews();
        if (!results.isActive() && !results.isDone()) {
            acceptButton.setVisibility(View.GONE);
            detailedAddressTextView.setVisibility(View.VISIBLE);
            detailedAddressTextView.setText(results.getDetailedAdress());
            finishButton.setVisibility(View.VISIBLE);
            callButton.setVisibility(View.VISIBLE);

        } else if (results.isDone()) {
            acceptButton.setVisibility(View.GONE);
            detailedAddressTextView.setVisibility(View.VISIBLE);
            detailedAddressTextView.setText(results.getDetailedAdress());
            finishButton.setVisibility(View.GONE);
            callButton.setVisibility(View.GONE);
        }
        sharedPreferences = getApplicationContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        initOnClickListeners();
        setViewInfo();


    }

    private void initViews() {
        acceptButton = findViewById(R.id.accept);
        finishButton = findViewById(R.id.finish);
        callButton = findViewById(R.id.callButton2);
        closeIcon = findViewById(R.id.close_icon);
        carTypeTextView = findViewById(R.id.textView);
        shortAddressTextView = findViewById(R.id.textView2);
        paymentTextView = findViewById(R.id.textView4);
        cargoTypeTextView = findViewById(R.id.textView5);
        commentTextView = findViewById(R.id.commentsTextView);
        detailedAddressTextView = findViewById(R.id.detailed_address_active);
    }

    private void initOnClickListeners() {
        finishButton.setOnClickListener(view -> showConfirmFinishAlertDialog());

        acceptButton.setOnClickListener(view -> showAcceptAlertDialog());

        callButton.setOnClickListener(view -> makePhoneCall());

        closeIcon.setOnClickListener(view -> {
            onBackPressed();
            finish();
        });
    }

    public void makePhoneCall(){
        String phoneNumber = results.getPhoneNumber();
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
            } else {
                Toast.makeText(DetailActivity.this,"Невозможно позвонить, пожалуйста свяжитесь с диспетчером",Toast.LENGTH_LONG).show();

            }
        }
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void setViewInfo() {
        carTypeTextView.setText(results.getCarType());
        shortAddressTextView.setText(results.getShortAddress());

        String commission = results.getCommission();
        double res = results.getPrice()*Integer.parseInt(commission)/100;
        String strRes;

        if(res == (long) res) {
            strRes =  String.format("%d", (long) res);
        } else {
            strRes = String.format("%s", res);
        }

        paymentTextView.setText(String.format("%s сом + %s", String.valueOf((int) results.getPrice()), strRes));

        cargoTypeTextView.setText(results.getTypeOfCargo());
        commentTextView.setText(results.getComments());

        editor.putString("detailed_address", results.getDetailedAdress()).commit();
        editor.putString("short_address", results.getShortAddress()).commit();

    }

    @Override
    public void showAcceptAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        builder.setTitle(getString(R.string.acceptOrder_title));
        builder.setMessage(getString(R.string.acceptOrder_dialog));
        builder.setNegativeButton(R.string.cancel_order, null);
        builder.setPositiveButton(getString(R.string.button_accept), (dialog, which) -> {
            setResult(RESULT_OK);
            presenter.acceptOrder(results.getId());

        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void showConfirmFinishAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        builder.setTitle(getString(R.string.finish_order_title));
        builder.setMessage(getString(R.string.confirm_finish_order));
        builder.setNegativeButton(R.string.cancel_order, null);
        builder.setPositiveButton(getString(R.string.finish), (dialog, which) -> {
            setResult(RESULT_OK);
            presenter.finishOrder(results.getId());
            finish();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }



    @Override
    public void startCallActivity() {
        Intent intent = new Intent(DetailActivity.this, CallActivity.class);
        intent.putExtra("phoneNumber", results.getPhoneNumber());
        startActivity(intent);
        finish();
    }


    @Override
    public void showAcceptError() {
        Toast.makeText(getApplicationContext(), R.string.accept_order_error, Toast.LENGTH_LONG).show();

    }

    @Override
    public void notAuthorized() {
        Intent intent = new Intent(this, LoginActivity.class);
        editor.putString("authToken", null).commit();
        startActivity(intent);
        finish();
    }

    @Override
    public void showCarTypeError() {
        Toast.makeText(getApplicationContext(), R.string.car_error, Toast.LENGTH_LONG).show();
    }


    @Override
    public void showBalanceError(){
        Toast.makeText(getApplicationContext(), R.string.balance_error, Toast.LENGTH_LONG).show();
    }




}

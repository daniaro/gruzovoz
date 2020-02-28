package kg.gruzovoz.details;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
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
import kg.gruzovoz.models.Order;

public class DetailActivity extends AppCompatActivity implements DetailContract.DetailView {

    private DetailContract.DetailPresenter presenter;

    Button acceptButton;
    Button finishButton;
    Button callButton;
    ImageView closeIcon;

    TextView carTypeTextView;
    TextView initialAddressTextView;
    TextView finalAddressTextView;
    TextView paymentTextView;
    TextView cargoTypeTextView;
    TextView commentTextView;

    private static final int REQUEST_CALL = 1;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        order = (Order) getIntent().getSerializableExtra("order");
        presenter = new DetailPresenter(this);

        initViews();
        if (!order.isActive() && !order.isDone()) {
            acceptButton.setVisibility(View.GONE);
            finishButton.setVisibility(View.VISIBLE);
            callButton.setVisibility(View.VISIBLE);

        } else if (order.isDone()) {
            acceptButton.setVisibility(View.GONE);
            finishButton.setVisibility(View.GONE);
            callButton.setVisibility(View.GONE);
        }
        initOnClickListeners();
        setViewInfo();
    }

    private void initViews() {
        acceptButton = findViewById(R.id.accept);
        finishButton = findViewById(R.id.finish);
        callButton = findViewById(R.id.callButton2);
        closeIcon = findViewById(R.id.close_icon);
        carTypeTextView = findViewById(R.id.textView);
        initialAddressTextView = findViewById(R.id.textView2);
        finalAddressTextView = findViewById(R.id.textView3);
        paymentTextView = findViewById(R.id.textView4);
        cargoTypeTextView = findViewById(R.id.textView5);
        commentTextView = findViewById(R.id.commentsTextView);
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
        String phoneNumber = order.getPhoneNumber();
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

    @Override
    public void setViewInfo() {
        carTypeTextView.setText(order.getCarType());
        initialAddressTextView.setText(order.getStartAddress());
        finalAddressTextView.setText(order.getFinishAddress());
        String commission = order.getCommission();
        // Here we check if the admin entered the commission's value with or without the percent sign
        double res = order.getPrice()*Integer.parseInt(commission)/100;
        String strRes;

        if(res == (long) res) {
            strRes =  String.format("%d", (long) res);
        } else {
            strRes = String.format("%s", res);
        }

//        if (commission.charAt(commission.length() - 1) == '%') {
//            paymentTextView.setText(String.format("%s сом - %s = %s сом", String.valueOf((int) order.getPrice()), commission, strRes));
//        } else {
//            paymentTextView.setText(String.format("%s сом - %s%% = %s сом", String.valueOf((int) order.getPrice()), commission, strRes));
//        }

            paymentTextView.setText(String.format("%s сом + %s", String.valueOf((int) order.getPrice()), strRes));



        cargoTypeTextView.setText(order.getCargoType());
        commentTextView.setText(order.getComments());
    }

    @Override
    public void showAcceptAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        builder.setTitle(getString(R.string.acceptOrder_title));
        builder.setMessage(getString(R.string.acceptOrder_dialog));
        builder.setNegativeButton(R.string.cancel_order, null);
        builder.setPositiveButton(getString(R.string.button_accept), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setResult(RESULT_OK);
                presenter.acceptOrder(order.getId());

            }
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
        builder.setPositiveButton(getString(R.string.finish), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setResult(RESULT_OK);
                presenter.finishOrder(order.getId());

                finish();

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void startCallActivity() {
        Intent intent = new Intent(DetailActivity.this, CallActivity.class);
        intent.putExtra("phoneNumber", order.getPhoneNumber());
        startActivity(intent);
        finish();
    }


    @Override
    public void showError() {
        Toast.makeText(getApplicationContext(), "Невозможно принять заказ", Toast.LENGTH_LONG).show();
    }


}

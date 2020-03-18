package kg.gruzovoz.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import kg.gruzovoz.BaseActivity;
import kg.gruzovoz.BaseContract;
import kg.gruzovoz.R;
import kg.gruzovoz.models.Order;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder> {

    private List<Order> ordersList;
    private BaseContract.OnItemClickListener clickListener;
    private BaseActivity baseActivity = new BaseActivity();

    public OrdersAdapter(BaseContract.OnItemClickListener clickListener) {
        ordersList = new ArrayList<>();
        this.clickListener = clickListener;
    }

    public void setValues(List<Order> values) {
        ordersList.clear();
        if (values != null) {
            ordersList.addAll(values);
        }
        this.notifyDataSetChanged();
    }


    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.bind(ordersList.get(position), clickListener);
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView dateTextView;
        TextView dateTextView_month;
        TextView paymentTextView;
        TextView carTypeTextView;
        TextView addressTextView;
        TextView typeInTextView;
        ImageView culculate1p;
        ImageView culculate2p;

        OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            paymentTextView = itemView.findViewById(R.id.paymentTextView);
            carTypeTextView = itemView.findViewById(R.id.carTypeTextView);
            addressTextView = itemView.findViewById(R.id.addressTextView);
            dateTextView_month = itemView.findViewById(R.id.dateTextView_month);
            typeInTextView = itemView.findViewById(R.id.typeIn);
            culculate1p = itemView.findViewById(R.id.culculate1p);
            culculate2p = itemView.findViewById(R.id.culculate2p);
        }

        @SuppressLint({"LongLogTag", "DefaultLocale"})
        void bind(final Order order, final BaseContract.OnItemClickListener onItemClickListener) {
            itemView.setOnClickListener(view -> {
                onItemClickListener.onItemClick(order);
                itemView.setEnabled(false);

                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                     baseActivity.runOnUiThread(() -> itemView.setEnabled(true));
                    }
                }, 3000);
            });



            String commission = order.getCommission();
            double res = order.getPrice() * Integer.parseInt(commission) / 100;
            String strRes;

            if (res == (long) res) {
                strRes =  String.format("%d", (long) res);
            } else {
                strRes = String.format("%s", res);
            }

            paymentTextView.setText(String.format("%s  ||  %s", String.valueOf((int) order.getPrice()), strRes));

            carTypeTextView.setText(order.getCarType());
            addressTextView.setText(order.getStartAddress());
            typeInTextView.setText(order.getTypeOfCargo());

            if (order.isCalculated()){
                culculate1p.setVisibility(View.VISIBLE);
                culculate2p.setVisibility(View.VISIBLE);
            }else {
                culculate1p.setVisibility(View.GONE);
                culculate2p.setVisibility(View.GONE);
            }

            Date date;
            TimeZone timeZone = TimeZone.getTimeZone("Asia/Bishkek");
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");

            sdf.setTimeZone(timeZone);
            String dateStr = order.getDateOfCreated();
            try {
                date = sdf.parse(dateStr);
                assert date != null;
                String formatedDate =  sdf.format(date);

                String completedMonth = formatedDate.substring(5,7);
                String completedDate = formatedDate.substring(8);

                dateTextView.setText(completedDate);


                switch (completedMonth){
                    case "01":
                        dateTextView_month.setText("Янв");
                        break;
                    case "02":
                        dateTextView_month.setText("Фев.");
                        break;
                    case "03":
                        dateTextView_month.setText("Мар.");
                        break;
                    case "04":
                        dateTextView_month.setText("Апр.");
                        break;
                    case "05":
                        dateTextView_month.setText("Май");
                        break;
                    case "06":
                        dateTextView_month.setText("Июн.");
                        break;
                    case "07":
                        dateTextView_month.setText("Июл.");
                        break;
                    case "08":
                        dateTextView_month.setText("Авг.");
                        break;
                    case "09":
                        dateTextView_month.setText("Сен.");
                        break;
                    case "10":
                        dateTextView_month.setText("Окт.");
                        break;
                    case "11":
                        dateTextView_month.setText("Ноя.");
                        break;
                    case "12":
                        dateTextView_month.setText("Дек.");
                        break;

                }

            } catch (ParseException e) {
                e.printStackTrace();
                Log.e("ParseException catched", "ParseException");

            }

        }

    }
}

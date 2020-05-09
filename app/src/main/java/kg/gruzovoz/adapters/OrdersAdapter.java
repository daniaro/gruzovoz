package kg.gruzovoz.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import kg.gruzovoz.models.Results;
import kg.gruzovoz.paging.BaseViewHolder;

public class OrdersAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private BaseContract.OnItemClickListener clickListener;
    private BaseActivity baseActivity = new BaseActivity();

    private final int VIEW_TYPE_LOADING = 0;
    private final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;
    private List<Results> resultsList;


    public OrdersAdapter(Order order, List<Results> results, BaseContract.OnItemClickListener clickListener) {
        resultsList = new ArrayList();
//        resultsList = order.getResults();
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new OrderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_item, parent, false)) {
                };
            case VIEW_TYPE_LOADING:
                return new LoadingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_loading, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position, clickListener);

    }

    @Override
    public int getItemViewType(int position) {
        if (isLoaderVisible) {
            return position == resultsList.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return resultsList == null ? 0 : resultsList.size();
    }


    public void addItems(Order order) {
        resultsList.addAll(order.getResults());
        notifyDataSetChanged();
    }


    public void setValues(List<Results> values) {
        resultsList.clear();
        if (values != null) {
            resultsList.addAll(values);
        }
        this.notifyDataSetChanged();
    }


    public void addLoading() {
        isLoaderVisible = true;
        resultsList.add(new Results());
        notifyItemInserted(resultsList.size() - 1);
    }


    public void removeLoading() {
        try {
            isLoaderVisible = false;
            int position = resultsList.size() - 1;
            Results result = getOrders(position);
            if (result != null) {
                resultsList.remove(position);
                notifyItemRemoved(position);
            }
        } catch (Exception ignored) {

        }
    }


    public void clear() {
        resultsList.clear();
        notifyDataSetChanged();
    }


    private Results getOrders(int position) {
        return resultsList.get(position);
    }


    class OrderViewHolder extends BaseViewHolder {

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

        @Override
        protected void clear() {

        }

        @SuppressLint({"LongLogTag", "DefaultLocale"})
        public void onBind(int position, final BaseContract.OnItemClickListener onItemClickListener) {
            Results results = resultsList.get(position);

            itemView.setOnClickListener(view -> {
                onItemClickListener.onItemClick(results);
                itemView.setEnabled(false);

                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                     baseActivity.runOnUiThread(() -> itemView.setEnabled(true));
                    }
                }, 3000);
            });

            String commission = results.getCommission();
            double res = results.getPrice() * Integer.parseInt(commission) / 100;
            String strRes;

            if (res == (long) res) {
                strRes =  String.format("%d", (long) res);
            } else {
                strRes = String.format("%s", res);
            }

            paymentTextView.setText(String.format("%s  ||  %s", String.valueOf((int) results.getPrice()), strRes));

            carTypeTextView.setText(results.getCarType());
            addressTextView.setText(results.getShortAddress());
            typeInTextView.setText(results.getTypeOfCargo());

            if (results.isCalculated()){
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
            String dateStr = results.getDateOfCreated();
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

    class LoadingViewHolder extends BaseViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);

            progressBar = itemView.findViewById(R.id.progressBar);
        }

        @Override
        protected void clear() {

        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {

    }

    private void populateItemRows(OrderViewHolder viewHolder, int position) {

    }
}

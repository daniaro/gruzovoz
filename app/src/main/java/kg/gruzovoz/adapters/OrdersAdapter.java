package kg.gruzovoz.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import kg.gruzovoz.BaseContract;
import kg.gruzovoz.R;
import kg.gruzovoz.models.Order;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder> {

    private List<Order> ordersList;
    private BaseContract.OnItemClickListener clickListener;

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

    public List<Order> getOrdersList() {
        return ordersList;
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
        TextView paymentTextView;
        TextView carTypeTextView;
        TextView addressTextView;
        TextView dateOfCreated;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            paymentTextView = itemView.findViewById(R.id.paymentTextView);
            carTypeTextView = itemView.findViewById(R.id.carTypeTextView);
            addressTextView = itemView.findViewById(R.id.addressTextView);
            dateOfCreated = itemView.findViewById(R.id.date_of_created);
        }

        public void bind(final Order order, final BaseContract.OnItemClickListener onItemClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(order);
                }
            });
            switch (order.getLeadTime()) {
                case 1:
                    dateTextView.setText("СЕГОДНЯ");
                    break;
                case 2:
                    dateTextView.setText("ЗАВТРА");
                    break;
                case 3:
                    dateTextView.setText("СРОЧНО");
                    break;
            }

            String commission = order.getCommission();
            double res = order.getPrice()*Integer.parseInt(commission)/100;
            String strRes;

            if(res == (long) res) {
                strRes =  String.format("%d", (long) res);
            } else {
                strRes = String.format("%s", res);
            }

            if (commission.charAt(commission.length() - 1) == '%') {
                paymentTextView.setText(String.format("%s сом - %s = %s сом", String.valueOf((int) order.getPrice()), commission, strRes));
            } else {
                paymentTextView.setText(String.format("%s сом - %s%% = %s сом", String.valueOf((int) order.getPrice()), commission, strRes));
            }
            carTypeTextView.setText(order.getCarType());
            addressTextView.setText(order.getFinishAddress());

            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");//formating according to my need
            String date = formatter.format(order.getDateOfCreated());
            dateOfCreated.setText(date);
        }
    }
}

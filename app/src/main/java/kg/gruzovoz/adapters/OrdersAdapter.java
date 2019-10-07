package kg.gruzovoz.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kg.gruzovoz.BaseContract;
import kg.gruzovoz.R;
import kg.gruzovoz.main.MainContract;
import kg.gruzovoz.models.Order;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder> {

    private List<Order> ordersList;
    private BaseContract.OnItemClickListener clickListener;

    public OrdersAdapter(BaseContract.OnItemClickListener clickListener) {
        ordersList = new ArrayList<>();
        this.clickListener = clickListener;
    }

    public void setValues(List<Order> values) {
        ordersList = values;
        notifyDataSetChanged();
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
        return 0;
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView dateTextView;
        TextView paymentTextView;
        TextView carTypeTextView;
        TextView addressTextView;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            paymentTextView = itemView.findViewById(R.id.paymentTextView);
            carTypeTextView = itemView.findViewById(R.id.carTypeTextView);
            addressTextView = itemView.findViewById(R.id.addressTextView);
        }

        public void bind(final Order order, final BaseContract.OnItemClickListener onItemClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(order);
                }
            });
            dateTextView.setText(order.getDate());
            paymentTextView.setText(String.format("%d + %d%", order.getPayment(), order.getCommission()));
            carTypeTextView.setText(order.getCarType());
            addressTextView.setText(order.getFinalDestination());
        }
    }
}

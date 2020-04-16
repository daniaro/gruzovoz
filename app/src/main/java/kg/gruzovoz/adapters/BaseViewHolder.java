package kg.gruzovoz.adapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import kg.gruzovoz.BaseContract;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    private int currentPosition;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    protected abstract void clear();

    public void onBind(int position, BaseContract.OnItemClickListener clickListener) {
        currentPosition = position;
        clear();
    }

    public int getCurrentPosition() {
        return currentPosition;
    }
}

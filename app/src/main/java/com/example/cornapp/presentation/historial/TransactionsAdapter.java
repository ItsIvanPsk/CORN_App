package com.example.cornapp.presentation.historial;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cornapp.R;
import com.example.cornapp.data.models.UserTransactionBo;

import java.util.ArrayList;
import java.util.Date;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.ViewHolder> {

    private ArrayList<UserTransactionBo> transactionBos = new ArrayList<>();
    private int user_id;
    private LayoutInflater mInflater;

    TransactionsAdapter(Context context, ArrayList<UserTransactionBo> _records, int user_id) {
        this.mInflater = LayoutInflater.from(context);
        this.transactionBos = _records;
        this.user_id = user_id;
    }

    TransactionsAdapter() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.transaction_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (transactionBos.get(position).getOrigin() == user_id) {
            String name = transactionBos.get(position).getDestinationName();
            String id = String.valueOf(transactionBos.get(position).getDestination());
            String amount = "-" + String.valueOf(transactionBos.get(position).getAmount());
            String date = transactionBos.get(position).getTimeAccepted();
            holder.name.setText(name);
            holder.id.setText(id);
            holder.amount.setText(amount);
            holder.date.setText(date);
            holder.amount.setTextColor(R.color.green);
        } else {
            String name = transactionBos.get(position).getOriginName();
            String id = String.valueOf(transactionBos.get(position).getOrigin());
            String amount = "+" + String.valueOf(transactionBos.get(position).getAmount());
            String date = transactionBos.get(position).getTimeAccepted();
            holder.name.setText(name);
            holder.id.setText(id);
            holder.amount.setText(amount);
            holder.date.setText(date);
            holder.amount.setTextColor(R.color.app_red);
        }

    }

    @Override
    public int getItemCount() {
        return transactionBos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView id;
        TextView amount;
        TextView date;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.transaction_name);
            id = itemView.findViewById(R.id.transaction_id);
            amount = itemView.findViewById(R.id.transaction_amount);
            date = itemView.findViewById(R.id.transaction_date);
        }
    }
}
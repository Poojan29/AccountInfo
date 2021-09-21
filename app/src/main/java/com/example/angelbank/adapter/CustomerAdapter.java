package com.example.angelbank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.angelbank.R;
import com.example.angelbank.datamodel.BankTable;

import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private List<BankTable> allCustomer = new ArrayList<>();
    private onItemClickListener listener;


    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_customer_layout, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        holder.name.setText(allCustomer.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return allCustomer.size();
    }

    public void submitCustomer(List<BankTable> customer){
        this.allCustomer = customer;
        notifyDataSetChanged();
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageButton imageButton;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.customer_name_single_layout);
            imageButton = itemView.findViewById(R.id.imageButton);

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(allCustomer.get(position));
                    }
                }
            });
        }
    }

    public interface onItemClickListener{
        void onItemClick(BankTable bankTable);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }
}

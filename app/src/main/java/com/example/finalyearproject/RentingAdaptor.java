package com.example.finalyearproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RentingAdaptor extends RecyclerView.Adapter<RentingAdaptor.MyviewHolder> {


    Context context;
    List<Renting_Details> list;

    public RentingAdaptor(Context context, List<Renting_Details> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.renting_details_recyclerview,parent,false);
        MyviewHolder holder=new MyviewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {

        Renting_Details details=list.get(position);
        holder.Name.setText(details.getName());
        holder.CarName.setText(details.getCarName());
        holder.RentDate.setText(details.getRent_Date());
        holder.ReturnDate.setText(details.getReturn_Date());
        holder.RentTime.setText(details.getRent_Time());
        holder.ReturnTime.setText(details.getReturn_Time());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder{

        TextView Name;
        TextView CarName;
        TextView RentTime;
        TextView ReturnTime;
        TextView RentDate;
        TextView ReturnDate;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            Name=itemView.findViewById(R.id.Renting_Name);
            CarName=itemView.findViewById(R.id.Renting_CarName);
            RentTime=itemView.findViewById(R.id.Renting_RentTime);
            ReturnTime=itemView.findViewById(R.id.Renting_ReturnTime);
            RentDate=itemView.findViewById(R.id.Renting_RentDate);
            ReturnDate=itemView.findViewById(R.id.Rening_ReturnDate);
        }
    }
}

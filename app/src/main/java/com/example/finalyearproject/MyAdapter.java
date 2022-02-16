package com.example.finalyearproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private String []names;
    private String []price;
    private int [] cars;
    Context context;

    public MyAdapter(String [] names,String[]price,int[]cars,Context context){
        this.names=names;
        this.price=price;
        this.cars=cars;
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.recycler_view_items,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String namesofcar= names[position];
        String priceofcar=price[position];
        int carpicture=cars[position];
        holder.car_name.setText(namesofcar);
        holder.car_price.setText(priceofcar);
        holder.car_image.setImageResource(carpicture);
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView car_image;
        TextView car_name;
        TextView car_price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            car_image=itemView.findViewById(R.id.car);
            car_name=itemView.findViewById(R.id.carname);
            car_price=itemView.findViewById(R.id.carprice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position= getAdapterPosition();
                    Intent intent =new Intent(context, CarViewforRent.class);
                    intent.putExtra("carname", names[getAdapterPosition()]);
                    intent.putExtra("carprice",price[getAdapterPosition()]);
                    intent.putExtra("carpicture",cars[getAdapterPosition()]);
                    context.startActivity(intent);

                }
            });
        }
    }
}

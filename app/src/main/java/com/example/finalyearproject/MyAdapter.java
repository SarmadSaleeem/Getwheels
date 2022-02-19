package com.example.finalyearproject;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Filterable{

    ArrayList<CarData> data;
    Context context;

    ArrayList<CarData> backup;

    public MyAdapter(Context context, ArrayList<CarData> data){

        this.context=context;
        this.data=new ArrayList<CarData>(data);
        this.backup=new ArrayList<CarData>(data);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.recycler_view_items,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.car_name.setText(data.get(position).getCarname());
        holder.car_price.setText(data.get(position).getCarprice());
        holder.car_image.setImageResource(data.get(position).getCarimage());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public Filter getFilter(){

        return filter;
    }


    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            ArrayList <CarData> filteredlist=new ArrayList<>();

            if(constraint.toString().isEmpty()){
                filteredlist.addAll(backup);

            }else {

                for (CarData obj:backup)
                {
                    if(obj.getCarname().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredlist.add(obj);
                    }
                }
            }

            FilterResults filterResults=new FilterResults();
            filterResults.values=filteredlist;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

                data.clear();
                data.addAll((ArrayList<CarData>)results.values);
                notifyDataSetChanged();

        }
    };


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
                    Intent intent =new Intent(context, CarViewforRent.class);
                    intent.putExtra("carname", data.get(getAdapterPosition()).getCarname());
                    intent.putExtra("carprice", data.get(getAdapterPosition()).getCarprice());
                    intent.putExtra("carpicture", data.get(getAdapterPosition()).getCarimage());
                    context.startActivity(intent);

                }
            });
        }
    }
}

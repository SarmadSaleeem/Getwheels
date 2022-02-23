package com.example.finalyearproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class Slider extends PagerAdapter {

    ArrayList<Integer> otherimage;
    public Slider(ArrayList<Integer> otherimage) {
        this.otherimage=new ArrayList<>(otherimage);
    }

    @Override
    public int getCount() {
        return otherimage.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view= LayoutInflater.from(container.getContext()).inflate(R.layout.otherimageslider,container,false);
        ImageView imageView= view.findViewById(R.id.otherimages);
        imageView.setImageResource(otherimage.get(position));

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

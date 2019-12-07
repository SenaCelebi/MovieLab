package com.example.scele.movielab.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.scele.movielab.R;
import com.example.scele.movielab.Models.Slidep;

import java.util.List;

public class SliderPageAdapter extends PagerAdapter {

    private Context context;
    private List<Slidep> listSlides;


    public SliderPageAdapter(Context context, List<Slidep> listSlides) {
        this.context = context;
        this.listSlides = listSlides;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View slideLayout;
        slideLayout = inflater.inflate(R.layout.slide_item,container, false);

        ImageView slideImage = slideLayout.findViewById(R.id.image_slide);
        TextView slideTitle = slideLayout.findViewById(R.id.title_slide);
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/"+listSlides.get(position).getImage()).into(slideImage);
        slideTitle.setText(listSlides.get(position).getTitle());
      //  Log.v("heyoo", listSlides.get(0).getTitle());

        container.addView(slideLayout);
        return slideLayout;
    }

    @Override
    public int getCount() {
        return listSlides.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}

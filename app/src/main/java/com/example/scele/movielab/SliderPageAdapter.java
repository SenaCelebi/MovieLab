package com.example.scele.movielab;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
        View slideLayout = inflater.inflate(R.layout.slide_item,null);

        ImageView slideImage = slideLayout.findViewById(R.id.image_slide);
        TextView slideTitle = slideLayout.findViewById(R.id.title_slide);
        slideImage.setImageResource(listSlides.get(position).getImage());
        slideTitle.setText(listSlides.get(position).getTitle());

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

package com.example.scele.movielab.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scele.movielab.Models.Trailer;
import com.example.scele.movielab.MovieDetailActivity;
import com.example.scele.movielab.R;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.MyViewHolder3> {

    private Context context;
    private List<Trailer> trailerList;

    public TrailerAdapter(Context context, List<Trailer> trailerList){
        this.context = context;
        this.trailerList = trailerList;

    }
    @NonNull
    @Override
    public TrailerAdapter.MyViewHolder3 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trailer_item, viewGroup, false);
        return new MyViewHolder3(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerAdapter.MyViewHolder3 myViewHolder3, final int i) {
        myViewHolder3.title.setText(trailerList.get(i).getName());


        myViewHolder3.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Trailer clikedDataItem = trailerList.get(i);
               String videoId = trailerList.get(i).getKey();
               Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+videoId));
               intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               intent.putExtra("video_id", videoId);
               context.startActivity(intent);
                Toast.makeText(v.getContext(), "You cliked", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    public class MyViewHolder3 extends RecyclerView.ViewHolder{

        private TextView title;
        private CardView cardView;

        public MyViewHolder3 (View view){
            super(view);
            title = view.findViewById(R.id.trailer_link_tv);
            cardView = view.findViewById(R.id.trailer_layout);


        }

    }
}

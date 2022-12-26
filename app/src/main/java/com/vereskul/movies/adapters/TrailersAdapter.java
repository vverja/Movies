package com.vereskul.movies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vereskul.movies.R;
import com.vereskul.movies.model.Video;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailersHolder>{
    private OnPlayButtonClick onPlayButtonClick;
    private List<Video> trailersList = new ArrayList<>();
    @NonNull
    @Override
    public TrailersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_item, parent, false);
        return new TrailersHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersHolder holder, int position) {
        Video video = trailersList.get(position);
        holder.trailerName.setText(video.getName());
        holder.trailerPlayImage.setOnClickListener(view -> {
            if (onPlayButtonClick!=null){
                onPlayButtonClick.onClick(video);
            }
        });

    }

    @Override
    public int getItemCount() {
        return trailersList.size();
    }

    static class TrailersHolder extends RecyclerView.ViewHolder{
        private final TextView trailerName;
        private final ImageView trailerPlayImage;
        public TrailersHolder(@NonNull View itemView) {
            super(itemView);
            trailerName = itemView.findViewById(R.id.trailer_name);
            trailerPlayImage = itemView.findViewById(R.id.trailer_play_image);
        }

    }

    public interface OnPlayButtonClick{
        void onClick(Video video);
    }

    public void setTrailersList(List<Video> trailersList) {
        this.trailersList = trailersList;
        notifyDataSetChanged();
    }

    public void setOnPlayButtonClick(OnPlayButtonClick onPlayButtonClick) {
        this.onPlayButtonClick = onPlayButtonClick;
    }
}

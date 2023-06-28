package com.example.avangard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder> {
    // ...
    private List<Match> matches;
    private Context context;
    public ResultsAdapter(List<Match> matches, Context context){
        this.matches = matches;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resultitem,
                parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Match item = matches.get(position);
        Team teamHome = item.getTeamHome();
        Team teamGuest = item.getTeamGuest();
        String teamHomeName = teamHome.getName();
        String teamGuestName = teamGuest.getName();
        String teamHomePath = teamHome.getEmblemPath();
        String teamGuestPath = teamGuest.getEmblemPath();
        holder.nameHome.setText(teamHomeName);
        holder.nameGuest.setText(teamGuestName);
        int imageResourceIdHome = context.getResources().getIdentifier(
                teamHomePath,
                "drawable",
                context.getPackageName()
        );
        holder.emblemHome.setImageResource(imageResourceIdHome);
        int imageResourceIdGuest = context.getResources().getIdentifier(
                teamGuestPath,
                "drawable",
                context.getPackageName()
        );
        holder.emblemGuest.setImageResource(imageResourceIdGuest);
        holder.cityHome.setText(teamHome.getCity());
        holder.cityGuest.setText(teamGuest.getCity());
        holder.scoreHome.setText(Integer.toString(item.getTeamHomeGoals()));
        holder.scoreGuest.setText(Integer.toString(item.getTeamGuestGoals()));
        holder.cardView.setCardBackgroundColor(0x261ad6);
        holder.cardView.setMinimumWidth(ViewGroup.LayoutParams.MATCH_PARENT);


    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameHome;
        public ImageView emblemHome;
        public ImageView emblemGuest;
        public TextView nameGuest;
        public TextView cityHome;
        public TextView cityGuest;
        public TextView scoreHome;
        public TextView scoreGuest;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameHome = itemView.findViewById(R.id.homeTeamNameR);
            emblemHome = itemView.findViewById(R.id.homeTeamEmblemR);
            nameGuest = itemView.findViewById(R.id.guestTeamNameR);
            emblemGuest = itemView.findViewById(R.id.guestTeamEmblemR);
            cityHome = itemView.findViewById(R.id.homeTeamCityR);
            cityGuest = itemView.findViewById(R.id.guestTeamCityR);
            scoreHome = itemView.findViewById(R.id.scoreHomeR);
            scoreGuest = itemView.findViewById(R.id.scoreGuestR);
            cardView = itemView.findViewById(R.id.cardResults);



        }
    }
}


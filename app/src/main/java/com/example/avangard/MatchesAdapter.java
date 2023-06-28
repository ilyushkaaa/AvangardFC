package com.example.avangard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ViewHolder> {
    // ...
    private List<Match> matches;
    private Context context;
    public MatchesAdapter(List<Match> matches, Context context){
        this.matches = matches;
        this.context = context;
    }
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Team text1, Team text2, Match item);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.matchitem,
                parent, false);
        return new ViewHolder(view);
    }

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
        holder.buyTickets.setOnClickListener(v -> {
            Team team1 = item.getTeamHome();
            Team team2 = item.getTeamGuest();
            if (listener != null) {
                listener.onItemClick(team1, team2, item);
            }
            else{
                System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
            }
        });


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
        public Button buyTickets;

        public ViewHolder(View itemView) {
            super(itemView);
            nameHome = itemView.findViewById(R.id.homeTeamName);
            emblemHome = itemView.findViewById(R.id.homeTeamEmblem);
            nameGuest = itemView.findViewById(R.id.guestTeamName);
            emblemGuest = itemView.findViewById(R.id.guestTeamEmblem);
            cityHome = itemView.findViewById(R.id.homeTeamCity);
            cityGuest = itemView.findViewById(R.id.guestTeamCity);
            buyTickets = itemView.findViewById(R.id.buyButton);


        }
    }
}


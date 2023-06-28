package com.example.avangard;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.ViewHolder> {
    // ...
    private List<Ticket> tickets;

    private Context context;
    public TicketsAdapter(List<Ticket> tickets, Context context){
        this.tickets = tickets;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticketsitem,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ticket item = tickets.get(position);
        Team teamHome = item.getMatch().getTeamHome();
        Team teamGuest = item.getMatch().getTeamGuest();
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
        String tribunaField = item.getTribuna() + "трибуна";
        holder.tribuna.setText(tribunaField);
        String sectorField = Integer.toString(item.getSector()) + " сектор";
        holder.sector.setText(sectorField);
        String rowField = Integer.toString(item.getRow()) + " ряд";
        holder.row.setText(rowField);
        String placeField = Integer.toString(item.getPlace()) + " место";
        holder.place.setText(placeField);
        GradientDrawable border = new GradientDrawable();
        //border.setColor(); // цвет фона

        border.setCornerRadius(20);
        int color = Color.argb(240, 255, 255, 255);
        border.setColor(color);

        holder.cardTicket.setBackground(border);





    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameHome;
        public ImageView emblemHome;
        public ImageView emblemGuest;
        public TextView nameGuest;
        public TextView cityHome;
        public TextView cityGuest;
        public TextView tribuna;
        public TextView sector;
        public TextView row;
        public TextView place;
        public CardView cardTicket;


        public ViewHolder(View itemView) {
            super(itemView);
            nameHome = itemView.findViewById(R.id.homeTeamTicketsItem);
            emblemHome = itemView.findViewById(R.id.imageHomeTicketsItem);
            nameGuest = itemView.findViewById(R.id.guestTeamTicketsItem);
            emblemGuest = itemView.findViewById(R.id.imageGuestTicketsItem);
            cityHome = itemView.findViewById(R.id.homeTeamTicketsCityItem);
            cityGuest = itemView.findViewById(R.id.guestTeamTicketsCityItem);
            tribuna = itemView.findViewById(R.id.tribunaTicket);
            sector = itemView.findViewById(R.id.sectorTicket);
            row = itemView.findViewById(R.id.rowTicket);
            place = itemView.findViewById(R.id.placeTicket);
            cardTicket = itemView.findViewById(R.id.cardTicket);





        }
    }
}

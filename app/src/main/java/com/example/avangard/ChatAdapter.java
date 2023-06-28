package com.example.avangard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;



public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    // ...
    private List<String> emails;

    private Context context;
    public ChatAdapter(List<String> emails, Context context){
        this.emails = emails;
        this.context = context;

    }
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;

        //адаптеру нужно будет установить эту штуку
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatitem,
                parent, false);
        return new ViewHolder(view);
    }

    private String makeEmailString(String notFullString){

        int index = 0;
        if (notFullString.contains("com")){
            index = notFullString.indexOf("com");
        }

        if (notFullString.contains("ru")){
            index = notFullString.indexOf("ru");
        }

        return notFullString.substring(0, index) + "." + notFullString.substring(index);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String currentEmail = emails.get(position);
        GradientDrawable border = new GradientDrawable();
        border.setCornerRadius(20);
        int color = Color.argb(240, 0, 255, 255);
        border.setColor(color);
        String textButton = currentEmail.substring(7);
        String str = makeEmailString(currentEmail.substring(7));

        holder.chatButton.setText(str);

        System.out.println("ttttttttttttttttttttttttttttttttttttttt          " + holder.chatButton.getText());
        holder.chatButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(textButton);
            }
            else{
                System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
            }
        });

        holder.cardChat.setBackground(border);





    }

    @Override
    public int getItemCount() {
        return emails.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cardChat;
        public Button chatButton;


        public ViewHolder(View itemView) {
            super(itemView);
            chatButton = itemView.findViewById(R.id.buttonChat);
            cardChat = itemView.findViewById(R.id.cardChat);





        }
    }
    public interface OnItemClickListener {
        void onItemClick(String email);
    }
}

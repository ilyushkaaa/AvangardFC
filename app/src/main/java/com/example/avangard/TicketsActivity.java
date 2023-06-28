package com.example.avangard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TicketsActivity extends AppCompatActivity {
    private Fan fan;
    private RecyclerView listOfTickets;
    private List<Ticket> ticketsOfCurrentFan;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private void getData() {
        OnDataReceivedListener listener = new OnDataReceivedListener() {
            @Override
            public void onDataReceived(List<Ticket> data) {
                makeRecyclerView();
            }
        };
        getDataFromDatabase(listener);
    }

    private void makeRecyclerView(){
        TicketsAdapter adapter = new TicketsAdapter(ticketsOfCurrentFan, TicketsActivity.this);
        listOfTickets.setAdapter(adapter);
        listOfTickets.setLayoutManager(new GridLayoutManager(this, 1));
        System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb" + ticketsOfCurrentFan.size());
    }

    private void getDataFromDatabase(final OnDataReceivedListener listener) {
        DatabaseReference ticketsRef = database.getReference("TicketsSouth" +
                stringWithoutDot(fan.getEmail()));


        ticketsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Обработка полученных данных
                for (DataSnapshot ticketSnapshot : dataSnapshot.getChildren()) {
                    // Извлекаем данные из снимка
                    Integer row = ticketSnapshot.child("row").getValue(Integer.class);
                    Integer place = ticketSnapshot.child("place").getValue(Integer.class);
                    Integer sector = ticketSnapshot.child("sector").getValue(Integer.class);
                    String tribuna = ticketSnapshot.child("tribuna").getValue(String.class);
                    Match match = ticketSnapshot.child("match").getValue(Match.class);
                    System.out.println(row + place + sector + "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");


                    // Создаем объект Ticket и добавляем его в список
                    Ticket ticket = new Ticket(match, sector, row, place, tribuna);
                    ticketsOfCurrentFan.add(ticket);
                    System.out.println("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj" + ticketsOfCurrentFan.size());
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ticketsRef = database.getReference("TicketsNorth" +
                stringWithoutDot(fan.getEmail()));


        ticketsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Обработка полученных данных
                for (DataSnapshot ticketSnapshot : dataSnapshot.getChildren()) {
                    // Извлекаем данные из снимка
                    Integer row = ticketSnapshot.child("row").getValue(Integer.class);
                    Integer place = ticketSnapshot.child("place").getValue(Integer.class);
                    Integer sector = ticketSnapshot.child("sector").getValue(Integer.class);
                    String tribuna = ticketSnapshot.child("tribuna").getValue(String.class);
                    Match match = ticketSnapshot.child("match").getValue(Match.class);
                    System.out.println(row + place + sector + "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");


                    // Создаем объект Ticket и добавляем его в список
                    Ticket ticket = new Ticket(match, sector, row, place, tribuna);
                    ticketsOfCurrentFan.add(ticket);
                    System.out.println("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj" + ticketsOfCurrentFan.size());
                }
                listener.onDataReceived(ticketsOfCurrentFan);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);
        Intent intent = getIntent();
        fan = (Fan)intent.getSerializableExtra("fan");
        listOfTickets = findViewById(R.id.listOfTickets);
        ticketsOfCurrentFan = new ArrayList<>();
        getData();
    }
    private String stringWithoutDot(String str){
        String newStr = "";
        int dotIndex = 0;
        for (int i = 0; i < str.length(); ++i){
            if (str.charAt(i) == '.'){
                newStr = str.substring(0, i);
                dotIndex = i;
                break;
            }
        }
        newStr += str.substring(dotIndex + 1);
        return newStr;
    }

}
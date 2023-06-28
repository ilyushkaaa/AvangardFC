package com.example.avangard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatActivitySupport extends AppCompatActivity implements ChatAdapter.OnItemClickListener{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference rootRef = database.getReference();
    private List<String> messagesDataBasePaths;
    private RecyclerView listOfChats;
    int totalChildCount = 0;
    int processedChildCount = 0;
    private Fan fan;



    private void getData() {
        OnDataReceivedListenerChat listener = new OnDataReceivedListenerChat() {
            @Override
            public void onDataReceivedChat(List<String> data) {
                makeRecyclerView();
            }
        };
        getDataFromDatabase(listener);
    }
    private void makeRecyclerView(){
        ChatAdapter adapter = new ChatAdapter(messagesDataBasePaths, ChatActivitySupport.this);
        adapter.setOnItemClickListener(this);
        listOfChats.setAdapter(adapter);
        listOfChats.setLayoutManager(new GridLayoutManager(this, 1));

        //System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb" + ticketsOfCurrentFan.size());
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

    private void getDataFromDatabase(final OnDataReceivedListenerChat listener) {
        rootRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                totalChildCount++;
                String path = dataSnapshot.getKey();
                if (path.startsWith("Support")) {
                    // Обработка пути
                    messagesDataBasePaths.add(path);


                    System.out.println(path);
                }
                processedChildCount++;
                if (processedChildCount == totalChildCount) {
                    // Все дочерние элементы обработаны
                    listener.onDataReceivedChat(messagesDataBasePaths);

                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });







        DatabaseReference ticketsRef = database.getReference(//олжен быть какой то путь
               );


        ticketsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Обработка полученных данных
                for (DataSnapshot ticketSnapshot : dataSnapshot.getChildren()) {
                    // Извлекаем данные из снимка

                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_support);
        Intent intent = getIntent();
        fan = (Fan)intent.getSerializableExtra("fan");
        listOfChats = findViewById(R.id.listOfChats);
        messagesDataBasePaths = new ArrayList<>();
        getData();
        System.out.println(messagesDataBasePaths.size() + "oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");

    }

    @Override
    public void onItemClick(String currentEmail) {
        Intent intent = new Intent(ChatActivitySupport.this, ChatActivity.class);
        intent.putExtra("email", currentEmail);
        intent.putExtra("fan", fan);
        startActivity(intent);
    }

}
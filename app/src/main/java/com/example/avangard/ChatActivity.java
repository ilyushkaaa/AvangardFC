package com.example.avangard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.github.library.bubbleview.BubbleTextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.text.format.DateFormat;

public class ChatActivity extends AppCompatActivity {
    private Fan fan;
    private String emailClient; // для режима входа за поддержку, почта клиента, диалог с которым был открыт
    private final static int SIGN_IN_CODE = 1;
    private FirebaseListAdapter<Message> adapter;
    private FloatingActionButton sendButton;



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_CODE){
            if (resultCode == RESULT_OK){
                displayAllMessages();
            }
            else{
                finish();
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        /*androidx.cardview.widget.CardView card = findViewById(R.id.card2);
        GradientDrawable border = new GradientDrawable();
        border.setCornerRadius(20);
        int color = Color.argb(100, 9, 173, 232);
        border.setColor(color);

        card.setBackground(border);*/


        Intent intent = getIntent();
        fan = (Fan)intent.getSerializableExtra("fan");
        emailClient = (String)intent.getSerializableExtra("email");

        if (FirebaseAuth.getInstance().getCurrentUser() == null){
           startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(),
                   SIGN_IN_CODE);
        }
        else{
            displayAllMessages();
        }



        sendButton = findViewById(R.id.floatingActionButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText messageField = findViewById(R.id.messageField);
                if (messageField.getText().toString().equals("")){
                    return;
                }
                if (!fan.getEmail().equals("support@gmail.com")){
                    FirebaseDatabase.getInstance().getReference().child("Chats"+
                            stringWithoutDot(fan.getEmail())).push().setValue(
                            new Message(fan.getEmail(), "support@gmail.com", messageField.getText().toString())
                    );
                    FirebaseDatabase.getInstance().getReference().child("Support"+
                            stringWithoutDot(fan.getEmail())).push().setValue(
                            new Message(fan.getEmail(), "support@gmail.com", messageField.getText().toString())
                    );
                }
                else{
                    FirebaseDatabase.getInstance().getReference().child("Chats"+
                            emailClient).push().setValue(
                            new Message("support@gmail.com", emailClient, messageField.getText().toString())
                    );
                    FirebaseDatabase.getInstance().getReference().child("Support"+
                            emailClient).push().setValue(
                            new Message("support@gmail.com", emailClient, messageField.getText().toString())
                    );
                }

                messageField.setText("");
            }
        });

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

    private void displayAllMessages() {
        System.out.println("1111111111111111111111111111111111111111111111111111111111111");
        ListView lisOfMessages = findViewById(R.id.listofmessages);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref;
        if (fan.getEmail().equals("support@gmail.com")){
            ref = database.getReference("Chats" + emailClient);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>         Chats" + emailClient);
        }
        else{
            ref = database.getReference("Chats" +
                    stringWithoutDot(fan.getEmail()));
        }


        FirebaseListOptions<Message> options = new FirebaseListOptions.Builder<Message>()
                .setQuery(ref, Message.class)
                .setLayout(R.layout.messageitem)
                .build();

        adapter = new FirebaseListAdapter<Message>(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Message model, int position) {
                System.out.println("sssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
                TextView mess_user = v.findViewById(R.id.message_user);
                TextView mess_time = v.findViewById(R.id.message_time);
                BubbleTextView mess_text = v.findViewById(R.id.message_text);
                BubbleTextView mess_text_me = v.findViewById(R.id.message_text_me);
               // String userName = fan.getName() + " " + fan.getSurname();
                if (model.getSenderEmail().equals(fan.getEmail())){
                    mess_text_me.setText(model.getMessage());
                    mess_text.setVisibility(View.GONE);
                    mess_text_me.setVisibility(View.VISIBLE);
                }
                else{
                    mess_text.setText(model.getMessage());
                    mess_text_me.setVisibility(View.GONE);
                    mess_text.setVisibility(View.VISIBLE);
                }
                mess_user.setText(model.getSenderEmail());
                mess_time.setText(DateFormat.format("dd-mm-yyyy HH:mm:ss", model.getLocalTime()));

            }
        };
        lisOfMessages.setAdapter(adapter);




    }
    @Override
    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop(){
        super.onStop();
        adapter.stopListening();
    }
}
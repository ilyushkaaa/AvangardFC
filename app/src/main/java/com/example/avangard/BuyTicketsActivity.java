package com.example.avangard;

import static android.app.Notification.PRIORITY_HIGH;
import static android.app.Notification.PRIORITY_LOW;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.datatransport.Priority;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BuyTicketsActivity extends AppCompatActivity {
    private Fan fan;
    private Team team1;
    private Team team2;
    private Match match;
    String chosenSector = "";
    List<Button> buttonsOfPlaces;
    private List<Ticket> purchasedTickets;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private NotificationManager notificationManager;
    private static final int NOTIFY_ID = 1;
    private static final String CHANNEL_ID = "CHANNEL_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_tickets);
        Intent intent = getIntent();
        fan = (Fan) intent.getSerializableExtra("fan");
        team1 = (Team) intent.getSerializableExtra("team1");
        team2 = (Team) intent.getSerializableExtra("team2");
        match = (Match) intent.getSerializableExtra("match");
        TextView team1Name = findViewById(R.id.homeTeamTickets);
        TextView team2Name = findViewById(R.id.guestTeamTickets);
        ImageView team1Emblem = findViewById(R.id.imageHomeTickets);
        ImageView team2Emblem = findViewById(R.id.imageGuestTickets);
        team1Name.setText(team1.getName());
        team2Name.setText(team2.getName());
        int imageResourceIdHome = getResources().getIdentifier(
                team1.getEmblemPath(),
                "drawable",
                getPackageName()
        );
        team1Emblem.setImageResource(imageResourceIdHome);
        int imageResourceIdGuest = getResources().getIdentifier(
                team2.getEmblemPath(),
                "drawable",
                getPackageName()
        );
        team2Emblem.setImageResource(imageResourceIdGuest);
        TextView team1City = findViewById(R.id.homeTeamTicketsCity);
        TextView team2City = findViewById(R.id.guestTeamTicketsCity);
        team1City.setText(team1.getCity());
        team2City.setText(team2.getCity());
        notificationManager = (NotificationManager)getApplicationContext().
                getSystemService(Context.NOTIFICATION_SERVICE);

    }

    public void onSouthSectorClick(View view) {


        Button button = (Button) view;
        chosenSector = button.getText().toString();
        purchasedTickets = new ArrayList<>();


        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Покупка билетов");
        LayoutInflater inflater = LayoutInflater.from(this);
        View rg_window = inflater.inflate(R.layout.sectorwindowsouth, null);
        dialog.setView(rg_window);
        GridLayout gridLayout = rg_window.findViewById(R.id.gridLayout);
        gridLayout.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        TextView textSector = rg_window.findViewById(R.id.sectorTribunaSouth);
        String newText = textSector.getText().toString() + " " + chosenSector;
        textSector.setText(newText);


        TextView team1Name = rg_window.findViewById(R.id.homeTeamTicketsSouth);
        TextView team2Name = rg_window.findViewById(R.id.guestTeamTicketsSouth);
        ImageView team1Emblem = rg_window.findViewById(R.id.imageHomeTicketsSouth);
        ImageView team2Emblem = rg_window.findViewById(R.id.imageGuestTicketsSouth);
        team1Name.setText(team1.getName());
        team2Name.setText(team2.getName());
        int imageResourceIdHome = getResources().getIdentifier(
                team1.getEmblemPath(),
                "drawable",
                getPackageName()
        );
        team1Emblem.setImageResource(imageResourceIdHome);
        int imageResourceIdGuest = getResources().getIdentifier(
                team2.getEmblemPath(),
                "drawable",
                getPackageName()
        );
        team2Emblem.setImageResource(imageResourceIdGuest);
        TextView team1City = rg_window.findViewById(R.id.homeTeamTicketsCitySouth);
        TextView team2City = rg_window.findViewById(R.id.guestTeamTicketsCitySouth);
        team1City.setText(team1.getCity());
        team2City.setText(team2.getCity());
        final int normalColor = getResources().getColor(R.color.normal_button_color);
        final int pressedColor = getResources().getColor(R.color.pressed_button_color);

        buttonsOfPlaces = new ArrayList<>();
        DatabaseReference ticketsRef = database.getReference("South" + chosenSector +
                team1.getName()
                + team1.getCity() + team2.getName() + team2.getCity());


        ticketsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Обработка полученных данных
                for (DataSnapshot ticketSnapshot : dataSnapshot.getChildren()) {
                    // Извлекаем данные из снимка
                    Integer row = ticketSnapshot.child("row").getValue(Integer.class);
                    Integer place = ticketSnapshot.child("place").getValue(Integer.class);
                    Integer sector = ticketSnapshot.child("sector").getValue(Integer.class);
                    System.out.println(row + place + sector + "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
                    markBadButton(row, place);

                    // Создаем объект Ticket и добавляем его в список
                    Ticket ticket = new Ticket(match, sector, row, place, "Южная ");
                    purchasedTickets.add(ticket);
                    System.out.println("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj" + purchasedTickets.size());
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        for (int i = 0; i < 100; i++) {
            Button buttonPlace = new Button(this);
            buttonsOfPlaces.add(buttonPlace);
            buttonPlace.setBackgroundColor(normalColor);
            // Настройте кнопку по вашему усмотрению
            buttonPlace.setText(Integer.toString(i % 10 + 1));
            int row = i / 10 + 1;
            int place = i % 10 + 1;

            if (placeIsUnavailable(row, place)) {
                buttonPlace.setEnabled(false);
                final int purchasedColor = getResources().getColor(R.color.purchased_button_color);
                buttonPlace.setBackgroundColor(purchasedColor);
            } else {
                buttonPlace.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (buttonPlace.getBackground() instanceof ColorDrawable) {
                            int currentColor = ((ColorDrawable) buttonPlace.getBackground()).getColor();
                            if (currentColor == normalColor) {
                                buttonPlace.setBackgroundColor(pressedColor);
                            } else {
                                buttonPlace.setBackgroundColor(normalColor);
                            }
                        } else {
                            buttonPlace.setBackgroundColor(normalColor);
                        }

                    }
                });
            }


            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            buttonPlace.setGravity(Gravity.CENTER);
            buttonPlace.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            params.width = getResources().getDimensionPixelSize(R.dimen.button_width);
            params.height = getResources().getDimensionPixelSize(R.dimen.button_height);
            params.setMargins(
                    getResources().getDimensionPixelSize(R.dimen.button_margin_start),
                    getResources().getDimensionPixelSize(R.dimen.button_margin_top),
                    getResources().getDimensionPixelSize(R.dimen.button_margin_end),
                    getResources().getDimensionPixelSize(R.dimen.button_margin_bottom)
            );
            gridLayout.addView(buttonPlace, params);

        }


        dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.setPositiveButton("Купить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                for (int g = 0; g < buttonsOfPlaces.size(); ++g) {
                    int currentColor = ((ColorDrawable) buttonsOfPlaces.get(g).getBackground()).getColor();
                    if (currentColor == pressedColor) {
                        int row = g / 10 + 1;
                        int place = g % 10 + 1;
                        int sector = Integer.parseInt(chosenSector);

                        buyTicketsSouth(row, place, sector);
                    }
                }


            }
        });
        dialog.show();


    }

    private void markBadButton(Integer row, Integer place) {
        for (int i = 0; i < buttonsOfPlaces.size(); ++i) {
            int rowBut = i / 10 + 1;
            int placeBut = i % 10 + 1;
            if (rowBut == row && placeBut == place) {
                buttonsOfPlaces.get(i).setEnabled(false);
                final int purchasedColor = getResources().getColor(R.color.purchased_button_color);
                buttonsOfPlaces.get(i).setBackgroundColor(purchasedColor);
            }
        }
    }

    private boolean placeIsUnavailable(int row, int place) {
        System.out.println(purchasedTickets.size() + "sssssssssssssssiiiiiiiiiiiiizzzzzzzzzzzzzeeeeeeeeeeeeeeeeeeee");
        for (int j = 0; j < purchasedTickets.size(); ++j) {
            if (row == purchasedTickets.get(j).getRow() && place ==
                    purchasedTickets.get(j).getPlace()) {
                return true;

            }
        }
        return false;


    }

    private void buyTicketsSouth(int row, int place, int sector) {
        FirebaseDatabase.getInstance().getReference().child("South" +
                Integer.toString(sector) + team1.getName()
                + team1.getCity() + team2.getName() + team2.getCity()
        ).push().setValue(
                new Ticket(match, sector, row, place, "Южная ")
        );
        FirebaseDatabase.getInstance().getReference().child("TicketsSouth" +
                stringWithoutDot(fan.getEmail())).push().setValue(
                new Ticket(match, sector, row, place, "Южная ")
        );
        String contentText = "Вы приобрели билет на матч " + match.getTeamHome().getName() +
                " " + match.getTeamHome().getCity() + " - " + match.getTeamGuest().getName() +
                " " + match.getTeamGuest().getCity() + " на Южную трибуну, " + sector + " сектор, " +
                row + " ряд, " + place + " место";
        makeNotification(contentText);

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

    private void buyTicketsNorth(int row, int place, int sector) {
        FirebaseDatabase.getInstance().getReference().child("North" +
                Integer.toString(sector) + team1.getName()
                + team1.getCity() + team2.getName() + team2.getCity()
        ).push().setValue(
                new Ticket(match, sector, row, place, "Северная ")
        );
        FirebaseDatabase.getInstance().getReference().child("TicketsNorth" +
                stringWithoutDot(fan.getEmail())).push().setValue(
                new Ticket(match, sector, row, place, "Северная ")
        );
        String contentText = "Вы приобрели билет на матч " + match.getTeamHome().getName() +
                " " + match.getTeamHome().getCity() + " - " + match.getTeamGuest().getName() +
                " " + match.getTeamGuest().getCity() + " на Северную трибуну, " + sector + " сектор, " +
                row + " ряд, " + place + " место";
        makeNotification(contentText);

    }
    private void makeNotification(String contentText){
        Intent intent = new Intent();
        intent.putExtra("team1", team1);
        intent.putExtra("team2", team2);
        intent.putExtra("fan", fan);
        intent.putExtra("match", match);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationCompatBuilder =
                new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        .setAutoCancel(false)
                        .setSmallIcon(R.drawable.avaicon)
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(pendingIntent)
                        .setContentTitle("Произведена покупка билетов")
                        .setContentText(contentText)
                        .setPriority(PRIORITY_HIGH);
        createChannelIfNeeded(notificationManager);
        notificationManager.notify(NOTIFY_ID, notificationCompatBuilder.build());
    }
    public static void createChannelIfNeeded(NotificationManager notificationManager){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public void onNorthSectorClick(View view) {
        Button button = (Button) view;
        chosenSector = button.getText().toString();


        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Покупка билетов");
        LayoutInflater inflater = LayoutInflater.from(this);
        View rg_window = inflater.inflate(R.layout.sectorwindownorth, null);
        dialog.setView(rg_window);
        GridLayout gridLayout = rg_window.findViewById(R.id.gridLayoutNorth);
        gridLayout.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        TextView textSector = rg_window.findViewById(R.id.sectorTribunaNorth);
        String newText = textSector.getText().toString() + " " + chosenSector;
        textSector.setText(newText);


        purchasedTickets = new ArrayList<>();
        TextView team1Name = rg_window.findViewById(R.id.homeTeamTicketsNorth);
        TextView team2Name = rg_window.findViewById(R.id.guestTeamTicketsNorth);
        ImageView team1Emblem = rg_window.findViewById(R.id.imageHomeTicketsNorth);
        ImageView team2Emblem = rg_window.findViewById(R.id.imageGuestTicketsNorth);
        team1Name.setText(team1.getName());
        team2Name.setText(team2.getName());
        int imageResourceIdHome = getResources().getIdentifier(
                team1.getEmblemPath(),
                "drawable",
                getPackageName()
        );
        team1Emblem.setImageResource(imageResourceIdHome);
        int imageResourceIdGuest = getResources().getIdentifier(
                team2.getEmblemPath(),
                "drawable",
                getPackageName()
        );
        team2Emblem.setImageResource(imageResourceIdGuest);
        TextView team1City = rg_window.findViewById(R.id.homeTeamTicketsCityNorth);
        TextView team2City = rg_window.findViewById(R.id.guestTeamTicketsCityNorth);
        team1City.setText(team1.getCity());
        team2City.setText(team2.getCity());
        final int normalColor = getResources().getColor(R.color.normal_button_color);
        final int pressedColor = getResources().getColor(R.color.pressed_button_color);
        buttonsOfPlaces = new ArrayList<>();
        DatabaseReference ticketsRef = database.getReference("North" + chosenSector +
                team1.getName()
                + team1.getCity() + team2.getName() + team2.getCity());


        ticketsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("ccccccccccccccccccccccccccccccccccccccccccccccccccccccccc");
                // Обработка полученных данных
                for (DataSnapshot ticketSnapshot : dataSnapshot.getChildren()) {
                    // Извлекаем данные из снимка
                    System.out.println("ппппппппппппппппппппппппппппппппппппппппппппппппппп");
                    Integer row = ticketSnapshot.child("row").getValue(Integer.class);
                    Integer place = ticketSnapshot.child("place").getValue(Integer.class);
                    Integer sector = ticketSnapshot.child("sector").getValue(Integer.class);
                    System.out.println(row + "dddddddddddddddddddddddddd");
                    markBadButton(row, place);
                    Ticket ticket = new Ticket(match, sector, row, place, "Северная ");
                    purchasedTickets.add(ticket);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        for (int i = 0; i < 100; i++) {
            Button buttonPlace = new Button(this);
            buttonsOfPlaces.add(buttonPlace);
            buttonPlace.setBackgroundColor(normalColor);
            // Настройте кнопку по вашему усмотрению
            buttonPlace.setText(Integer.toString(i % 10 + 1));
            int row = i / 10 + 1;
            int place = i % 10 + 1;

            if (placeIsUnavailable(row, place)) {
                buttonPlace.setEnabled(false);
                final int purchasedColor = getResources().getColor(R.color.purchased_button_color);
                buttonPlace.setBackgroundColor(purchasedColor);
            } else {
                buttonPlace.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (buttonPlace.getBackground() instanceof ColorDrawable) {
                            int currentColor = ((ColorDrawable) buttonPlace.getBackground()).getColor();
                            if (currentColor == normalColor) {
                                buttonPlace.setBackgroundColor(pressedColor);
                            } else {
                                buttonPlace.setBackgroundColor(normalColor);
                            }
                        } else {
                            buttonPlace.setBackgroundColor(normalColor);
                        }

                    }
                });
            }

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            buttonPlace.setGravity(Gravity.CENTER);
            buttonPlace.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            params.width = getResources().getDimensionPixelSize(R.dimen.button_width);
            params.height = getResources().getDimensionPixelSize(R.dimen.button_height);
            params.setMargins(
                    getResources().getDimensionPixelSize(R.dimen.button_margin_start),
                    getResources().getDimensionPixelSize(R.dimen.button_margin_top),
                    getResources().getDimensionPixelSize(R.dimen.button_margin_end),
                    getResources().getDimensionPixelSize(R.dimen.button_margin_bottom)
            );
            gridLayout.addView(buttonPlace, params);
        }

        dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.setPositiveButton("Купить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                for (int g = 0; g < buttonsOfPlaces.size(); ++g) {
                    int currentColor = ((ColorDrawable) buttonsOfPlaces.get(g).getBackground()).getColor();
                    if (currentColor == pressedColor) {
                        int row = g / 10 + 1;
                        int place = g % 10 + 1;
                        int sector = Integer.parseInt(chosenSector);

                        buyTicketsNorth(row, place, sector);
                    }
                }


            }
        });
        dialog.show();
    }

    public void onBadButtonClick(View view) {
        Toast.makeText(BuyTicketsActivity.this, "Сектор недоступен", Toast.LENGTH_LONG)
                .show();
    }

}
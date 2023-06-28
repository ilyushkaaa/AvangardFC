package com.example.avangard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity implements MatchesAdapter.OnItemClickListener {
    private Fan fan;
    private List<Match> futureMatches;
    private List<Team> teams;
    private RecyclerView listOfMatches;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        TextView twName = findViewById(R.id.namesurname);
        Intent intent = getIntent();
        fan = (Fan)intent.getSerializableExtra("fan");
        String text = fan.getName() + " " + fan.getSurname();
        twName.setText(text);
        TextView twPhone = findViewById(R.id.phonenum);
        twPhone.setText(fan.getPhone());
        TextView twEmail = findViewById(R.id.email);
        twEmail.setText(fan.getEmail());
        displayFutureMatches();
    }
    private void displayFutureMatches(){
        futureMatches = new ArrayList<>();
        teams = new ArrayList<>();
        addTeams();
        addMatches();
        listOfMatches = findViewById(R.id.listOfMatches);
        MatchesAdapter adapter = new MatchesAdapter(futureMatches, MenuActivity.this);
        adapter.setOnItemClickListener(this);
        listOfMatches.setAdapter(adapter);
        listOfMatches.setLayoutManager(new GridLayoutManager(this, 1));




    }
    private void addMatches(){
        futureMatches.add(createMatch(teams.get(0), teams.get(1), 0, 0, false));
        futureMatches.add(createMatch(teams.get(0), teams.get(2), 0, 0, false));
        futureMatches.add(createMatch(teams.get(0), teams.get(3), 0, 0, false));
        futureMatches.add(createMatch(teams.get(0), teams.get(4), 0, 0, false));
    }
    private void addTeams(){
        teams.add(createTeam("Авангард", "Курск", "ava"));
        teams.add(createTeam("Салют", "Белгород", "salyut"));
        teams.add(createTeam("Сокол", "Саратов", "sokol"));
        teams.add(createTeam("Динамо", "Брянск", "dynamobryansk"));
        teams.add(createTeam("Металлург", "Липецк", "metallurg"));
        teams.add(createTeam("Родина-2", "Москва", "rodina"));
        teams.add(createTeam("Калуга", "Калуга", "kaluga"));
        teams.add(createTeam("Динамо", "Владивосток", "dynamovladivostok"));
        teams.add(createTeam("Рязань", "Рязань", "ryazan"));
        teams.add(createTeam("Космос", "Догопрудный", "kosmos"));
        teams.add(createTeam("Сатурн", "Раменское", "saturn"));
        teams.add(createTeam("Сахалинец", "Москва", "sakhalinets"));
    }
    private Team createTeam(String name, String city, String emblemPath){
        return new Team(name, city, emblemPath);
    }
    private Match createMatch(Team teamHome, Team teamGuest, int teamHomeGoals, int teamGuestGoals,
                              boolean isOver){
        return new Match(teamHome, teamGuest, teamHomeGoals, teamGuestGoals, isOver);
    }

    public void onResultsClick(View view){
        Intent intent = new Intent(MenuActivity.this, ResultsActivity.class);
        startActivity(intent);

    }
    public void onTableClick(View view){
        Intent intent = new Intent(MenuActivity.this, TableActivity.class);
        startActivity(intent);

    }
    public void onChatClick(View view){
        Intent intent = new Intent(MenuActivity.this, ChatActivity.class);
        intent.putExtra("fan", fan);

        startActivity(intent);


    }
    public void onTicketsClick(View view){
        Intent intent = new Intent(MenuActivity.this, TicketsActivity.class);
        intent.putExtra("fan", fan);
        startActivity(intent);


    }

    @Override
    public void onItemClick(Team team1, Team team2, Match item) {
        Intent intent = new Intent(MenuActivity.this, BuyTicketsActivity.class);
        intent.putExtra("team1", team1);
        intent.putExtra("team2", team2);
        intent.putExtra("fan", fan);
        intent.putExtra("match", item);
        startActivity(intent);
    }
}
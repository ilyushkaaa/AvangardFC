package com.example.avangard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {
    private RecyclerView listOfResults;
    private List<Match> futureMatches;
    private List<Team> teams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        listOfResults = findViewById(R.id.listOfResults);
        displayMatches();


    }
    private void displayMatches(){
        futureMatches = new ArrayList<>();
        teams = new ArrayList<>();
        addTeams();
        addMatches();
        listOfResults = findViewById(R.id.listOfResults);
        ResultsAdapter adapter = new ResultsAdapter(futureMatches, ResultsActivity.this);
        listOfResults.setAdapter(adapter);
        listOfResults.setLayoutManager(new GridLayoutManager(this, 1));



    }
    private void addMatches(){
        futureMatches.add(createMatch(teams.get(0), teams.get(5), 1, 1, true));
        futureMatches.add(createMatch(teams.get(5), teams.get(0), 1, 2, true));
        futureMatches.add(createMatch(teams.get(0), teams.get(6), 0, 1, true));
        futureMatches.add(createMatch(teams.get(6), teams.get(0), 2, 1, true));
        futureMatches.add(createMatch(teams.get(0), teams.get(7), 1, 1, true));
        futureMatches.add(createMatch(teams.get(7), teams.get(0), 0, 2, true));
        futureMatches.add(createMatch(teams.get(0), teams.get(8), 2, 0, true));
        futureMatches.add(createMatch(teams.get(8), teams.get(0), 1, 1, true));
        futureMatches.add(createMatch(teams.get(0), teams.get(9), 1, 0, true));
        futureMatches.add(createMatch(teams.get(9), teams.get(0), 0, 2, true));
        futureMatches.add(createMatch(teams.get(0), teams.get(10), 3, 0, true));
        futureMatches.add(createMatch(teams.get(10), teams.get(0), 2, 4, true));
        futureMatches.add(createMatch(teams.get(0), teams.get(11), 0, 0, true));
        futureMatches.add(createMatch(teams.get(11), teams.get(0), 1, 7, true));


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
    public void onCurrentMatchClick(View view){
        //переход на протокол матча

    }
}
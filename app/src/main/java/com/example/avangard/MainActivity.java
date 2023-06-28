package com.example.avangard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

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

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private EditText editEmail;
    private EditText editPassword;
    private EditText newEmail;
    private EditText newPassword;
    private EditText newName;
    private EditText newSurname;
    private EditText newNumber;
    private RelativeLayout root;
    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference users;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        androidx.cardview.widget.CardView card = findViewById(R.id.card);
        GradientDrawable border = new GradientDrawable();
        //border.setColor(); // цвет фона

        border.setCornerRadius(20);
        int color = Color.argb(240, 255, 255, 255);
        border.setColor(color);

        card.setBackground(border);
        root = findViewById(R.id.root_element);
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");


        // устанавливаем фон

    }

    private String stringToSHA2(String input){
        try {
            // Создаем экземпляр MessageDigest с алгоритмом SHA-256
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");

            // Преобразуем строку в массив байтов и вычисляем хэш
            byte[] hash = sha256.digest(input.getBytes());

            // Преобразуем хэш в шестнадцатеричную строку
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    public void onGoOnClick(View view){
        editPassword = findViewById(R.id.password_field);
        editEmail = findViewById(R.id.email_field);

        String shaPass = stringToSHA2(editPassword.getText().toString());
        auth.signInWithEmailAndPassword(editEmail.getText().toString(), shaPass)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        String userId = authResult.getUser().getUid();
                        users = FirebaseDatabase.getInstance().getReference("Users");

                        users.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    if (snapshot.child("email").getValue(String.class).equals(editEmail.getText().toString())){
                                        Toast.makeText(MainActivity.this, "Выполнен вход",
                                                Toast.LENGTH_SHORT).show();
                                        // Получение данных пользователя
                                        String firstName = snapshot.child("name").getValue(String.class);
                                        String lastName = snapshot.child("surname").getValue(String.class);
                                        String hexPass = snapshot.child("hexPassword").getValue(String.class);
                                        String email = snapshot.child("email").getValue(String.class);
                                        String phone = snapshot.child("phone").getValue(String.class);

                                        Fan fan = new Fan(firstName, lastName, hexPass, phone, email);
                                        Intent intent;
                                        if(editEmail.getText().toString().equals("support@gmail.com")){
                                            intent = new Intent(MainActivity.this, ChatActivitySupport.class);
                                        }
                                        else{
                                            intent = new Intent(MainActivity.this, MenuActivity.class);
                                        }
                                        intent.putExtra("fan", fan);
                                        startActivity(intent);
                                    }


                                }

                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Данные введены неверно" +
                                e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });





    }

    public void onSignUpClick(View view) {


        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Регистрация");
        dialog.setMessage("Введите данные для регистрации");
        LayoutInflater inflater = LayoutInflater.from(this);
        View rg_window = inflater.inflate(R.layout.register_window, null);
        dialog.setView(rg_window);


        newEmail = rg_window.findViewById(R.id.email_field_new);
        newPassword = rg_window.findViewById(R.id.pass_field);
        newName = rg_window.findViewById(R.id.name_field);
        newSurname = rg_window.findViewById(R.id.surname_field);
        newNumber = rg_window.findViewById(R.id.phone_field);

        dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.setPositiveButton("Зарегистрироваться", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (badEmail(newEmail)){
                    return;

                }
                if (badPhone(newNumber)){
                    return;

                }
                if (badPassword(newPassword)){
                    return;

                }
                if (TextUtils.isEmpty(newName.getText().toString())){
                    Snackbar.make(root, "Введите ваше имя", Snackbar.LENGTH_SHORT).show();
                    return;

                }
                if (TextUtils.isEmpty(newSurname.getText().toString())){
                    Snackbar.make(root, "Введите вашу фамилию", Snackbar.LENGTH_SHORT).show();
                    return;

                }
                auth.createUserWithEmailAndPassword(newEmail.getText().toString(),
                        stringToSHA2(newPassword.getText().toString())).addOnSuccessListener(
                        new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Fan newFan = new Fan(newName.getText().toString(), newSurname.getText().toString(),
                                        stringToSHA2(newPassword.getText().toString()), newNumber.getText().toString(),
                                        newEmail.getText().toString());
                                users.child("Users").setValue(newFan).
                                        addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(MainActivity.this, "Вы успешно зарегистрировались!",
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(MainActivity.this, "Пользователь" +
                                                                "с таким адресом электронной почты уже" +
                                                                "зарегистрирован!",
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        });
                            }
                        }
                );



            }
        });
        dialog.show();

    }
    private boolean badEmail(EditText email){
        if (TextUtils.isEmpty(email.getText().toString())){
            Snackbar.make(root, "Введите вашу почту", Snackbar.LENGTH_SHORT).show();
            return true;

        }
        return false;
    }
    private boolean badPassword(EditText pass){
        if (TextUtils.isEmpty(pass.getText().toString()) || pass.getText().toString().
                length() < 8){
            Snackbar.make(root, "Введите пароль длиной не менее 8 символов",
                    Snackbar.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }
    private boolean badPhone(EditText phone){
        if (TextUtils.isEmpty(phone.getText().toString())){
            Snackbar.make(root, "Введите ваш номер телефона", Snackbar.LENGTH_SHORT).show();
            return true;

        }

        return false;
    }

}
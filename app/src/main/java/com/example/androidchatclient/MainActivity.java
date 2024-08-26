package com.example.androidchatclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    static String roomNameKey = "room-name";
    static String userNameKey ="user-name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void handleBtnClicked(View view){
        TextInputEditText userInput = findViewById(R.id.userNameInput);
        TextInputEditText roomInput = findViewById(R.id.roomNameInput);
        String userName = userInput.getText().toString();
        String roomName = roomInput.getText().toString();

        Button loginBtn = findViewById(R.id.login);
        Intent intent = new Intent(this,ChatRoom.class);
        intent.putExtra(roomNameKey,roomName);
        intent.putExtra(userNameKey,userName);
        startActivity(intent);
    }
}
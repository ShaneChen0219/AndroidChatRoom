package com.example.androidchatclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketFactory;

import java.io.IOException;
import java.util.ArrayList;

public class ChatRoom extends AppCompatActivity {
    public static final String  tags = "ChatRoom";
    private static final String WS_URL = "ws://10.0.2.2:8080/endpoint";
    public static ArrayList<String> messages =new ArrayList<>();
    public static WebSocket ws = null;

    public static ListView msg;
    public static ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        TextView room = findViewById(R.id.ChatRoomName);
        TextView user = findViewById(R.id.userNameGot);
        msg = findViewById(R.id.chatBoxInput);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            room.setText(extras.getString(MainActivity.roomNameKey));
            user.setText(extras.getString(MainActivity.userNameKey));
        }

        try {
            ws = new WebSocketFactory().createSocket(WS_URL);
            //listen for event and will use this class to implement them
            ws.addListener(new MyWebsocket(user.getText().toString(), room.getText().toString()));
            ws.connectAsynchronously();

            adapter = new ArrayAdapter<>(msg.getContext(), android.R.layout.simple_list_item_1,messages);
            msg.setAdapter(adapter);



        } catch (IOException e) {
            //AlertDialog alert = new AlertDialog("Server failed");
            Log.d(tags, "some error");
        }
    }

    public void handleClick(View view){
        TextView user = findViewById(R.id.userNameGot);

        Button sendBtn = findViewById(R.id.sendBtn);
        TextInputEditText messageInput = findViewById(R.id.message);
        String msg = messageInput.getText().toString();
        messageInput.setText("");
        if (MyWebsocket.wsOpen) {
            ws.sendText("message " +msg);
        } else {
            Log.d(tags, "WebSocket connection is not open");
        }
    }


}
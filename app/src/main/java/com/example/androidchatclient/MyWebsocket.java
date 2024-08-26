package com.example.androidchatclient;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyWebsocket extends WebSocketAdapter {
    public String  tags = "MyWebSocket";
    static boolean wsOpen = false;
    private String user;
    private String room;

    public  static ArrayList<String> m = new ArrayList<>();
    public MyWebsocket(String user, String room) {
        this.user = user;
        this.room = room;
    }

    @Override
    public synchronized void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception
    {
        wsOpen = true;
        Log.d(tags,"Open Socket");
        System.out.println(1);
        websocket.sendText("join "+user+" "+room);
    }

    @Override
    public synchronized void onTextMessage(WebSocket websocket, String message) throws Exception
    {

//        String message = new String(data, "UTF-8"); // Convert the byte data to a string
        try {
            Log.d(tags,"got");
            JSONObject jsonMessage = new JSONObject(message);
            String messageType = jsonMessage.getString("type");
            Log.d(tags,messageType);
            String user = jsonMessage.getString("user");
            String room = jsonMessage.getString("room");
            Log.d(tags,room);


            // Handle the message based on its type
            if ("message".equals(messageType)) {
                String messageContent = user + " : " + jsonMessage.getString("message");
                Log.d(tags,messageContent);
                ChatRoom.messages.add(messageContent);
                //ArrayAdapter adapter = new ArrayAdapter<>(ChatRoom.msg.getContext(), android.R.layout.simple_list_item_1,ChatRoom.messages);
                //ChatRoom.msg.setAdapter(adapter);
                ChatRoom.msg.post(new Runnable() {
                    @Override
                    public void run() {
                        ChatRoom.adapter.notifyDataSetChanged();
                        ChatRoom.msg.smoothScrollToPosition(ChatRoom.adapter.getCount()-1);
                    }
                });
                // Process the chat message here
                Log.d(tags, "Received message from user " + user + " in room " + room + ": " + messageContent);
                Log.d(tags,ChatRoom.messages.toString());
                // You can update your UI to display the received message or perform other actions.
            } else {
                Log.d(tags, "Received an unsupported message type: " + messageType);
            }
        } catch (JSONException e) {
            Log.e(tags, "Error parsing JSON message: " + e.getMessage());
        }
    }









}

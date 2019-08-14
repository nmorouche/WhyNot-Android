package com.example.why_not_android.views;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.why_not_android.R;
import com.example.why_not_android.data.Models.MemberData;
import com.example.why_not_android.data.Models.Message;
import com.example.why_not_android.data.SharedPreferences.SharedPref;
import com.example.why_not_android.data.adapter.MessageAdapter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaledrone.lib.Listener;
import com.scaledrone.lib.Room;
import com.scaledrone.lib.RoomListener;
import com.scaledrone.lib.Scaledrone;
import com.scaledrone.lib.SubscribeOptions;

import java.util.Random;

public class MessageActivity extends AppCompatActivity {

    private String channelID = "3pmgszkbyzy483lR";
    private String roomName = "observable-room";
    private EditText editText;
    private Scaledrone scaledrone;
    private MessageAdapter messageAdapter;
    private ListView messagesView;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        sharedPreferences = SharedPref.getInstance(this);
        editText = (EditText) findViewById(R.id.editText);

        messageAdapter = new MessageAdapter(this);
        messagesView = (ListView) findViewById(R.id.list_messages);
        messagesView.setAdapter(messageAdapter);

        MemberData data = new MemberData(sharedPreferences.getString("username", ""), getRandomColor());

        scaledrone = new Scaledrone(channelID, data);
        scaledrone.connect(new Listener() {
            @Override
            public void onOpen() {
                System.out.println("Scaledrone connection open");
                scaledrone.subscribe(roomName, new RoomListener() {
                    @Override
                    public void onOpen(Room room) {
                        room.listenToHistoryEvents((room1, receivedMessage) -> {
                            final ObjectMapper mapper = new ObjectMapper();
                            try {
                                final MemberData data = mapper.treeToValue(receivedMessage.getMember().getClientData(), MemberData.class);
                                boolean belongsToCurrentUser = receivedMessage.getClientID().equals(scaledrone.getClientID());
                                final Message message1 = new Message(receivedMessage.getData().asText(), data, belongsToCurrentUser);
                                runOnUiThread(() -> {
                                    messageAdapter.add(message1);
                                    messagesView.setSelection(messagesView.getCount() - 1);
                                });
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void onOpenFailure(Room room, Exception ex) {

                    }

                    @Override
                    public void onMessage(Room room, com.scaledrone.lib.Message message) {
                        final ObjectMapper mapper = new ObjectMapper();
                        try {
                            final MemberData data = mapper.treeToValue(message.getMember().getClientData(), MemberData.class);
                            boolean belongsToCurrentUser = message.getClientID().equals(scaledrone.getClientID());
                            final Message message1 = new Message(message.getData().asText(), data, belongsToCurrentUser);
                            runOnUiThread(() -> {
                                messageAdapter.add(message1);
                                messagesView.setSelection(messagesView.getCount() - 1);
                            });
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    }
                }, new SubscribeOptions(50));
            }

            @Override
            public void onOpenFailure(Exception ex) {
                System.err.println(ex);
            }

            @Override
            public void onFailure(Exception ex) {
                System.err.println(ex);
            }

            @Override
            public void onClosed(String reason) {
                System.err.println(reason);
            }
        });
    }

    public void sendMessage(View v) {
        String message = editText.getText().toString();
        if (message.length() > 0) {
            scaledrone.publish(roomName, message);
            editText.getText().clear();
        }
    }

    private String getRandomColor() {
        Random r = new Random();
        StringBuffer sb = new StringBuffer("#");
        while (sb.length() < 7) {
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, 7);
    }
}

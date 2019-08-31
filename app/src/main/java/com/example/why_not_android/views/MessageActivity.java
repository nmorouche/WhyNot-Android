package com.example.why_not_android.views;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.why_not_android.R;
import com.example.why_not_android.data.Models.MemberData;
import com.example.why_not_android.data.Models.Message;
import com.example.why_not_android.data.Models.User;
import com.example.why_not_android.data.SharedPreferences.SharedPref;
import com.example.why_not_android.data.adapter.MessageAdapter;
import com.example.why_not_android.data.dto.FBMNotificationDTO;
import com.example.why_not_android.data.dto.FBMPushNotificationDTO;
import com.example.why_not_android.data.dto.FirebaseTokenDTO;
import com.example.why_not_android.data.dto.MessageDTO;
import com.example.why_not_android.data.service.ChatService;
import com.example.why_not_android.data.service.FirebaseService;
import com.example.why_not_android.data.service.providers.NetworkProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaledrone.lib.Listener;
import com.scaledrone.lib.Member;
import com.scaledrone.lib.Room;
import com.scaledrone.lib.RoomListener;
import com.scaledrone.lib.Scaledrone;
import com.scaledrone.lib.SubscribeOptions;

import java.util.List;
import java.util.Random;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageActivity extends AppCompatActivity {

    private final String channelID = "3pmgszkbyzy483lR";
    private String roomName;
    private EditText editText;
    private Scaledrone scaledrone;
    private MessageAdapter messageAdapter;
    private ListView messagesView;
    private SharedPreferences sharedPreferences;
    public static User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        sharedPreferences = SharedPref.getInstance(this);
        roomName = getIntent().getExtras().getString("roomName", "");
        setUserInformations();
        editText = (EditText) findViewById(R.id.editText);
        messageAdapter = new MessageAdapter(this);
        messagesView = (ListView) findViewById(R.id.list_messages);
        messagesView.setAdapter(messageAdapter);
        MemberData data = new MemberData(getIntent().getExtras().getString("myID"), sharedPreferences.getString("username", ""));
        getMessageHistory();
        scaledrone = new Scaledrone(channelID, data);
        scaledrone.connect(new Listener() {
            @Override
            public void onOpen() {
                scaledrone.subscribe(roomName, new RoomListener() {
                    @Override
                    public void onOpen(Room room) {

                    }

                    @Override
                    public void onOpenFailure(Room room, Exception ex) {

                    }

                    @Override
                    public void onMessage(Room room, com.scaledrone.lib.Message message) {
                        boolean belongsToCurrentUser = message.getClientID().equals(scaledrone.getClientID());
                        final Message message1 = new Message(message.getData().asText(), belongsToCurrentUser);
                        runOnUiThread(() -> {
                            messageAdapter.add(message1);
                            messagesView.setSelection(messagesView.getCount() - 1);
                        });
                    }
                });
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
            sendNotification();
            sendMessageToServer(message);
        }
    }

    private void sendNotification() {
        FirebaseService firebaseService;
        firebaseService = NetworkProvider.getClient().create(FirebaseService.class);
        FBMNotificationDTO fbmNotificationDTO = new FBMNotificationDTO(getString(R.string.activity_message_send_firebase_notification_title), getString(R.string.activity_message_send_firebase_notification_body));
        Call<FirebaseTokenDTO> firebaseTokenDTOCall = firebaseService.getFirebaseToken(sharedPreferences.getString("token", ""), user.get_id());
        firebaseTokenDTOCall.enqueue(new Callback<FirebaseTokenDTO>() {
            @Override
            public void onResponse(Call<FirebaseTokenDTO> call, Response<FirebaseTokenDTO> response) {
                if (response.isSuccessful()) {
                    FirebaseTokenDTO firebaseTokenDTO = response.body();
                    FBMPushNotificationDTO fbmPushNotificationDTO = new FBMPushNotificationDTO(firebaseTokenDTO.getFirebaseToken(), fbmNotificationDTO);
                    Call<ResponseBody> responseCall = firebaseService.sendNotification("key=" + getString(R.string.firebaseID), fbmPushNotificationDTO);
                    responseCall.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<FirebaseTokenDTO> call, Throwable t) {

            }
        });
    }

    private void setUserInformations() {
        Bundle extras = getIntent().getExtras();
        user.set_id(extras.getString("userid"));
        user.setUsername(extras.getString("userName"));
        user.setBirthdate(extras.getString("userBirth"));
        user.setPhoto(extras.getString("userPic"));
        user.setBio(extras.getString("userBio"));
    }

    private void getMessageHistory() {
        ChatService chatService;
        chatService = NetworkProvider.getClient().create(ChatService.class);
        Call<List<MessageDTO>> listCall = chatService.getMessages(sharedPreferences.getString("token", ""), user.get_id());
        listCall.enqueue(new Callback<List<MessageDTO>>() {
            @Override
            public void onResponse(Call<List<MessageDTO>> call, Response<List<MessageDTO>> response) {
                if (response.isSuccessful()) {
                    List<MessageDTO> list = response.body();
                    for (MessageDTO messageDTO : list) {
                        boolean belongsToCurrentUser = messageDTO.getUser1().equals(getIntent().getExtras().getString("myID"));
                        final Message message1 = new Message(messageDTO.getMessage(), belongsToCurrentUser);
                        runOnUiThread(() -> {
                            messageAdapter.add(message1);
                            messagesView.setSelection(messagesView.getCount() - 1);
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<List<MessageDTO>> call, Throwable t) {
                Toast.makeText(MessageActivity.this, "CA MARCHE AP", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendMessageToServer(String message) {
        MessageDTO messageDTO = new MessageDTO(user.get_id(), message);
        ChatService chatService;
        chatService = NetworkProvider.getClient().create(ChatService.class);
        Call<ResponseBody> responseBodyCall = chatService.insertMessage(sharedPreferences.getString("token", ""), messageDTO);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(MessageActivity.this, "CA MARCHE ;)", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MessageActivity.this, "CA MARCHE PAS", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

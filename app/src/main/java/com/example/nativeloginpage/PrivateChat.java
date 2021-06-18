package com.example.nativeloginpage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nativeloginpage.activities.OpponentsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class PrivateChat extends AppCompatActivity {

    private static String chatId, chatImage, chatName;
    private EditText sendMessage;
    private Context mContext;
    private ScrollView scrollView;
    private static boolean isHangedUp;
    private static boolean isOutgoing;

    private final ArrayList<String> mChatId = new ArrayList<>();
    private final ArrayList<String> mChatContent = new ArrayList<>();

    private final Socket mSocket;
    {
        try {
            // mSocket = IO.socket("http://faceapp.vindana.com.au/api/v1/faceapp/conversation");
            mSocket = IO.socket("http://35.247.160.195");
//            mSocket = IO.socket("http://facetest.evokemusic.net:6001/");
            Log.i("TEST2","Socket initialize: http://35.247.160.195");
        } catch (URISyntaxException e) {
            Log.i("TEST2","Error in socket initialize" + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_chat);
        Objects.requireNonNull(getSupportActionBar()).hide();

        setIsHangedUp(false);
        setIsOutgoing(false);
        mContext = getApplicationContext();
        Button btnBack = findViewById(R.id.btnBack);
        Button btnSend = findViewById(R.id.btnSend);
        Button btnVideo = findViewById(R.id.btnVideo);
        CircleImageView civ = findViewById(R.id.chat_private_header_image);
        scrollView = findViewById(R.id.svMessages);

        OpponentsActivity.setCallerId(126815134); //set opponent chat id
        Intent intent = getIntent();
        if(intent.hasExtra("ChatId")){
            chatId = intent.getStringExtra("ChatId");
            chatName = intent.getStringExtra("ChatName");
            chatImage = intent.getStringExtra("ChatImage");
            Log.i("TEST2", chatId + chatName + chatImage);
        }

        TextView txtChatName = findViewById(R.id.txtChatName);
        sendMessage = findViewById(R.id.txtMessageSend);
        txtChatName.setText(chatName);

        Glide.with(mContext)
                .asBitmap()
                .load(chatImage)
                .into(civ);

        initPrivateChat();
        scrollFix();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PrivateChat.this, Tabs.class).putExtra("From", "Chat"));
                finish();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = sendMessage.getText().toString();
                if(msg != null && !msg.trim().equals("")){
                    mChatId.add(Tabs.getProfileId());
                    mChatContent.add(msg);
                    initRecycleView();

                    attemptSend();
                }
                scrollFix();
                new Handler().postDelayed(() -> sendMessage.requestFocus(), 100);
            }
        });
        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChatId.add("2");
                mChatContent.add("Dummy test added");
                initRecycleView();
                scrollFix();
                new Handler().postDelayed(() -> sendMessage.requestFocus(), 100);
//                setIsOutgoing(true);
//                Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
//                startActivity(intent);
            }
        });

        mSocket.connect();
        mSocket.on("textMessage", onNewMessage);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
        mSocket.off("textMessage", onNewMessage);
    }

    private void initPrivateChat(){
        mChatId.add(Tabs.getProfileId());
        mChatContent.add("Hi");

        mChatId.add("2");
        mChatContent.add("Hi");

        mChatId.add(Tabs.getProfileId());
        mChatContent.add("How are you?");

        mChatId.add("2");
        mChatContent.add("I'm ok");

        mChatId.add(Tabs.getProfileId());
        mChatContent.add("AsyncTask enables proper and easy use of the UI thread. This class allows you to perform background operations and publish results on the UI thread without having to manipulate threads and/or handlers.");

        mChatId.add("2");
        mChatContent.add("Where are you?");

        mChatId.add(Tabs.getProfileId());
        mChatContent.add("Hi there");

        mChatId.add("2");
        mChatContent.add("Are you ok?");

        mChatId.add(Tabs.getProfileId());
        mChatContent.add("Hi");

        mChatId.add("2");
        mChatContent.add("Where are you?");

        mChatId.add(Tabs.getProfileId());
        mChatContent.add("Hi there");

        mChatId.add("2");
        mChatContent.add("Are you ok?");

        initRecycleView();
    }

    private void initRecycleView(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvPrivateChat);
        AdapterChatContent adapter = new AdapterChatContent(mChatId, mChatContent);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
    }

    private void closeKeyboard(){
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(mContext.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    private void scrollFix(){
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    public static boolean getIsHangedUp() {
        return isHangedUp;
    }

    public static void setIsHangedUp(boolean isHangedUp) { PrivateChat.isHangedUp = isHangedUp; }

    public static boolean getIsOutgoing() { return isOutgoing; }

    public static void setIsOutgoing(boolean isOutgoing) { PrivateChat.isOutgoing = isOutgoing; }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PrivateChat.this, Tabs.class).putExtra("From", "Chat"));
        finish();
    }

    private void attemptSend() {
        String fbId = Tabs.getProfileId();
        String message = sendMessage.getText().toString().trim();
        String endId = chatId;
        sendMessage.setText(null);
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("fbId", fbId);
            jsonObject.put("message", message);
            jsonObject.put("endId", endId);
            Log.i("TEST2", "Data collected");
        }catch (Exception e){
            Log.i("TEST2", "Error: " + e);
        }

        if (TextUtils.isEmpty(message)) {
            return;
        }

        mSocket.emit("textMessage", jsonObject);
    }

    private final Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            Log.i("TEST2", "Message received");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                     String msgFrom = "Chamara";
                    String message;
                    try {
                        msgFrom = data.getString("endId");
                        message = data.getString("message");
                    } catch (JSONException e) {
                        Log.i("TEST2", "Exception in onNewMessage " + e);
                        return;
                    }
                    Log.i("TEST2", "received " + data);

                    mChatId.add(msgFrom);
                    mChatContent.add(message);

                    initRecycleView();
                    scrollFix();
                }
            });
        }
    };


}
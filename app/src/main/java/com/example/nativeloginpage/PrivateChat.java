package com.example.nativeloginpage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.nativeloginpage.activities.SplashActivity;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class PrivateChat extends AppCompatActivity {

    private Button btnBack, btnSend, btnVideo;
    private static String chatId, chatImage;
    private Intent intent;
    private CircleImageView civ;
    private TextView chatName;
    private EditText sendMessage;
    private Context mContext;
    private ScrollView scrollView;
    private static boolean isHangedUp;
    private static boolean isOutgoing;

    private ArrayList<String> mChatId = new ArrayList<>();
    private ArrayList<String> mChatContent = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_chat);
        Objects.requireNonNull(getSupportActionBar()).hide();

        setIsHangedUp(false);
        setIsOutgoing(false);
        mContext = getApplicationContext();
        btnBack = findViewById(R.id.btnBack);
        btnSend = findViewById(R.id.btnSend);
        btnVideo = findViewById(R.id.btnVideo);
        civ = findViewById(R.id.chat_private_header_image);
        scrollView = findViewById(R.id.svMessages);

        OpponentsActivity.setCallerId(126815134); //set opponent chat id
        intent = getIntent();
        if(intent.hasExtra("ChatId")){
            chatId = intent.getStringExtra("ChatId");
            chatImage = intent.getStringExtra("ChatImage");
        }

        chatName = findViewById(R.id.txtChatName);
        sendMessage = findViewById(R.id.txtMessageSend);
        chatName.setText(chatId);

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
                    mChatId.add("1");
                    mChatContent.add(msg);
                    initRecycleView();
                    sendMessage.setText(null);
                }
                scrollFix();
                new Handler().postDelayed(() -> sendMessage.requestFocus(), 100);
            }
        });
        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mChatId.add("2");
//                mChatContent.add("Dummy test added");
//                initRecycleView();
//                scrollFix();
//                new Handler().postDelayed(() -> sendMessage.requestFocus(), 100);
                setIsOutgoing(true);
                Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initPrivateChat(){
        mChatId.add("1");
        mChatContent.add("Hi");

        mChatId.add("2");
        mChatContent.add("Hi");

        mChatId.add("1");
        mChatContent.add("How are you?");

        mChatId.add("2");
        mChatContent.add("I'm ok");

        mChatId.add("1");
        mChatContent.add("AsyncTask enables proper and easy use of the UI thread. This class allows you to perform background operations and publish results on the UI thread without having to manipulate threads and/or handlers.");

        mChatId.add("2");
        mChatContent.add("Where are you?");

        mChatId.add("1");
        mChatContent.add("Hi there");

        mChatId.add("2");
        mChatContent.add("Are you ok?");

        mChatId.add("1");
        mChatContent.add("Hi");

        mChatId.add("2");
        mChatContent.add("Where are you?");

        mChatId.add("1");
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
}
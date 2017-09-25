package com.example.vivek.myapplication_chat;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    ArrayList<ChatMessage> messages = new ArrayList<>();
    ChatAdapter adapter;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        Button buttonSend = (Button) findViewById(R.id.buttonSend);
        final EditText chatText = (EditText) findViewById(R.id.chatText);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        adapter = new ChatAdapter(messages);
        recyclerView.setAdapter(adapter);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String submitMsg = chatText.getText().toString();
                chatText.setText("");
                if (!submitMsg.trim().isEmpty()) {
                    ChatMessage message = new ChatMessage();
                    message.setMessage(submitMsg);
                    messages.add(message);
                    adapter.setList(messages);
                    adapter.notifyItemInserted(messages.size() - 1);
                    recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
                    submitChatMessage(message);
                }

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(MySingelton.getInstance(getApplicationContext()).getRequestQueue()!=null)
            MySingelton.getInstance(getApplicationContext()).getRequestQueue().cancelAll(MainActivity.class.getSimpleName());
    }

    public void submitChatMessage(ChatMessage message) {
        String apikey = "6nt5d1nJHkqbkphe";
        int chatBotId = 63906;
        String externalId = "chirag1";
        String url = "https://www.personalityforge.com/api/chat/?apiKey=" + apikey + "&message=" + message + "&chatBotID=" + chatBotId
                + "&externalID=" + externalId;

        VolleyWrapper.getJsonObjectFromServer(getApplicationContext(), url, Request.Method.GET, null, MainActivity.class.getSimpleName(), handler);
    }

    JSONObjectResponseHandler handler = new JSONObjectResponseHandler() {

        @Override
        public void onResponse(JSONObject response) {
            try {
                int success = response.getInt("success");
                if(success ==0) {
                    String errorMessage = response.getString("errorMessage");
                    showSnackbar(errorMessage, false);
                }
                else {
                    JSONObject message = response.getJSONObject("message");
                    Gson gson = new Gson();
                    Type type = new TypeToken<ChatMessage>(){}.getType();
                    ChatMessage chatMessage = gson.fromJson(message.toString(), type);
                    chatMessage.setLeft(true);
                    messages.add(chatMessage);
                    adapter.setList(messages);
                    adapter.notifyItemInserted(messages.size() - 1);
                    recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            showSnackbar("Oops...Something went wrong", false);
        }
    };

    public void showSnackbar(String msg, boolean success) {
        Snackbar snackbar = Snackbar.make(linearLayout, msg, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        if(success)
            snackbarView.setBackgroundColor(linearLayout.getResources().getColor(R.color.green));
        else
            snackbarView.setBackgroundColor(linearLayout.getResources().getColor(R.color.error_red));
        TextView textView = (TextView) snackbarView.findViewById(R.id.snackbar_text);
        textView.setMaxLines(3);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }
}

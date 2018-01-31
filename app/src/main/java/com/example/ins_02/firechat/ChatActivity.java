package com.example.ins_02.firechat;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "ChatActivity";

    public String name, email, userId, currentuserId;
    private Context context;
    private TextView chat_name;
    private EditText msg;
    private FloatingActionButton floatingActionButton;
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Message> messageList;
    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        context=this;
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            currentuserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        userId = getIntent().getStringExtra("userid");



        FirebaseDatabase database = FirebaseDatabase.getInstance();

        databaseReference = database.getReference();




        chat_name = findViewById(R.id.app_bar_name);
        msg = findViewById(R.id.send_box);

        chat_name.setText(name);

        Log.d(TAG, "onCreate: " + name + "  " + email+" "+currentuserId+" "+userId);

        recyclerView=findViewById(R.id.rv_chat);
        RecyclerView.LayoutManager manager=new GridLayoutManager(context,1);
        recyclerView.setLayoutManager(manager);

        messageList=new ArrayList<>();
        messageAdapter=new MessageAdapter(messageList,context,userId);

        recyclerView.setAdapter(messageAdapter);

        floatingActionButton = findViewById(R.id.send);

        loadMoreMessages();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = msg.getText().toString();

                SendMessage(message);
            }
        });

    }

    private void SendMessage(String m) {

        if (!TextUtils.isEmpty(m)) {
            String current_user_ref = "messages/" + currentuserId + "/" + userId;
            String chat_user = "messages/" + userId + "/" + currentuserId;

            DatabaseReference userMessges = databaseReference.child("messages").child(currentuserId).child(userId).push();

            String push_id = userMessges.getKey();

            Map messageMap = new HashMap();
            messageMap.put("message", m);
            messageMap.put("from",userId);
            messageMap.put("username",name);


            Map messageUserMap = new HashMap();

            messageUserMap.put(current_user_ref + "/" + push_id, messageMap);

            messageUserMap.put(chat_user + "/" + push_id, messageMap);

            msg.setText("");

            databaseReference.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {


                    if (databaseError != null) {
                        Log.d(TAG, "onComplete: " + databaseError.getMessage().toString());
                    }

                }
            });

        }
    }

    private void loadMoreMessages() {

        DatabaseReference messageRef = databaseReference.child("messages").child(currentuserId).child(userId);

       messageRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {




               for (DataSnapshot ds:dataSnapshot.getChildren())

               {
                   Message message = ds.getValue(Message.class);
                   String m= (String) ds.child("message").getValue();
                   String u= (String) ds.child("from").getValue();
                   String n= (String) ds.child("username").getValue();

                   message.setFrom(u);
                   message.setMessage(m);
                   message.setUserName(n);

                   messageList.add(message);

               }
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });

    }

}

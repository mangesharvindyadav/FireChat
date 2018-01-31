package com.example.ins_02.firechat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private DatabaseReference myref;
    private FirebaseAuth mAuth;
    private Context context;
    private List<User> userList;
    private RecyclerView recyclerView;
    private UserAdapter adapter;
    public static interface ClickListener{
        void onclick(View view,int position);
        void onLongclick(View view,int position);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context=this;

        FirebaseDatabase database=FirebaseDatabase.getInstance();

        myref=database.getReference().child("users");
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser()==null)
        {
            startActivity(new Intent(context,LoginActivity.class));
            finish();
        }


        recyclerView=findViewById(R.id.rv_user);
        RecyclerView.LayoutManager manager=new GridLayoutManager(context,1);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(context,0));


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context, recyclerView, new ClickListener() {
            @Override
            public void onclick(View view, int position) {

                Intent intent=new Intent(context,ChatActivity.class);
                intent.putExtra("name",userList.get(position).getName());
                intent.putExtra("email",userList.get(position).getEmail());
                intent.putExtra("userid",userList.get(position).getUserId());
                startActivity(intent);

            }

            @Override
            public void onLongclick(View view, int position) {

            }
        }));


        getAlluser();
    }




    private void getAlluser()
    {
             myref.addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(DataSnapshot dataSnapshot) {

                userList=new ArrayList<>();

                    for (DataSnapshot ds:dataSnapshot.getChildren()) {
                         User user = dataSnapshot.getValue(User.class);
                        String userId= (String)ds.child("userId").getValue();
                        String name = (String) ds.child("name").getValue();
                        String email= (String) ds.child("email").getValue();




                        userList.add(new User(name,email,userId));
                        Log.d(TAG, "onDataChange: "+userId);
                     }

                     adapter = new UserAdapter(userList, context);
                     recyclerView.setAdapter(adapter);
                 }

                 @Override
                 public void onCancelled(DatabaseError databaseError) {

                 }
             });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.logout:
                mAuth.signOut();
                startActivity(new Intent(context,LoginActivity.class));
                finish();

        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }
}

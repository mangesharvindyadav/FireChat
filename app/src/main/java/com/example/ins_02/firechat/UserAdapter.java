package com.example.ins_02.firechat;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ins-02 on 29/1/18.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.Myholder> {

    private List<User> userList;
    private Context context;

    public UserAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @Override
    public Myholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.single_user_row,parent,false);

        return new Myholder(view);
    }

    @Override
    public void onBindViewHolder(Myholder holder, int position) {

        User user=userList.get(position);

        holder.textView.setText(user.getName());
    }

    @Override
    public int getItemCount() {
        return userList==null ? 0:userList.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {

        ImageView pic;
        TextView textView;

        public Myholder(View itemView) {
            super(itemView);


            pic=itemView.findViewById(R.id.profile_pic);
            textView=itemView.findViewById(R.id.name);
        }
    }
}

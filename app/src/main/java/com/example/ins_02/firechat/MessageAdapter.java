package com.example.ins_02.firechat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ins-02 on 30/1/18.
 */

public class MessageAdapter extends RecyclerView.Adapter {

    private List<Message> messageList;
    private Context context;
    private String CurrentId;

    public MessageAdapter(List<Message> messageList, Context context, String currentId) {
        this.messageList = messageList;
        this.context = context;
        CurrentId = currentId;

        Log.d("", "Adapter "+currentId);
    }


    public static class SendViewHolder extends RecyclerView.ViewHolder{

        TextView tv_s;
        public SendViewHolder(View itemView) {
            super(itemView);
            tv_s=itemView.findViewById(R.id.s_m);

        }
    }

    public static class ReceiverViewHolder extends RecyclerView.ViewHolder{

        TextView tv_r;
        public ReceiverViewHolder(View itemView) {
            super(itemView);
            tv_r=itemView.findViewById(R.id.r_m);

        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;



        switch (viewType)
        {
            case 0:
                view=LayoutInflater.from(context).inflate(R.layout.sender,parent,false);
                return new SendViewHolder(view);

            case 1:
                view=LayoutInflater.from(context).inflate(R.layout.receiver,parent,false);
                return new ReceiverViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
          Message message=messageList.get(position);

        Log.d("Adapter", "onBindViewHolder: "+messageList.get(position).getFrom().equals(CurrentId));;
          if (message!=null)
          {
              switch (check(position))
              {
                  case 0:
                      ((SendViewHolder)holder).tv_s.setText(message.getMessage());
                      break;

                  case 1:
                      ((ReceiverViewHolder)holder).tv_r.setText(message.getMessage());
                      break;
              }
          }



    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    private int check(int p)
    {
        if (messageList.get(p).getFrom().equals(CurrentId))
        {
            return 0;
        }
       else {
          return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {

        switch (check(position))
        {
            case 1:
                return 1;

            case 0:
                return 0;

            default:
                return -1;

        }

    }

    @Override
    public int getItemCount() {
        return messageList==null?0:messageList.size();
    }
}

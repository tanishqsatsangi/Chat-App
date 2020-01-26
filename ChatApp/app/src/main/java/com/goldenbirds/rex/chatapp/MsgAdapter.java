package com.goldenbirds.rex.chatapp;

import android.content.Context;
import android.graphics.Color;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.viewHolder> {
    ArrayList<Message> msglist;
    Context mcontext;
    String Deviceid="";

    public  MsgAdapter(Context context,ArrayList<Message> list){
        mcontext=context;
        msglist=list;
        Deviceid=android.provider.Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Message currentmsg=msglist.get(position);
        if(currentmsg.getId().equals(Deviceid)){
       //     holder.chatlayout.setBackgroundColor(mcontext.getResources().getColor(R.color.backgroundsendermsgcolor));
        //    holder.chatlayout.setGravity(Gravity.RIGHT);
         holder.username.setText("You");
         holder.txtmsg.setText(currentmsg.getMsg());
        }
        else{
            holder.username.setText("Anonymous");
            holder.txtmsg.setText(currentmsg.getMsg());
        }
    }

    @Override
    public int getItemCount() {
        Log.i("Adaptrarray","size"+msglist.size());
        return msglist.size();
    }

   public class viewHolder extends RecyclerView.ViewHolder{
            TextView username,txtmsg;
            LinearLayout chatlayout;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.username);
            txtmsg=itemView.findViewById(R.id.textmessage);
            chatlayout=itemView.findViewById(R.id.chatlayout);
        }
    }
}

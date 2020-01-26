package com.goldenbirds.rex.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Snapshot;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    DatabaseReference msgdbreference;
ArrayList<Message> messagearray=new ArrayList<>();
String DeviceID="";
    String msg="";
    RecyclerView msglist;
    EditText textmsg;
    Button sendbtn;
//adview
private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
     // adview
        mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        msgdbreference= FirebaseDatabase.getInstance().getReference("Message/roomid_1");
    msglist=findViewById(R.id.msgview);
    msglist.setHasFixedSize(true);
    msglist.setLayoutManager(new LinearLayoutManager(this));
    msgdbreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messagearray.clear();
                for(DataSnapshot postsnapshot : dataSnapshot.getChildren()){
                    Message download=postsnapshot.getValue(Message.class);
                    Log.i("uploadmessage","snapshot "+postsnapshot);
                    messagearray.add(download);
                }
                for(int i=0;i<messagearray.size();i++){
                    Log.i("arraylist",i+" "+messagearray.get(i).getMsg());
                }
                    MsgAdapter adapter=new MsgAdapter(getApplicationContext(),messagearray);
                msglist.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.i("valueerrorchatapp","error:"+databaseError.getMessage());
            }
        });
        DeviceID=android.provider.Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        textmsg=findViewById(R.id.mssgtxtbox);
        sendbtn=findViewById(R.id.sendbtn);
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg=textmsg.getText().toString().trim();
                textmsg.getText().clear();
                if(msg.equals("") || msg.equals(" ")){
                    Toast.makeText(getApplicationContext(),"Please type a msg",Toast.LENGTH_SHORT).show();
                }
                else{
                   msgdbreference.push().setValue(new Message(msg,DeviceID,"tine"));
                   Log.i("chatapp_msgsent","msg : "+msg);
                }

            }
        });
    }

}

package com.goldenbirds.rex.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
//adview component

    private AdView mAdView;
    // Database components
    DatabaseReference searchreference;
    String deviceid,gender="any";
    //UI Components
    Button startbtn;
    TextView usercount;
    RadioButton malebtn,femalebtn,anybtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //adview to display admob ads
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        getusercount();
       startbtn=findViewById(R.id.startbtn);
        malebtn=findViewById(R.id.male);
        femalebtn=findViewById(R.id.female);
        anybtn=findViewById(R.id.any);
        usercount=findViewById(R.id.activeusers);
        deviceid=android.provider.Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        searchreference= FirebaseDatabase.getInstance().getReference("searchlist");

        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(malebtn.isChecked()){
                    gender="male";
                }
                if(femalebtn.isChecked()){
                    gender="female";
                }
                if(anybtn.isChecked()){
                    gender="any";
                }
                if(!malebtn.isChecked() && !femalebtn.isChecked() && !anybtn.isChecked()){
                    gender="any";
                }

               SearchList sl=new SearchList(deviceid,gender);
               searchreference.push().setValue(sl);
               findingmatch();
               Intent intent=new Intent(MainActivity.this,ChatActivity.class);
                startActivity(intent);
            }
        });
    }

    void getusercount(){
        DatabaseReference referencesearch=FirebaseDatabase.getInstance().getReference("searchlist");
     referencesearch.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
         long count= dataSnapshot.getChildrenCount();
             usercount.setText("Online Users :"+count);

         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {

         }
     });

    }
    void findingmatch(){
        final ArrayList<SearchList> searcharray=new ArrayList<>();
        final DatabaseReference referencesearch=FirebaseDatabase.getInstance().getReference("searchlist");

        referencesearch.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String id="";
                for(DataSnapshot postsnapshot : dataSnapshot.getChildren()){
                    SearchList upload=postsnapshot.getValue(SearchList.class);
                    searcharray.add(upload);
                    Log.i("uploadgender","gender"+upload.getGender());
                    Log.i("uploaddevice","id"+upload.getDeviceID());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
   getusercount();
    }
}


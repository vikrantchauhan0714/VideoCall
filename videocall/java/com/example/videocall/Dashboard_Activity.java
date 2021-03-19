package com.example.videocall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class Dashboard_Activity extends AppCompatActivity {
    private EditText codebox;
    private Button join , share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_);
        codebox=findViewById(R.id.codebox);
        join=findViewById(R.id.joinbtn);
        share=findViewById(R.id.sharebtn);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,codebox.getText().toString());
                intent.setType("text/plain");
                Intent.createChooser(intent,"Share via");
                startActivity(intent);
            }
        });

        URL serverURL;

        try {
            serverURL=new URL("https://meet.jit.si");

            JitsiMeetConferenceOptions defaultoption = new JitsiMeetConferenceOptions.Builder()

                     .setServerURL(serverURL)
                    .setWelcomePageEnabled(false)
                    .build();
            JitsiMeet.setDefaultConferenceOptions(defaultoption);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          JitsiMeetConferenceOptions options=new JitsiMeetConferenceOptions.Builder()
                   .setRoom(codebox.getText().toString())

                   .setWelcomePageEnabled(false)

                   .build();
                JitsiMeetActivity.launch(Dashboard_Activity.this, options);
            }
        });
    }
}

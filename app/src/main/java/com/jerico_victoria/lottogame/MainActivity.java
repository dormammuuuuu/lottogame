package com.jerico_victoria.lottogame;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    EditText username;
    Button submit;
    String User;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText)findViewById(R.id.username);
        submit = (Button)findViewById(R.id.submit_name);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                User = username.getText().toString();
                Intent i = new Intent(MainActivity.this, Game.class);
                i.putExtra("user", User);
                startActivity(i);
                finish();
            }
        });
    }
}
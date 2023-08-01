package com.project.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class feedback extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        EditText edit1=(EditText) findViewById(R.id.edit1);
        EditText edit2=(EditText) findViewById(R.id.edit2);
        Button btn=(Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_SEND);
                i.setType("message/html");
                i.putExtra(Intent.EXTRA_EMAIL,new String[]{"bharat.dhiman112@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT,"Feedback From App");
                i.putExtra(Intent.EXTRA_TEXT, "Name: "+edit1.getText()+"\n Message: "+ edit2.getText());
                try {
                    startActivity(Intent.createChooser(i,  "Please select Email"));

                }
                catch(android.content.ActivityNotFoundException ex)
                {
                    Toast.makeText(feedback.this, "There are no Email Clients", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
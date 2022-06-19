package com.example.projek_akhir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText ednama;
    Button btnmasuk;
    String nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnmasuk = findViewById(R.id.button);
        ednama = findViewById(R.id.ednama);

        btnmasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nama = ednama.getText().toString();

                if (ednama.getText().toString().length()==0) {
                    ednama.setError("nama diperlukan!!!");
                }

                Intent i = new Intent(MainActivity.this,hasil.class);
                startActivity(i);


            }
        });
    }
}
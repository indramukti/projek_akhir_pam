package com.example.projek_akhir;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

public class Detail extends AppCompatActivity {
    private TextView nama, nohp, date, pilih;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        nama = findViewById(R.id.nama);
        nohp = findViewById(R.id.nohp);
        date = findViewById(R.id.date);
        pilih = findViewById(R.id.pilih);

        progressDialog = new ProgressDialog(Detail.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Mengambil data...");

        Intent intent = getIntent();
        if(intent != null){
            nama.setText(intent.getStringExtra("nama"));
            nohp.setText(intent.getStringExtra("nohp"));
            date.setText(intent.getStringExtra("date"));
            pilih.setText(intent.getStringExtra("pilih"));
        }
    }
}
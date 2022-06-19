package com.example.projek_akhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class transaksi extends AppCompatActivity {
    /**
     *   Mendefinisikan variable yang akan dipakai
     */
    private EditText nama, nohp, date, pilih;
    private Button btnnext;
    /**
     *  Inisialisai firestore
     */
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaksi);

        btnnext = findViewById(R.id.btnnext);
        nama = findViewById(R.id.nama);
        nohp = findViewById(R.id.nohp);
        date = findViewById(R.id.date);
        pilih = findViewById(R.id.pilih);

        progressDialog = new ProgressDialog(transaksi.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Menyimpan...");

        btnnext.setOnClickListener(view -> {
            if (nama.getText().length() > 0 && nohp.getText().length() > 0 && date.getText().length() > 0 && pilih.getText().length() > 0 ){

                savedata(nama.getText().toString(), nohp.getText().toString(), date.getText().toString(), pilih.getText().toString());
            }else {
                Toast.makeText(this, "Silahkan isi semua data", Toast.LENGTH_SHORT).show();
            }
        });
        Intent intent = getIntent();
        if (intent!= null){
            id = intent.getStringExtra("id");
            nama.setText(intent.getStringExtra("nama"));
            nohp.setText(intent.getStringExtra("nohp"));
            date.setText(intent.getStringExtra("date"));
            pilih.setText(intent.getStringExtra("pilih"));
        }
    }
    private  void  savedata(String nama,String nohp,String date,String pilih) {
        Map<String, Object> user = new HashMap<>();
        user.put("nama", nama);
        user.put("nohp", nohp);
        user.put("date", date);
        user.put("pilih", pilih);

        progressDialog.show();

        if (id != null) {
            db.collection("users").document(id)
                    .set(user)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(transaksi.this, "Berhasil", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(transaksi.this, "Gagal", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }else {
            db.collection("users")
                    .add(user)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(transaksi.this, "Berhasil di simpan", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        Toast.makeText(transaksi.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }

    }
}
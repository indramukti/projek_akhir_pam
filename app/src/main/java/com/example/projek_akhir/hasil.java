package com.example.projek_akhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.projek_akhir.adapter.UserAdapter;
import com.example.projek_akhir.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class hasil extends AppCompatActivity {
    /**
     *  Mendefinisikan variable yang akan di pakai
     */
    private RecyclerView recyclerView;
    private FloatingActionButton btnAdd;
    /**
     *  inisialisasi object firebase firestore
     *  untuk menghubungkan dengan firestore
     */
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private List<User> list = new ArrayList<>();
    private UserAdapter UserAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);
        recyclerView = findViewById(R.id.recycler_view);
        btnAdd = findViewById(R.id.btn_add);

        progressDialog = new ProgressDialog(hasil.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Mengambil data...");
        UserAdapter = new UserAdapter(getApplicationContext(),  list);
        UserAdapter.setDialog(new UserAdapter.Dialog() {
            @Override
            public void onClick(int pos) {
                final CharSequence[] dialogItem = {"Edit", "Hapus","Lihat Data"};
                AlertDialog.Builder diaglog = new AlertDialog.Builder(hasil.this);
                diaglog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            /**
                             *  Melemparkan data ke class berikutnya
                             */
                            case 0:
                                Intent intent = new Intent(getApplicationContext(),transaksi.class);
                                intent.putExtra("id", list.get(pos).getId());
                                intent.putExtra("nama", list.get(pos).getName());
                                intent.putExtra("nohp", list.get(pos).getNohp());
                                intent.putExtra("date", list.get(pos).getDate());
                                intent.putExtra("pilih", list.get(pos).getPilihan());
                                startActivity(intent);
                                break;
                            case 1:
                                /**
                                 * memanggil class delete data
                                 */
                                deleteData(list.get(pos).getId());
                                break;
                            case 2:
                                Intent intent1 = new Intent(getApplicationContext(), Detail.class);
                                intent1.putExtra("id", list.get(pos).getId());
                                intent1.putExtra("nama", list.get(pos).getName());
                                intent1.putExtra("nohp", list.get(pos).getNohp());
                                intent1.putExtra("date", list.get(pos).getDate());
                                intent1.putExtra("pilih", list.get(pos).getPilihan());
                                startActivity(intent1);
                                break;
                        }
                    }
                });
                diaglog.show();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(UserAdapter);

        btnAdd.setOnClickListener(v ->{
            startActivity(new Intent(getApplicationContext(), transaksi.class));
        });
    }
    /**
     * method untuk menampilkan data agar di tampilkan
     * pada saat aplikasi pertama kali di running
     */

    @Override
    protected void onStart(){
        super.onStart();
        getData();
    }

    private void getData() {
        progressDialog.show();
        /**
         * Mengambil data dari firestore
         */
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDateSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        if (task.isSuccessful()){
                            /**
                             *  Code ini mengambil data dari collection
                             */
                            for(QueryDocumentSnapshot document : task.getResult()){
                                /**
                                 * Data Apa saja yang ingin diambil dari collection
                                 */
                                User user = new User(document.getString("nama"), document.getString("nohp"), document.getString("date"), document.getString("pilih"));
                                user.setId(document.getId());
                                list.add(user);
                            }
                            UserAdapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(hasil.this, "Data Gagal di ambil!!!", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    /**
     *  method untuk menghapus data
     */
    private void  deleteData(String id){
        progressDialog.show();
        db.collection("users").document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(hasil.this, "Data Gagal di hapus", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                        getData();
                    }
                });
    }

}
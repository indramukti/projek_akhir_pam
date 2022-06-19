package com.example.projek_akhir.model;

public class User {
    private  String id, nama, nohp, date, pilih;

    public User(){

    }
    public User(String nama, String nohp, String date, String pilih){
        this.nama =  nama;
        this.nohp = nohp;
        this.date =date;
        this.pilih = pilih;

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return nama;
    }

    public void setName(String nama) {
        this.nama = nama;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPilihan() {
        return pilih;
    }

    public void setPilihan(String pilih) {
        this.pilih = pilih;
    }

}

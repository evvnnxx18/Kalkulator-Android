package com.made06.kalkulator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends Activity {

   private int [] nobtn = {
           R.id.btnsatu,R.id.btndua,R.id.btntiga,R.id.btnempat,
           R.id.btnlima,R.id.btnenam,R.id.btntujuh,R.id.btndelapan,R.id.btnsembilan,R.id.btnnol,R.id.btntik
   };
    private int [] opbtn = {
            R.id.btntambah,R.id.btnkali,R.id.btnmin,R.id.btnbagi
    };

    private TextView layar;
    private boolean noakhir;
    private boolean eerror;
    private boolean titik;
    int nilai;
    double hasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.layar = (TextView) findViewById(R.id.layar);
        // Find and set OnClickListener to numeric buttons
        setNomor();
        setOperasi();

    }



    private void setNomor() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button) v;
                if (eerror){
                    layar.setText(btn.getText());
                    eerror = false;
                }else{
                    layar.append(btn.getText());
                }
                noakhir = true;
            }
        };
        for(int id : nobtn){
            findViewById(id).setOnClickListener(listener);
        }
    }

    //operasi
    private void setOperasi() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noakhir && !eerror){
                    Button btn = (Button) v;
                    layar.append(btn.getText());
                    noakhir = false;
                    titik  = false;
                }
            }
        };
        for (int id: opbtn){
            findViewById(id).setOnClickListener(listener);
        }

        //titik
        findViewById(R.id.btntik).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noakhir && !eerror && !titik){
                    layar.append(".");
                    noakhir = false;
                    titik = true;
                }
            }
        });

        //clear
//        findViewById(R.id.btnbersih).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                layar.setText(" ");
//                noakhir = false;
//                eerror = false;
//                titik = false;
//            }
//        });

        //sama dengan
        findViewById(R.id.btnhasil).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHasil();
            }
        });

        //hapus
        findViewById(R.id.btnhapus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDel();
            }
        });

    }

    private void onDel() {
        String str = layar.getText().toString();
        if (!str.equals("")){
            str = str.substring(0,str.length()-1);
            layar.setText(str);
        }


    }

    //tampil hasil jika koma / error
    private void onHasil() {
        if (noakhir && !eerror){
            String str = layar.getText().toString();
            Expression exp = new ExpressionBuilder(str).build();

            try{
                hasil = exp.evaluate();
//                layar.setText(Double.toString(hasil));
//                titik = true;
            }catch (ArithmeticException ex){
                layar.setText("Error");
                eerror = true;
                noakhir = false;
            }

            nilai = (int) hasil;

            if (nilai == hasil){
                layar.setText(String.valueOf((int)hasil));
            }else{
                layar.setText(String.valueOf(hasil));
            }


        }else{
            Toast.makeText(MainActivity.this, "Masukkan Angka", Toast.LENGTH_SHORT).show();
        }
    }

}


package com.example.kh.externalstorage;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kh.externalstorage.Module.externalHelper;
import com.example.kh.externalstorage.ViewHolder.viewHolder_main;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "data flow";
    com.example.kh.externalstorage.ViewHolder.viewHolder_main holder;
    private static String filename="congvinh.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            return;
        };
        init();
    }

    private void init() {
        holder = new viewHolder_main();
        holder.etdata = (EditText) findViewById(R.id.etdata);
        holder.txtdata = (TextView) findViewById(R.id.txtdata);
        holder.btnexternalstorage = (Button) findViewById(R.id.btnexternalstorage);
        holder.btnshowdata = (Button) findViewById(R.id.btnshowdata);
        holder.btnDeleteData = (Button) findViewById(R.id.btndeletedata);
        holder.btnDeleteData.setOnClickListener(onclicklistener);
        holder.btnshowdata.setOnClickListener(onclicklistener);
        holder.btnexternalstorage.setOnClickListener(onclicklistener);
    }

    private void requestWriteExternalStoragePermission() {
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Inform and request")
                    .setMessage("You need to enable permissions, bla bla bla")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        }
                    })
                    .show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {

                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {

                    Log.i(TAG, "Permission has been denied by user");

                } else {

                    Log.i(TAG, "Permission has been granted by user");

                }
                return;
            }
        }
    }

    private View.OnClickListener onclicklistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btndeletedata:

                  Boolean a=  com.example.kh.externalstorage.Module.externalHelper.getInstance().deleteData(filename);
                    if(a)
                        Toast.makeText(MainActivity.this, "successful", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MainActivity.this, "fail", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btnexternalstorage:
                    Toast.makeText(MainActivity.this, externalHelper.getInstance().a, Toast.LENGTH_SHORT).show();
                    try {

                        com.example.kh.externalstorage.Module.externalHelper.getInstance().setData(holder.etdata.getText().toString().trim(),filename);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case  R.id.btnshowdata:

                    try {

                            String data = com.example.kh.externalstorage.Module.externalHelper.getInstance().getData(filename);
                        Log.i("data", "onClick: "+data);
                            holder.txtdata.setText(data);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
}

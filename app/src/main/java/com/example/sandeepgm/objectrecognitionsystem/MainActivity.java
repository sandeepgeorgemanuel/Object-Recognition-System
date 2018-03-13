package com.example.sandeepgm.objectrecognitionsystem;

import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scan=findViewById(R.id.bt_scan);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent s=new Intent(MainActivity.this,ScanActivity.class);
                startActivity(s);

            }
        });
    }
}

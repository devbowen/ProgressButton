package com.even.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.even.progressbutton.ProgressButton;
import com.even.progressbutton.R;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private ProgressButton progressButton;
    private Button loadBtn;
    private Button stopBtn;
    private Button succcessBtn;
    private Button failBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressButton = findViewById(R.id.btn_progress);
        loadBtn = findViewById(R.id.btn_load);
        stopBtn = findViewById(R.id.btn_stop);
        succcessBtn = findViewById(R.id.btn_success);
        failBtn = findViewById(R.id.btn_fail);

        progressButton.setOnClickListener(this);
        loadBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
        succcessBtn.setOnClickListener(this);
        failBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_progress:
                Toast.makeText(this, "仅作为展示", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_load:
                progressButton.startLoad();
                break;
            case R.id.btn_stop:
                progressButton.stopLoad();
                break;
            case R.id.btn_success:
                progressButton.loadSuccess();
                break;
            case R.id.btn_fail:
                progressButton.loadFail();
                break;
            default:
                break;
        }
    }
}

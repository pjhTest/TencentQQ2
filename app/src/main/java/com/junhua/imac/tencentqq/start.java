package com.junhua.imac.tencentqq;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class start extends AppCompatActivity {

    private final  int SPLASH_DISPLAY_LENGTH =2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainTntent =new Intent(start.this, MainActivity.class);
                start.this.startActivity(mainTntent);
                start.this.finish();

            }
        },SPLASH_DISPLAY_LENGTH);
    }
}

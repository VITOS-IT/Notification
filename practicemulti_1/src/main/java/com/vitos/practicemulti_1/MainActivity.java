package com.vitos.practicemulti_1;

import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.Space;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button red, yellow, green, onOff;
    Boolean check=false,start = false;
    Handler h;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        red = (Button)findViewById(R.id.buttonRed);
        yellow = (Button)findViewById(R.id.buttonYellow);
        green= (Button)findViewById(R.id.buttonGreen);
        onOff = (Button)findViewById(R.id.onOff);
        onOff.setOnClickListener(this);

        h = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case 1:
                        green.setBackgroundColor(Color.GREEN);
                        break;
                    case 2:
                        green.setBackgroundColor(Color.GREEN);
                        yellow.setBackgroundColor(Color.YELLOW);
                        break;
                    case 3:
                        green.setBackgroundColor(Color.WHITE);
                        yellow.setBackgroundColor(Color.WHITE);
                        red.setBackgroundColor(Color.RED);
                        break;
                    case 4:
                        red.setBackgroundColor(Color.RED);
                        yellow.setBackgroundColor(Color.YELLOW);
                        break;
                    case 5:
                        red.setBackgroundColor(Color.WHITE);
                        yellow.setBackgroundColor(Color.WHITE);

                        break;
                    case 0:
                        green.setBackgroundColor(Color.WHITE);
                        yellow.setBackgroundColor(Color.WHITE);
                        red.setBackgroundColor(Color.WHITE);
                        onOff.setEnabled(true);
                        onOff.setText("ввімкнути");
                        break;
                }
            };
        };

    }

    @Override
    public void onClick(View v) {

        if (!check) {
            check = true;

            onOff.setText("вимкнути");
            if (!start) {
                start = true;

                Thread t = new Thread(new Runnable() {
                    public void run() {
                        try {
                            while (start) {
                                if (check) h.sendEmptyMessage(1);
                                else h.sendEmptyMessage(0);
                                Thread.sleep(7000);
                                if (check) h.sendEmptyMessage(2);
                                else h.sendEmptyMessage(0);
                                Thread.sleep(3000);
                                if (check) h.sendEmptyMessage(3);
                                else h.sendEmptyMessage(0);
                                Thread.sleep(7000);
                                if (check) h.sendEmptyMessage(4);
                                else h.sendEmptyMessage(0);
                                Thread.sleep(3000);
                                if (check) h.sendEmptyMessage(5);
                                else h.sendEmptyMessage(0);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                t.start();

            }
        }
        else {
            check = false;
            onOff.setEnabled(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        start = false;
    }
}

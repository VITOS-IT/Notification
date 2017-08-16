package com.vitos.practicemulti_2;


import android.content.ClipData;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity{
    ProgressBar pb;
    MyTask mt;
    TextView tvInfo;
    Button btnPou;

    boolean pouseD=false;
    int status=0;
    final Random random = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb=(ProgressBar)findViewById(R.id.progress);
        pb.setVisibility(ProgressBar.VISIBLE);
        tvInfo = (TextView)findViewById(R.id.textView);
        mt = (MyTask) getLastNonConfigurationInstance();
        btnPou = (Button)findViewById(R.id.pouse);
    }
    public void onclick(View v) {
        switch (v.getId()) {
            case R.id.prepare:
                if (status==0) {

                    mt = new MyTask();
                    status=1;
                }
                break;
            case R.id.load:
                if (mt == null || status!=1) break;
                mt.execute();
                status=2;

                break;
            case R.id.pouse:
                if(status==2){
                    pouseD = true;
                   btnPou.setText("Відновити");
                    status=3;
                }else if (status==3){
                    btnPou.setText("Пауза");
                    status=2;
                    pouseD = false;
                }else break;
                break;
            case R.id.stop:
                cancelTask();
                status=0;
                tvInfo.setText("");
                pb.setProgress(0);
                break;
            case R.id.status:
                switch (status) {
                    case 0:
                        Toast.makeText(MainActivity.this, "Файли не підготовані", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(MainActivity.this, "Файли підготовані", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this, "Файли завантажуються", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(MainActivity.this, "Пауза", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(MainActivity.this, "Файли завантажено", Toast.LENGTH_SHORT).show();
                        break;
                }
        }
    }
    private void cancelTask() {
        if (mt == null) return;
        mt.cancel(true);
    }
    class MyTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                int i=0;
                while(i<20) {
                    while (pouseD)TimeUnit.SECONDS.sleep(1);
                    if(random.nextInt(100)%2==0)i++;
                    // выводим промежуточные результаты
                    publishProgress(i);

                    // разъединяемся
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            pb.setProgress(values[0]);
            if(values[0]==20){ pb.setBackgroundColor(Color.GREEN);
            pb.setProgressDrawable(getResources().getDrawable(R.drawable.progresstheme2));

                status = 4;
                }
            tvInfo.setText("загружено "+values[0]+" файлів з 20");
        }

    }
}

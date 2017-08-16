package com.vitos.practicemulti_2;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ProogBaar extends AppCompatActivity{
    ProgressBar pb;
    MyTask mt;
    TextView tvInfo;
    Button btnPou;
    ProgressBar myProgressBar;
    boolean prep=false, startD=false,pouseD=false,stopD=false;
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
        myProgressBar = new ProgressBar(this);
       // myProgressBar.setProgressDrawable(getResources().getDrawable(R.drawable.verticalprogressbar));
        myProgressBar.setProgress(50);
    }
    public void onclick(View v) {
        switch (v.getId()) {
            case R.id.prepare:
                if (status==0) {
                    prep = true;
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
                    btnPou.setText("Відновити");
                    status=3;
                }else if (status==3){
                    btnPou.setText("Пауза");
                    status=2;
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
                        Toast.makeText(ProogBaar.this, "Файли не підготовані", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(ProogBaar.this, "Файли підготовані", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(ProogBaar.this, "Файли завантажуються", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(ProogBaar.this, "Пауза", Toast.LENGTH_SHORT).show();
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
                while(i<=20) {
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
            tvInfo.setText("загружено "+values[0]+" файлів з 20");
        }

    }
}

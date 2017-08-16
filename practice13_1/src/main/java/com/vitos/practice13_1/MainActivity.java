package com.vitos.practice13_1;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements OnTouchListener{
    TextView choise, changeList;
    final String LOG_TAG = "myLogs";
    RelativeLayout linLey;
    StringBuilder sb = new StringBuilder();
    String logi="";
    String[] strLog = {"","","","","","","","","",""};
    int downPI = 0, moveX=0, moveY=0,sensivity = 20;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        choise = (TextView)findViewById(R.id.currentChoice);
        changeList = (TextView)findViewById(R.id.changeList);
        linLey=(RelativeLayout)findViewById(R.id.lay1);
        linLey.setOnTouchListener(this);
       // choise.setOnTouchListener(this);
        choise.setText("Без дотику");
        //for(int j=0;j<10;j++)
         //   strLog[j]="";

    }
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        // событие
        int actionMask = event.getActionMasked();
        // индекс касания
        int pointerIndex = event.getActionIndex();
        // число касаний
        int pointerCount = event.getPointerCount();

        switch (actionMask) {
            case MotionEvent.ACTION_DOWN: // первое касание
               //Log.d(LOG_TAG, downPI+" DOWN");
                moveX = (int)event.getX()/sensivity;
                moveY = (int)event.getY()/sensivity;
            case MotionEvent.ACTION_POINTER_DOWN: // последующие касания
                downPI = pointerCount;
                //Log.d(LOG_TAG, downPI+" POINTER_DOWN");
                break;

            case MotionEvent.ACTION_UP: // прерывание последнего касания
               // Log.d(LOG_TAG, downPI+" UP");
            case MotionEvent.ACTION_POINTER_UP: // прерывания касаний
                downPI = pointerCount-1;
                //Log.d(LOG_TAG, downPI+" POINTER_UP");
                break;

            case MotionEvent.ACTION_MOVE: // движение
                //moveX = (int)event.getX()/10;
               // Log.d(LOG_TAG, (int)event.getX()/sensivity+" XX= "+moveX);
                if(moveX<(int)event.getX()/sensivity){
                    downPI = 12;
                    moveX=(int)event.getX()/sensivity;
                }
                else if (moveX>(int)event.getX()/sensivity){
                    downPI = 11;
                    moveX=(int)event.getX()/sensivity;
                }
                else if (moveY>(int)event.getY()/sensivity){
                    downPI = 13;
                    moveY=(int)event.getY()/sensivity;
                }
                else if (moveY<(int)event.getY()/sensivity){
                    downPI = 14;
                    moveY=(int)event.getY()/sensivity;
                }
              break;
        }
        //touch(downPI);
        choise.setText(touch(downPI));
        if (logi!=touch(downPI)) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String time = sdf.format(new Date(System.currentTimeMillis()));
            for (int j=1;j<10;j++){
                strLog[j-1]=strLog[j];
            }
            strLog[9]=touch(downPI)+time+" "+"\n";
            //i++;

            for(int j=0;j<10;j++)
                sb.append(strLog[j]);
            //sb.append(touch(downPI)+"\n");
            changeList.setText(sb);
            logi=touch(downPI);

        }
        sb.setLength(0);
        return true;
    }
    public String touch(int i){
        switch (i){
            case 0: return ("Без дотику");
                //break;
            case 1: return("Дотик: один палець");
                //break;
            case 2: return("Дотик: два пальці");
               // break;
            case 11: return("Вліво");
                //break;
            case 12: return("Вправо");
               // break;
            case 13: return("Вгору");
                //break;
            case 14: return("Вниз");
               // break;
            default:return("Дотик: багато пальців");
               // break;
        }
    }
}

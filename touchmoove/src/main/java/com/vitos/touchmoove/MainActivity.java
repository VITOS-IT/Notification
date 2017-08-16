package com.vitos.touchmoove;


import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }

    class MyView extends View {
        Paint p;
        // координаты для рисования квадрата
        float x = 100;
        float y = 100;
        int side = 100,i;
        Path path;

        // переменные для перетаскивания
        boolean drag = false,isReal = true;
        float dragX = 0;
        float dragY = 0;

        public MyView(Context context) {
            super(context);
            path = new Path();
            p = new Paint();
            p.setColor(Color.GREEN);
        }

        protected void onDraw(Canvas canvas) {

            // рисуем квадрат
            objDraw(canvas);
            p.setColor(Color.RED);

            canvas.drawRect(250,250,350,350,p);
            p.setColor(Color.BLACK);
            canvas.drawText(i+"", 300, 300, p);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            // координаты Touch-события
            float evX = event.getX();
            float evY = event.getY();

            switch (event.getAction()) {
                // касание началось
                case MotionEvent.ACTION_DOWN:
                    // если касание было начато в пределах квадрата
                    if (evX >= x && evX <= x + side && evY >= y && evY <= y + side) {
                        // включаем режим перетаскивания
                        drag = true;
                        // разница между левым верхним углом квадрата и точкой касания
                        dragX = evX - x;
                        dragY = evY - y;
                    }
                    break;
                // тащим
                case MotionEvent.ACTION_MOVE:
                    // если режим перетаскивания включен
                    if (drag) {
                        // определеяем новые координаты для рисования
                        x = evX - dragX;
                        y = evY - dragY;
                        // перерисовываем экран
                        invalidate();
                    }
                    break;
                // касание завершено
                case MotionEvent.ACTION_UP:
                    // выключаем режим перетаскивания
                    drag = false;
                    if(x>=200 && x<=300 && y>=200 && y<=300) {
                        isReal=false;
                        i+=1;
                        x = 0;
                        y = 0;
                    }
                    invalidate();
                    break;
            }
            return true;
        }

        public void objDraw(Canvas canvas) {
            if (isReal){
                path.reset();
                p.setColor(Color.GREEN);
                path.addRect(x, y, x + side, y + side, Path.Direction.CCW);
                canvas.drawPath(path,p);

            }
            else{
                path=null;
            }


        }
    }
}
package com.vitos.practice13_2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
Button btn;

    EditText edLogin, edPas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=(Button)findViewById(R.id.button);
        edLogin = (EditText)findViewById(R.id.editText);
        edPas = (EditText)findViewById(R.id.editText2);
        edLogin.clearFocus();
        edPas.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) edLogin.setHint("Логін введено неправильно");
            }
        });

        btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MainActivity.this, "Спочатку зареєструйтесь", Toast.LENGTH_SHORT).show();

                return false;
            }
        });
    }

}

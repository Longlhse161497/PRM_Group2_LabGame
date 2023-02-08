package com.example.group2_labgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    CheckBox cbHorse1, cbHorse2, cbHorse3;
    SeekBar sbHorse1, sbHorse2, sbHorse3;
    Button btnStart,btnReset;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();

        //cài đặt để ko thao tác đc với seekbar
        sbHorse1.setEnabled(false);
        sbHorse2.setEnabled(false);
        sbHorse3.setEnabled(false);

        //tắt nút reset trước khi bắt đầu
        btnReset.setClickable(false);

        final CountDownTimer countDownTimer = new CountDownTimer(12000, 300) {
            @Override
            public void onTick(long millisUntilFinished) {
                int finish = sbHorse1.getMax();

                //thuật toán chính của chương trình
                Random random = new Random();
                sbHorse1.setProgress(sbHorse1.getProgress() + random.nextInt(10));
                sbHorse2.setProgress(sbHorse2.getProgress() + random.nextInt(10));
                sbHorse3.setProgress(sbHorse3.getProgress() + random.nextInt(10));

                int isChosen = isChosen();
                if (sbHorse1.getProgress() >= finish && isChosen == 1){
                    this.cancel();
                    tvResult.setText("You win");
                    btnReset.setClickable(true);
                } else if (sbHorse2.getProgress() >= finish && isChosen == 2){
                    this.cancel();
                    tvResult.setText("You win");
                    btnReset.setClickable(true);
                } else if (sbHorse3.getProgress() >= finish && isChosen == 3){
                    this.cancel();
                    tvResult.setText("You win");
                    btnReset.setClickable(true);
                } else if ((sbHorse1.getProgress() >= finish && isChosen != 1) || (sbHorse2.getProgress() >= finish && isChosen != 2) || (sbHorse3.getProgress() >= finish && isChosen != 3)){
                    this.cancel();
                    tvResult.setText("You lose");
                    btnReset.setClickable(true);
                }
            }

            @Override
            public void onFinish() {

            }
        };

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChosen() != 0){
                    btnStart.setClickable(false);
                    btnReset.setClickable(false);
                    //khi bắt đầu chơi thì chặn thao tác với checkbox và seekbar
                    disable();
                    countDownTimer.start();
                }
                else //nếu ko có con ngựa nào đc chọn
                    Toast.makeText(MainActivity.this, "Please choose a horse", Toast.LENGTH_SHORT).show();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //nhấn reset --> đặt ngựa về vị trí xuất phát, xóa textview, xóa checkbox
                sbHorse1.setProgress(0);
                sbHorse2.setProgress(0);
                sbHorse3.setProgress(0);
                tvResult.setText("");
                cbHorse1.setChecked(false);
                cbHorse2.setChecked(false);
                cbHorse3.setChecked(false);

                //cho phép thao tác với checkbox để chọn
                enable();

                //bật nút start và tắt nút reset
                btnStart.setClickable(true);
                btnReset.setClickable(false);
            }
        });
    }

    //hàm ánh xạ
    private void mapping(){
        cbHorse1 = (CheckBox) findViewById(R.id.cbHorse1);
        cbHorse2 = (CheckBox) findViewById(R.id.cbHorse2);
        cbHorse3 = (CheckBox) findViewById(R.id.cbHorse3);
        sbHorse1 = (SeekBar) findViewById(R.id.sbHorse1);
        sbHorse2 = (SeekBar) findViewById(R.id.sbHorse2);
        sbHorse3 = (SeekBar) findViewById(R.id.sbHorse3);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnReset = (Button) findViewById(R.id.btnReset);
        tvResult = (TextView) findViewById(R.id.tvResult);
    }

    //hàm trả về lựa chọn
    private int isChosen() {
        if(cbHorse1.isChecked())
            return 1;
        if(cbHorse2.isChecked())
            return 2;
        if(cbHorse3.isChecked())
            return 3;
        return 0;
    }

    //hàm chặn thao tác
    private void disable() {
        cbHorse1.setEnabled(false);
        cbHorse2.setEnabled(false);
        cbHorse3.setEnabled(false);
    }

    //hàm cho phép thao tác
    private void enable() {
        cbHorse1.setEnabled(true);
        cbHorse2.setEnabled(true);
        cbHorse3.setEnabled(true);
    }
}
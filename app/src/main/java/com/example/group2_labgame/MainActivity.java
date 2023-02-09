package com.example.group2_labgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    CheckBox cbHorse1, cbHorse2, cbHorse3;
    SeekBar sbHorse1, sbHorse2, sbHorse3;
    Button btnStart,btnReset;
    TextView tvMoney, tvResult;
    EditText edtBetAmount;
    int balance = 100; //biến balance tổng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();

        //Set balance textView
        tvMoney.setText("Your balance: "+ balance);

        //cài đặt để ko thao tác đc với seekbar
        sbHorse1.setEnabled(false);
        sbHorse2.setEnabled(false);
        sbHorse3.setEnabled(false);

        //tắt nút reset trước khi bắt đầu
        btnReset.setClickable(false);

        final CountDownTimer countDownTimer = new CountDownTimer(10000, 200) {
            @Override
            public void onTick(long millisUntilFinished) {
                sbHorse1.setMax(100);
                int finish = sbHorse1.getMax();

                //thuật toán chính của chương trình
                Random random = new Random();
                sbHorse1.setProgress(sbHorse1.getProgress() + random.nextInt(10));
                sbHorse2.setProgress(sbHorse2.getProgress() + random.nextInt(10));
                sbHorse3.setProgress(sbHorse3.getProgress() + random.nextInt(10));

                int isChosen = isChosen();
                //lấy bet amount từ editText
                int betAmount = Integer.parseInt(edtBetAmount.getText().toString());

                if (sbHorse1.getProgress() == finish && isChosen == 1){
                    this.cancel();
                    tvResult.setText("You win");
                    btnReset.setClickable(true);

                    //Cộng bet amount vào balance
                    balance = balance + betAmount;
                    tvMoney.setText("Your balance: "+ balance);
                } else if (sbHorse2.getProgress() == finish && isChosen == 2){
                    this.cancel();
                    tvResult.setText("You win");
                    btnReset.setClickable(true);

                    balance = balance + betAmount;
                    tvMoney.setText("Your balance: "+ balance);
                } else if (sbHorse3.getProgress() == finish && isChosen == 3){
                    this.cancel();
                    tvResult.setText("You win");
                    btnReset.setClickable(true);

                    balance = balance + betAmount;
                    tvMoney.setText("Your balance: "+ balance);
                } else if ((sbHorse1.getProgress() == finish && isChosen != 1) || (sbHorse2.getProgress() == finish && isChosen != 2) || (sbHorse3.getProgress() == finish && isChosen != 3)){
                    this.cancel();
                    tvResult.setText("You lose");
                    btnReset.setClickable(true);

                    //trừ bet amount ra khỏi balance
                    balance = balance - betAmount;
                    tvMoney.setText("Your balance: "+ balance);
                }
            }

            @Override
            public void onFinish() {

            }
        };

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int betAmount = 0;
                //Nếu balance=0, báo lỗi
                if(balance > 0){
                    //Nếu ko nhập bet amount, báo lỗi
                    if(!edtBetAmount.getText().toString().isEmpty()){
                        betAmount = Integer.parseInt(edtBetAmount.getText().toString());
                        //Nếu bet amount <= 0, báo lỗi
                        if(betAmount > 0){
                            //Nếu bet amount lớn hơn balance, báo lỗi
                            if(betAmount <= balance){
                                if (isChosen() != 0){
                                    btnStart.setClickable(false);
                                    btnReset.setClickable(false);
                                    //khi bắt đầu chơi thì chặn thao tác với checkbox và seekbar
                                    disable();
                                    countDownTimer.start();
                                }
                                else //nếu ko có con ngựa nào đc chọn
                                    Toast.makeText(MainActivity.this, "Please choose a horse", Toast.LENGTH_SHORT).show();
                            }else{
                                edtBetAmount.setError("Bet amount cannot exceed balance");
                            }
                        }else{
                            edtBetAmount.setError("Bet Amount must be larger than 0");
                        }
                    }else{
                        edtBetAmount.setError("Enter a bet amount");
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Balance is 0, hold Reset to restart", Toast.LENGTH_SHORT).show();
                    btnReset.setClickable(true);
                }
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

        //Giữ reset để restart game
        btnReset.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //reset lại balance và editBetAmount
                balance = 100;
                tvMoney.setText("Your balance: " + balance);
                edtBetAmount.setText("");

                sbHorse1.setProgress(0);
                sbHorse2.setProgress(0);
                sbHorse3.setProgress(0);
                tvResult.setText("");
                cbHorse1.setChecked(false);
                cbHorse2.setChecked(false);
                cbHorse3.setChecked(false);

                enable();

                btnStart.setClickable(true);
                btnReset.setClickable(false);

                Toast.makeText(MainActivity.this, "Game has been restarted", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        //hàm nếu chọn checkbox này thì hủy chọn 2 checkbox còn lại
        cbHorse1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    cbHorse2.setChecked(false);
                    cbHorse3.setChecked(false);
                }
            }
        });

        cbHorse2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    cbHorse1.setChecked(false);
                    cbHorse3.setChecked(false);
                }
            }
        });

        cbHorse3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    cbHorse1.setChecked(false);
                    cbHorse2.setChecked(false);
                }
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
        tvMoney = (TextView) findViewById(R.id.tvMoney);
        edtBetAmount = (EditText) findViewById(R.id.etBetAmount);
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
package com.example.passgenerator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String nums = "0123456789";
    String symbols = "!@#$^&*=+()|?/\\;[]{}-.,";
    String finalString = "";
    TextView passwordField;
    TextView passText;
    TextView passLen;
    Switch upperCase;
    Switch lowerCase;
    Switch symbolCase;
    Switch numberCase;
    Switch darkMode;
    boolean checked;
    SeekBar passLength;
    TextView passStrength;
    ConstraintLayout constraintLayout;
    Button generateBtn;
    Button copyBtn;
    Boolean darkModeEnabled;
    SharedPreferences sharedPreferences;
    boolean getDarkMode;
    Drawable drawable;
    ClipboardManager clipboard;
    ClipData clip;

    int passProgress;
    int minPass;
    int maxPass;

    @SuppressLint("WrongConstant")
    public void smh(){
        getDarkMode = sharedPreferences.getBoolean("darkModeEnabled",true);
        if(getDarkMode){
            constraintLayout.setBackgroundColor(Color.BLACK);
            passText.setTextColor(Color.WHITE);
            upperCase.setTextColor(Color.WHITE);
            lowerCase.setTextColor(Color.WHITE);
            symbolCase.setTextColor(Color.WHITE);
            numberCase.setTextColor(Color.WHITE);
            darkMode.setTextColor(Color.WHITE);
            passLen.setTextColor(Color.WHITE);
            passStrength.setTextColor(Color.WHITE);
            passwordField.setTextColor(Color.WHITE);
            generateBtn.setBackground(getDrawable(R.drawable.dark_btn_background));
            generateBtn.setTextColor(Color.WHITE);
            copyBtn.setBackground(getDrawable(R.drawable.dark_btn_background));
            copyBtn.setTextColor(Color.WHITE);
        } else {
            constraintLayout.setBackgroundColor(Color.WHITE);
            passText.setTextColor(Color.BLACK);
            upperCase.setTextColor(Color.BLACK);
            lowerCase.setTextColor(Color.BLACK);
            symbolCase.setTextColor(Color.BLACK);
            numberCase.setTextColor(Color.BLACK);
            darkMode.setTextColor(Color.BLACK);
            passLen.setTextColor(Color.BLACK);
            passStrength.setTextColor(Color.BLACK);
            passwordField.setTextColor(Color.BLACK);
            generateBtn.setBackground(getDrawable(R.drawable.light_btn_background));
            generateBtn.setTextColor(Color.BLACK);
            copyBtn.setBackground(getDrawable(R.drawable.light_btn_background));
            copyBtn.setTextColor(Color.BLACK);
        }
    }

    public void generatePass(View view){
        Random r = new Random();
        String newWord = "";
        boolean contains = false;
        if(upperCase.isChecked() && lowerCase.isChecked() && symbolCase.isChecked() && numberCase.isChecked()) {
            finalString = chars + nums + symbols;
        } else if (upperCase.isChecked() && lowerCase.isChecked() && symbolCase.isChecked()) {
            finalString = chars + symbols;
        } else if (upperCase.isChecked() && lowerCase.isChecked() && numberCase.isChecked()) {
            finalString = chars + nums;
        } else if (upperCase.isChecked() && symbolCase.isChecked() && numberCase.isChecked()) {
            finalString = chars.toUpperCase() + nums + symbols;
        } else if (lowerCase.isChecked() && symbolCase.isChecked() && numberCase.isChecked()) {
            finalString = chars.toLowerCase() + nums + symbols;
        } else if (upperCase.isChecked() && lowerCase.isChecked()) {
            finalString = chars;
        } else if (upperCase.isChecked() && symbolCase.isChecked()) {
            finalString = chars.toUpperCase() + symbols;
        } else if (upperCase.isChecked() && numberCase.isChecked()) {
            finalString = chars.toUpperCase() + nums;
        } else if (lowerCase.isChecked() && symbolCase.isChecked()) {
            finalString = chars.toLowerCase() + symbols;
        } else if (lowerCase.isChecked() && numberCase.isChecked()) {
            finalString = chars.toLowerCase() + nums;
        } else if (symbolCase.isChecked() && numberCase.isChecked()){
            finalString = symbols + nums;
        } else if (upperCase.isChecked()){
            finalString = chars.toUpperCase();
        } else if (lowerCase.isChecked()){
            finalString = chars.toLowerCase();
        } else if (symbolCase.isChecked()){
            finalString = symbols;
        } else if (numberCase.isChecked()){
            finalString = nums;
        }

        for(int i = 0; i < passLength.getProgress(); i++) {
            char rand = finalString.charAt(r.nextInt(finalString.length()));
            if (i >= 1 && String.valueOf(rand).equals(newWord.substring(newWord.length() - 1))){
                rand = finalString.charAt(r.nextInt(finalString.length()));
                newWord = newWord + rand;
            } else {
                newWord = newWord + rand;
            }
        }

            if(numberCase.isChecked() && !newWord.matches(".*\\d.*")){
                System.out.println("in if");
                newWord = newWord.replace(newWord.charAt(r.nextInt(newWord.length())),nums.charAt(r.nextInt(nums.length())));
                passwordField.setText(newWord);
            } else {
                System.out.println("in else");
                passwordField.setText(newWord);
            }
    }

    public void copyPassword(View view){
        String passValue = passwordField.getText().toString();
        if (!passValue.isEmpty()){
            clip = ClipData.newPlainText("label",passValue);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this,"Copied to clipboard",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this,"Nothing to copy! Generate a password first.",Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e){
            setContentView(R.layout.activity_main);
        }

        passText = findViewById(R.id.passText);
        passLen = findViewById(R.id.passLength);
        passwordField =  findViewById(R.id.password);
        upperCase = findViewById(R.id.uppserCaseSwitch);
        lowerCase = findViewById(R.id.lowerCaseSwitch);
        symbolCase = findViewById(R.id.symbolSwitch);
        numberCase = findViewById(R.id.numSwitch);
        darkMode = findViewById(R.id.darkMode);
        passLength = findViewById(R.id.seekBar);
        passStrength = findViewById(R.id.passStrength);
        constraintLayout = findViewById(R.id.constraintLayout);
        generateBtn = findViewById(R.id.generateBtn);
        copyBtn = findViewById(R.id.copyBtn);
        darkModeEnabled = darkMode.isChecked();
        drawable = getApplicationContext().getResources().getDrawable(R.drawable.light_btn_background);


        sharedPreferences = this.getSharedPreferences("com.example.passgenerator",Context.MODE_PRIVATE);

        clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        passProgress = 12;
        minPass = 8;
        maxPass = 20;

        smh();
        darkMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                darkModeEnabled = ((Switch)v).isChecked();
                sharedPreferences.edit().putBoolean("darkModeEnabled",darkModeEnabled).apply();
                System.out.println(sharedPreferences.getBoolean("darkModeEnabled",false));
                smh();
            }
        });
        passStrength.setText(passProgress + " Characters");

        if (getDarkMode){
            darkMode.setChecked(true);
        }

        passLength.setMin(minPass);
        passLength.setMax(maxPass);
        passLength.setProgress(passProgress);

        passLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                passStrength.setText(progress + " Characters");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SpannableString spannable;
                SpannableStringBuilder stringBuilder;
                String strLength;
                String strProgress = String.valueOf(seekBar.getProgress()) + " Characters" + "\n";
                ForegroundColorSpan fcsRed = new ForegroundColorSpan(Color.RED);
                ForegroundColorSpan fcsYellow = new ForegroundColorSpan(Color.YELLOW);
                ForegroundColorSpan fcsGreen = new ForegroundColorSpan(Color.GREEN);

                if(passLength.getProgress() < passProgress){
                    strLength = "Weak password";
                    spannable = new SpannableString(strLength);
                    stringBuilder = new SpannableStringBuilder(strProgress);
                    spannable.setSpan(fcsRed, 0,strLength.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    stringBuilder.append(spannable);
                } else if (passLength.getProgress() >= passProgress && passLength.getProgress() <= 16 ){
                    strLength = "Medium password";
                    spannable = new SpannableString(strLength);
                    stringBuilder = new SpannableStringBuilder(strProgress);
                    spannable.setSpan(fcsYellow, 0,strLength.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    stringBuilder.append(spannable);
                } else {
                    strLength = "Strong password";
                    spannable = new SpannableString(strLength);
                    stringBuilder = new SpannableStringBuilder(strProgress);
                    spannable.setSpan(fcsGreen, 0,strLength.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    stringBuilder.append(spannable);
                }
                passStrength.setText(stringBuilder);

            }
        });


    }
}

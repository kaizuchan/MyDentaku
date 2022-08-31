package com.example.mydentaku;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String strTemp = "";
    String strTemp2 = "";
    String strResult = "0";
    int operator =0;
    int ct_flag =0;
    String copy_text = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void numKeyOnClick(View v) {
        String strInKey = (String) ((Button) v).getText();

        if (strInKey.equals(".")) {
            if (strTemp.length() == 0) {
                strTemp = strTemp + "0.";
            } else {
                if (strTemp.indexOf(".") == -1) {
                    strTemp = strTemp + ".";
                }
            }
        } else {
            strTemp=strTemp+strInKey;
        }
        showNumber(strTemp);
    }

    public void showNumber(String strNum){
        DecimalFormat form = new DecimalFormat("#,##0");
        String strDecimal = "";
        String strInt = "";
        String fText = "";
        if(strNum.length() > 0){
            int decimalPoint = strNum.indexOf(".");
            if (decimalPoint > -1){
                strDecimal = strNum.substring(decimalPoint);
                strInt = strNum.substring(0,decimalPoint);
            }else{
                strInt = strNum;
            }
            fText = form.format(Double.parseDouble(strInt))+strDecimal;
        } else {
            fText = "0";
        }
        ((TextView)findViewById(R.id.DisplayPanel)).setText(fText);
    }

    public void functionKeyOnClick(View v){
        switch (v.getId()){
            case R.id.KeypadAC:
                strTemp="";
                ct_flag=0;
                break;
            case R.id.KeypadC:
                strTemp="";
                break;
            case R.id.KeypadBS:
                if(strTemp.length()==0){
                    return;
                }else{
                    strTemp=strTemp.substring(0,strTemp.length()-1);
                }
                break;
            case R.id.KeypadCopy:
                if(ct_flag == 0){
                    copy_text = strTemp;
                    ct_flag = 1;
                    return;
                }else{
                    strTemp = copy_text;
                    ct_flag = 0;
                }
                break;
        }
        showNumber(strTemp);
    }

    public void operatorKeyOnClick(View v){
        if (operator!=0){
            if (strTemp.length() > 0){
                BigDecimal bd1 = new BigDecimal(strTemp);
                BigDecimal bd2 = new BigDecimal(strTemp2);
                BigDecimal result = BigDecimal.ZERO;
                switch (operator){
                    case R.id.KeypadAdd:
                        result = bd2.add(bd1);
                        break ;
                    case R.id.KeypadSub:
                        result = bd2.subtract(bd1);
                        break;
                    case R.id.KeypadMulti:
                        result = bd2.multiply(bd1);
                        break ;
                    case R.id.KeypadDiv:
                        if (!bd1.equals(BigDecimal.ZERO)){
                            result = bd2.divide(bd1,2,BigDecimal.ROUND_HALF_UP);
                        }else{
                            Toast toast = Toast.makeText(this,"0で割れません",Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        break;
                }
                if (result.toString().indexOf(".")>0){
                    strTemp2 = result.toString().replaceAll("\\.0+$|0+$","");
                }else{
                    strTemp2 = result.toString();
                }
            }
        }else {
            if (strTemp.length() > 0){
                strTemp2 = strTemp;
            }
        }

        strTemp = "";

        if (v.getId()==R.id.KeypadEq){
            operator = 0;
            strTemp = strTemp2;
            showNumber(strTemp);
        }else{
            operator = v.getId();
        }
    }

}
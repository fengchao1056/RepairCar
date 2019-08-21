package com.nengyuanbox.repaircar.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.nengyuanbox.repaircar.R;
import com.nengyuanbox.repaircar.activity.LoginActivity;
import com.nengyuanbox.repaircar.app.CommApplication;


public class LoginExpirelUtils {


// 退出登录

    public  static void getLoginExpirel(Context context){
        if (context==null){
             context= CommApplication.getContext();
        }
        final DialogSure rxDialogSure = new DialogSure(context);//提示弹窗
        rxDialogSure.setTitle("");
        rxDialogSure.setCancelable(false);
        rxDialogSure.setContent( context.getResources().getString(R.string.login_expired));
        final Context finalContext = context;
        rxDialogSure.getSureView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(finalContext, LoginActivity.class);
                finalContext.startActivity(intent);
                rxDialogSure.cancel();


            }
        });

        rxDialogSure.show();
    }

    public  static void getLoginExpirel_Acticity(final Activity context){
        final DialogSure rxDialogSure = new DialogSure(context);//提示弹窗
        rxDialogSure.setTitle("");
        rxDialogSure.setCancelable(false);
        rxDialogSure.setContent( context.getResources().getString(R.string.login_expired));
        rxDialogSure.getSureView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                rxDialogSure.cancel();
                context.finish();


            }
        });

        rxDialogSure.show();
    }



}

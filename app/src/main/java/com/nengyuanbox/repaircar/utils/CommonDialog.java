package com.nengyuanbox.repaircar.utils;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;




/***
 * @ClassName: CommonDialog
 * @Description: 对话框工具类
 */
public class CommonDialog {
    private final static String TAG = "CommonDialog";

    private  static int MSG_DISMISS_DIALOG = 0;
    private static Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);

            if(MSG_DISMISS_DIALOG == msg.what){


                if(null != progressDialog){

                    if(progressDialog.isShowing()){
                        Log.i(TAG, "handler get mesage");
//                        showInfoDialog(CommApplication.getContext(),"网络不好");
                        closeProgressDialog();
//                        finish();
                    }
                }
            }
        }


    };
    /**
     * @param @param context
     * @return void
     * @throws
     * @Title: showInfoDialog
     * @Description: 信息提示框
     */
    public static void showInfoDialog(Context context, String message) {
        new AlertDialog(context).builder().setTitle("提示")
                .setMsg(message)
                .show();
    }



    public static void showInfoDialogFailure(Context context) {
        showInfoDialog(context, "网络连接失败");
    }
    public static void showInfoDialogFailure_New(Context context) {
        showInfoDialog(context, "请求失败");
    }

    public static void showInfoDialog(Context context, String message, String titleStr) {

        new AlertDialog(context).builder().setTitle(titleStr)
                .setMsg(message)
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .show();
    }


    public static void showInfoDialog(Context context, String message, String titleStr, String negativeStr, String positiveStr, View.OnClickListener onClickListener) {

        new AlertDialog(context).builder().setTitle(titleStr)
                .setMsg(message)
                .setPositiveButton(positiveStr, onClickListener)
                .setNegativeButton(negativeStr, onClickListener)
                .show();
    }


    public static CustomProgressDialog progressDialog;
    private static boolean mShowingDialog = false;

    /**
     * 启动一个Progressdialog
     */
    public static void showProgressDialog(Context context) {
        try {
            if (!mShowingDialog) {
                progressDialog = CustomProgressDialog.createDialog(context);
                progressDialog.setCanceledOnTouchOutside(false);
                // this.progressDialog.setTitle(getString(R.string.loadTitle));
                progressDialog.setMessage("加载中");
                // this.progressDialog.setMessage(getString(R.string.LoadContent));
                if (!progressDialog.isShowing())
                    progressDialog.show();

                mShowingDialog = true;
                mHandler.sendEmptyMessageDelayed(MSG_DISMISS_DIALOG,15000);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }


    /**
     * 关闭dialog
     */
    public static void closeProgressDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                mShowingDialog = false;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}

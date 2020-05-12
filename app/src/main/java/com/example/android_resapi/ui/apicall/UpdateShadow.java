package com.example.android_resapi.ui.apicall;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import java.net.MalformedURLException;
import java.net.URL;

import com.example.android_resapi.httpconnection.PutRequest;
import com.example.android_resapi.ui.SendMessagePopUpActivity;

public class UpdateShadow extends PutRequest {
    final static String TAG = "AndroidAPITest";
    String urlStr;
    int mode = 0;

    public UpdateShadow(Activity activity, String urlStr) {

        super(activity);
        this.urlStr = urlStr;
    }

    public UpdateShadow(Activity activity, String urlStr,int mode){
        super(activity);
        this.urlStr = urlStr;
        this.mode = mode;
    }

    @Override
    protected void onPreExecute() {
        try {
            Log.e(TAG, urlStr);
            url = new URL(urlStr);
            if(mode == 1)
                Toast.makeText(activity,"등록중", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(activity,"메세지 전송중...", Toast.LENGTH_SHORT).show();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Toast.makeText(activity,"URL is invalid:"+urlStr, Toast.LENGTH_SHORT).show();
            activity.finish();

        }
    }
    @Override
   protected void onPostExecute(String result) {
        if(mode == 1)
            Toast.makeText(activity,"정상적으로 등록되었습니다.", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(activity,"메세지가 정상적으로 전송되었습니다.", Toast.LENGTH_SHORT).show();
        //Toast.makeText(activity,result, Toast.LENGTH_SHORT).show();
        super.activity.finish();
    }




}

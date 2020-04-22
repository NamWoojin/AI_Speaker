package com.example.android_resapi.ui.apicall;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_resapi.R;
import com.example.android_resapi.httpconnection.GetRequest;
import com.example.android_resapi.ui.CareMembersListViewAdapter;
import com.example.android_resapi.ui.HealthInfoActivity;
import com.example.android_resapi.ui.ListViewAdapter;
import com.example.android_resapi.ui.ListViewItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GetLogSleepTime extends GetRequest {
    final static String TAG = "AndroidAPITest";
    String urlStr;
    String mode;
    Context mContext = null;
    public GetLogSleepTime(Activity activity, String urlStr,String mode,Context context) {
        super(activity);
        this.urlStr = urlStr;
        this.mode = mode;
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        try {
            url = new URL(urlStr);
            Log.i(TAG,"urlStr="+urlStr);
        } catch (MalformedURLException e) {
            Toast.makeText(activity,"URL is invalid:"+urlStr, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(String jsonString) {
        ArrayList<Tag> arrayList = getArrayListFromJSONString(jsonString);
        ArrayList<Tag> sleepListViewItem = new ArrayList<>();
        ArrayList<Tag> wakeListViewItem = new ArrayList<>() ;
        //가장 최근 시간의 기록이 리스트의 맨 앞, 내림차순
        for(int i = 0; i< arrayList.size();i++){
            if(arrayList.get(i).Type.equals("취침")){
                if(sleepListViewItem.size()== 0)
                    sleepListViewItem.add(arrayList.get(i));

                for(int j =0; j< sleepListViewItem.size();j++){
                    if(arrayList.get(i).Time.compareTo(sleepListViewItem.get(j).Time) > 0){
                        sleepListViewItem.add(j,arrayList.get(i));
                        break;
                    }
                    else
                        continue;
                }
            }
            else if(arrayList.get(i).Type.equals("기상")){
                if(wakeListViewItem.size()== 0)
                    wakeListViewItem.add(arrayList.get(i));

                for(int j =0; j< wakeListViewItem.size();j++){
                    if(arrayList.get(i).Time.compareTo(wakeListViewItem.get(j).Time) > 0){
                         wakeListViewItem.add(j,arrayList.get(i));
                        break;
                    }
                    else
                        continue;
                }
            }
        }


        if(mode.equals("simple")){ //간단히 보기
            Boolean viewflag = true;
            String sleepTime="";
            String wakeupTime="";
            String wholeTime = "";

            if(sleepListViewItem.size() != 0 && wakeListViewItem.size() != 0) {
                sleepTime = sleepListViewItem.get(0).Time;
                wakeupTime = wakeListViewItem.get(0).Time;

                if(sleepTime.compareTo(wakeupTime)>0){  //기상시간은 수면시간보다 나중이어야 한다.
                    if(sleepListViewItem.size()>1)
                        sleepTime = sleepListViewItem.get(1).Time;
                    else
                        viewflag = false;
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                ParsePosition pos = new ParsePosition(0);
                Date sleep = sdf.parse(sleepTime,pos);
                pos = new ParsePosition(0);
                Date wake = sdf.parse(wakeupTime,pos);
                long diffDate = wake.getTime()-sleep.getTime();




                SimpleDateFormat sdf2 = new SimpleDateFormat("HH시간 mm분");
                wholeTime = sdf2.format(diffDate);

                SimpleDateFormat sdf3 = new SimpleDateFormat("MM/dd HH:mm");
                sleepTime = sdf3.format(sleep);
                wakeupTime = sdf3.format(wake);

                if(diffDate>= 86400000) //하루이상 차이
                    viewflag= false;
                else
                    viewflag = true;
            }
            else//취침정보 혹은 기상정보 없을 때
                viewflag = false;

            if(viewflag) {
                ((HealthInfoActivity)mContext).SetHealtInfoSleepTime(wholeTime,sleepTime,wakeupTime);
            }
            else{
                ((HealthInfoActivity)mContext).SetHealtInfoSleepTime("정보가 없습니다.","","");
            }

        }

        /*else if(mode.equals("detail")){

        }*/


    }


    protected ArrayList<Tag> getArrayListFromJSONString(String jsonString) {
        ArrayList<Tag> output = new ArrayList();
        try {
            jsonString = jsonString.substring(1,jsonString.length()-1);
            jsonString = jsonString.replace("\\\"","\"");


            Log.i(TAG, "jsonString="+jsonString);

            JSONObject root = new JSONObject(jsonString);
            JSONArray jsonArray = root.getJSONArray("data");
            if(jsonArray.length() >0){
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = (JSONObject)jsonArray.get(i);

                    Tag thing = new Tag(jsonObject.getString("Type"),
                            jsonObject.getString("Device_id"),
                            jsonObject.getString("Time")
                    );

                    output.add(thing);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return output;
    }

    class Tag {
        String Type;
        String Device_id;
        String Time;

        public Tag(String type,String device_di,String time) {

            this.Type = type;
            this.Device_id =device_di;
            this.Time = time;

        }

        public String toString() {
            return String.format("Type : %s, Device_id: %s, Time: %s", Type,Device_id,Time);
        }
    }

}


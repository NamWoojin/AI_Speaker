package com.example.android_resapi.ui.apicall;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.android_resapi.httpconnection.GetRequest;
import com.example.android_resapi.ui.DetailItemData;
import com.example.android_resapi.ui.DetailScrollingActivity;
import com.example.android_resapi.ui.HealthInfoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GetLogMeal extends GetRequest{
    final static String TAG = "AndroidAPITest";
    String urlStr;
    String mode;
    int loadNum = 0;

    private Context mContext = null;

    public GetLogMeal(Activity activity, String urlStr, String mode,Context context) {
        super(activity);
        this.urlStr = urlStr;
        this.mode = mode;
        this.mContext = context;
    }

    public GetLogMeal(Activity activity, String urlStr, String mode,Context context,int loadNum) {
        super(activity);
        this.urlStr = urlStr;
        this.mode = mode;
        this.mContext = context;
        this.loadNum = loadNum;
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
        if (mode.equals("simple")) {

            Boolean breakfast = false;
            Boolean lunch = false;
            Boolean dinner = false;

            if (arrayList.size() > 0) {
                for (int i = 0; i < arrayList.size(); i++) {
                    if (arrayList.get(i).Type.equals("아침")) {
                        breakfast = true;
                    } else if (arrayList.get(i).Type.equals("점심")) {
                        lunch = true;
                    } else if (arrayList.get(i).Type.equals("저녁")) {
                        dinner = true;
                    }
                }
            } else {
                Toast.makeText(activity, "식사 정보가 없습니다.", Toast.LENGTH_SHORT).show();
            }
            ((HealthInfoActivity) mContext).SetHealthInfoMeal(breakfast, lunch, dinner);
        }

        else if (mode.equals("detail")) {
            ArrayList<DetailItemData> itemData = new ArrayList<>();
            String[] BreakfastTime = new String[]{"0","0","0","0","0","0","0"};
            String[] LunchTime = new String[]{"0","0","0","0","0","0","0"};
            String[] DinnerTime = new String[]{"0","0","0","0","0","0","0"};
            DetailItemData did;

            for (int i = 0; i < 14; i++) {
                did = new DetailItemData();
                Calendar day = Calendar.getInstance();
                day.add(Calendar.DATE, (loadNum) * (-14) - i);
                did.setMealDate(new java.text.SimpleDateFormat("yyyy-MM-dd").format(day.getTime()));
                itemData.add(did);
            }
            if (arrayList.size() > 0) {
                for (int i = 0; i < arrayList.size(); i++) {
                    for (int j = 0; j < itemData.size(); j++) {
                        if (arrayList.get(i).Time.substring(0, 10).equals(itemData.get(j).getMealDate())) {

                            if (arrayList.get(i).Type.equals("아침")) {
                                if(j < 7)
                                    BreakfastTime[j] = arrayList.get(i).Time.substring(11, 16);
                                itemData.get(j).setBreakfast(true);
                            } else if (arrayList.get(i).Type.equals("점심")){
                                if(j < 7)
                                    LunchTime[j] = arrayList.get(i).Time.substring(11, 16);
                                itemData.get(j).setLunch(true);
                            }else if (arrayList.get(i).Type.equals("저녁")){
                                if(j < 7)
                                    DinnerTime[j] = arrayList.get(i).Time.substring(11,16);
                                itemData.get(j).setDinner(true);
                            }

                            break;


                        }
                    }
                }
            }

            if(loadNum == 0 && arrayList.size() >0){
                ((DetailScrollingActivity) mContext).SetAverageMealTime(CalculateAverageTime(BreakfastTime),CalculateAverageTime(LunchTime), CalculateAverageTime(DinnerTime));
            }


            if (loadNum == 0) {
                ((DetailScrollingActivity) mContext).AddData(itemData);
            }
            else {
                ((DetailScrollingActivity) mContext).AddMoreData(itemData);
            }


        }
    }

    String CalculateAverageTime(String[] TimeArray){
        int countNum=0;
        int wholeTime = 0;
        int averageNum = 0;
        for(int i = 0; i< TimeArray.length;i++){
            if(!TimeArray[i].equals("0")) {
                wholeTime += CalculateMinutefromString(TimeArray[i]);
                ++countNum;
            }
        }
        if(countNum != 0)
            averageNum = wholeTime / countNum;

        return averageNum/60 + "시 "+averageNum % 60 +"분";

    }

    int CalculateMinutefromString(String st){
        String[] stArray = st.split(":");
        int time = 0;

        try{
            time = Integer.parseInt(stArray[0].trim());
            time = (time * 60) + Integer.parseInt(stArray[1]);
        }
        catch (NumberFormatException e){}
        Log.i(this.getClass().getName(),"++"+time);
        return time;
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


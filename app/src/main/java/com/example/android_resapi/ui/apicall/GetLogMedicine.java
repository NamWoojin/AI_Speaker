package com.example.android_resapi.ui.apicall;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.android_resapi.httpconnection.GetRequest;
import com.example.android_resapi.ui.DetailItemData;
import com.example.android_resapi.ui.DetailScrollingActivity;
import com.example.android_resapi.ui.HealthInfoActivity;
import com.example.android_resapi.ui.ListViewItem;
import com.example.android_resapi.ui.medicineListViewItem;

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

public class GetLogMedicine extends GetRequest {
    final static String TAG = "AndroidAPITest";
    String urlStr;
    String mode;
    Context mContext = null;
    public GetLogMedicine(Activity activity, String urlStr, String mode, Context context) {
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
        ArrayList<medicineListViewItem> mLVIarrayList = new ArrayList<>();
        medicineListViewItem mLVI;
        if(mode.equals("simple")){ //간단히 보기
            if(jsonString.length() >0){
                for(int i = 0;i<arrayList.size();i++){
                    mLVI = new medicineListViewItem();
                    mLVI.setMedicineName(arrayList.get(i).Name);
                    mLVI.setMedicineType(arrayList.get(i).Type);
                    mLVI.setMedicineCycle(arrayList.get(i).Cycle);
                    mLVIarrayList.add(mLVI);
                }
                Log.i(this.getClass().getName(),mLVIarrayList.size()+"+++++");
                ((HealthInfoActivity)mContext).SetHealtInfoMedicine(mLVIarrayList);
                ((HealthInfoActivity)mContext).setmLVI(mLVIarrayList);
            }
            else{

            }

        }

        else if(mode.equals("edit")) {
        }


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
                            jsonObject.getString("Medicine_Name"),
                            jsonObject.getString("Cycle")
                    );

                    output.add(thing);
                }
            }
            else{
                Toast.makeText(activity,"복약 정보가 없습니다.", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return output;
    }

    class Tag {
        String Type;
        String Device_id;
        String Name;
        String Cycle;
        String Time;

        public Tag(String type,String device_di,String name,String cycle) {

            this.Type = type;
            this.Device_id =device_di;
            this.Name = name;
            this.Cycle = cycle;

        }

        public String toString() {
            return String.format("Type : %s, Device_id: %s, Name: %s, Cycle: %s", Type,Device_id,Name,Cycle);
        }
    }

}


package com.example.android_resapi.ui.apicall;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.example.android_resapi.R;
import com.example.android_resapi.httpconnection.GetRequest;
import com.example.android_resapi.ui.CareMembersListViewAdapter;

public class GetLog extends GetRequest {
    final static String TAG = "AndroidAPITest";
    String urlStr;
    public GetLog(Activity activity, String urlStr) {
        super(activity);
        this.urlStr = urlStr;
    }

    @Override
    protected void onPreExecute() {
        try {
            url = new URL(urlStr);
            Log.i(TAG,"urlStr="+urlStr);
            Toast.makeText(activity,"정보 가져오는 중...", Toast.LENGTH_LONG).show();
        } catch (MalformedURLException e) {
            Toast.makeText(activity,"URL is invalid:"+urlStr, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(String jsonString) {
        ArrayList<Tag> arrayList = getArrayListFromJSONString(jsonString);
        String socialWorkerName = arrayList.get(0).SocialWorkerName;
        TextView SoWoNameTextView = (TextView)activity.findViewById(R.id.socialWorkerNameTextView_id);
        SoWoNameTextView.setText("사회복지사 "+socialWorkerName+"님");

        EditText WorkerNameEditText = (EditText)activity.findViewById(R.id.EditName_id);
        ListView CareMembersListView = (ListView) activity.findViewById(R.id.CareMembersListView_id);
        CareMembersListViewAdapter CMListViewAdapter = new CareMembersListViewAdapter();

        for(int i = 0; i< arrayList.size();i++)
            CMListViewAdapter.addItem(arrayList.get(i).MemberName, arrayList.get(i).MemberGender,arrayList.get(i).MemberAge);

        CareMembersListView.setAdapter(CMListViewAdapter);
    }

    protected ArrayList<Tag> getArrayListFromJSONString(String jsonString) {
        ArrayList<Tag> output = new ArrayList();
        try {
            jsonString = jsonString.substring(1,jsonString.length()-1);
            jsonString = jsonString.replace("\\\"","\"");


            Log.i(TAG, "jsonString="+jsonString);

            JSONObject root = new JSONObject(jsonString);
            JSONArray jsonArray = root.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = (JSONObject)jsonArray.get(i);

                Tag thing = new Tag(jsonObject.getString("RegisterTime"),
                        jsonObject.getString("MemberGender"),
                        jsonObject.getString("SocialWorkerName"),
                        jsonObject.getString("MemberAge"),
                        jsonObject.getString("MemberName")

                );

                output.add(thing);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return output;
    }

    class Tag {
        String SocialWorkerName;
        String MemberName;
        String MemberAge;
        String MemberGender;
        String RegisterTime;

        public Tag(String time,String member_Gender,String worker_Name, String member_Age, String member_Name) {


            SocialWorkerName = worker_Name;
            MemberName = member_Name;
            MemberAge = member_Age;
            MemberGender = member_Gender;
            RegisterTime = time;

        }

        public String toString() {
            return String.format("WorkerName : %s, MemberName: %s, MemberAge: %s,  MemberGender: %s, RegisterTime: %s", SocialWorkerName, MemberName,MemberAge,MemberGender,RegisterTime);
        }
    }
}


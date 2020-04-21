package com.example.android_resapi.ui.apicall;

import android.app.Activity;
import android.util.Log;
import android.view.View;
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

public class GetLogCareMembers extends GetRequest {
    final static String TAG = "AndroidAPITest";
    String urlStr;
    String Worker_Name;
    Boolean getJson = true;

    TextView NonInfoTextView = (TextView)activity.findViewById(R.id.NonInfoTextView_id) ;
    ListView CareMembersListView = (ListView) activity.findViewById(R.id.CareMembersListView_id);
    public GetLogCareMembers(Activity activity, String urlStr,String Worker_Name) {
        super(activity);
        this.urlStr = urlStr;
        this.Worker_Name = Worker_Name;
    }

    @Override
    protected void onPreExecute() {
        try {
            url = new URL(urlStr);
            Log.i(TAG,"urlStr="+urlStr);
            CareMembersListView.setVisibility(View.GONE);
            NonInfoTextView.setVisibility(View.VISIBLE);
            NonInfoTextView.setText("정보 가져오는 중...");
        } catch (MalformedURLException e) {
            Toast.makeText(activity,"URL is invalid:"+urlStr, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(String jsonString) {
        ArrayList<Tag> arrayList = getArrayListFromJSONString(jsonString);
        TextView WorkerNameTextView= (TextView)activity.findViewById(R.id.socialWorkerNameTextView_id);
        WorkerNameTextView.setText("사회복지사 "+Worker_Name+"님");



        CareMembersListViewAdapter CMListViewAdapter = new CareMembersListViewAdapter();

        if(getJson) {
            CareMembersListView.setVisibility(View.VISIBLE);
            NonInfoTextView.setVisibility(View.GONE);
            for (int i = 0; i < arrayList.size(); i++)
                CMListViewAdapter.addItem(arrayList.get(i).MemberName, arrayList.get(i).MemberGender, arrayList.get(i).MemberAge,arrayList.get(i).Device_Id);

            CareMembersListView.setAdapter(CMListViewAdapter);
        }
        else{
            CareMembersListView.setVisibility(View.GONE);
            NonInfoTextView.setVisibility(View.VISIBLE);
            NonInfoTextView.setText("정보가 없습니다.");
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
                getJson = true;
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = (JSONObject)jsonArray.get(i);

                    Tag thing = new Tag(jsonObject.getString("RegisterTime"),
                            jsonObject.getString("MemberGender"),
                            jsonObject.getString("SocialWorkerName"),
                            jsonObject.getString("Device_Id"),
                            jsonObject.getString("MemberAge"),
                            jsonObject.getString("MemberName")

                    );

                    output.add(thing);
                }
            }
            else{
                getJson = false;
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
        String Device_Id;
        String MemberGender;
        String RegisterTime;

        public Tag(String time,String member_Gender,String worker_Name,String Device_Id, String member_Age, String member_Name) {


            this.SocialWorkerName = worker_Name;
            this.MemberName = member_Name;
            this.Device_Id = Device_Id;
            this.MemberAge = member_Age;
            this.MemberGender = member_Gender;
            this.RegisterTime = time;

        }

        public String toString() {
            return String.format("WorkerName : %s, MemberName: %s, MemberAge: %s,  MemberGender: %s, RegisterTime: %s", SocialWorkerName, MemberName,MemberAge,MemberGender,RegisterTime);
        }
    }
}


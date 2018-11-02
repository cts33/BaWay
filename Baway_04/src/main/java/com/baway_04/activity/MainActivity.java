package com.baway_04.activity;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baway_04.R;
import com.baway_04.activity.BdMapbdActivity;
import com.baway_04.activity.GaodeMapActivity;
import com.baway_04.utils.Urls;
import com.baway_04.adapter.XutilAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @ViewInject(R.id.listview)
    private ListView listView;

    private List<String> itemList = new ArrayList<>();
    private XutilAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        x.view().inject(this);

        initView();
//        initXutilsQuest();
    }

    public void initView(){
        adapter = new XutilAdapter(this);
//        adapter.setList(itemList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String className =   adapter.getKey(i);
                Intent intent = new Intent();
                String packageName = "com.baway_04.";
                intent.setClassName(packageName+"MainActivity",packageName+className);
                startActivity(intent);

            }
        });
    }

    public void initXutilsQuest(){
        RequestParams requestParams = new RequestParams(Urls.url1);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                LogUtil.e(result+"-is-mainThread="+(Looper.getMainLooper()==Looper.myLooper()));

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray objArr = jsonObject.optJSONArray("data");

                    for (int x=0;x<objArr.length();x++){
                        JSONObject object = objArr.getJSONObject(x);

                        itemList.add(object.optString("pic"));
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e(cex.getMessage());
            }

            @Override
            public void onFinished() {
                LogUtil.e("onFinished");
            }
        });
    }
}

package com.apkplug.game.speedsliding.lenovo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.apkplug.pay.bean.PayPlatform;
import com.apkplug.pay.inter.pay.PayManagerService;
import com.apkplug.pay.inter.pay.ResultCallBack;
import com.apkplug.pay.inter.pay.model.PayResponse;
import com.apkplug.pay.manager.PayManager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = "MainActivity";
    private final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).build();
    private EditText mEditText;
    private ListView mListView ;
    private ArrayList<String> mList = new ArrayList<String>();
    private PayListAdapter mPayListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listView);

        mPayListAdapter = new PayListAdapter(MainActivity.this, mList);

        mListView.setAdapter(mPayListAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                doPay("https://api.payplug.cn",mList.get(position));
            }
        });
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mList.clear();
                List<PayPlatform> payList = PayManager.getInstance().getPayList(PayManagerService.available);
                for (PayPlatform payPlatform : payList) {
                    mList.add(payPlatform.getName());
                }
                Log.e(TAG,mList.toString());
                mPayListAdapter.notifyDataSetChanged();

            }
        });
    }



    private void doPay(String host,String channel){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = String.valueOf(System.currentTimeMillis());
        String orderid=dateFormat.format(new Date()) + "000";
        ParamsBuilder pb =new ParamsBuilder();
        pb.add("app_id", "1PdtD8tsX1S");
        pb.add("container_id", "yang");
        pb.add("order_no", orderid);
        pb.add("commodity_id", "1");
        pb.add("amount", "1");
        pb.add("count", "1");
        pb.add("channel", channel);
        pb.add("currency", "cny");
        pb.add("subject", "cwt");
        pb.add("body", "chuweitao");
        pb.add("webhook_url", host+"/test");
        pb.add("description", "chuweitao");
        pb.add("_time", time);

        String str = pb.orderByNameAscending() + "&app_secret=" + "BQgU7lzwobsycXtTWdF6v0NYMVCDf4nm";

        RequestBody formBody = new FormBody.Builder()
                .add("app_id", "1PdtD8tsX1S")
                .add("container_id", "yang")
                .add("order_no", orderid)
                .add("commodity_id", "1")
                .add("amount", "1")
                .add("count", "1")
                .add("channel", channel)
                .add("currency", "cny")
                .add("subject", "cwt")
                .add("body", "chuweitao")
                .add("webhook_url", host+"/test")
                .add("description", "chuweitao")
                .add("_time", time)
                .add("sign",MD5.getMessageDigest(str.getBytes()))
                .build();

        Request request = new Request.Builder()
                .url(host+"/v1/order/create.json")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Request request, IOException throwable) {
                throwable.printStackTrace();
            }

            @Override public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                String str = response.body().string();
                Log.e(TAG,str);
                payWithDefaultResultCallBack(str);
            }
        });
    }

    private  void payWithDefaultResultCallBack(String orderInfo)
    {

        try {

            PayManager.getInstance().doPay(orderInfo, new ResultCallBack() {
                @Override
                public void onResult(int resultCode, PayResponse resultInfo) {
                    if (resultCode == ResultCallBack.PAY_SUCCESS) {

                        String result=resultInfo.getParam("result");
                        if(result!=null){
                            Toast.makeText(MainActivity.this, "支付成功:"+result, Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        }



                    } else if (resultCode == ResultCallBack.PAY_CANCEL) {
                        String result=resultInfo.getParam("result");
                        if(result!=null){
                            Toast.makeText(MainActivity.this, "支付取消:"+result, Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "支付取消", Toast.LENGTH_SHORT).show();
                        }

                    } else if (resultCode == ResultCallBack.PAY_FAIL) {


                        String result=resultInfo.getParam("result");
                        if(result!=null){

                            Toast.makeText(MainActivity.this, "支付失败:"+result, Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        }

                    } else if (resultCode == ResultCallBack.PAY_HANDLING) {

                        String result=resultInfo.getParam("result");

                        if(result!=null){
                            Toast.makeText(MainActivity.this, "支付处理中:"+result, Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "支付处理中", Toast.LENGTH_SHORT).show();
                        }

                    } else if (resultCode == ResultCallBack.PLUG_VERSION_TO_LOW) {
                        Toast.makeText(MainActivity.this, "支付失败:"+resultInfo.getLogs(), Toast.LENGTH_SHORT).show();


                    }else if (resultCode == ResultCallBack.PAY_PLUG_NOFIND) {
                        Toast.makeText(MainActivity.this, "支付失败:"+resultInfo.getLogs(), Toast.LENGTH_SHORT).show();


                    }else if (resultCode == ResultCallBack.PAY_CONFIG_PARAMS_ERROR) {
                        Toast.makeText(MainActivity.this, "支付失败:"+resultInfo.getLogs(), Toast.LENGTH_SHORT).show();


                    }else if (resultCode == ResultCallBack.PAY_REQUEST_PARAMS_ERROR) {
                        Toast.makeText(MainActivity.this, "支付失败:"+resultInfo.getLogs(), Toast.LENGTH_SHORT).show();


                    }

                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

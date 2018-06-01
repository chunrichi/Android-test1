package com.example.test6;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;


public class MainActivity extends Activity implements View.OnClickListener {

    private ListView chat_list;
    private EditText et_message;
    private Button btn_send;
    //    ChatAdapter初始化，用于添加list内容
    private ChatAdapter mAdapter = null;
    //    信息存储
    private List<ChatMessage> mData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chat_list = (ListView) findViewById(R.id.chat_list);
        et_message = (EditText)findViewById(R.id.et_message);
        btn_send = (Button)findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);
        initData();

    }

    private void initData() {
        mData =  new ArrayList<ChatMessage>();
        ChatMessage chatMessage = new ChatMessage("您好！",1,new Date());
        mData.add(chatMessage);
        Log.e("chatMessage", chatMessage.getaSpeak());
        Log.e("mData", chatMessage.getaSpeak());
        mAdapter = new ChatAdapter(this,mData);
//        mAdapter = new ChatAdapter( MainActivity.this,(LinkedList<ChatMessage>) mData);
        chat_list.setAdapter(mAdapter);
    }


    @Override
    public void onClick(View v) {
        final String send_message = et_message.getText().toString();
        if(!"".equals(send_message)) {
            ChatMessage chatMessage = new ChatMessage(send_message,0,new Date());
            mData.add(chatMessage);
            mAdapter.notifyDataSetChanged();
//            chat_list.setSelection(mData.size());
            et_message.setText("");
            Log.e("--------------", chatMessage.getaSpeak());
            Log.e("--------------", mData.toString());
            Log.e("--------------",chat_list.toString());

            //        线程定义
            new Thread(new Runnable() {
                @Override
                public void run() {
//                调用函数，获得该城市的 JSON 字符串
                    final String json = getAanswer(send_message);
                    Log.e("--------",json);
                    if (json == null)
                        Toast.makeText(MainActivity.this, "未得到回复", Toast.LENGTH_SHORT).show();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                        对JSON字符串进行处理和输出
                            doJson(json);
                        }
                    });
                }
            }).start();
        }
    }

    public String getAanswer(String question){
//        根据API进行初始化 URL 和 header内容（header主要是 appcode内容）
//        使用的API为 阿里云 API市场中的一个免费API：
//        智能回复接口

        String host = "http://jisuznwd.market.alicloudapi.com";
        String path = "/iqa/query";
//        个人的appcode内容
        String appcode = "37fa9494a7c44f1bb0e451fba133d4bf";
        String querys = "question=" + question ;
        String url =    host + path + '?' + querys;
//        在平台上显示调试信息
        Log.d("URL", url);

        try {
            OkHttpClient client = new OkHttpClient();
            //获取请求对象
            Request request = new Request.Builder()
                    .url(url)
//                    添加header 信息
                    .addHeader("Authorization", "APPCODE " + appcode)
                    .build();
            //        在平台上显示调试信息
            Log.d("request",request.toString());
            Log.d("HEADER",request.headers().toString());
//            请求API 获取返回结果
            ResponseBody body = client.newCall(request).execute().body();

//            此处踩坑，string直接获得的json
            return body.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void doJson(String json) {
        try {
//            对字符串状态的JSON 进行处理，转化成JSON Obeject
            JSONObject jsonObject = new JSONObject(json);
            JSONObject result = jsonObject.getJSONObject("result");
            String content = result.getString("content");
            ChatMessage chatMessage = new ChatMessage(content,1,new Date());
            Log.e("content----",content);
            mData.add(chatMessage);
            mAdapter.notifyDataSetChanged();

        }catch(JSONException e){
        // Recovery
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

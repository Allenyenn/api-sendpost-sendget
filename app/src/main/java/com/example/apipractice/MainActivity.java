package com.example.apipractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private class Data{
        int id;
        String data;

    }
    TextView textView;
    OkHttpClient client=new OkHttpClient().newBuilder().build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendPOST();
        sendGET();
    }
    private void sendPOST(){
        textView=findViewById(R.id.message);
        FormBody formBody=new FormBody.Builder()
                .add("id","123")
                .add("data","text_by_Allenyeeen")
                .build();
        //建立Request,設置連線資訊(包含formbody)
        Request request=new Request.Builder()
                .url("https://85dd-2001-b011-b800-5564-b8d1-27f-68e0-7528.ngrok.io/api/store")
                .post(formBody)
                .build();
        Call call=client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                //連線失敗
                textView.setText(e.toString());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
            //連線成功
                sendGET();
            }
        });
    }
    private void sendGET(){
        textView=findViewById(R.id.message);
        //建立Request,設置連線資訊
        Request request=new Request.Builder()
                .url("https://85dd-2001-b011-b800-5564-b8d1-27f-68e0-7528.ngrok.io")
                .build();
        //建立Call
        Call call=client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                //連線失敗
                textView.setText(e.toString());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //連線成功
                String print="";
                String result=response.body().string();

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //textView.setText(result);
                        List<Data>list=new Gson().fromJson(result,new TypeToken<List<Data>>(){
                        }.getType());
                        for (Data data:list){
                            textView.setText(textView.getText()+"\n"+data.data);
                        }

                    }
                });
            }
        });
    }
}
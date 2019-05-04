package com.vczyh.networktest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private TextView responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        responseText = findViewById(R.id.response_text);
        Button sendRequest = findViewById(R.id.send_request);
        sendRequest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_request) {
//            sendRequestWithHttpURLConnection();
            sendRequestWithOkHttp();
        }
    }

    private void sendRequestWithHttpURLConnection() {
        // 开启线程发起网络请求
        new Thread(() -> {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL("https://www.baidu.com");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(8000);
                connection.setReadTimeout(8000);
                InputStream in = connection.getInputStream();
                //读取数据流
                reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder response = new StringBuilder();
                String line = "aaa";
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                showResponse(response.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }).start();
    }

    private void sendRequestWithOkHttp() {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
//                        .url("https://www.baidu.com")
//                        .url("https://static.vczyh.com/get_data.xml")
                        .url("https://static.vczyh.com/get_data.json")
                        .build();
                Response response = client.newCall(request).execute();
                String responseData = response.body().string();
                showResponse(responseData);
//                parseXMLWithPull(responseData);
//                parseXMLWithSAX(responseData);
//                parseJSONWithJSONObject(responseData);
                parseJSONWithGSON(responseData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void showResponse(final String response) {
        Log.d(TAG, "showResponse: response is " + response);
        runOnUiThread(() -> {
            // 在这里进行UI操作 将结果显示到界面上
            // 不允许在子线程中进行UI操作 通过这个方法切换到主线程
            responseText.setText(response);
        });
    }

    private void parseXMLWithPull(String xmlData) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType = xmlPullParser.getEventType();
            String id = "";
            String name = "";
            String version = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    // 开始解析某个节点
                    case XmlPullParser.START_TAG:
                        if ("id".equals(nodeName)) {
                            id = xmlPullParser.nextText();
                        } else if ("name".equals(nodeName)) {
                            name = xmlPullParser.nextText();
                        } else if ("version".equals(nodeName)) {
                            version = xmlPullParser.nextText();
                        }
                        break;
                    // 完成解析某个节点
                    case XmlPullParser.END_TAG:
                        if ("app".equals(nodeName)) {
                            Log.d(TAG, "parseXMLWithPull: id is " + id);
                            Log.d(TAG, "parseXMLWithPull: name is " + name);
                            Log.d(TAG, "parseXMLWithPull: version is " + version);
                        }
                        break;
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseXMLWithSAX(String xmlData) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader xmlReader = factory.newSAXParser().getXMLReader();
            ContentHandler contentHandler = new ContentHandler();
            // 将ContentHandler实例设置到XMLReader中
            xmlReader.setContentHandler(contentHandler);
            // 开始解析
            xmlReader.parse(new InputSource(new StringReader(xmlData)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String version = jsonObject.getString("version");
                Log.d(TAG, "parseJSONWithJSONObject: id is " + id);
                Log.d(TAG, "parseJSONWithJSONObject: name is " + name);
                Log.d(TAG, "parseJSONWithJSONObject: version is " + version);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<App>>() {
        }.getType();
        List<App> appList = gson.fromJson(jsonData, type);
        for (App app : appList) {
            Log.d(TAG, "parseJSONWithGSON: id is " + app.getId());
            Log.d(TAG, "parseJSONWithGSON: name is " + app.getName());
            Log.d(TAG, "parseJSONWithGSON: version is " + app.getVersion());
        }

    }


}

package com.caiyi.lottery;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.blankj.utilcode.util.SizeUtils;
import com.caiyi.data.TrendData;
import com.caiyi.ui.LotteryTrendView;
import com.caiyi.ui.VerticleTrendView;
import com.lottery9188.Activity.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Owen on 2019/11/13
 */
public class LotteryTrendActivity2 extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();
    private LotteryTrendView ltv_trendView;
    private static ArrayList arrayList;
    private VerticleTrendView ltv_leftView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lotto_trend_two);
        ltv_trendView= (LotteryTrendView) findViewById(R.id.ltv_trendView);
        ltv_leftView= (VerticleTrendView) findViewById(R.id.ltv_leftView);
        if(arrayList!=null&&arrayList.size()>0){
            mHandler.sendMessage(mHandler.obtainMessage(120, arrayList));
        }else{
            loadData();
        }
    }


    private void loadData() {
        // 根据01/30.xml 或者是01/50.xm可以调整数字
        String url = "http://mobile.9188.com/data/app/zst/01/50.xml";
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                try {
                    a(inputStream);
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
            }
        });
    }

    protected void a(InputStream inputStream) throws XmlPullParserException, IOException {
        arrayList = new ArrayList();
        Collection arrayList2 = new ArrayList();
        XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
        newPullParser.setInput(inputStream, "utf-8");
        TrendData r0;
        for (int eventType = newPullParser.getEventType(); 1 != eventType; eventType = newPullParser.next()) {
            String name = newPullParser.getName();
            if (eventType == 2) {
                if ("row".equals(name)) {
                    TrendData trendData = new TrendData();
                    trendData.setType("row");
                    String attributeValue = newPullParser.getAttributeValue(null, "pid");
                    if (!(TextUtils.isEmpty(attributeValue) || attributeValue.length() <= 4)) {
                        attributeValue = attributeValue.substring(4);
                    }
                    trendData.setPid(attributeValue);
                    trendData.setRed(newPullParser.getAttributeValue(null, "red"));
                    trendData.setBlue(newPullParser.getAttributeValue(null, "blue")+",17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32");
                    trendData.setBalls(newPullParser.getAttributeValue(null, "balls"));
                    trendData.setOes(newPullParser.getAttributeValue(null, "oe"));
                    trendData.setBss(newPullParser.getAttributeValue(null, "bs"));
                    trendData.setOne(newPullParser.getAttributeValue(null, "one"));
                    trendData.setTwo(newPullParser.getAttributeValue(null, "two"));
                    trendData.setThree(newPullParser.getAttributeValue(null, "three"));
                    trendData.setCodes(newPullParser.getAttributeValue(null, "codes"));
                    trendData.setSum(newPullParser.getAttributeValue(null, "sum"));
                    trendData.setSpace(newPullParser.getAttributeValue(null, "space"));
                    trendData.setNum(newPullParser.getAttributeValue(null, "num"));
                    trendData.setTimes(newPullParser.getAttributeValue(null, "times"));
                    trendData.setForm(newPullParser.getAttributeValue(null, "form"));
                    arrayList.add(trendData);
                }

            }
        }
        //人为加入一行作为X轴标题栏
        TrendData bean=new TrendData();
        bean.setBlue("01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32");
        bean.setPid("00");
        bean.setType("titleRow");
        arrayList.add(0,bean);
        arrayList.addAll(arrayList2);
        mHandler.sendMessage(mHandler.obtainMessage(120, arrayList));
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message paramMessage) {
            super.handleMessage(paramMessage);
            if(ltv_trendView!=null&&arrayList!=null&&arrayList.size()>0){
             ViewGroup.LayoutParams params= ltv_trendView.getLayoutParams();
             params.width= SizeUtils.dp2px(33)*32;
             params.height=SizeUtils.dp2px(33)*arrayList.size();
             ltv_trendView.setLayoutParams(params);
             ViewGroup.LayoutParams params1=ltv_leftView.getLayoutParams();
             params1.width=SizeUtils.dp2px(60);
             params1.height=SizeUtils.dp2px(33)*arrayList.size();
             ltv_leftView.setLayoutParams(params1);
             ltv_leftView.updateData((ArrayList) paramMessage.obj);
             ltv_trendView.updateData((ArrayList) paramMessage.obj);
            }
        }
    };
}

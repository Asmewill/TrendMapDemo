package com.caiyi.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.SizeUtils;
import com.caiyi.data.TrendData;
import com.lottery9188.Activity.R;

import java.util.ArrayList;

public class VerticleTrendView extends View  {
    protected int mCApCount;
    protected int mCAvgYilou;
    protected int mCAvgYilouBg;
    protected int mCBallBlue;
    protected int mCBallRed;
    protected int mCBallSelectedBlue;
    protected int mCBallSelectedRed;
    protected int mCBallSelectedStroke;
    protected int mCEvenY;
    protected int mCLianchu;
    protected int mCMaxYilou;
    protected int mCOddContent;
    protected int mCOddY;
    protected int mCXbottomTextBlue;
    protected int mCXbottomTextRed;
    protected int mCYText;
    protected int mYItemHeight;
    protected int mYItemWidth;
    protected int mYTextSize;
    protected Paint mPaint = new Paint(1);
    protected final Picture mPicY = new Picture();
    private ArrayList<TrendData> mTrendData;
    protected Rect mRect = new Rect();

    public VerticleTrendView(Context context) {
        this(context, null, 0);
    }
    public VerticleTrendView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }
    public VerticleTrendView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Resources resources = context.getResources();
        this.mCYText = resources.getColor(R.color.lottery_title_color);
        this.mCOddY = resources.getColor(R.color.trend_list_odd);
        this.mCEvenY = resources.getColor(R.color.trend_list_even);
        this.mCOddContent = resources.getColor(R.color.trend_line_odd_bg);
        this.mCBallRed = resources.getColor(R.color.trend_ball_red);
        this.mCBallBlue = resources.getColor(R.color.trend_ball_blue);
        this.mCBallSelectedRed = resources.getColor(R.color.trend_x_ball_red);
        this.mCBallSelectedBlue = resources.getColor(R.color.trend_x_ball_blue);
        this.mCBallSelectedStroke = resources.getColor(R.color.trend_x_ball_stroke);
        this.mCXbottomTextRed = resources.getColor(R.color.trend_x_red_text);
        this.mCXbottomTextBlue = resources.getColor(R.color.trend_x_blue_text);
        this.mCApCount = resources.getColor(R.color.trend_max_count_color);
        this.mCAvgYilou = resources.getColor(R.color.trend_avg_yilou_color);
        this.mCAvgYilouBg = resources.getColor(R.color.trend_avg_yilou_bg);
        this.mCMaxYilou = resources.getColor(R.color.trend_max_yilou_color);
        this.mCLianchu = resources.getColor(R.color.trend_max_lianchu_color);
        //固定设置宽度和高度
        this.mYItemWidth = SizeUtils.dp2px(60);
        this.mYItemHeight = SizeUtils.dp2px(33);
        this.mYTextSize = SizeUtils.dp2px(12);

    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("LotteryView","Event:onDraw");
        drawY(canvas);
    }
    /**
     * 绘制最左边Y轴期号
     */
    public void drawY(Canvas canvas) {
        if (this.mTrendData != null && this.mTrendData.size() >= 4) {
            Canvas beginRecording = this.mPicY.beginRecording(this.mYItemWidth, (this.mYItemHeight * this.mTrendData.size()));//设置画布的宽高
            this.mPaint.setStyle(Paint.Style.FILL);
            int size = this.mTrendData.size();
            for (int i = 0; i < size; i++) {
                int i2 = i * this.mYItemHeight;//累加高度
                this.mRect.set(0, i2, this.mYItemWidth, this.mYItemHeight + i2);//递增式设置每一期期号的正方形范围
                String type = ((TrendData) this.mTrendData.get(i)).getType();
                if (type.equals("row")) {
                    type = ((TrendData) this.mTrendData.get(i)).getPid();
                    if (i % 2 == 0) {
                        this.mPaint.setColor(this.mCOddY);//画布白灰背景
                    } else {
                        this.mPaint.setColor(this.mCEvenY);//画布深灰背景
                    }
                    beginRecording.drawRect(this.mRect, this.mPaint);
                    this.mPaint.setColor(this.mCYText);//设置绘制文字的颜色为黑色
                } else if (type.equals("titleRow")) {
                    type = "期号";
                    this.mPaint.setColor(-1);//画布白色
                    beginRecording.drawRect(this.mRect, this.mPaint);
                    this.mPaint.setColor(this.mCApCount);//设置绘制文字的颜色为蓝色
                }
                this.mPaint.setTextSize((float) this.mYTextSize);
                drawText2Rect(type, beginRecording, this.mRect, this.mPaint);
            }
            this.mPicY.endRecording();
            int width = mPicY.getWidth();
            int height=mPicY.getHeight();
            canvas.save();
            this.mRect.set(0, 0, width, height);
            canvas.drawPicture(mPicY);
        }
    }

    protected void drawText2Rect(String str, Canvas canvas, Rect rect, Paint paint) {
        if (!TextUtils.isEmpty(str)) {
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            float textWidth=paint.measureText(str);
            float textX=rect.centerX()-textWidth/2;
            canvas.drawText(str, textX, (((float) rect.top) + (((((float) (rect.bottom - rect.top)) - fontMetrics.bottom) + fontMetrics.top) / 2.0f)) - fontMetrics.top, paint);
        }
    }
    public void  updateData(ArrayList<TrendData> mTrendData){
        if(mTrendData!=null&&mTrendData.size()>0){
            this.mTrendData=mTrendData;
            this.invalidate();
        }
    }
}

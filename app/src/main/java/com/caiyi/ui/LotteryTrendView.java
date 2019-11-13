package com.caiyi.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
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

public class LotteryTrendView extends View  {
    protected int mCApCount;
    protected int mCAvgYilou;
    protected int mCAvgYilouBg;
    protected int mCBallBlue;
    protected int mCBallRed;
    protected int mCBallSelectedBlue;
    protected int mCBallSelectedRed;
    protected int mCBallSelectedStroke;
    protected int mCDiv;
    protected int mCEvenY;
    protected int mCLianchu;
    protected int mCLianchuBg;
    protected int mCMaxYilou;
    protected int mCOddContent;
    protected int mCOddY;
    protected int mCTextSize;
    protected int mCXbottomTextBlue;
    protected int mCXbottomTextRed;
    protected int mCYText;

    protected int mXItemHeight;
    protected int mXItemWidth;
    protected int mYItemHeight;
    protected int mDefBallSize;
    protected Paint mPaint = new Paint(1);
    protected final Picture mPicContent = new Picture();
    private Path mPathPoint = new Path();
    private boolean mShowYilou=true;
    private int blueCount = 16;
    private ArrayList<TrendData> mTrendData;
    protected Rect mRect = new Rect();

    public LotteryTrendView(Context context) {
        this(context, null, 0);
    }
    public LotteryTrendView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }
    public LotteryTrendView(Context context, AttributeSet attributeSet, int i) {
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
        this.mCLianchuBg = resources.getColor(R.color.trend_max_lianchu_bg);
        this.mCDiv = resources.getColor(R.color.trend_divider);
        this.mXItemWidth = SizeUtils.dp2px(33);
        this.mXItemHeight =SizeUtils.dp2px(33);
        this.mYItemHeight =SizeUtils.dp2px(33);
        this.mCTextSize =SizeUtils.dp2px(14);
        this.mDefBallSize = SizeUtils.dp2px(12);
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i("LotteryView","Event:onMeasure");
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("LotteryView","Event:onDraw1");
        drawContent(canvas);
        Log.i("LotteryView","Event:onDraw2");
    }
    /**
     * 绘制内容
     */
    public void drawContent(Canvas canvas) {
        if(mTrendData!=null&&mTrendData.size()>0){
            int i;
            int i2 = (this.mXItemWidth * (this.blueCount));//总宽度
            Canvas beginRecording = this.mPicContent.beginRecording(i2, (this.mYItemHeight * this.mTrendData.size()));
            this.mPaint.setTextSize((float) this.mCTextSize);
            this.mPaint.setStyle(Paint.Style.FILL);
            int i3 = this.blueCount;
            int size = this.mTrendData.size();
            //绘制X轴轴线,绘制白灰上下交错的画布
            for (i = 0; i <= size; i++) {
                int i4 = i * this.mXItemHeight;
                    this.mRect.set(0, i4, i2, this.mXItemHeight + i4);
                    if(i==0){
                        this.mPaint.setColor(this.mCLianchu);//设置画布背景灰色
                    }else{
                        if (i % 2 == 0) {
                            this.mPaint.setColor(-1);//设置画布背景白色
                        } else {
                            this.mPaint.setColor(this.mCOddContent);//设置画布背景灰色
                        }
                    }
                    beginRecording.drawRect(this.mRect, this.mPaint);//画画布
                    this.mPaint.setColor(mCXbottomTextRed);
                    beginRecording.drawLine(0.0f, (float) i4, (float) i2, (float) i4, this.mPaint);//Y轴不变画X轴横线
            }
            //  绘制Y轴轴线
            int size2 = this.mTrendData.size() * this.mXItemWidth;//总宽度
            for (i = 0; i <= i3; i++) {
                int i5 = i * this.mXItemWidth;//逐渐累加的宽度
                if (i == 0) {
                    this.mPaint.setColor(this.mCXbottomTextBlue);
                    beginRecording.drawLine((float) i5, 0.0f, (float) i5, (float) size2, this.mPaint);//绘制第一条Y轴轴线
                }  else {
                    this.mPaint.setColor(this.mCAvgYilou);
                    beginRecording.drawLine((float)i5, 0.0f, (float) i5, (float) size2, this.mPaint);//绘制第二条以后的Y轴轴线
                }
            }
            i = (size - 4) * this.mXItemHeight;
            this.mPaint.setColor(-1);
            this.mRect.set(0, i, i2,  + i);
            //绘制二球之间的连接线
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mPaint.setColor(this.mCXbottomTextBlue);
            beginRecording.drawPath(this.mPathPoint, this.mPaint);
            this.mPaint.setStyle(Paint.Style.FILL);

            i = this.mTrendData.size();
            this.mPaint.setStyle(Paint.Style.FILL);
            for (int i6 = 0; i6 < i; i6++) {
                int i7;
                String str;
                TrendData trendData = (TrendData) this.mTrendData.get(i6);
                String[] split2 = trendData.getBlue().split(",");
                int i8 = this.mXItemHeight * i6;//Y轴高度逐渐累加
                //画蓝球
                for (i7 = 0; i7 < split2.length; i7++) {
                    this.mRect.set(((i7) * this.mXItemWidth) , i8, (((i7) + 1) * this.mXItemWidth), this.mXItemHeight + i8);//设定画球的区域正方形
                    if ("titleRow".equals(trendData.getType())) {//画标题栏文字
                        this.mPaint.setColor(-1);
                        drawText2Rect(split2[i7], beginRecording, this.mRect, this.mPaint);
                    } else if (split2[i7].equals("0")) {//画篮球
                        this.mPaint.setColor(this.mCBallBlue);
                        beginRecording.drawCircle(this.mRect.exactCenterX(), (float) this.mRect.centerY(), (float) this.mDefBallSize, this.mPaint);//画圆形
                        this.mPaint.setColor(-1);//白色
                        if (i7 < 9) {
                            str = "0" + (i7 + 1);
                        } else {
                            str = "" + (i7 + 1);

                        }
                        drawText2Rect(str, beginRecording, this.mRect, this.mPaint);//画篮球中间的白色数字
                    } else if (this.mShowYilou) {//画其他数字
                        this.mPaint.setColor(this.mCXbottomTextRed);
                        drawText2Rect(split2[i7], beginRecording, this.mRect, this.mPaint);
                    }
                }
            }


            this.mPicContent.endRecording();
            int width = mPicContent.getWidth();
            int height=mPicContent.getHeight();
            canvas.save();
            this.mRect.set(0, 0, width, height);
            canvas.drawPicture(mPicContent);
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
            this.blueCount=mTrendData.get(0).getBlue().split(",").length;
            this.mPathPoint.reset();
            for (int i = 0; i < mTrendData.size(); i++) {
                TrendData trendData = (TrendData) mTrendData.get(i);
                if ("row".equals(trendData.getType())) {
                    String[] split = trendData.getBlue().split(",");
                    for (int i2 = 0; i2 < split.length; i2++) {
                        if (split[i2].equals("0")) {
                            float f = ((( + 0.5f) + ((float) i2)) * ((float) this.mXItemWidth));
                            float f2 = (((float) i) + 0.5f) * ((float) this.mXItemHeight);
                            if (i == 1) {
                                this.mPathPoint.moveTo(f, f2);
                            } else {
                                this.mPathPoint.lineTo(f, f2);
                            }
                        }
                    }
                }
            }
            this.invalidate();
        }
    }
}

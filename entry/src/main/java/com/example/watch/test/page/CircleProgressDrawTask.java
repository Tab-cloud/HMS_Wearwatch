package com.example.watch.test.page;

import ohos.agp.components.Component;
import ohos.agp.render.Arc;
import ohos.agp.render.Canvas;
import ohos.agp.render.LinearShader;
import ohos.agp.render.Paint;
import ohos.agp.render.Shader;
import ohos.agp.utils.Color;
import ohos.agp.utils.Point;
import ohos.agp.utils.RectFloat;

/**
 * 自定义带有圆环效果的LinearLayout。通过xml配置
 * 圆环的圆心在中间，x轴水平向右，y轴水平向下，按极坐标绘制。
 *
 * @author tab
 * @since 2020-12-25
 */

public class CircleProgressDrawTask implements Component.DrawTask{
    // 业务模块可以在xml里配置, 用来配置圆环的粗细, 预留, 后续可以通过xml配置
    private static final String STROKE_WIDTH_KEY = "stroke_width";

    // 业务模块可以在xml里配置, 用来配置圆环的最大值
    private static final String MAX_PROGRESS_KEY = "max_progress";

    // 业务模块可以在xml里配置, 用来配置圆环的当前值
    private static final String CURRENT_PROGRESS_KEY = "current_progress";

    // 业务模块可以在xml里配置, 用来配置起始位置的颜色
    private static final String STRT_COLOR_KEY = "start_color";

    // 业务模块可以在xml里配置, 用来配置结束位置的颜色
    private static final String END_COLOR_KEY = "end_color";

    // 业务模块可以在xml里配置, 用来配置背景色
    private static final String BACKGROUND_COLOR_KEY = "background_color";

    // 业务模块可以在xml里配置, 用来起始位置的角度
    private static final String START_ANGLE = "start_angle";

    private static final float MAX_ARC = 360f;

    private static final int DEFAULT_STROKE_WIDTH = 20;

    private static final int DEFAULT_MAX_VALUE = 100;

    private static final int DEFAULT_START_COLOR = 0xFFB566FF;

    private static final int DEFAULT_END_COLOR = 0xFF8A2BE2;

    private static final int DEFAULT_BACKGROUND_COLOR = 0xA8FFFFFF;

    private static final int DEFAULT_START_ANGLE = -90;

    private static final float DEFAULT_LINER_MAX = 100f;

    private static final int HALF = 2;

    private static final int NEARLY_FULL_CIRCL = 350;

    // 圆环的宽度, 默认20个像素
    private int mStrokeWidth = DEFAULT_STROKE_WIDTH;

    // 最大的进度值, 默认是100
    private int mMaxValue = DEFAULT_MAX_VALUE;

    // 当前的进度值, 默认是0
    private int mCurrentValue = 0;

    // 起始位置的颜色, 默认浅紫色
    private Color mStartColor = new Color(DEFAULT_START_COLOR);

    // 结束位置的颜色, 默认深紫色
    private Color mEndColor = new Color(DEFAULT_END_COLOR);

    // 背景颜色, 默认浅灰色
    private Color mBackgroundColor = new Color(DEFAULT_BACKGROUND_COLOR);

    // 当前的进度值, 默认从-90度进行绘制
    private int mStartAngle = DEFAULT_START_ANGLE;

    private Component mComponent;

    /**
     * 传入要进行修改的view
     *
     * @param component 要进行修改的view
     */
    public CircleProgressDrawTask(Component component) {
        mComponent = component;
        mComponent.addDrawTask(this);
    }

    /**
     * 设置当前进度并且刷新所在的view
     *
     * @param value 当前进度
     */
    public void setCurrentValue(int value) {
        mCurrentValue = value;
        mComponent.invalidate();
    }

    /**
     * 设置最大的进度值并且刷新所在的view
     *
     * @param maxValue 最大的进度值
     */
    public void setMaxValue(int maxValue) {
        mMaxValue = maxValue;
        mComponent.invalidate();
    }

    @Override
    public void onDraw(Component component, Canvas canvas) {
        // 计算中心点的位置, 如果是长方形, 则应该是较短的部分
        int center = Math.min(component.getWidth() / HALF, component.getHeight() / HALF);

        // 使用背景色绘制圆环, 选择一个画刷，宽度为设置的宽度，然后画圆。
        Paint roundPaint = new Paint();
        roundPaint.setAntiAlias(true);
        roundPaint.setStyle(Paint.Style.STROKE_STYLE);
        roundPaint.setStrokeWidth(mStrokeWidth);
        roundPaint.setStrokeCap(Paint.StrokeCap.ROUND_CAP);
        roundPaint.setColor(mBackgroundColor);
        int radius = center - mStrokeWidth / HALF;
        canvas.drawCircle(center, center, radius, roundPaint);

        // 使用渐变色绘制弧形
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE_STYLE);
        paint.setStrokeWidth(mStrokeWidth);
        float sweepAngle = MAX_ARC * mCurrentValue / mMaxValue;

        // 绘制的弧形接近满圆的时候使用BUTT画笔头
        if (sweepAngle > NEARLY_FULL_CIRCL) {
            paint.setStrokeCap(Paint.StrokeCap.BUTT_CAP);
        } else {
            paint.setStrokeCap(Paint.StrokeCap.ROUND_CAP);
        }
        Point point1 = new Point(0, 0);
        Point point2 = new Point(DEFAULT_LINER_MAX, DEFAULT_LINER_MAX);
        Point[] points = {point1, point2};
        Color[] colors = {mStartColor, mEndColor};
        Shader shader = new LinearShader(points, null, colors, Shader.TileMode.CLAMP_TILEMODE);
        paint.setShader(shader, Paint.ShaderType.LINEAR_SHADER);
        RectFloat oval = new RectFloat(center - radius, center - radius, center + radius, center + radius);
        Arc arc = new Arc();
        arc.setArc(mStartAngle, sweepAngle, false);
        canvas.drawArc(oval, arc, paint);
    }
}

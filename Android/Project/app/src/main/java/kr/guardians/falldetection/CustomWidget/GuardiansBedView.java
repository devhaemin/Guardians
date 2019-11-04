package kr.guardians.falldetection.CustomWidget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import kr.guardians.falldetection.POJO.Bed;

import java.util.ArrayList;

public class GuardiansBedView extends View {

    ArrayList<Bed> beds;

    public GuardiansBedView(Context context) {
        super(context);
    }

    public GuardiansBedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GuardiansBedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GuardiansBedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }
}

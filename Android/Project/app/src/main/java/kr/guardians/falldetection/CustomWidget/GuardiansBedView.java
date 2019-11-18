package kr.guardians.falldetection.CustomWidget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import kr.guardians.falldetection.POJO.Bed;
import kr.guardians.falldetection.POJO.Patient;
import kr.guardians.falldetection.R;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class GuardiansBedView extends View implements View.OnTouchListener {

    ArrayList<Bed> beds;
    Context context;
    AttributeSet attrs;
    View currentView;
    int bitmapWidth;
    int bitmapHeight;
    int dx;
    int dy;

    int width, height;

    private Paint paint;
    private Patient patient;

    private Bitmap selectBed, resizeSelectBed;
    private Bitmap normalBed, resizeNormalBed;

    private Bed touchedBed;

    private boolean bed_editable = false;


    public GuardiansBedView(Context context) {
        super(context);
    }

    public GuardiansBedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
        init();
    }

    public GuardiansBedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.attrs = attrs;
        init();
    }

    public GuardiansBedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        this.attrs = attrs;
        init();
    }

    public static float convertDpToPixel(float dp, Context context) {

        Resources resources = context.getResources();

        DisplayMetrics metrics = resources.getDisplayMetrics();

        float px = dp * (metrics.densityDpi / 160f);

        return px;

    }

    public ArrayList<Bed> getBeds() {
        return beds;
    }

    public void setBeds(Patient patient) {
        this.patient = patient;
        this.beds.clear();
        this.beds.addAll(patient.getBedInfo());
        invalidate();
    }

    private void init() {
        beds = new ArrayList<>();
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.GuardiansBedView,
                0, 0);
        bed_editable = typedArray.getBoolean(R.styleable.GuardiansBedView_bed_editable, false);
        if (bed_editable) setOnTouchListener(this);
        Drawable drawable = getResources().getDrawable(R.drawable.toggle_bed_on, null);
        selectBed = ((BitmapDrawable) drawable).getBitmap();
        normalBed = ((BitmapDrawable) getResources().getDrawable(R.drawable.toggle_bed_off, null)).getBitmap();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = right - left;
        height = bottom - top;
        resizeNormalBed = Bitmap.createScaledBitmap(normalBed, width / 4, width / 4, false);
        resizeSelectBed = Bitmap.createScaledBitmap(selectBed, width / 4, width / 4, false);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawCurrentBeds(canvas);
    }

    private void drawCurrentBeds(Canvas canvas) {
        if (beds != null && beds.size() > 0) {
            for (Bed bed : beds) {

                bitmapWidth = resizeNormalBed.getWidth();
                bitmapHeight = resizeNormalBed.getHeight();

                Rect bedRect = new Rect((int) (width * bed.getBedX()), (int) (height * bed.getBedY()), (int) (height * bed.getBedX()) + bitmapWidth, (int) (height * bed.getBedY()) + bitmapHeight);
                bed.setRect(bedRect);

                if (bed.getPatientSeq().equals(patient.getPatientSeq())) {
                    canvas.drawBitmap(resizeSelectBed, bedRect.left, bedRect.top, paint);
                } else {
                    canvas.drawBitmap(resizeNormalBed, bedRect.left, bedRect.top, paint);
                }
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                float touchX = event.getX();
                float touchY = event.getY();
                boolean hasTouch = false;
                for (Bed bed : beds) {
                    if (bed.getRect().contains((int) touchX, (int) touchY)) {
                        touchedBed = bed;
                        hasTouch = true;
                        Log.e(TAG, "The bed is Touched! Name is " + bed.getPatientName());
                    }
                    Log.e(TAG, "touchX : " + touchX);
                    Log.e(TAG, "touchY : " + touchY);
                    Log.e(TAG, "bed.getBedX() : " + (int) (width * bed.getBedX()));
                    Log.e(TAG, "bed.getBedY() : " + (int) (height * bed.getBedY()));
                    Log.e(TAG, "bed.getBedRightX() : " + (int) (width * bed.getBedX()) + bitmapWidth);
                    Log.e(TAG, "bed.getBedRightY() : " + (int) (height * bed.getBedY()) + bitmapHeight);
                }
                if (!hasTouch)
                    touchedBed = null;
                break;
            }
            case MotionEvent.ACTION_MOVE: {

                if (touchedBed != null) {

                    touchedBed.setBedX((event.getX() - (touchedBed.getRect().width() / 2.0f)) / width);
                    touchedBed.setBedY((event.getY() - (touchedBed.getRect().height() / 2.0f)) / height);

                    if (touchedBed.getBedX() > 0.72f) {
                        touchedBed.setBedX(0.72f);
                    }
                    if (touchedBed.getBedY() > 0.72f) {
                        touchedBed.setBedY(0.72f);
                    }
                    if (touchedBed.getBedX() < 0.04f) {
                        touchedBed.setBedX(0.04f);
                    }
                    if (touchedBed.getBedY() < 0.0f) {
                        touchedBed.setBedY(0.0f);
                    }

                    Log.e(TAG, "touchedBedX : " + touchedBed.getBedX());
                }
                invalidate();
                break;
            }
            case MotionEvent.ACTION_UP: {
                break;
            }
        }
        invalidate();
        return true;
    }
}

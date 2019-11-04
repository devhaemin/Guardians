package kr.guardians.falldetection.CustomWidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import kr.guardians.falldetection.R;

public class CustomBedView extends RelativeLayout {
    public CustomBedView(Context context) {
        super(context);
        init(context);
    }

    public CustomBedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomBedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public CustomBedView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context){
        View v = LayoutInflater.from(context)
                .inflate(R.layout.guardians_bedview,this, false);
        addView(v);

    }
}

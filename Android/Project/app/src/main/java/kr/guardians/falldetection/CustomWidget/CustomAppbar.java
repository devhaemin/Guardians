package kr.guardians.falldetection.CustomWidget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import kr.guardians.falldetection.R;

public class CustomAppbar extends RelativeLayout  {

    AttributeSet attrs;
    String title = "";
    boolean isSettingVisible = false;
    boolean isBackVisible = false;

    OnClickListener backButtonClickListener;
    OnClickListener toolButtonClickListener;


    public void setBackButtonClickListener(OnClickListener backButtonClickListener) {
        this.backButtonClickListener = backButtonClickListener;
    }

    public void setToolButtonClickListener(OnClickListener toolButtonClickListener) {
        this.toolButtonClickListener = toolButtonClickListener;
    }

    TextView titleView;
    ImageButton backButton, toolButton;

    public TextView getTitleView() {
        return titleView;
    }


/*
    public CustomAppbar(Context context) {
        super(context);

        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert li != null;
        li.inflate(R.layout.custom_app_bar,null,false);
    }
*/

    public CustomAppbar(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert li != null;
        View v = li.inflate(R.layout.custom_app_bar,this,false);
        addView(v);


        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CustomAppbar,
                0, 0);
        this.attrs = attrs;
        title = typedArray.getString(R.styleable.CustomAppbar_title);
        isSettingVisible = typedArray.getBoolean(R.styleable.CustomAppbar_isSettingVisible,true);
        isBackVisible = typedArray.getBoolean(R.styleable.CustomAppbar_isBackVisible,true);

        titleView = v.findViewById(R.id.text_title);
        backButton = v.findViewById(R.id.btn_back);
        toolButton = v.findViewById(R.id.btn_tool);

        if(backButtonClickListener != null);
        backButton.setOnClickListener(backButtonClickListener);
        if(toolButtonClickListener != null);
        toolButton.setOnClickListener(toolButtonClickListener);

        titleView.setText(title);
        titleView.setTextColor(typedArray.getColor(R.styleable.CustomAppbar_titleTextColor,getResources().getColor(R.color.colorAccent,null)));

        if(isBackVisible)
        backButton.setVisibility(View.VISIBLE);
        else backButton.setVisibility(View.GONE);

        if(isSettingVisible)
        toolButton.setVisibility(View.VISIBLE);
        else toolButton.setVisibility(GONE);


    }

    public void setBackDefault(final Activity activity){
        backButtonClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        };
    }

}

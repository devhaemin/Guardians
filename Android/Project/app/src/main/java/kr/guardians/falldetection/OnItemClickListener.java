package kr.guardians.falldetection;

import android.view.View;
import kr.guardians.falldetection.POJO.Patient;

public interface OnItemClickListener<T>{
    void onItemClick(View v, T patient);
}
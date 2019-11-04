package kr.guardians.falldetection.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import kr.guardians.falldetection.R;

public class PatientModifyActivity extends AppCompatActivity {

    String patientSeq;

    public static void start(Context context, String patientSeq) {
        Intent starter = new Intent(context, PatientModifyActivity.class);
        starter.putExtra("patientSeq",patientSeq);
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_modify);

        Intent fromOutside = getIntent();
        patientSeq = fromOutside.getStringExtra("patientSeq");

        ImageView profileImage = findViewById(R.id.profile_image);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGallery();
            }
        });
    }
    private void pickFromGallery(){
        //Create an Intent with action as ACTION_PICK
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        startActivityForResult(intent,400);

    }
}

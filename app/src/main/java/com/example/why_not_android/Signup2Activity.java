package com.example.why_not_android;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Signup2Activity extends AppCompatActivity {

    private static final int REQUEST_GET_SINGLE_FILE = 0;
    DatePickerDialog datePickerDialog;
    @BindView(R.id.signup2Birthdate) EditText birthdateEdt;
    @BindView(R.id.signup2radioGroup) RadioGroup genderGr;
    String gender = "";
    String image = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);
        ButterKnife.bind(this);
        initDatePicker();

        Button loginbtn = findViewById(R.id.photoButton);
        loginbtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"),REQUEST_GET_SINGLE_FILE);
        });

        genderGr.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioButtonHomme) {
                gender = "homme";
            } else {
                gender="femme";
            }
        });

        Button signupnbtn = findViewById(R.id.signup2Button);
        signupnbtn.setOnClickListener(v -> {
            Bundle extras = getIntent().getExtras();
            String mail = extras.getString("mail");
            String name = extras.getString("name");
            String password = extras.getString("password");
            String bdate = birthdateEdt.getText().toString();
            Intent intent = new Intent(Signup2Activity.this, Signup3Activity.class);
            intent.putExtra("mail",mail);
            intent.putExtra("name",name);
            intent.putExtra("password",password);
            intent.putExtra("gender",gender);
            intent.putExtra("bdate",bdate);
            intent.putExtra("image",image);
            Log.d("1",mail);
            Log.d("2",name);
            Log.d("3",password);
            Log.d("4",gender);
            Log.d("5",bdate);

            startActivity(intent);
        });
    }




    private void initDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            String format = "dd/MM/yy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.FRANCE);

            calendar.set(Calendar.YEAR, year1);
            calendar.set(Calendar.MONTH, month1);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            birthdateEdt.setText(simpleDateFormat.format(calendar.getTime()));
        }, year, month, day);
    }

    @OnClick(R.id.signup2Birthdate) void onBirthdateClick() {
        datePickerDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == REQUEST_GET_SINGLE_FILE) {
                    Uri selectedImageUri = data.getData();

                    final String path = getPathFromURI(selectedImageUri);
                    if (path != null) {
                        File f = new File(path);
                        selectedImageUri = Uri.fromFile(f);
                    }

                    image = selectedImageUri+"";
                    Log.d("test",selectedImageUri+"");
                    ImageView img = findViewById(R.id.photoImgView);
                    img.setImageURI(selectedImageUri);
                }
            }
        } catch (Exception e) {
            Log.e("FileSelectorActivity", "File select error", e);
        }
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }


}

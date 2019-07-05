package com.example.why_not_android.views;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.why_not_android.R;
import com.example.why_not_android.data.Models.Signup;
import com.example.why_not_android.data.Permissions;
import com.example.why_not_android.data.dto.ImageDTO;
import com.example.why_not_android.data.service.SessionService;
import com.example.why_not_android.data.service.providers.NetworkProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup2Activity extends AppCompatActivity {

    private static final int REQUEST_GET_SINGLE_FILE = 0;
    DatePickerDialog datePickerDialog;
    @BindView(R.id.signup2Birthdate)
    EditText birthdateEdt;
    @BindView(R.id.signup2radioGroup)
    RadioGroup genderGr;
    @BindView(R.id.photoImgView)
    ImageView imageView;
    @BindView(R.id.photoButton)
    Button photobutton;
    String gender = "";

    private String imgPath = null;
    private Bitmap bitmap;
    private InputStream inputStreamImg;
    private Permissions permissions;
    private Uri fileUri;
    private File image_file = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);
        ButterKnife.bind(this);
        initDatePicker();
        genderGr.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioButtonHomme) {
                gender = "homme";
            } else {
                gender = "femme";
            }
        });
    }

    @OnClick(R.id.photoButton)
    void selectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GET_SINGLE_FILE);
    }

    @OnClick(R.id.signup2Button)
    void nextSignupActivity() {
        if (birthdateEdt.getText().toString().length() == 0) {
            Toast.makeText(this, "Entrer une date de naissance", Toast.LENGTH_SHORT).show();
        } else if (gender.length() == 0) {
            Toast.makeText(this, "Selectionner un genre", Toast.LENGTH_SHORT).show();
        } else {
            Signup.getClient().setGender(gender);
            Signup.getClient().setBirthdate(birthdateEdt.getText().toString());
            //Signup.getClient().setImage(image);
            Intent intent = new Intent(this, Signup3Activity.class);
            startActivity(intent);
        }
    }


    private void initDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            String format = "dd/MM/yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.FRANCE);

            calendar.set(Calendar.YEAR, year1);
            calendar.set(Calendar.MONTH, month1);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            birthdateEdt.setText(simpleDateFormat.format(calendar.getTime()));
        }, year, month, day);
    }

    @OnClick(R.id.signup2Birthdate)
    void onBirthdateClick() {
        datePickerDialog.show();
    }

    /*public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }*/

    /*@OnClick(R.id.upload)
    void uploadToServer() {
        SessionService sessionService;
        sessionService = NetworkProvider.getClient().create(SessionService.class);
        File file = new File(image);
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("upload", file.getName(), fileReqBody);
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "image-type");
        Call<ResponseBody> responseBodyCall = sessionService.uploadImage(part, description);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("yes", "ça marche ;)");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("yes", "ca fail :(");
            }
        });
    }*/

    /*@OnClick(R.id.upload)
    void uploadNewUserImage() {
        SessionService imageService;
        imageService = NetworkProvider.getClient().create(SessionService.class);
        File destination = new File(getRealPathFromURI(fileUri));

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(getContentResolver().getType(fileUri)),
                        destination
                );

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", destination.getName(), requestFile);

        // add another part within the multipart request
        String descriptionString = "hello, this is description speaking";
        RequestBody description =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, descriptionString);

        // finally, execute the request
        Call<ImageDTO> call = imageService.uploadImage(description, body);
        call.enqueue(new Callback<ImageDTO>() {
            @Override
            public void onResponse(Call<ImageDTO> call,
                                   Response<ImageDTO> response) {
                Log.d("toz", response.body().getToz());
            }

            @Override
            public void onFailure(Call<ImageDTO> call, Throwable t) {

            }
        });
    }*/
}

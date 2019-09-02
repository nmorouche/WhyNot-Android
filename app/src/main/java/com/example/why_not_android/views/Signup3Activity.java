package com.example.why_not_android.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.why_not_android.R;
import com.example.why_not_android.data.Models.Signup;
import com.example.why_not_android.data.SharedPreferences.SharedPref;
import com.example.why_not_android.data.dto.SessionDTO;
import com.example.why_not_android.data.service.SessionService;
import com.example.why_not_android.data.service.providers.NetworkProvider;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup3Activity extends AppCompatActivity {

    @BindView(R.id.signup3Bio)
    EditText bioEdit;
    @BindView(R.id.signup3radioGroup)
    RadioGroup prefRadio;
    @BindView(R.id.signup3_hobby_button)
    Button hobbyButton;
    @BindView(R.id.signup3_selected_hobbies)
    TextView mItemSelected;
    int preferences = -1;
    private SharedPreferences sharedPreferences;
    private String[] listItems;
    boolean[] checkedItems;
    private ArrayList<Integer> mUserItems = new ArrayList<>();
    private String[] hobbyArray = new String[]{};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup3);
        ButterKnife.bind(this);
        sharedPreferences = SharedPref.getInstance(this);
        listItems = getResources().getStringArray(R.array.preferences);
        checkedItems = new boolean[listItems.length];
        prefRadio.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.signup3radioButtonHomme) {
                preferences = 0;
            } else if (checkedId == R.id.signup3radioButtonFemme) {
                preferences = 1;
            }
        });
    }

    private void signup(Uri fileUri, File imageFile) {
        SessionService sessionService;
        sessionService = NetworkProvider.getClient().create(SessionService.class);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(getContentResolver().getType(fileUri)),
                        imageFile
                );

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", imageFile.getName(), requestFile);

        // add another part within the multipart request
        RequestBody email =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, Signup.getClient().getEmail());
        RequestBody username =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, Signup.getClient().getUsername());
        RequestBody password =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, Signup.getClient().getPassword());
        RequestBody gender =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, String.valueOf(Signup.getClient().getGender()));
        RequestBody birthdate =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, Signup.getClient().getBirthdate());
        RequestBody bio =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, Signup.getClient().getBio());

        RequestBody hobbies =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, Signup.getClient().getHobbies());

        RequestBody preference =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, String.valueOf(Signup.getClient().getPreferences()));

        // finally, execute the request
        Call<SessionDTO> call = sessionService.signup(
                email,
                username,
                password,
                gender,
                birthdate,
                bio,
                hobbies,
                preference,
                body);
        call.enqueue(new Callback<SessionDTO>() {
            @Override
            public void onResponse(Call<SessionDTO> call,
                                   Response<SessionDTO> response) {
                if (response.isSuccessful()) {
                    SessionDTO sessionDTO = response.body();
                    String hobbies = "";
                    String[] hobbiesDTO = sessionDTO.getHobbies();
                    for (int i = 0; i < hobbiesDTO.length; i++) {
                        hobbies += hobbiesDTO[i];
                        if (i != hobbiesDTO.length - 1) {
                            hobbies += ", ";
                        }
                    }
                    sharedPreferences.edit()
                            .putString("token", sessionDTO.getToken())
                            .putString("email", sessionDTO.getEmail())
                            .putString("username", sessionDTO.getUsername())
                            .putString("photo", sessionDTO.getPhoto())
                            .putString("hobbies", hobbies)
                            .putString("password", Signup.getClient().getPassword())
                            .apply();
                    Intent intent = new Intent(Signup3Activity.this, Home.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    try {
                        JSONObject errorJSON = new JSONObject(response.errorBody().string());
                        Toast.makeText(Signup3Activity.this, errorJSON.getString("error"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.d("toz", e.toString());
                    }
                }

            }

            @Override
            public void onFailure(Call<SessionDTO> call, Throwable t) {
                Log.d("toz", t.toString());
            }
        });
    }

    @OnClick(R.id.signup3_hobby_button)
    void checkBox() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle(R.string.signup3_hobby_button_title);
        mBuilder.setMultiChoiceItems(listItems, checkedItems, (dialogInterface, position, isChecked) -> {
            if (isChecked) {
                mUserItems.add(position);
            } else {
                mUserItems.remove((Integer.valueOf(position)));
            }
        });

        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton(R.string.valider, (dialogInterface, which) -> {
            String item = "";
            for (int i = 0; i < mUserItems.size(); i++) {
                item = item + listItems[mUserItems.get(i)];
                if (i != mUserItems.size() - 1) {
                    item = item + ", ";
                }
            }
            hobbyArray = item.split(", ");
            mItemSelected.setText(item);
        });

        mBuilder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());

        mBuilder.setNeutralButton("Clear all", (dialogInterface, which) -> {
            for (int i = 0; i < checkedItems.length; i++) {
                checkedItems[i] = false;
                mUserItems.clear();
                hobbyArray = new String[]{};
                mItemSelected.setText("");
            }
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    @OnClick(R.id.signup2Button)
    void submit() {
        if (preferences == -1) {
            Toast.makeText(this, "Selectionner une préférence", Toast.LENGTH_SHORT).show();
        } else if (bioEdit.getText().toString().length() == 0) {
            Toast.makeText(this, "Merci d'entrer une bio", Toast.LENGTH_SHORT).show();
        } else if (hobbyArray.length == 0) {
            Toast.makeText(this, "Veuillez sélectionner une ou plusieurs passions", Toast.LENGTH_SHORT).show();
        } else {
            Signup.getClient().setHobbies(mItemSelected.getText().toString());
            Signup.getClient().setBio(bioEdit.getText().toString());
            Signup.getClient().setPreferences(preferences);
            signup(Signup.getClient().getFileUri(), Signup.getClient().getImageFile());
        }
    }
}
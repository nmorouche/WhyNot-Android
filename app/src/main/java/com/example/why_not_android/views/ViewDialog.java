package com.example.why_not_android.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.service.autofill.FieldClassification;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.why_not_android.R;
import com.example.why_not_android.data.dto.UserDTO;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewDialog extends AppCompatActivity {

    public void showDialog(Activity activity, String username, String image) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_match_dialog);
        TextView matchWithUsername = (TextView) dialog.findViewById(R.id.custom_match_dialog_text);
        Button dialogDismiss = (Button) dialog.findViewById(R.id.custom_match_dialog_button_dismiss);
        ImageView userImage = (ImageView) dialog.findViewById(R.id.custom_match_dialog_image);
        matchWithUsername.setText(String.format("%s%s%s", activity.getString(R.string.custom_dialog_message), "\n", username));
        String url = image.replace("localhost", "10.0.2.2");
        Glide.with(activity).load(url).into(userImage);
        dialogDismiss.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }
}
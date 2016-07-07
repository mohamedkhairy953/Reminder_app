package com.example.moham.contacting;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by moham on 6/18/2016.
 */
public class AddContacter_activity extends AppCompatActivity {
    EditText remindme_edittext, name_edittext, phone_edittext, facebook_edittext;
    ImageView add_imagview;
    Button add_button;
    private final int PHOTO_CODE = 1;
    private byte[] image_byte_arrary;
    DBController controller = new DBController(this);
    DeleteAlarm_class managerClass;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contacter);
        remindme_edittext = (EditText) findViewById(R.id.remind_edittext_id);
        name_edittext = (EditText) findViewById(R.id.name_edittext_id);
        phone_edittext = (EditText) findViewById(R.id.phone_edittext_id);
        facebook_edittext = (EditText) findViewById(R.id.face_user_edittext_id);
        add_imagview = (ImageView) findViewById(R.id.add_imageview_id);
        add_button = (Button) findViewById(R.id.add_button_id);
        managerClass = new DeleteAlarm_class(this);
        remindme_edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    showDatePickerDialog(v);
            }
        });
        add_imagview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, PHOTO_CODE);
            }
        });
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContacterModel model = new ContacterModel();
                model.setImage(image_byte_arrary);
                String RemindMeString = (remindme_edittext.getText().toString().equals("")) ? null : remindme_edittext.getText().toString();
                try {
                    model.setRemindMe(RemindMeString);
                    model.setPhone(phone_edittext.getText().toString());
                    model.setName(name_edittext.getText().toString());
                    controller.insert(model);
                    finish();
                } catch (Exception s) {
                    Toast.makeText(AddContacter_activity.this, s.getLocalizedMessage() + "", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void showDatePickerDialog(View v) {
        android.support.v4.app.DialogFragment newFragment = new Date_Picker();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PHOTO_CODE:
                if (resultCode == RESULT_OK) {
                    final Uri img_uri = data.getData();
                    try {
                        InputStream img_is = getContentResolver().openInputStream(img_uri);
                        final Bitmap img_bitmap = BitmapFactory.decodeStream(img_is);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        img_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        image_byte_arrary = byteArrayOutputStream.toByteArray();
                        add_imagview.setImageBitmap(img_bitmap);
                    } catch (OutOfMemoryError b) {
                        Toast.makeText(this, "too size photo", Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }
    }
}
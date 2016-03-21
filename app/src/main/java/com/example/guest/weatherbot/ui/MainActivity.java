package com.example.guest.weatherbot.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.weatherbot.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.backgroundImage) ImageView mBackgroundImage;
    @Bind(R.id.userZip) EditText mUserZip;
    @Bind(R.id.submitButton) Button mSubmitButton;

    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Picasso.with(MainActivity.this).load("http://i.imgur.com/5ibRTZ8.jpg").fit().centerCrop().into(mBackgroundImage);
        mSubmitButton.setOnClickListener(this);

        mUserZip.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    mSubmitButton.performClick();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == mSubmitButton) {
            String location = mUserZip.getText().toString();
            Intent intent = new Intent(MainActivity.this, CurrentWeatherActivity.class);
            intent.putExtra("location", location);
            mUserZip.setText("");
            startActivity(intent);
        }
    }
}

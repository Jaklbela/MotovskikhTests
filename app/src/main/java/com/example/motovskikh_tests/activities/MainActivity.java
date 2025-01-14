package com.example.motovskikh_tests.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.motovskikh_tests.R;

public class MainActivity extends AppCompatActivity {
    Button feedbackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewGroup rootView = findViewById(R.id.buttonGroup);
        for (int i = 0; i < rootView.getChildCount(); i++) {
            View child = rootView.getChildAt(i);
            if (child instanceof  Button) {
                child.setOnTouchListener(touchBoneTestListener);
            }
        }

        feedbackButton = findViewById(R.id.feedbackButton);
        feedbackButton.setOnTouchListener(touchFeedbackListener);
    }

    @SuppressLint("ClickableViewAccessibility")
    View.OnTouchListener touchBoneTestListener = (view, event) -> {
        Button button = (Button) view;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                button.setBackgroundResource(R.drawable.button_background);
                button.setBackgroundColor(getResources().getColor(R.color.button_pressed));
                button.setTextAppearance(R.style.PressedTextStyle);
                return true;

            case MotionEvent.ACTION_UP:
                String url = getResources().getString(R.string.bone_test_link);
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            case MotionEvent.ACTION_CANCEL:
                button.setBackgroundResource(R.drawable.button_background);
                button.setBackgroundColor(getResources().getColor(R.color.transparent));
                button.setTextAppearance(R.style.CasualTextStyle);
                return true;
        }
        return false;
    };

    @SuppressLint("ClickableViewAccessibility")
    View.OnTouchListener touchFeedbackListener = (view, event) -> {
        Button button = (Button) view;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                button.setBackgroundResource(R.drawable.button_background);
                button.setBackgroundColor(getResources().getColor(R.color.green));
                button.setTextAppearance(R.style.FeedbackTextPressedStyle);
                return true;

            case MotionEvent.ACTION_UP:
                String url = getResources().getString(R.string.bone_test_link);
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            case MotionEvent.ACTION_CANCEL:
                button.setBackgroundResource(R.drawable.button_background);
                button.setBackgroundColor(getResources().getColor(R.color.transparent));
                button.setTextAppearance(R.style.FeedbackTextStyle);
                return true;
        }
        return false;
    };
}
package com.example.motovskikh_tests.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.motovskikh_tests.R;
import com.yandex.mobile.ads.common.AdError;
import com.yandex.mobile.ads.common.AdInfo;
import com.yandex.mobile.ads.common.AdRequest;
import com.yandex.mobile.ads.common.AdRequestConfiguration;
import com.yandex.mobile.ads.common.AdRequestError;
import com.yandex.mobile.ads.common.ImpressionData;
import com.yandex.mobile.ads.common.MobileAds;
import com.yandex.mobile.ads.instream.MobileInstreamAds;
import com.yandex.mobile.ads.interstitial.InterstitialAd;
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener;
import com.yandex.mobile.ads.interstitial.InterstitialAdLoadListener;
import com.yandex.mobile.ads.interstitial.InterstitialAdLoader;

public class MainActivity extends AppCompatActivity {
    Button feedbackButton;
    @Nullable
    private InterstitialAd mInterstitialAd = null;
    @Nullable
    private InterstitialAdLoader mInterstitialAdLoader = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobileAds.initialize(this, () -> {});
        MobileInstreamAds.setAdGroupPreloading(true);
        MobileAds.enableLogging(true);

        setContentView(R.layout.activity_main);

        ViewGroup rootView = findViewById(R.id.buttonGroup);
        for (int i = 0; i < rootView.getChildCount(); i++) {
            View child = rootView.getChildAt(i);
            if (child instanceof Button) {
                child.setOnTouchListener(touchBoneTestListener);
            }
        }

        feedbackButton = findViewById(R.id.feedbackButton);
        feedbackButton.setOnTouchListener(touchFeedbackListener);

        mInterstitialAdLoader = new InterstitialAdLoader(this);
        mInterstitialAdLoader.setAdLoadListener(new InterstitialAdLoadListener() {
            @Override
            public void onAdLoaded(@NonNull final InterstitialAd interstitialAd) {
                mInterstitialAd = interstitialAd;
                // The ad was loaded successfully. Now you can show loaded ad.
            }

            @Override
            public void onAdFailedToLoad(@NonNull final AdRequestError adRequestError) {
                // Ad failed to load with AdRequestError.
                // Attempting to load a new ad from the onAdFailedToLoad() method is strongly discouraged.
            }
        });
        loadInterstitialAd();
    }

    private void loadInterstitialAd() {
        if (mInterstitialAdLoader != null ) {
            final AdRequestConfiguration adRequestConfiguration =
                    new AdRequestConfiguration.Builder("R-M-13774252-1").build();
            mInterstitialAdLoader.loadAd(adRequestConfiguration);
        }
    }

    private void showAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd.setAdEventListener(new InterstitialAdEventListener() {
                @Override
                public void onAdShown() {
                    // Called when ad is shown.
                }

                @Override
                public void onAdFailedToShow(@NonNull final AdError adError) {
                    // Called when an InterstitialAd failed to show.
                }

                @Override
                public void onAdDismissed() {
                    // Called when ad is dismissed.
                    // Clean resources after Ad dismissed
                    if (mInterstitialAd != null) {
                        mInterstitialAd.setAdEventListener(null);
                        mInterstitialAd = null;
                    }

                    // Now you can preload the next interstitial ad.
                    loadInterstitialAd();
                }

                @Override
                public void onAdClicked() {
                    // Called when a click is recorded for an ad.
                }

                @Override
                public void onAdImpression(@Nullable final ImpressionData impressionData) {
                    // Called when an impression is recorded for an ad.
                }
            });
            mInterstitialAd.show(this);
        }
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
                showAd();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mInterstitialAdLoader != null) {
            mInterstitialAdLoader.setAdLoadListener(null);
            mInterstitialAdLoader = null;
        }
        destroyInterstitialAd();
    }

    private void destroyInterstitialAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd.setAdEventListener(null);
            mInterstitialAd = null;
        }
    }
}
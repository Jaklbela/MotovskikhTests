package com.example.motovskikh_tests.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.motovskikh_tests.R;
import com.example.motovskikh_tests.TestAdapter;
import com.yandex.mobile.ads.common.AdError;
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
    @Nullable
    private InterstitialAd mInterstitialAd = null;
    @Nullable
    private InterstitialAdLoader mInterstitialAdLoader = null;

    private static TestAdapter testAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobileAds.initialize(this, () -> {});
        MobileInstreamAds.setAdGroupPreloading(true);
        MobileAds.enableLogging(true);

        setContentView(R.layout.activity_main);

        String[] tests = getResources().getStringArray(R.array.tests);

        testAdapter = new TestAdapter(this, tests);
        ListView list = findViewById(R.id.testLists);
        list.setAdapter(testAdapter);

        list.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = tests[position];
            String url = getTestUrl(selectedItem);

            testAdapter.setSelectedPosition(position);

            Intent intent = new Intent(MainActivity.this, WebsiteActivity.class);
            intent.putExtra("url", url);
            startActivity(intent);
            if (!url.equals(getResources().getString(R.string.feedback_link))) {
                showAd();
            }
        });

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

    public static void changeButtonAppearance() {
        testAdapter.setSelectedPosition(-1);
    }

    private String getTestUrl(String selectedItem) {
        if (selectedItem.equals(getResources().getString(R.string.skeleton_button))) {
            return getResources().getString(R.string.skeleton_test_link);
        }
        if (selectedItem.equals(getResources().getString(R.string.eye_button))) {
            return getResources().getString(R.string.eye_test_link);
        }
        if (selectedItem.equals(getResources().getString(R.string.hand_button))) {
            return getResources().getString(R.string.hand_test_link);
        }
        if (selectedItem.equals(getResources().getString(R.string.nephron_button))) {
            return getResources().getString(R.string.nephron_test_link);
        }
        if (selectedItem.equals(getResources().getString(R.string.neuron_button))) {
            return getResources().getString(R.string.neuron_test_link);
        }
        if (selectedItem.equals(getResources().getString(R.string.skull_button))) {
            return getResources().getString(R.string.skull_test_link);
        }
        if (selectedItem.equals(getResources().getString(R.string.teeth_button))) {
            return getResources().getString(R.string.teeth_test_link);
        }
        if (selectedItem.equals(getResources().getString(R.string.feedback_button))) {
            return getResources().getString(R.string.feedback_link);
        }
        return "";
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
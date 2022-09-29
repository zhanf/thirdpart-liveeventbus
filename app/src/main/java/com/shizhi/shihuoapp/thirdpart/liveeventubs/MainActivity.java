package com.shizhi.shihuoapp.thirdpart.liveeventubs;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static final String KEY_TEST_OBSERVE = "key_test_observe";
    public static final String KEY_TEST_OBSERVE_FOREVER = "key_test_observe_forever";

    private Observer<String> observer = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String s) {
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        LiveEventBus
                .get(KEY_TEST_OBSERVE, String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
        LiveEventBus
                .get(KEY_TEST_OBSERVE_FOREVER, String.class)
                .observeForever(observer);
        LiveEventBus
                .get(KEY_TEST_OBSERVE_FOREVER, String.class)
                .observeForever(observer);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LiveEventBus
                .get(KEY_TEST_OBSERVE_FOREVER, String.class)
                .removeObserver(observer);
    }

    public void sendMsgByPostValue(View view) {
        LiveEventBus.get(KEY_TEST_OBSERVE)
                .post("Message By PostValue: " + new Random().nextInt(100));
    }

    public void sendMsgToForeverObserver(View view) {
        LiveEventBus.get(KEY_TEST_OBSERVE_FOREVER)
                .post("Message To ForeverObserver: " + new Random().nextInt(100));
    }
}
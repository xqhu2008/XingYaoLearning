package com.bluehawk.xingyaolearning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.bluehawk.xingyaolearning.word.MainActivity;
import com.bluehawk.xingyaolearning.word.YaoResourceManager;
import com.bluehawk.xingyaolearning.word.YaoWordXMLParser;
import com.bluehawk.xingyaplearning.R;

public class SplashActivity extends Activity {
    private static final int AUTO_HIDE_DELAY_MILLIS = 2000;
    private YaoResourceManager mYaoResourceManager;

    public static String YAO_RESOURCE_MANAGER = "yao_resource_manager";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mYaoResourceManager = YaoWordXMLParser.readFromFile(this,
                getResources().getString(R.string.resource_config_file));

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(SplashActivity.YAO_RESOURCE_MANAGER, mYaoResourceManager);

                mainIntent.putExtras(bundle);
                startActivity(mainIntent);
                finish();
            }

        }, AUTO_HIDE_DELAY_MILLIS);
    }
}

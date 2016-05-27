package com.bluehawk.xingyaolearning;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluehawk.xingyaplearning.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class LearningActivity extends AppCompatActivity {
    private String[] mResourceName;
    private int  mIndex;

    private ImageView mImageView;
    private ImageButton mImgBtnNext;
    private ImageButton mImgBtnPrev;
    private ImageButton mImgBtnExit;
    private ImageButton mImgBtnPlay;
    private TextView mEnglishText;
    private SoundPool mSndPool;
    private HashMap<Integer, Integer> mSndPoolMap;
    private float mVolumnRatio;

    private String mFuncType;
    private boolean mLearningType = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_learning);

        HashMap<String, Integer> funcMap = new HashMap<String, Integer>();
        funcMap.put("color", R.array.learning_color);
        funcMap.put("fruit", R.array.learning_fruit);
        funcMap.put("number", R.array.learning_number);
        funcMap.put("vegetable", R.array.learning_vegetable);
        funcMap.put("animal", R.array.learning_animal);
        funcMap.put("insect", R.array.learning_insect);

        Bundle bundle = getIntent().getExtras();
        mFuncType = bundle.getString(MainActivity.ACTIVITY_FUNCTION);
        mLearningType = bundle.getBoolean(MainActivity.ACTIVITY_TYPE);
        int id = mLearningType ? R.string.title_learing : R.string.title_example;
        setTitle(id);
        mResourceName = getResources().getStringArray(funcMap.get(mFuncType));
        mImageView = (ImageView)findViewById(R.id.imageView_learning);
        mIndex = 0;

        mImgBtnNext = (ImageButton)findViewById(R.id.btn_learning_next);
        mImgBtnExit = (ImageButton)findViewById(R.id.btn_learning_exit);
        mImgBtnPrev = (ImageButton)findViewById(R.id.btn_learning_prev);
        mEnglishText = (TextView)findViewById(R.id.tv_english);
        mImgBtnPlay = (ImageButton)findViewById(R.id.btn_learning_play);

        createSoundPool();

        mImgBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSoundFromAsset();
            }
        });

        mImgBtnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mImgBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIndex += 1;
                if (mIndex >= mResourceName.length) {
                    mIndex = 0;
                }

                readImageFromAsset();
            }
        });

        mImgBtnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIndex -= 1;
                if (mIndex < 0) {
                    mIndex = mResourceName.length - 1;
                }

                readImageFromAsset();
            }
        });


        readImageFromAsset();
    }

    private void createSoundPool() {
        mSndPool = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        mSndPoolMap = new HashMap<Integer, Integer>();

        try {
            int index = 1;
            for (String sndFile : mResourceName) {
                AssetFileDescriptor afdSnd = getResources().getAssets()
                    .openFd("resource/" + mFuncType + "/" + sndFile + ".wav");
                int sound = mSndPool.load(afdSnd, 1);
                mSndPoolMap.put(index, sound);
                index += 1;
            }

            AudioManager am = (AudioManager) this
                    .getSystemService(Context.AUDIO_SERVICE);
            float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            float volumnCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
            mVolumnRatio = volumnCurrent / audioMaxVolumn;

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void playSoundFromAsset(){
        mSndPool.play(mIndex + 1, mVolumnRatio, mVolumnRatio, 1, 0, 1);
    }

    private void readImageFromAsset() {
        if (mIndex >= mResourceName.length || mIndex < 0) {
            return;
        }

        byte [] buffer = null;
        try{
            InputStream in = getResources().getAssets().open("resource/" + mFuncType + "/" +
                    mResourceName[mIndex] + ".bmp");

            int length = in.available();
            buffer = new byte[length];
            in.read(buffer);
        }catch(Exception e){
            e.printStackTrace();
        }

        if (buffer != null) {
            Bitmap bm = BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
            mImageView.setImageBitmap(bm);
            mEnglishText.setText(mResourceName[mIndex]);
            if (mLearningType)
                playSoundFromAsset();
        }
    }
}

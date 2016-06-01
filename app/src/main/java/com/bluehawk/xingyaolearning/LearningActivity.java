package com.bluehawk.xingyaolearning;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluehawk.xingyaplearning.R;

public class LearningActivity extends AppCompatActivity {
    private ImageView mImageView;
    private ImageButton mImgBtnNext;
    private ImageButton mImgBtnPrev;
    private ImageButton mImgBtnExit;
    private ImageButton mImgBtnPlay;
    private TextView mEnglishText;

    private String mFuncType;
    private boolean mLearningType = true;

    private LearningResourceManager mLearningResourceManager;
    private int  mIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_learning);

        Bundle bundle = getIntent().getExtras();
        mFuncType = bundle.getString(MainActivity.ACTIVITY_FUNCTION);
        mLearningType = bundle.getBoolean(MainActivity.ACTIVITY_TYPE);
        int id = mLearningType ? R.string.title_learing : R.string.title_example;
        setTitle(id);

        mImageView = (ImageView)findViewById(R.id.imageView_learning);
        mIndex = 0;
        mImgBtnNext = (ImageButton)findViewById(R.id.btn_learning_next);
        mImgBtnExit = (ImageButton)findViewById(R.id.btn_learning_exit);
        mImgBtnPrev = (ImageButton)findViewById(R.id.btn_learning_prev);
        mEnglishText = (TextView)findViewById(R.id.tv_english);
        mImgBtnPlay = (ImageButton)findViewById(R.id.btn_learning_play);

        mLearningResourceManager = new LearningResourceManager(this, mFuncType, "resource");

        mImgBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLearningResourceManager.playSound(mIndex);
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
                if (mIndex >= mLearningResourceManager.getSize()) {
                    mIndex = 0;
                }

                displayResource();
            }
        });

        mImgBtnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIndex -= 1;
                if (mIndex < 0) {
                    mIndex = mLearningResourceManager.getSize() - 1;
                }

                displayResource();
            }
        });


        displayResource();
    }

    private void displayResource() {
        if (mIndex >= mLearningResourceManager.getSize() || mIndex < 0) {
            return;
        }

        mImageView.setImageBitmap(mLearningResourceManager.getImage(mIndex));
        mEnglishText.setText(mLearningResourceManager.getResourceName(mIndex));
        if (mLearningType) {
            mLearningResourceManager.playSound(mIndex);
        }
    }
}

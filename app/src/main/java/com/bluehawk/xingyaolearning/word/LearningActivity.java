package com.bluehawk.xingyaolearning.word;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluehawk.xingyaolearning.YaoWordImage;
import com.bluehawk.xingyaplearning.R;

public class LearningActivity extends AppCompatActivity {
    private ImageView mImageView;
    private ImageButton mImgBtnNext;
    private ImageButton mImgBtnPrev;
    private ImageButton mImgBtnExit;
    private ImageButton mImgBtnPlay;
    private TextView mEnglishText;

    private boolean mLearningType = true;

    private YaoWordCategory mYaoWordCategory;
    private YaoWordSound mYaoWordSound;
    private int  mIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_learning);

        Bundle bundle = getIntent().getExtras();
        mYaoWordCategory = (YaoWordCategory)bundle.getSerializable(MainActivity.ACTIVITY_FUNCTION);
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

        mImgBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mYaoWordSound.playSound(mIndex);
            }
        });

        mYaoWordSound = new YaoWordSound(this, mYaoWordCategory);

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
                if (mIndex >= mYaoWordCategory.getSize()) {
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
                    mIndex = mYaoWordCategory.getSize() - 1;
                }

                displayResource();
            }
        });


        displayResource();
    }

    private void displayResource() {
        if (mIndex >= mYaoWordCategory.getSize() || mIndex < 0) {
            return;
        }

        YaoWordVocabulary vocabulary = mYaoWordCategory.getVocabulary(mIndex);
        Bitmap bmp = YaoWordImage.loadBitmapFromAsset(this, vocabulary.getImageFileName());
        mImageView.setImageBitmap(bmp);
        mEnglishText.setText(vocabulary.getName());
        if (mLearningType) {
            mYaoWordSound.playSound(mIndex);
        }
    }
}

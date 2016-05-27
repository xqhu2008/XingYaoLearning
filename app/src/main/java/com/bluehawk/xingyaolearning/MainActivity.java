package com.bluehawk.xingyaolearning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.bluehawk.xingyaplearning.R;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {
    private Button mColorButton;
    private Button mNumberButton;
    private Button mFruitButton;
    private Button mVegeButton;
    private Button mExitButton;
    private Button mAnimalButton;
    private Button mInsectButton;
    private boolean mLearningType = true;

    private Switch mLearningTypeSwitch;

    static final String ACTIVITY_FUNCTION = "activity_function";
    static final String ACTIVITY_TYPE = "activity_type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        mColorButton = (Button) findViewById(R.id.btn_color);
        mNumberButton = (Button)findViewById(R.id.btn_number);
        mFruitButton = (Button)findViewById(R.id.btn_fruit);
        mVegeButton = (Button)findViewById(R.id.btn_vegetable);
        mExitButton = (Button)findViewById(R.id.btn_exit);
        mAnimalButton = (Button)findViewById(R.id.btn_animal);
        mInsectButton = (Button)findViewById(R.id.btn_insect);

        mColorButton.setOnClickListener(this);
        mNumberButton.setOnClickListener(this);
        mFruitButton.setOnClickListener(this);
        mVegeButton.setOnClickListener(this);
        mExitButton.setOnClickListener(this);
        mAnimalButton.setOnClickListener(this);
        mInsectButton.setOnClickListener(this);


        mLearningTypeSwitch = (Switch)findViewById(R.id.sw_learning_type);
        mLearningTypeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mLearningType = isChecked;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, LearningActivity.class);
        intent.putExtra(ACTIVITY_TYPE, mLearningType);
        switch (v.getId()) {
            case R.id.btn_color :
                intent.putExtra(ACTIVITY_FUNCTION, "color");
                startActivity(intent);
                break;

            case R.id.btn_fruit :
                intent.putExtra(ACTIVITY_FUNCTION, "fruit");
                startActivity(intent);
                break;

            case R.id.btn_number :
                intent.putExtra(ACTIVITY_FUNCTION, "number");
                startActivity(intent);
                break;

            case R.id.btn_vegetable :
                intent.putExtra(ACTIVITY_FUNCTION, "vegetable");
                startActivity(intent);
                break;

            case R.id.btn_animal :
                intent.putExtra(ACTIVITY_FUNCTION, "animal");
                startActivity(intent);
                break;

            case R.id.btn_insect :
                intent.putExtra(ACTIVITY_FUNCTION, "insect");
                startActivity(intent);
                break;

            case R.id.btn_exit :
                finish();
                break;

            default :
                break;
        }
    }
}


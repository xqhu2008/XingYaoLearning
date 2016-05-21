package com.bluehawk.xingyaolearning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.bluehawk.xingyaplearning.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner mSpnLearningType = null;
    private ArrayAdapter<String> mArrayAdapter;
    private ImageButton mColorButton;
    private ImageButton mNumberButton;
    private ImageButton mFruitButton;
    private ImageButton mVegeButton;
    private ImageButton mExitButton;
    private boolean mLearningType = true;

    static final String ACTIVITY_FUNCTION = "activity_function";
    static final String ACTIVITY_TYPE = "activity_type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        mSpnLearningType = (Spinner)findViewById(R.id.spn_learning_type);
        mArrayAdapter = new ArrayAdapter<String>(
                this,
                R.layout.spinner_row,
                R.id.weekofday,
                getResources().getStringArray(R.array.learning_type));

        //mArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        mSpnLearningType.setAdapter(mArrayAdapter);
        mSpnLearningType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mLearningType = position == 0 ? true : false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mColorButton = (ImageButton)findViewById(R.id.btn_color);
        mNumberButton = (ImageButton)findViewById(R.id.btn_number);
        mFruitButton = (ImageButton)findViewById(R.id.btn_fruit);
        mVegeButton = (ImageButton)findViewById(R.id.btn_vegetable);
        mExitButton = (ImageButton)findViewById(R.id.btn_exit);

        mColorButton.setOnClickListener(this);
        mNumberButton.setOnClickListener(this);
        mFruitButton.setOnClickListener(this);
        mVegeButton.setOnClickListener(this);
        mExitButton.setOnClickListener(this);
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

            case R.id.btn_exit :
                finish();
                break;

            default :
                break;
        }
    }
}

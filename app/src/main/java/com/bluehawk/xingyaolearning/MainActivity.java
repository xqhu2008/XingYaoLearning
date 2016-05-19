package com.bluehawk.xingyaolearning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.bluehawk.xingyaplearning.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner mSpnLearningType = null;
    private ArrayAdapter mArrayAdapter;
    private Button mColorButton;
    private Button mNumberButton;
    private Button mFruitButton;
    private Button mVegeButton;
    private Button mExitButton;

    static final String ACTIVITY_FUNCTION = "activity_function";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSpnLearningType = (Spinner)findViewById(R.id.spn_learning_type);
        mArrayAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.learning_type,
                android.R.layout.simple_spinner_item);

        mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpnLearningType.setAdapter(mArrayAdapter);
        mColorButton = (Button)findViewById(R.id.btn_color);
        mNumberButton = (Button)findViewById(R.id.btn_number);
        mFruitButton = (Button)findViewById(R.id.btn_fruit);
        mVegeButton = (Button)findViewById(R.id.btn_vegetable);
        mExitButton = (Button)findViewById(R.id.btn_exit);

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

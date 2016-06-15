package com.bluehawk.xingyaolearning.word;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.bluehawk.xingyaolearning.SplashActivity;
import com.bluehawk.xingyaolearning.YaoWordImage;
import com.bluehawk.xingyaplearning.R;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private Switch mLearningTypeSwitch;

    private boolean mLearningType = true;
    private YaoResourceManager mYaoResourceManager;

    static final String ACTIVITY_FUNCTION = "activity_function";
    static final String ACTIVITY_TYPE = "activity_type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        mYaoResourceManager = (YaoResourceManager) bundle.getSerializable(SplashActivity.YAO_RESOURCE_MANAGER);

        mListView = (ListView)findViewById(R.id.lv_learning_item);
        mListView.setDivider(null);
        final LearningAdapter adapter = new LearningAdapter(this, R.layout.learning_listview,
                mYaoResourceManager);

        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectedPosition(position);
                adapter.notifyDataSetInvalidated();
                YaoWordCategory item = (YaoWordCategory) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, LearningActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ACTIVITY_FUNCTION, item);
                bundle.putBoolean(ACTIVITY_TYPE, mLearningType);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

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

    private class LearningAdapter extends BaseAdapter {
        private LayoutInflater mLayoutInflater;
        private final int mLayout;
        private final Context mContext;
        private YaoResourceManager mYaoResourceManager;
        private int mSelectedPosition = -1;

        public LearningAdapter(Context context, int layout, YaoResourceManager ysrm) {
            this.mLayoutInflater = LayoutInflater.from(context);
            this.mLayout = layout;
            this.mContext = context;
            this.mYaoResourceManager = ysrm;
        }

        public void setSelectedPosition(int position) {
            mSelectedPosition = position;
        }

        @Override
        public int getCount() {
            return this.mYaoResourceManager.getSize();
        }

        @Override
        public Object getItem(int position) {
            return this.mYaoResourceManager.getCategory(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(mLayout, null);
                holder = new ViewHolder();
                holder.mImageView = (ImageView)convertView.findViewById(R.id.iv_image);
                holder.mTextView = (TextView)convertView.findViewById(R.id.tv_text);
                holder.mLayout = (LinearLayout)convertView.findViewById(R.id.lo_item);
                holder.mTextViewZh = (TextView)convertView.findViewById(R.id.tv_text_zh);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }

            YaoWordCategory item = (YaoWordCategory) getItem(position);
            holder.mImageView.setImageBitmap(YaoWordImage.loadBitmapFromAsset(mContext, item.getImageFileName()));
            holder.mTextView.setText(item.getName());
            holder.mTextViewZh.setText(item.getChineseMeaning());

            if (mSelectedPosition == position) {
                holder.mLayout.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
            } else {
                holder.mLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            }

            return convertView;
        }

        public final class ViewHolder {
            public ImageView mImageView;
            public TextView mTextView;
            public TextView mTextViewZh;
            public LinearLayout mLayout;
        }
    }
}


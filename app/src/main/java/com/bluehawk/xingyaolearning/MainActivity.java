package com.bluehawk.xingyaolearning;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.bluehawk.xingyaplearning.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private boolean mLearningType = true;
    private Switch mLearningTypeSwitch;

    static final String ACTIVITY_FUNCTION = "activity_function";
    static final String ACTIVITY_TYPE = "activity_type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        Vector<LearningItem> vector = new Vector<LearningItem>();

        try {
            String[] items = getResources().getAssets().list("resource");
            InputStream inputStream = null;
            for(String item : items) {
                inputStream = getResources().getAssets().open("item/" + item + ".png");
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                vector.add(new LearningItem(bitmap, item));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        mListView = (ListView)findViewById(R.id.lv_learning_item);
        mListView.setAdapter(new LearningAdapter(this, R.layout.learning_listview, vector));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LearningItem item = (LearningItem)parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, LearningActivity.class);
                intent.putExtra(ACTIVITY_FUNCTION, item.getName());
                intent.putExtra(ACTIVITY_TYPE, mLearningType);
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

    private class LearningItem {
        private Bitmap mImage;
        private String mName;

        public LearningItem(Bitmap image, String name) {
            mImage = image;
            mName = name;
        }

        public Bitmap getImage() {
            return mImage;
        }

        public String getName() {
            return mName;
        }
    }

    private class LearningAdapter extends BaseAdapter {
        private LayoutInflater mLayoutInflater;
        private final int mLayout;
        private final Context mContext;
        private Vector<LearningItem> mItemVector;

        public LearningAdapter(Context context, int layout, Vector<LearningItem> vector) {
            this.mLayoutInflater = LayoutInflater.from(context);
            this.mLayout = layout;
            this.mContext = context;
            this.mItemVector = vector;
        }

        @Override
        public int getCount() {
            return mItemVector.size();
        }

        @Override
        public Object getItem(int position) {
            return mItemVector.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.learning_listview, null);
                holder = new ViewHolder();
                holder.mImageView = (ImageView)convertView.findViewById(R.id.iv_image);
                holder.mTextView = (TextView)convertView.findViewById(R.id.tv_text);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }

            LearningItem item = (LearningItem) getItem(position);
            holder.mImageView.setImageBitmap(item.getImage());
            holder.mTextView.setText(item.getName());

            return convertView;
        }

        public final class ViewHolder {
            public ImageView mImageView;
            public TextView mTextView;
        }
    }
}


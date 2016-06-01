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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.bluehawk.xingyaplearning.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private Switch mLearningTypeSwitch;

    private boolean mLearningType = true;
    private LearningCategory mLearningCategory;

    private YaoStudyResourceManager mYaoStudyResourceManager;

    static final String ACTIVITY_FUNCTION = "activity_function";
    static final String ACTIVITY_TYPE = "activity_type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        try {
            String xmlFileName = getResources().getString(R.string.resource_config_file);
            InputStream xmlStream = getResources().getAssets().open(xmlFileName);
            mYaoStudyResourceManager = new YaoStudyResourceManager();
            mYaoStudyResourceManager.readYaoStudySites(xmlStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mLearningCategory = new LearningCategory("dictionary.txt", "resource", "category");
        mLearningCategory.LoadResource();

        mListView = (ListView)findViewById(R.id.lv_learning_item);
        mListView.setDivider(null);
        final LearningAdapter adapter = new LearningAdapter(this, R.layout.learning_listview,
                mLearningCategory.getItemVector());

        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectedPosition(position);
                adapter.notifyDataSetInvalidated();
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

    private  class LearningCategory {
        private String mCategoryFileName;
        private String mCategoryPathName;
        private String mResourcePathName;

        private Vector<LearningItem> mItemVector;

        public LearningCategory(String categoryFileName, String resourcePathName,
                                String categoryPathName) {
            mCategoryFileName = categoryFileName;
            mResourcePathName = resourcePathName;
            mCategoryPathName = categoryPathName;
        }

        public void LoadResource() {
            mItemVector = new Vector<LearningItem>();
            HashMap<String, String> categorys = new HashMap<String, String>();
            try {
                InputStream is = getResources().getAssets().open(mCategoryPathName + "/" +
                        mCategoryFileName);
                if (is != null)
                {
                    InputStreamReader inputreader = new InputStreamReader(is);
                    BufferedReader buffreader = new BufferedReader(inputreader);
                    String line;
                    while (( line = buffreader.readLine()) != null) {
                        int start = line.indexOf(":");
                        if (start != -1) {
                            categorys.put(line.substring(0, start), line.substring(start + 1));
                        }
                    }
                    is.close();
                }

                String[] items = getResources().getAssets().list(mResourcePathName);
                InputStream inputStream = null;
                for(String item : items) {
                    inputStream = getResources().getAssets().open(mCategoryPathName + "/" + item + ".png");
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    mItemVector.add(new LearningItem(bitmap, item, categorys.get(item)));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public Vector<LearningItem> getItemVector() {
            return mItemVector;
        }
    }

    private class LearningItem {
        private Bitmap mImage;
        private String mName;
        private String mNameZh;

        public LearningItem(Bitmap image, String name, String namezh) {
            mImage = image;
            mName = name;
            mNameZh = namezh;
        }

        public Bitmap getImage() {
            return mImage;
        }

        public String getName() {
            return mName;
        }

        public String getNameZh() {
            return mNameZh;
        }
    }

    private class LearningAdapter extends BaseAdapter {
        private LayoutInflater mLayoutInflater;
        private final int mLayout;
        private final Context mContext;
        private Vector<LearningItem> mItemVector;
        private int mSelectedPosition = -1;

        public LearningAdapter(Context context, int layout, Vector<LearningItem> vector) {
            this.mLayoutInflater = LayoutInflater.from(context);
            this.mLayout = layout;
            this.mContext = context;
            this.mItemVector = vector;
        }

        public void setSelectedPosition(int position) {
            mSelectedPosition = position;
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
                holder.mLayout = (LinearLayout)convertView.findViewById(R.id.lo_item);
                holder.mTextViewZh = (TextView)convertView.findViewById(R.id.tv_text_zh);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }

            LearningItem item = (LearningItem) getItem(position);
            holder.mImageView.setImageBitmap(item.getImage());
            holder.mTextView.setText(item.getName());
            holder.mTextViewZh.setText(item.getNameZh());

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


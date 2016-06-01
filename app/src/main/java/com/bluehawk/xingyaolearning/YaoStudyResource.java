package com.bluehawk.xingyaolearning;

import java.util.Vector;

/**
 * Created by Alex on 2016/5/30.
 */
public class YaoStudyResource {
    private String mCategory;
    private String mDescription;
    private String mImageFile;
    private Vector<YaoStudyItem> mYaoStudyItems;

    public YaoStudyResource(String category, String imageFile, String description) {
        mCategory = category;
        mDescription = description;
        mImageFile = imageFile;

        mYaoStudyItems = new Vector<YaoStudyItem>();
    }

    public String getCategory() {
        return mCategory;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getImageFile() {
        return mImageFile;
    }

    public Vector<YaoStudyItem> getYaoStudyItems() {
        return mYaoStudyItems;
    }

    public void addResource(YaoStudyItem item) {
        mYaoStudyItems.add(item);
    }

    public void addResource(String englishName, String chineseName,
                            String imageFileName, String audioFileName) {
        YaoStudyItem item = new YaoStudyItem(mCategory, englishName,
                chineseName, imageFileName, audioFileName);
        addResource(item);
    }
}

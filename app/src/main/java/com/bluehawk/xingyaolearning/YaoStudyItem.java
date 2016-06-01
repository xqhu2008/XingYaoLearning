package com.bluehawk.xingyaolearning;

/**
 * Created by Alex on 2016/5/30.
 */
public class YaoStudyItem {
    private String mCategory;
    private String mEnglishName;
    private String mChineseName;
    private String mImageFileName;
    private String mAudioFileName;

    public YaoStudyItem(String category, String englishName, String chineseName,
                        String imageFileName, String audioFileName) {
        mCategory = category;
        mEnglishName = englishName;
        mChineseName = chineseName;
        mImageFileName = imageFileName;
        mAudioFileName = audioFileName;
    }

    public YaoStudyItem(String category) {
        mCategory = category;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getEnglishName() {
        return mEnglishName;
    }

    public void setEnglishName(String englishName) {
        mEnglishName = englishName;
    }

    public String getChineseName() {
        return mChineseName;
    }

    public void setChineseName(String chineseName) {
        mChineseName = chineseName;
    }

    public String getImageFileName() {
        return mImageFileName;
    }

    public void setImageFileName(String imageFileName) {
        mImageFileName = imageFileName;
    }

    public String getAudioFileName() {
        return mAudioFileName;
    }

    public void setAudioFileName(String audioFileName) {
        mAudioFileName = audioFileName;
    }
}

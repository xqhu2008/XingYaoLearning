package com.bluehawk.xingyaolearning.word;

import java.io.Serializable;

/**
 * Created by Alex on 2016/6/14.
 */
public class YaoWordVocabulary implements Serializable {
    private String mName;
    private String mChineseMeaning;
    private String mImageFileName;
    private String mSoundFileName;

    public YaoWordVocabulary(String name, String chineseMeaning, String imageFileName, String soundFileName) {
        mName = name;
        mChineseMeaning = chineseMeaning;
        mImageFileName = imageFileName;
        mSoundFileName = soundFileName;
    }

    public YaoWordVocabulary(){

    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getChineseMeaning() {
        return mChineseMeaning;
    }

    public void setChineseMeaning(String chineseMeaning) {
        mChineseMeaning = chineseMeaning;
    }

    public String getImageFileName() {
        return mImageFileName;
    }

    public void setImageFileName(String imageFileName) {
        mImageFileName = imageFileName;
    }

    public String getSoundFileName() {
        return mSoundFileName;
    }

    public void setSoundFileName(String soundFileName) {
        mSoundFileName = soundFileName;
    }
}

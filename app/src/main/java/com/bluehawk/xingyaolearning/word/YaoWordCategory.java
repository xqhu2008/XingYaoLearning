package com.bluehawk.xingyaolearning.word;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by Alex on 2016/5/30.
 */
public class YaoWordCategory extends YaoWordVocabulary implements Serializable, Iterable<YaoWordVocabulary> {
    private Vector<YaoWordVocabulary> mVocabularyList;
    private HashMap<String, Integer> mVocabularyIndexes;

    public YaoWordCategory(String sName, String sCnMeaning, String sImgFilename) {
        super(sName, sCnMeaning, sImgFilename, "");

        mVocabularyList = new Vector<YaoWordVocabulary>();
        mVocabularyIndexes = new HashMap<String, Integer>();
    }

    public YaoWordCategory() {
        super();

        mVocabularyList = new Vector<YaoWordVocabulary>();
        mVocabularyIndexes = new HashMap<String, Integer>();
    }

    @Override
    public Iterator<YaoWordVocabulary> iterator() {
        return mVocabularyList.iterator();
    }

    public void addCategory(String sName, String sCnMeaning, String sImgFilename, String sSndFileName) {
        YaoWordVocabulary vocabulary = new YaoWordVocabulary(sName, sCnMeaning, sImgFilename, sSndFileName);
        addVocabulary(vocabulary);
    }

    public void addVocabulary(YaoWordVocabulary vocabulary) {
        mVocabularyList.add(vocabulary);
        mVocabularyIndexes.put(vocabulary.getName(), mVocabularyList.indexOf(vocabulary));
    }

    public YaoWordVocabulary getVocabulary(int position) {
        return mVocabularyList.get(position);
    }

    public YaoWordVocabulary getVocabulary(String sName) {
        int position = mVocabularyIndexes.get(sName);
        return getVocabulary(position);
    }

    public int getSize() {
        return mVocabularyList.size();
    }
}

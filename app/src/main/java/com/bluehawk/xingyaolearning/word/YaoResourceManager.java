package com.bluehawk.xingyaolearning.word;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Alex on 2016/5/30.
 */
public class YaoResourceManager implements Serializable, Iterable<YaoWordCategory>{
    private ArrayList<YaoWordCategory> mYaoWordCategories;
    private HashMap<String, Integer> mCategoryIndexes;

    public YaoResourceManager() {
        mYaoWordCategories = new ArrayList<YaoWordCategory>();
        mCategoryIndexes = new HashMap<String, Integer>();
    }

    @Override
    public Iterator<YaoWordCategory> iterator() {
        return mYaoWordCategories.iterator();
    }

    public void addCategory(YaoWordCategory category) {
        mYaoWordCategories.add(category);
        mCategoryIndexes.put(category.getName(), mYaoWordCategories.indexOf(category));
    }

    public YaoWordCategory getCategory(int position) {
        return mYaoWordCategories.get(position);
    }

    public YaoWordCategory getCategory(String sName) {
        int position = mCategoryIndexes.get(sName);
        return getCategory(position);
    }

    public int getSize() {
        return mYaoWordCategories.size();
    }
}

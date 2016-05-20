package com.bluehawk.xingyaolearning;

import android.content.Context;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Alex on 2016/5/19.
 */
public class LearningResourceManager {
    String mResoucePath = null;
    HashMap<String, LearningResource[]> mLearningResources = null;

    public LearningResourceManager(String resoucePath) {
        mResoucePath = resoucePath;
    }

    public boolean LoadResourceFromAsset(Context context, String path) {
        try {
            String[] resources = context.getAssets().list(path);
            mLearningResources = new HashMap<String, LearningResource[]>();
            for (String res : resources) {
                String[] resFiles = context.getAssets().list(path + "/" + res);
                processLearningResource(res, resFiles);
            }
        } catch (IOException e) {
            e.printStackTrace();
            mLearningResources = null;

            return false;
        }

        return true;
    }

    private void processLearningResource(String resType, String[] fileNames) {

    }

    private class LearningResource {
        String mResourceType;
        String mResourceName;
        String mAudioFileName;
        String mImageFileName;
        String mImageType;

        public LearningResource(String resourceType, String resourceName, String audioFileName,
                                String imageFileName, String imageType) {
            mResourceType = resourceType;
            mResourceName = resourceName;
            mAudioFileName = audioFileName;
            mImageFileName = imageFileName;
            mImageType = imageType;
        }

        public String getResourceType() {
            return mResourceType;
        }

        public void setResourceType(String resourceType) {
            mResourceType = resourceType;
        }

        public String getResourceName() {
            return mResourceName;
        }

        public void setResourceName(String resourceName) {
            mResourceName = resourceName;
        }

        public String getAudioFileName() {
            return mAudioFileName;
        }

        public void setAudioFileName(String audioFileName) {
            mAudioFileName = audioFileName;
        }

        public String getImageFileName() {
            return mImageFileName;
        }

        public void setImageFileName(String imageFileName) {
            mImageFileName = imageFileName;
        }

        public String getImageType() {
            return mImageType;
        }

        public void setImageType(String imageType) {
            mImageType = imageType;
        }
    }
}


package com.bluehawk.xingyaolearning;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

/**
 * Created by Alex on 2016/5/19.
 */
public class LearningResourceManager {
    Context mContext;
    String mResourcePath = null;
    String mResourceType = null;
    Vector<LearningResource> mLearningResources = null;

    private SoundPool mSndPool;
    private float mVolumnRatio;

    public LearningResourceManager(Context context, String resType, String resPath) {
        mResourcePath = resPath + "/" + resType;
        mContext = context;
        mResourceType = resType;
        mLearningResources = new Vector<LearningResource>();

        creatSoundPool();
        loadResource();
    }

    private void creatSoundPool() {
        mSndPool = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        AudioManager am = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volumnCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        mVolumnRatio = volumnCurrent / audioMaxVolumn;
    }

    private void loadResource() {
        try {
            String[] resources = mContext.getAssets().list(mResourcePath);

            Vector<String> bmpFiles = new Vector<String>();
            Vector<String> audioFiles = new Vector<String>();
            for (String item : resources) {
                if (item.endsWith(".bmp")) {
                    bmpFiles.add(item.substring(0, item.length() - 4));
                } else if (item.endsWith(".wav")) {
                    audioFiles.add(item.substring(0, item.length() - 4));
                }
            }

            // 检查对应的声音文件是否存在，如果不存在，则删除对应的文件；
            for (String file : bmpFiles) {
                if (audioFiles.contains(file)) {
                    LearningResource resItem = new LearningResource(mResourceType, file,
                            mResourcePath + "/" + file + ".wav",
                            mResourcePath + "/" + file + ".bmp",
                            "bmp");
                    AssetFileDescriptor afdSnd = mContext.getAssets()
                            .openFd(resItem.getAudioFileName());
                    int sound = mSndPool.load(afdSnd, 1);
                    resItem.setSoundIndex(sound);

                    mLearningResources.add(resItem);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playSound(int index) {
        int sound = mLearningResources.get(index).getSoundIndex();
        mSndPool.play(sound, mVolumnRatio, mVolumnRatio, 1, 0, 1);

    }

    public Bitmap getImage(int index) {
        Bitmap bmp = null;
        try{
            byte [] buffer = null;
            InputStream in = mContext.getAssets().open(mLearningResources.get(index).getImageFileName());

            int length = in.available();
            buffer = new byte[length];
            in.read(buffer);
            if (buffer != null) {
                bmp = BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return bmp;
    }

    public String getResourceName(int index) {
        return mLearningResources.get(index).getResourceName();
    }

    public int getSize() {
        return mLearningResources.size();
    }

    private class LearningResource {
        private String mResourceType;
        private String mResourceName;
        private String mAudioFileName;
        private String mImageFileName;
        private String mImageType;
        private int    mSoundIndex;

        public int getSoundIndex() {
            return mSoundIndex;
        }

        public void setSoundIndex(int soundIndex) {
            mSoundIndex = soundIndex;
        }

        public LearningResource(String resType, String resName, String audioFileName,
                                String imageFileName, String imageType) {
            mResourceType = resType;
            mAudioFileName = audioFileName;
            mImageFileName = imageFileName;
            mImageType = imageType;
            mResourceName = resName;
        }

        public String getResourceType() {
            return mResourceType;
        }

        public String getResourceName() {
            return mResourceName;
        }

        public void setResourceType(String resourceType) {
            mResourceType = resourceType;
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


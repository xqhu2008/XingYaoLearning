package com.bluehawk.xingyaolearning.word;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.SoundPool;

import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by Alex on 2016/6/12.
 */
public class YaoWordSound {
    private SoundPool mSndPool;
    private Context mContext;
    private float mVolumnRatio;
    private YaoWordCategory mYaoWordCategory;
    private Vector<Integer> mVocabularySoundList;
    private HashMap<String, Integer> mVocabularySoundMap;

    public YaoWordSound(Context context, YaoWordCategory category) {
        mContext = context;

        mYaoWordCategory = category;
        mVocabularySoundList = new Vector<Integer>();
        mVocabularySoundMap = new HashMap<String, Integer>();

        creatSoundPool();

        loadSound();
    }

    private void creatSoundPool() {
        mSndPool = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        AudioManager am = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volumnCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        mVolumnRatio = volumnCurrent / audioMaxVolumn;
    }

    public void playSound(int index) {
        int sound = mVocabularySoundList.get(index);
        mSndPool.play(sound, mVolumnRatio, mVolumnRatio, 1, 0, 1);

    }

    public void playSound(String sSndName) {
        int sound = mVocabularySoundMap.get(sSndName);
        mSndPool.play(sound, mVolumnRatio, mVolumnRatio, 1, 0, 1);
    }

    private void loadSound() {
        mVocabularySoundList.clear();
        try {
            for (YaoWordVocabulary vocabulary : mYaoWordCategory) {
                AssetFileDescriptor afdSnd = mContext.getAssets()
                        .openFd(vocabulary.getSoundFileName());
                int sound = mSndPool.load(afdSnd, 1);
                mVocabularySoundList.add(sound);
                mVocabularySoundMap.put(vocabulary.getName(), sound);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

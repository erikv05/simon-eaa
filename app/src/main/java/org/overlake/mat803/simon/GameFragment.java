package org.overlake.mat803.simon;

import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;

import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.overlake.mat803.simon.databinding.FragmentGameBinding;

import java.util.ArrayList;
import java.util.EnumMap;

public class GameFragment extends Fragment {

    private SoundPool mSoundPool;
    private ArrayList<Button> mSequence;
    private int mSequenceLength;
    private EnumMap<Button, Integer> mSounds;

    public GameFragment() {
        mSequence = new ArrayList<>();
        mSounds = new EnumMap<Button, Integer>(Button.class);
        mSequenceLength = 0;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSoundPool = new SoundPool.Builder().build();
        mSounds.put(Button.GREEN, mSoundPool.load(getContext(),R.raw.tone415,1));
        mSounds.put(Button.RED, mSoundPool.load(getContext(),R.raw.tone310,1));
        mSounds.put(Button.YELLOW, mSoundPool.load(getContext(),R.raw.tone252,1));
        mSounds.put(Button.BLUE, mSoundPool.load(getContext(),R.raw.tone209,1));

        //i incremented this by 5 just for a test
        for (int i = 0; i < 5; i++) {
            incrementSequence();
        }

        CountDownTimer timer = new CountDownTimer(1000L * mSequenceLength,1000) {

            int index = 0;
            @Override
            public void onTick(long l) {
                mSoundPool.play(mSounds.get(mSequence.get(index)),1.0f,1.0f,1,0, 1.0f);
                changeColor(mSequence.get(index));
                index++;
            }

            @Override
            public void onFinish() {
                index = 0;
            }
        };
        FragmentGameBinding binding = FragmentGameBinding.inflate(inflater);
        binding.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    timer.start();
            }
        });

        return binding.getRoot();
    }

    private void incrementSequence() {
            mSequence.add(Button.getRandomButton());
            mSequenceLength++;
    }

    private void changeColor(Button b) {
        ImageView button;
        switch(b) {
            case RED:
                button = (ImageView) getActivity().findViewById(R.id.red);
                button.setImageResource(R.drawable.ic_red_dark);
                break;
            case YELLOW:
                button = (ImageView) getActivity().findViewById(R.id.yellow);
                button.setImageResource(R.drawable.ic_yellow_dark);
                break;
            case GREEN:
                button = (ImageView) getActivity().findViewById(R.id.green);
                button.setImageResource(R.drawable.ic_green_dark);
                break;
            case BLUE:
                button = (ImageView) getActivity().findViewById(R.id.blue);
                button.setImageResource(R.drawable.ic_blue_dark);
                break;
            default:
                button = (ImageView) getActivity().findViewById(R.id.blue);
                button.setImageResource(R.drawable.ic_blue_dark);
        }
        CountDownTimer timer = new CountDownTimer(250L,250) {

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                switch (b) {
                    case RED:
                        button.setImageResource(R.drawable.ic_red);
                        break;
                    case BLUE:
                        button.setImageResource(R.drawable.ic_blue);
                        break;
                    case YELLOW:
                        button.setImageResource(R.drawable.ic_yellow);
                        break;
                    case GREEN:
                        button.setImageResource(R.drawable.ic_green);
                        break;
                }
            }
        };
        timer.start();
    }
}
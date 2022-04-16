package org.overlake.mat803.simon;

import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;

import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import org.overlake.mat803.simon.databinding.FragmentGameBinding;

import java.util.ArrayList;
import java.util.EnumMap;

public class GameFragment extends Fragment {

    private SoundPool mSoundPool;
    private ArrayList<Button> mSequence;
    private int mSequenceLength;
    private EnumMap<Button, Integer> mSounds;
    private boolean mPlaying;
    private boolean mStart;
    private int currIndex;

    public GameFragment() {
        mSequence = new ArrayList<>();
        mSounds = new EnumMap<Button, Integer>(Button.class);
        mSequenceLength = 0;
        mPlaying = true;
        mStart = false;
        currIndex = 0;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSoundPool = new SoundPool.Builder().build();
        mSounds.put(Button.GREEN, mSoundPool.load(getContext(),R.raw.tone415,1));
        mSounds.put(Button.RED, mSoundPool.load(getContext(),R.raw.tone310,1));
        mSounds.put(Button.YELLOW, mSoundPool.load(getContext(),R.raw.tone252,1));
        mSounds.put(Button.BLUE, mSoundPool.load(getContext(),R.raw.tone209,1));

        incrementSequence();
        FragmentGameBinding binding = FragmentGameBinding.inflate(inflater);

        CountDownTimer timer = new CountDownTimer(1000L * mSequenceLength,1000) {
            int index = 0;
            @Override
            public void onTick(long l) {
                mPlaying = false;
                mStart = false;
                mSoundPool.play(mSounds.get(mSequence.get(index)),1.0f,1.0f,1,0, 1.0f);
                changeColor(mSequence.get(index));
                index++;
            }

            @Override
            public void onFinish() {
                mPlaying = true;
                mStart = true;
                index = 0;
            }
        };

        binding.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mPlaying) {
                    timer.start();
                }
            }
        });

        binding.green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mStart) {
                    int i = getIndex();
                    buttonCalled(Button.GREEN, i);
                    if(i+1>=mSequenceLength){
                        replay(timer);
                    }
                }
            }
        });
        binding.blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mStart) {
                        int i = getIndex();
                        buttonCalled(Button.BLUE, i);
                        if(i+1>=mSequenceLength){
                            replay(timer);
                        }
                }
            }
        });
        binding.yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mStart) {
                    int i = getIndex();
                    buttonCalled(Button.YELLOW, i);
                    if(i+1>=mSequenceLength){
                        replay(timer);
                    }
                }
            }
        });
        binding.red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mStart) {
                    int i = getIndex();
                    buttonCalled(Button.RED, i);
                    if(i+1>=mSequenceLength){
                        replay(timer);
                    }
                }
            }
        });


        return binding.getRoot();
    }

    private void addIndex(){
        currIndex++;
    }

    private void resetIndex(){
        currIndex = 0;
    }

    private int getIndex(){
        return currIndex;
    }

    private void gameOver(){
        Bundle bundle = new Bundle();
        bundle.putInt("score", mSequenceLength);
        NavHostFragment.findNavController(this).navigate(R.id.action_gameFragment2_to_endScreen, bundle);
    }

    private void buttonCalled(Button b, int i){
        mSoundPool.play(mSounds.get(b), 1.0f, 1.0f, 1, 0, 1.0f);
        changeColor(b);
        if(b==mSequence.get(i)){
            addIndex();
        }else{
            gameOver();
        }
    }

    private void replay(CountDownTimer timer1){
        timer1.cancel();
        incrementSequence();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            public void run(){
                CountDownTimer timer = new CountDownTimer(1000L * mSequenceLength,1000) {
                    int index = 0;
                    @Override
                    public void onTick(long l) {
                        mPlaying = false;
                        mSoundPool.play(mSounds.get(mSequence.get(index)),1.0f,1.0f,1,0, 1.0f);
                        changeColor(mSequence.get(index));
                        index++;
                    }

                    @Override
                    public void onFinish() {
                        mPlaying = true;
                        index = 0;
                    }
                };
                resetIndex();
                timer.start();
            }
        },1000);
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
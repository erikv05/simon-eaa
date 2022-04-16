package org.overlake.mat803.simon;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.overlake.mat803.simon.databinding.FragmentEndScreenBinding;


public class EndScreen extends Fragment {


    private FragmentEndScreenBinding mBinding;

    public EndScreen() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentEndScreenBinding.inflate(inflater);

        //goes back to GameFragment when clicked
        mBinding.playAgain.setOnClickListener(view -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_endScreen_to_gameFragment2);
        });
        mBinding.score.setText(String.valueOf(getArguments().getInt("score")));
        return mBinding.getRoot();
    }
}
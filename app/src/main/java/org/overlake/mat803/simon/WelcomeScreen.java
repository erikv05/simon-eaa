package org.overlake.mat803.simon;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.overlake.mat803.simon.databinding.FragmentWelcomeScreenBinding;


public class WelcomeScreen extends Fragment {

    private FragmentWelcomeScreenBinding mBinding;

    public WelcomeScreen() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentWelcomeScreenBinding.inflate(inflater);

        //navigates to gameFragment when "ready" is clicked
        mBinding.readyButton.setOnClickListener(view -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_welcomeScreen_to_gameFragment2);
        });

        return mBinding.getRoot();
    }
}
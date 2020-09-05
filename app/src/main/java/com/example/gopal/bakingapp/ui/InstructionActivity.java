package com.example.gopal.bakingapp.ui;

/**
 * Created by Gopal on 2/12/2019.
 */


import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.gopal.bakingapp.R;
import com.example.gopal.bakingapp.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

public class InstructionActivity extends AppCompatActivity {
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private List<Step> instructions;
    private int position;
    private Button mNextStepButton;
    private Button mPrevStepButton;
    private TextView mInfo;
    private int mTotalStep;
    boolean wasPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        mInfo = findViewById(R.id.step_info);
        mPlayerView = (SimpleExoPlayerView) findViewById(R.id.video_player);
        mNextStepButton = findViewById(R.id.next_step_btn);
        mPrevStepButton = findViewById(R.id.previous_step_btn);

        // Getting list of custom object(i.e. List<Step> ) using Serializable & position that is clicked
        instructions = (List<Step>) getIntent().getSerializableExtra("list");
        position = getIntent().getIntExtra("position",1);
        mTotalStep = instructions.size()-1;
        showCurrentStep(position);

        // when next button is clicked
        mNextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( position < mTotalStep ){position = position + 1;}
                else {position = 0;}
                showCurrentStep(position);
            }
        });

        // when previous button is clicked
        mPrevStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position>0) position = position - 1;
                else position = mTotalStep;
                showCurrentStep(position);
            }
        });

    }

    public void showCurrentStep(int position){
        Step currentStep = instructions.get(position);
        String stepNumber = currentStep.getStepNumber();
        String description = currentStep.getStepDescription();
        String videoUrl = currentStep.getStepVideoUrl();

        setTitle("Step " + stepNumber);
        mInfo.setText(description);

        // if currently video is playing then release it before initialising the others
          if( wasPlaying ) {
              wasPlaying = false;
              releasePlayer();}

        // Initialize the player.
        if(!TextUtils.isEmpty(videoUrl)){
            mPlayerView.setVisibility(View.VISIBLE);
            wasPlaying = true;
            initializePlayer(Uri.parse(videoUrl));}
            else{
            mPlayerView.setVisibility(View.GONE);
        }

    }

    /**
     * Initialize ExoPlayer.
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(this, "ClassicalMusicQuiz");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    this, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }
    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if(mExoPlayer!=null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releasePlayer();
    }
}


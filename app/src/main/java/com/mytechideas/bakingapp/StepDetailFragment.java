package com.mytechideas.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.mytechideas.bakingapp.retrofit.Ingredient;
import com.mytechideas.bakingapp.retrofit.Step;

public class StepDetailFragment extends Fragment {

    private static final String STEP_STATE ="step";
    private static final String TABLETMODE = "mode";

    private OnNextButtonClickListener mCallback;

    private Step mStep;
    private boolean mode;
    private SimpleExoPlayer mExoplayer;
    private SimpleExoPlayerView mSimpleExoplayerView ;
    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady;

    public StepDetailFragment(){

    }

    public void setStep(Step step){

        mStep=step;

    }

    public void setMode(boolean twoPanel) {
        mode=twoPanel;

    }

    public interface OnNextButtonClickListener {
        void onNextSelected(int  position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        if(!getActivity().getResources().getBoolean(R.bool.tablet_mode)) {
            try {

                mCallback = (OnNextButtonClickListener) context;


            } catch (ClassCastException e) {
                throw new ClassCastException(context.toString()
                        + " must implement OnNextButtonClickListener");
            }
        }
    }

    // Inflates the GridView of all AndroidMe images
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);

        mSimpleExoplayerView= rootView.findViewById(R.id.exoplayer_view);
        TextView textView = rootView.findViewById(R.id.step_description);


        if(savedInstanceState!=null){
            mStep=savedInstanceState.getParcelable(STEP_STATE);
        }
        Button button = rootView.findViewById(R.id.button_next);
        String overAllDescription=mStep.getDescription();

        textView.setText(overAllDescription);


        if(!getResources().getBoolean(R.bool.tablet_mode)) {


            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mCallback.onNextSelected(mStep.getId() +1);

                }
            });
        }
        else{
            button.setVisibility(View.GONE);

        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(STEP_STATE,mStep);




    }

    private void initializePlayer() {
     if(mExoplayer==null){

         TrackSelector trackSelector= new DefaultTrackSelector();
         LoadControl loadControl= new DefaultLoadControl();
         mExoplayer= ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector,loadControl);
         mSimpleExoplayerView.setPlayer(mExoplayer);
     }
        String FirstLink=mStep.getVideoURL();
        String SecondLink= mStep.getThumbnailURL();

        Uri uri =null;
        if( !FirstLink.equals("")){
            uri = Uri.parse(mStep.getVideoURL());
        }
        else if (!SecondLink.equals("")) {
            uri = Uri.parse(mStep.getThumbnailURL());
        }
        else{
            uri=null;
        }


        MediaSource mediaSource = buildMediaSource(uri);
        mExoplayer.prepare(mediaSource, true, false);


    }
    private MediaSource buildMediaSource(Uri uri) {


        return new ExtractorMediaSource.Factory(

                new DefaultHttpDataSourceFactory("BakingApp")).
                createMediaSource(uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //hideSystemUi();
        if ((Util.SDK_INT <= 23 || mExoplayer == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (mExoplayer!= null) {
            playbackPosition = mExoplayer.getCurrentPosition();
            
            currentWindow = mExoplayer.getCurrentWindowIndex();
            playWhenReady = mExoplayer.getPlayWhenReady();
            mExoplayer.release();
            mExoplayer = null;
        }
    }


}

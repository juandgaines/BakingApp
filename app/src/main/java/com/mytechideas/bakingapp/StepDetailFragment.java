package com.mytechideas.bakingapp;

import android.content.Context;
import android.media.session.MediaSession;
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
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.mytechideas.bakingapp.retrofit.Ingredient;
import com.mytechideas.bakingapp.retrofit.Step;

public class StepDetailFragment extends Fragment implements  ExoPlayer.EventListener{

    private static final String STEP_STATE ="step";
    private static final String LOG_TAG=StepDetailFragment.class.getSimpleName();
    private static final String PLAYBACK_POSITION = "playback";
    private static final String CURRENT_WINDOW ="current_window" ;
    private static final String PLAY_WHEN_READY ="play_when_ready" ;

    private OnNextButtonClickListener mCallback;

    private Step mStep;
    private boolean mode;
    private SimpleExoPlayer mExoplayer;
    private SimpleExoPlayerView mSimpleExoplayerView ;
    private static MediaSessionCompat mMediaSession;
    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady;
    private PlaybackStateCompat.Builder mStateBuilder;

    public StepDetailFragment(){

    }

    public void setStep(Step step){

        mStep=step;

    }

    public void setMode(boolean twoPanel) {
        mode=twoPanel;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);

        mSimpleExoplayerView= rootView.findViewById(R.id.exoplayer_view);
        TextView textView = rootView.findViewById(R.id.step_description);

        initializeMediaSession();

        if(savedInstanceState!=null){
            mStep=savedInstanceState.getParcelable(STEP_STATE);
            playbackPosition=savedInstanceState.getLong(PLAYBACK_POSITION);
            currentWindow=savedInstanceState.getInt(CURRENT_WINDOW);

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
        playbackPosition = mExoplayer.getCurrentPosition();
        outState.putLong(PLAYBACK_POSITION, playbackPosition);
        outState.putInt(CURRENT_WINDOW, currentWindow);
        outState.putBoolean(PLAY_WHEN_READY, playWhenReady);

    }
    private void initializePlayer() {
     if(mExoplayer==null){

         TrackSelector trackSelector= new DefaultTrackSelector();
         LoadControl loadControl= new DefaultLoadControl();
         mExoplayer= ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector,loadControl);
         mExoplayer.seekTo(playbackPosition);
         mSimpleExoplayerView.setPlayer(mExoplayer);
     }
        String FirstLink=mStep.getVideoURL();
        String SecondLink= mStep.getThumbnailURL();

        Uri uri =null;
        if( !FirstLink.equals("")){
            mSimpleExoplayerView.setVisibility(View.VISIBLE);
            uri = Uri.parse(mStep.getVideoURL());
        }
        else if (!SecondLink.equals("")) {
            uri = Uri.parse(mStep.getThumbnailURL());
            mSimpleExoplayerView.setVisibility(View.VISIBLE);
        }
        else{
            uri=null;
            mSimpleExoplayerView.setVisibility(View.GONE);
        }

        MediaSource mediaSource = buildMediaSource(uri);
        mExoplayer.prepare(mediaSource, true, false);
        mExoplayer.setPlayWhenReady(true);
        mExoplayer.seekTo(currentWindow, playbackPosition);


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


    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        mMediaSession.setActive(false);

    }

    private void initializeMediaSession() {


        mMediaSession = new MediaSessionCompat(getContext(), LOG_TAG);
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mMediaSession.setMediaButtonReceiver(null);
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());

        mMediaSession.setCallback(new MySessionCallback());

        mMediaSession.setActive(true);

    }


    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
             mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                   mExoplayer.getCurrentPosition(), 1f);
        } else if((playbackState == ExoPlayer.STATE_READY)){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                  mExoplayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());


    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }

    public interface OnNextButtonClickListener {
        void onNextSelected(int  position);
    }


    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoplayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoplayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoplayer.seekTo(0);
        }
    }




}

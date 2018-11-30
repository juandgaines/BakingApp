package com.mytechideas.bakingapp;

import android.content.Context;
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

import com.mytechideas.bakingapp.retrofit.Ingredient;
import com.mytechideas.bakingapp.retrofit.Step;

public class StepDetailFragment extends Fragment {

    private static final String STEP_STATE ="step";
    private static final String TABLETMODE = "mode";

    private OnNextButtonClickListener mCallback;

    private Step mStep;
    private boolean mode;

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

        ImageView imageView= rootView.findViewById(R.id.exoplayer_view);
        TextView textView = rootView.findViewById(R.id.step_description);

        if(savedInstanceState!=null){
            mStep=savedInstanceState.getParcelable(STEP_STATE);
            mode=savedInstanceState.getBoolean(TABLETMODE);
        }

        String overAllDescription=mStep.getDescription();

        textView.setText(overAllDescription);
        Button button = rootView.findViewById(R.id.button_next);

        if(!mode) {

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
        outState.putBoolean(TABLETMODE,mode);


    }


}

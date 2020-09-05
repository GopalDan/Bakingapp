package com.example.gopal.bakingapp;

import android.content.Context;
import android.graphics.Movie;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Gopal on 2/12/2019.
 */

public class RecipeAdapter  extends ArrayAdapter<Step> {

    public RecipeAdapter(Context context,List<Step> steps) {
        super(context, 0, steps);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Step currentStep = getItem(position);

        TextView stepNumberTextView = (TextView) listItemView.findViewById(R.id.step_number);
        stepNumberTextView.setText(currentStep.getStepNumber());

        TextView stepInfo = (TextView) listItemView.findViewById(R.id.step_instruction);
        stepInfo.setText(currentStep.getStepInfo());

        String videoUrl = currentStep.getStepVideoUrl();
        TextView playVideoIcon = (TextView) listItemView.findViewById(R.id.play_video_tv);

        if(!TextUtils.isEmpty(videoUrl)){
            playVideoIcon.setText("->");
        }

        return listItemView;
    }
}



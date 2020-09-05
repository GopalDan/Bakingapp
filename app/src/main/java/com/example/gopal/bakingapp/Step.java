package com.example.gopal.bakingapp;

import java.io.Serializable;

/**
 * Created by Gopal on 2/12/2019.
 */

/**
 * class represent all information of a single step
 */

public class Step implements Serializable{
    private String stepNumber;
    private String stepInfo;
    private String stepDescription;
    private String stepVideoUrl;
    private String stepThumbnailUrl;

    public Step(String stepNumber, String stepInfo, String stepDescription, String stepVideoUrl, String stepThumbnailUrl) {
        this.stepNumber = stepNumber;
        this.stepInfo = stepInfo;
        this.stepDescription = stepDescription;
        this.stepVideoUrl = stepVideoUrl;
        this.stepThumbnailUrl = stepThumbnailUrl;
    }

    public String getStepNumber() {
        return stepNumber;
    }

    public String getStepInfo() {
        return stepInfo;
    }

    public String getStepDescription() {
        return stepDescription;
    }

    public String getStepVideoUrl() {
        return stepVideoUrl;
    }

    public String getStepThumbnailUrl() {
        return stepThumbnailUrl;
    }
}

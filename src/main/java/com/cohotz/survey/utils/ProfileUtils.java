package com.cohotz.survey.utils;

public class ProfileUtils {

    public static String getYearRange(double years){
        if(years <= 1){
            return "1Yr";
        }else if(years <= 3){
            return "1-3Yrs";
        }else if(years <= 6){
            return "3-6Yrs";
        }else if(years <= 10){
            return "6-10Yrs";
        }else if(years <= 15){
            return "10-15Yrs";
        }else if(years <= 20){
            return "15-20Yrs";
        }else{
            return "20+Yrs";
        }
    }
}

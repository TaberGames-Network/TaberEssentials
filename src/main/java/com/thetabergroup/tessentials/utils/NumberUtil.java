package com.thetabergroup.tessentials.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class NumberUtil {

    static DecimalFormat twoDPlaces = new DecimalFormat("#,###.##");
    static DecimalFormat currentFormat = new DecimalFormat("#0.00", DecimalFormatSymbols.getInstance(Locale.US));

    public static boolean isInt(final String sInt){

        try{
            Integer.parseInt(sInt);
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }

}

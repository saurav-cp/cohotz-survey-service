package com.cohotz.survey.model.score;

import lombok.Data;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

@Data
public class Score {
    protected double value;
    protected long count;
    protected int month;
    protected int year;
    protected String xLabel;
    public Score xLabel(){
        LocalDate d = LocalDate.of(year, month, 1);
        this.xLabel = Month.of(this.month).getDisplayName(TextStyle.SHORT, Locale.US) +"-"+ year;
        return this;
    }
    public Score(double value, int month, int year){
        this.value = value;
        this.month = month;
        this.year = year;
    }
}

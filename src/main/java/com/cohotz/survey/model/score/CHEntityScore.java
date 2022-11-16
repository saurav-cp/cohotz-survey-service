package com.cohotz.survey.model.score;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.cohotz.boot.model.common.CohotzEntity;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CHEntityScore extends CohotzEntity {
    private int target;
    private List<CHEntityScore> options;
    private List<Score> scores = new ArrayList<>();

    public CHEntityScore(String name, List<Score> scores) {
        this.name = name;
        this.scores.addAll(scores);
    }
}

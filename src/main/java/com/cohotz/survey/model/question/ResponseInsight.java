package com.cohotz.survey.model.question;

import lombok.*;
import org.cohotz.boot.model.common.CohotzEntity;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseInsight {
    public ResponseInsight(String insight) {
        this.insight = insight;
    }
    String insight;
    CohotzEntity engine;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseInsight that = (ResponseInsight) o;
        return Objects.equals(insight, that.insight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(insight);
    }
}

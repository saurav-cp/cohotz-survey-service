package com.cohotz.survey.model.question;

import com.cohotz.survey.client.core.model.CultureEngineMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseInsight {
    public ResponseInsight(String insight) {
        this.insight = insight;
    }
    String insight;
    CultureEngineMin engine;
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

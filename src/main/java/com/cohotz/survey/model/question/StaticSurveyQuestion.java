package com.cohotz.survey.model.question;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.cohotz.boot.model.common.CohotzEntity;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "responseType",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ChoiceBasedSurveyQuestion.class, name = "SINGLE_SELECT"),
        @JsonSubTypes.Type(value = ChoiceBasedSurveyQuestion.class, name = "MULTI_SELECT"),
        @JsonSubTypes.Type(value = TextResponseBasedSurveyQuestion.class, name = "TEXT")
})
public class StaticSurveyQuestion {

    protected String id;
    @Field("pool_ques_ref_cd")
    protected String poolQuesReferenceCode;
    protected int position;
    protected boolean skippable;
    protected double weight;
    protected double max;
    protected String text;
    @Field("rsp_type")
    protected String responseType;
    private CohotzEntity engine;
    @Field("smart_skip")
    private boolean smartSkip = false;
    private double score;
    @Field("rsp_count")
    private int responseCount;

    public StaticSurveyQuestion(String poolQuesReferenceCode){
        this.poolQuesReferenceCode = poolQuesReferenceCode;
    }
    public StaticSurveyQuestion(String poolQuesReferenceCode, int position, boolean skippable, double weight, double max){
        this.poolQuesReferenceCode = poolQuesReferenceCode;
        this.position = position;
        this.skippable = skippable;
        this.weight = weight;
        this.max = max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StaticSurveyQuestion)) return false;
        StaticSurveyQuestion question = (StaticSurveyQuestion) o;
        return poolQuesReferenceCode.equals(question.poolQuesReferenceCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(poolQuesReferenceCode);
    }

    @Override
    public String toString() {
        return "StaticSurveyQuestion{" +
                "poolQuesReferenceCode='" + poolQuesReferenceCode + '\'' +
                '}';
    }
}

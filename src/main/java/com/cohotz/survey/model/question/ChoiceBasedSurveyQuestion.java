package com.cohotz.survey.model.question;

import com.cohotz.survey.client.core.model.ResponseOption;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;
import java.util.TreeMap;

@Getter
@Setter
@ToString
public class ChoiceBasedSurveyQuestion extends StaticSurveyQuestion {
    public ChoiceBasedSurveyQuestion(){
        super();
        responseOptionMap = new TreeMap<>();
    }
    @Field("rsp_option_map")
    private Map<Integer, ResponseOption> responseOptionMap;
}

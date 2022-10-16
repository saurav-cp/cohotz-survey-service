package com.cohotz.survey.dto.response;

import com.cohotz.survey.client.core.model.CultureEngineMin;
import com.cohotz.survey.model.SurveyStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class SurveyMinRes {
    protected String id;
    protected String name;
    protected SurveyStatus status;
    @Field("engines")
    protected List<CultureEngineMin> engines;
    @Field("block.name")
    protected String block;
    @Field("end_dt")
    protected LocalDate endDate;
    @Field("part_count")
    protected long partCount;
    @Field("res_count")
    protected long responseCount;

    @Override
    public String toString() {
        return "SurveyMinRes{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", block='" + block + '\'' +
                ", endDate=" + endDate +
                ", partCount=" + partCount +
                ", responseCount=" + responseCount +
                '}';
    }
}

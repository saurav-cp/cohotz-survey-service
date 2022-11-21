package com.cohotz.survey.dto.producer.responseInsight;

import com.cohotz.survey.response.insight.record.ResponseInsight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cohotz.boot.model.common.CohotzEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseInsightDTO {

    private String email;

    private String tenant;

    private CohotzEntity question;

    private CohotzEntity engine;

    private CohotzEntity block;

    private String insight;

    private String source;

    private String channel;

    public static ResponseInsightDTO generate(ResponseInsight record) {
        ResponseInsightDTO ri = new ResponseInsightDTO();
        ri.setEmail(record.getEmail());
        ri.setTenant(record.getTenant());
        ri.setQuestion(new CohotzEntity(record.getQuestion().getText(), record.getQuestion().getCode(), "QUESTION"));
        ri.setEngine(new CohotzEntity(record.getEngine().getName(), record.getEngine().getCode(), "ENGINE"));
        ri.setBlock(new CohotzEntity(record.getBlock().getName(), record.getBlock().getCode(), "BLOCK"));
        ri.setInsight(record.getInsight());
        return ri;

    }
}

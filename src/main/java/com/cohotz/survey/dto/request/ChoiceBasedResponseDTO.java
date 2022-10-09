package com.cohotz.survey.dto.request;

import com.cohotz.survey.client.core.model.PoolQuestion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChoiceBasedResponseDTO extends ResponseDTO{
    private List<Integer> selections;

    public ChoiceBasedResponseDTO(String questionCode, List<Integer> selections, String comment, boolean skipped, String type) {
        super(questionCode, comment, skipped, type);
        this.selections = selections;

    }
}

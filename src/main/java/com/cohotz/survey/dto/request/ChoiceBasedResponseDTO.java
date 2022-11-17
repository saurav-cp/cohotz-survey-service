package com.cohotz.survey.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.cohotz.boot.dto.CohotzChannel;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChoiceBasedResponseDTO extends ResponseDTO{
    private List<Integer> selections;

    public ChoiceBasedResponseDTO(String questionCode, List<Integer> selections, String comment, boolean skipped, String type, CohotzChannel channel) {
        super(questionCode, comment, skipped, type, channel);
        this.selections = selections;

    }
}

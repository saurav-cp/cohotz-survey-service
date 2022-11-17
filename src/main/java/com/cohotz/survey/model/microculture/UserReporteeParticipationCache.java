package com.cohotz.survey.model.microculture;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Sharded;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Document("ch_participation_micro_culture_cache")
@Sharded(shardKey = "tenant")
@AllArgsConstructor
@NoArgsConstructor
public class UserReporteeParticipationCache {
    private List<UserReporteeParticipation> score;
    private String tenant;
    private String code;
    private LocalDate start;
    private LocalDate end;
}

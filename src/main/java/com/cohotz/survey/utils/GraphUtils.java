package com.cohotz.survey.utils;

import com.cohotz.survey.model.score.Score;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class GraphUtils {

    public static List<Score> fillTimeSeriesGap(LocalDate from, LocalDate to, List<Score> data){
        LocalDate startDate = from;
        List<Score> response = new ArrayList<>();
        AtomicReference<Optional<Score>> previousAverage = new AtomicReference<>(Optional.ofNullable(null));
        AtomicInteger index = new AtomicInteger(1);
        while (startDate.isBefore(to) || startDate.isEqual(to)) {
            int currentDay = startDate.getDayOfMonth();
            int currentMonth = startDate.getMonthValue();
            int currentYear = startDate.getYear();
            data.stream()
                    .filter(d -> d.getMonth() == currentMonth)
                    .filter(d -> d.getYear() == currentYear)
                    .findFirst()
                    .ifPresentOrElse(
                            a -> {
                                Score point = a.xLabel();
                                response.add(point);
                                previousAverage.set(Optional.of(a));
                            },
                            () -> previousAverage.get().ifPresentOrElse(
                                    pa -> response.add(new Score(pa.getValue(), currentMonth, currentYear).xLabel()),
                                    () -> response.add(new Score(0, currentMonth, currentYear).xLabel())));


            startDate = startDate.plusMonths(1);
        }
        return response;
    }
}

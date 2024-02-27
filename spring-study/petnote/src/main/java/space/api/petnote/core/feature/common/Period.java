package space.api.petnote.core.feature.common;

import java.time.LocalDateTime;

public class Period {

    public LocalDateTime startDate;
    public LocalDateTime endDate;

    public Period(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate.withHour(0).withMinute(0).withSecond(0);
        this.endDate = endDate.withHour(23).withMinute(59).withSecond(59);
    }

    public Period(String startDate, String endDate) {

    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

}

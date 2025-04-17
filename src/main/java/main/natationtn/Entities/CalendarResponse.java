package main.natationtn.Entities;

import java.util.List;

public class CalendarResponse {
    private List<CompetitionEvent> events;
    private List<QualificationRule> qualifications;

    public CalendarResponse(List<CompetitionEvent> events, List<QualificationRule> qualifications) {
        this.events = events;
        this.qualifications = qualifications;
    }

    public List<CompetitionEvent> getEvents() {
        return events;
    }

    public void setEvents(List<CompetitionEvent> events) {
        this.events = events;
    }

    public List<QualificationRule> getQualifications() {
        return qualifications;
    }

    public void setQualifications(List<QualificationRule> qualifications) {
        this.qualifications = qualifications;
    }
}

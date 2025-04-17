package main.natationtn.Entities;

public class CompetitionEvent {
    private String title;
    private String locationAndName;


    public CompetitionEvent(String title, String location) {
        this.title = title;
        this.locationAndName = location;

    }

    public CompetitionEvent() {
        this.title = "";
        this.locationAndName = "";

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocationAndName() {
        return locationAndName;
    }

    public void setLocationAndName(String locationAndName) {
        this.locationAndName = locationAndName;
    }




}

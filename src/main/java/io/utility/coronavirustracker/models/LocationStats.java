package io.utility.coronavirustracker.models;

public class LocationStats {

    private String state;
    private String country;
    private int totalCaseReported;
    private int increaseFromPreviousDay;

    public int getIncreaseFromPreviousDay() {
        return increaseFromPreviousDay;
    }

    public void setIncreaseFromPreviousDay(int increaseFromPreviousDay) {
        this.increaseFromPreviousDay = increaseFromPreviousDay;
    }



    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getTotalCaseReported() {
        return totalCaseReported;
    }

    public void setTotalCaseReported(int totalCaseReported) {
        this.totalCaseReported = totalCaseReported;
    }

    @Override
    public String toString() {
        return "LocationStats{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", totalCaseReported=" + totalCaseReported +
                '}';
    }
}

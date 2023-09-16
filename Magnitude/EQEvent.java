/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Magnitude;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author clari
 */
public class EQEvent implements Comparable<EQEvent> {

    private String eventID;
    private LocalDateTime time;
    private double latitude;
    private double longitude;
    private double depthKm;
    private String author;
    private String catalog;
    private String contributor;
    private String contributorID;
    private String magType;
    private double magnitude;
    private String magAuthor;
    private String eventLocationName;

    public EQEvent() {

    }

    public EQEvent(String eventID, LocalDateTime time, double latitude, double longitude, double depthKm, String author, String catalog, String contributor, String contributorID, String magType, double magnitude, String magAuthor, String eventLocationName) {
        this.eventID = eventID;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
        this.depthKm = depthKm;
        this.author = author;
        this.catalog = catalog;
        this.contributor = contributor;
        this.contributorID = contributorID;
        this.magType = magType;
        this.magnitude = magnitude;
        this.magAuthor = magAuthor;
        this.eventLocationName = eventLocationName;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getDepthkm() {
        return depthKm;
    }

    public void setDepthkm(double depthkm) {
        this.depthKm = depthkm;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getContributor() {
        return contributor;
    }

    public void setContributor(String contributor) {
        this.contributor = contributor;
    }

    public String getContributorID() {
        return contributorID;
    }

    public void setContributorID(String contributorID) {
        this.contributorID = contributorID;
    }

    public String getMagType() {
        return magType;
    }

    public void setMagType(String magType) {
        this.magType = magType;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public String getMagAuthor() {
        return magAuthor;
    }

    public void setMagAuthor(String magAuthor) {
        this.magAuthor = magAuthor;
    }

    public String getEventLocationName() {
        return eventLocationName;
    }

    public void setEventLocationName(String eventLocationName) {
        this.eventLocationName = eventLocationName;
    }

    @Override
    public int hashCode() {
        int hash = 3;

        hash = 53 * hash + Objects.hashCode(this.eventID);

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EQEvent other = (EQEvent) obj;
        if (!Objects.equals(this.eventID, other.eventID)) {
            return false;
        }

        return true;
    }

    @Override
    public int compareTo(EQEvent o) {
        return this.eventID.compareTo(o.eventID);
    }

    @Override
    public String toString() {
        return "\nEQEvent:" + "\neventID=" + eventID + " \ntime=" + time + " \nlatitude=" + latitude + " \nlongitude=" + longitude + " \ndepthkm=" + depthKm + " \nauthor=" + author + " \ncontributor=" + contributor + " \ncontributorID=" + contributorID + " \nmagType=" + magType + " \nmagnitude=" + magnitude + " \nmagAuthor=" + magAuthor + " \neventLocationName=" + eventLocationName + "}\n";
    }
}

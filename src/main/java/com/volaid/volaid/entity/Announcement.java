package com.volaid.volaid.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@NodeEntity
public class Announcement {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String description;

    private String deliveryInfo;

    private String locationName;

    private String locationAddress;

    private Boolean announceStatus;

    private Date createdAt;

    private Date updatedAt;

    @Relationship(type = "ANNOUNCE_OWNER", direction = Relationship.INCOMING)
    private User user;

    @Relationship(type = "ANNOUNCE_CATEGORY", direction = Relationship.INCOMING)
    private AnnouncementCategory announcementCategory;

    @Relationship(type = "ANNOUNCE_PHOTOS")
    private Set<AnnouncementPhoto> announcementPhotos = new HashSet<>();

    @Relationship(type = "ANNOUNCE_OF")
    private Set<AnnouncementComment> announcementComments = new HashSet<>();

    @Relationship(type = "ANNOUNCE_REPORT_OF")
    private Set<AnnouncementReport> announceReports = new HashSet<>();

    @Relationship(type = "USER_WARNING_OF")
    private Set<UserWarning> userWarnings = new HashSet<>();

    @Relationship(type = "USER_PENALTY_OF")
    private Set<UserPenalty> userPenalties = new HashSet<>();

    @Relationship(type = "USER_FEEDBACK_OF")
    private Set<Announcement> announcement = new HashSet<>();

    public Announcement() {
        this.createdAt = Calendar.getInstance().getTime();
        this.updatedAt = Calendar.getInstance().getTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(String deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public Boolean getAnnounceStatus() {
        return announceStatus;
    }

    public void setAnnounceStatus(Boolean announceStatus) {
        this.announceStatus = announceStatus;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AnnouncementCategory getAnnouncementCategory() {
        return announcementCategory;
    }

    public void setAnnouncementCategory(AnnouncementCategory announcementCategory) {
        this.announcementCategory = announcementCategory;
    }

    public Set<AnnouncementPhoto> getAnnouncementPhotos() {
        return announcementPhotos;
    }

    public void setAnnouncementPhotos(Set<AnnouncementPhoto> announcementPhotos) {
        this.announcementPhotos = announcementPhotos;
    }

    public Set<AnnouncementComment> getAnnouncementComments() {
        return announcementComments;
    }

    public void setAnnouncementComments(Set<AnnouncementComment> announcementComments) {
        this.announcementComments = announcementComments;
    }

    public Set<AnnouncementReport> getAnnounceReports() {
        return announceReports;
    }

    public void setAnnounceReports(Set<AnnouncementReport> announceReports) {
        this.announceReports = announceReports;
    }

    public Set<UserWarning> getUserWarnings() {
        return userWarnings;
    }

    public void setUserWarnings(Set<UserWarning> userWarnings) {
        this.userWarnings = userWarnings;
    }

    public Set<UserPenalty> getUserPenalties() {
        return userPenalties;
    }

    public void setUserPenalties(Set<UserPenalty> userPenalties) {
        this.userPenalties = userPenalties;
    }

    public Set<Announcement> getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(Set<Announcement> announcement) {
        this.announcement = announcement;
    }
}

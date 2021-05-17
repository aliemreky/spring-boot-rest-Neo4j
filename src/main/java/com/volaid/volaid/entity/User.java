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
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private String username;

    private String password;

    private String profilePhoto;

    private Boolean emailPhoneShow;

    private Boolean emailVerify;

    private String token;

    private Date createdDate;

    private Boolean deleted;

    private Date deletedAt;

    @Relationship(type = "ANNOUNCE_OWNER")
    private Set<Announcement> announcements = new HashSet<>();

    @Relationship(type = "USER_REPORT")
    private Set<UserReport> userReportTargets = new HashSet<>();

    @Relationship(type = "USER_REPORT")
    private Set<UserReport> userReportSources = new HashSet<>();

    @Relationship(type = "COMMENT_OWNER")
    private Set<AnnouncementComment> announcementComments = new HashSet<>();

    @Relationship(type = "ANNOUNCE_REPORT_OWNER")
    private Set<Announcement> announceReports = new HashSet<>();

    @Relationship(type = "USER_WARNING_OWNER")
    private Set<UserWarning> userWarnings = new HashSet<>();

    @Relationship(type = "USER_PENALTY_OWNER")
    private Set<UserPenalty> userPenalties = new HashSet<>();

    @Relationship(type = "USER_FEEDBACK")
    private User fromUser;

    @Relationship(type = "USER_FEEDBACK")
    private User toUser;

    @Relationship(type = "HAS_ROLE")
    private Set<UserRole> roles = new HashSet<>();

    public User() {
        this.emailVerify = false;
        this.emailPhoneShow = true;
        this.createdDate = Calendar.getInstance().getTime();
        this.deleted = false;
        this.deletedAt = null;
    }

    public String getFullName(){
        return this.firstName + " " + this.lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getEmailVerify() {
        return emailVerify;
    }

    public void setEmailVerify(Boolean emailVerify) {
        this.emailVerify = emailVerify;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Boolean getEmailPhoneShow() {
        return emailPhoneShow;
    }

    public void setEmailPhoneShow(Boolean emailPhoneShow) {
        this.emailPhoneShow = emailPhoneShow;
    }

    public Set<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(Set<Announcement> announcements) {
        this.announcements = announcements;
    }

    public Set<UserReport> getUserReportTargets() {
        return userReportTargets;
    }

    public void setUserReportTargets(Set<UserReport> userReportTargets) {
        this.userReportTargets = userReportTargets;
    }

    public Set<UserReport> getUserReportSources() {
        return userReportSources;
    }

    public void setUserReportSources(Set<UserReport> userReportSources) {
        this.userReportSources = userReportSources;
    }

    public Set<AnnouncementComment> getAnnouncementComments() {
        return announcementComments;
    }

    public void setAnnouncementComments(Set<AnnouncementComment> announcementComments) {
        this.announcementComments = announcementComments;
    }

    public Set<Announcement> getAnnounceReports() {
        return announceReports;
    }

    public void setAnnounceReports(Set<Announcement> announceReports) {
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

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }
}

package com.jeavio.gosnow.Model;

/**
 * Created by Krishna on 8/5/2016.
 */
public class User {

    String facebookId;
    String sessionId;
    String gender;
    String user_id;
    String deletedContactId;
    String senderId;
    String email;

    String age;
    //@property (nonatomic, copy) NSMutableArray * sharedContacts;
    String aboutMe;
    String firstName;
    String lastName;
    String riderType;
    String riderTypeId;
    String skillLevel;
    String skillLevelID;
    String plannedTripId;
    String plannedTripName;
    String currentLocation;
    String latitude;
    String longitude;
    String userPhotos;
    String userPhotos250;
    String address;
    String distance;
    String profileImage;
    String interestId;
    String interestName;
    String locationName;
    String model;
    String tripSeason;
    String tripName;
    String tripDecription;
    String locationId;
    String contactsName;
    String friendCount;
    String interestCount;
    String locationCount;
    String inContactFrom;
    String tripImage;
    String tripThumbImage;
    String onlineTill;
    int nextDestinationId;
    String nextDestinationName;

  //  @property (nonatomic, retain) NSString *isOnline;

    Boolean isInMyContact;
    Boolean isSelect;
    Boolean isInvited;

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDeletedContactId() {
        return deletedContactId;
    }

    public void setDeletedContactId(String deletedContactId) {
        this.deletedContactId = deletedContactId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
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

    public String getRiderType() {
        return riderType;
    }

    public void setRiderType(String riderType) {
        this.riderType = riderType;
    }

    public String getRiderTypeId() {
        return riderTypeId;
    }

    public void setRiderTypeId(String riderTypeId) {
        this.riderTypeId = riderTypeId;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }

    public String getSkillLevelID() {
        return skillLevelID;
    }

    public void setSkillLevelID(String skillLevelID) {
        this.skillLevelID = skillLevelID;
    }

    public String getPlannedTripId() {
        return plannedTripId;
    }

    public void setPlannedTripId(String plannedTripId) {
        this.plannedTripId = plannedTripId;
    }

    public String getPlannedTripName() {
        return plannedTripName;
    }

    public void setPlannedTripName(String plannedTripName) {
        this.plannedTripName = plannedTripName;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getUserPhotos() {
        return userPhotos;
    }

    public void setUserPhotos(String userPhotos) {
        this.userPhotos = userPhotos;
    }

    public String getUserPhotos250() {
        return userPhotos250;
    }

    public void setUserPhotos250(String userPhotos250) {
        this.userPhotos250 = userPhotos250;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getInterestId() {
        return interestId;
    }

    public void setInterestId(String interestId) {
        this.interestId = interestId;
    }

    public String getInterestName() {
        return interestName;
    }

    public void setInterestName(String interestName) {
        this.interestName = interestName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTripSeason() {
        return tripSeason;
    }

    public void setTripSeason(String tripSeason) {
        this.tripSeason = tripSeason;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getTripDecription() {
        return tripDecription;
    }

    public void setTripDecription(String tripDecription) {
        this.tripDecription = tripDecription;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    public String getFriendCount() {
        return friendCount;
    }

    public void setFriendCount(String friendCount) {
        this.friendCount = friendCount;
    }

    public String getInterestCount() {
        return interestCount;
    }

    public void setInterestCount(String interestCount) {
        this.interestCount = interestCount;
    }

    public String getLocationCount() {
        return locationCount;
    }

    public void setLocationCount(String locationCount) {
        this.locationCount = locationCount;
    }

    public String getInContactFrom() {
        return inContactFrom;
    }

    public void setInContactFrom(String inContactFrom) {
        this.inContactFrom = inContactFrom;
    }

    public String getTripImage() {
        return tripImage;
    }

    public void setTripImage(String tripImage) {
        this.tripImage = tripImage;
    }

    public String getTripThumbImage() {
        return tripThumbImage;
    }

    public void setTripThumbImage(String tripThumbImage) {
        this.tripThumbImage = tripThumbImage;
    }

    public String getOnlineTill() {
        return onlineTill;
    }

    public void setOnlineTill(String onlineTill) {
        this.onlineTill = onlineTill;
    }

    public Boolean getInMyContact() {
        return isInMyContact;
    }

    public void setInMyContact(Boolean inMyContact) {
        isInMyContact = inMyContact;
    }

    public Boolean getSelect() {
        return isSelect;
    }

    public void setSelect(Boolean select) {
        isSelect = select;
    }

    public Boolean getInvited() {
        return isInvited;
    }

    public void setInvited(Boolean invited) {
        isInvited = invited;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNextDestinationId() {
        return nextDestinationId;
    }

    public void setNextDestinationId(int nextDestinationId) {
        this.nextDestinationId = nextDestinationId;
    }

    public String getNextDestinationName() {
        return nextDestinationName;
    }

    public void setNextDestinationName(String nextDestinationName) {
        this.nextDestinationName = nextDestinationName;
    }
}

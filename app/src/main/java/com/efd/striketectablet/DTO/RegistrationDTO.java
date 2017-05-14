package com.efd.striketectablet.DTO;

import java.io.Serializable;

public class RegistrationDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String sensor1DeviceAddress;
    private String sensor2DeviceAddress;
    private String birthday;
    private String gender;
    private String height;
    private String weight;
    private String stance;
    private String skillLevel;
    private String gloveType;
    private String reach;
    private String firstName;
    private String lastName;
    private String zipCode;
    private String country;
    private String email;
    private String password;
    private String securityQuestion;
    private String securityAnswer;
    private String countryId;
    private String questionId;
    private String traineeServerId;
    private String leftDeviceSensorName;
    private String leftDeviceGeneration;
    private String rightDeviceSensorName;
    private String rightDeviceGeneration;
    private String confirmEmail;
    private String confirmPassword;
    private String traineeAccessToken;

    public RegistrationDTO() {
        super();
    }

    public RegistrationDTO(String sensor1DeviceAddress,
                           String sensor2DeviceAddress, String birthday, String gender,
                           String height, String weight, String stance, String skillLevel,
                           String gloveType, String reach, String firstName, String lastName,
                           String zipCode, String country, String email, String password,
                           String securityQuestion, String securityAnswer, String countryId,
                           String questionId, String traineeServerId,
                           String leftDeviceSensorName, String leftDeviceGeneration,
                           String rightDeviceSensorName, String rightDeviceGeneration,
                           String confirmEmail, String confirmPassword,
                           String traineeAccessToken) {
        super();
        this.sensor1DeviceAddress = sensor1DeviceAddress;
        this.sensor2DeviceAddress = sensor2DeviceAddress;
        this.birthday = birthday;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.stance = stance;
        this.skillLevel = skillLevel;
        this.gloveType = gloveType;
        this.reach = reach;
        this.firstName = firstName;
        this.lastName = lastName;
        this.zipCode = zipCode;
        this.country = country;
        this.email = email;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.countryId = countryId;
        this.questionId = questionId;
        this.traineeServerId = traineeServerId;
        this.leftDeviceSensorName = leftDeviceSensorName;
        this.leftDeviceGeneration = leftDeviceGeneration;
        this.rightDeviceSensorName = rightDeviceSensorName;
        this.rightDeviceGeneration = rightDeviceGeneration;
        this.confirmEmail = confirmEmail;
        this.confirmPassword = confirmPassword;
        this.traineeAccessToken = traineeAccessToken;
    }

    public String getConfirmEmail() {
        return confirmEmail;
    }

    public void setConfirmEmail(String confirmEmail) {
        this.confirmEmail = confirmEmail;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getSensor1DeviceAddress() {
        return sensor1DeviceAddress;
    }

    public void setSensor1DeviceAddress(String sensor1DeviceAddress) {
        this.sensor1DeviceAddress = sensor1DeviceAddress;
    }

    public String getSensor2DeviceAddress() {
        return sensor2DeviceAddress;
    }

    public void setSensor2DeviceAddress(String sensor2DeviceAddress) {
        this.sensor2DeviceAddress = sensor2DeviceAddress;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getStance() {
        return stance;
    }

    public void setStance(String stance) {
        this.stance = stance;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }

    public String getGloveType() {
        return gloveType;
    }

    public void setGloveType(String gloveType) {
        this.gloveType = gloveType;
    }

    public String getReach() {
        return reach;
    }

    public void setReach(String reach) {
        this.reach = reach;
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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String questionAnswer) {
        this.securityAnswer = questionAnswer;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getTraineeServerId() {
        return traineeServerId;
    }

    public void setTraineeServerId(String traineeServerId) {
        this.traineeServerId = traineeServerId;
    }

    public String getLeftDeviceSensorName() {
        return leftDeviceSensorName;
    }

    public void setLeftDeviceSensorName(String leftDeviceSensorName) {
        this.leftDeviceSensorName = leftDeviceSensorName;
    }

    public String getLeftDeviceGeneration() {
        return leftDeviceGeneration;
    }

    public void setLeftDeviceGeneration(String leftDeviceGeneration) {
        this.leftDeviceGeneration = leftDeviceGeneration;
    }

    public String getRightDeviceSensorName() {
        return rightDeviceSensorName;
    }

    public void setRightDeviceSensorName(String rightDeviceSensorName) {
        this.rightDeviceSensorName = rightDeviceSensorName;
    }

    public String getRightDeviceGeneration() {
        return rightDeviceGeneration;
    }

    public void setRightDeviceGeneration(String rightDeviceGeneration) {
        this.rightDeviceGeneration = rightDeviceGeneration;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getTraineeAccessToken() {
        return traineeAccessToken;
    }

    public void setTraineeAccessToken(String traineeAccessToken) {
        this.traineeAccessToken = traineeAccessToken;
    }

    @Override
    public String toString() {
        return "RegistrationDTO [sensor1DeviceAddress=" + sensor1DeviceAddress
                + ", sensor2DeviceAddress=" + sensor2DeviceAddress
                + ", birthday=" + birthday + ", gender=" + gender + ", height="
                + height + ", weight=" + weight + ", stance=" + stance
                + ", skillLevel=" + skillLevel + ", gloveType=" + gloveType
                + ", reach=" + reach + ", firstName=" + firstName
                + ", lastName=" + lastName + ", zipCode=" + zipCode
                + ", country=" + country + ", email=" + email + ", password="
                + password + ", securityQuestion=" + securityQuestion
                + ", securityAnswer=" + securityAnswer + ", countryId="
                + countryId + ", questionId=" + questionId
                + ", traineeServerId=" + traineeServerId
                + ", leftDeviceSensorName=" + leftDeviceSensorName
                + ", leftDeviceGeneration=" + leftDeviceGeneration
                + ", rightDeviceSensorName=" + rightDeviceSensorName
                + ", rightDeviceGeneration=" + rightDeviceGeneration
                + ", confirmEmail=" + confirmEmail + ", confirmPassword="
                + confirmPassword + ", traineeAccessToken="
                + traineeAccessToken + "]";
    }
}

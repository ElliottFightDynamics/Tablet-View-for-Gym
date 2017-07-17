package com.efd.striketectablet.DTO.responsedto;

import android.os.Parcel;

import java.util.List;

public class SessionPunchinfosDTO extends HasSuccess {

    List<PunchInfoDTO> leftHand;
    List<PunchInfoDTO> rightHand;
    List<PunchInfoDTO> leftKick;
    List<PunchInfoDTO> rightKick;

   public List<PunchInfoDTO> getLeftHand(){
       return leftHand;
   }

    public List<PunchInfoDTO> getRightHand(){
        return rightHand;
    }

    public List<PunchInfoDTO> getLeftKick(){
        return leftKick;
    }

    public List<PunchInfoDTO> getRightKick(){
        return rightKick;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected SessionPunchinfosDTO(Parcel in) {
        this.leftHand = in.createTypedArrayList(PunchInfoDTO.CREATOR);
        this.rightHand = in.createTypedArrayList(PunchInfoDTO.CREATOR);
        this.leftKick = in.createTypedArrayList(PunchInfoDTO.CREATOR);
        this.rightKick = in.createTypedArrayList(PunchInfoDTO.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.leftHand);
        dest.writeTypedList(this.rightHand);
        dest.writeTypedList(this.leftKick);
        dest.writeTypedList(this.rightKick);
    }

    public static final Creator<SessionPunchinfosDTO> CREATOR = new Creator<SessionPunchinfosDTO>() {
        @Override
        public SessionPunchinfosDTO createFromParcel(Parcel source) {
            return new SessionPunchinfosDTO(source);
        }

        @Override
        public SessionPunchinfosDTO[] newArray(int size) {
            return new SessionPunchinfosDTO[size];
        }
    };
}

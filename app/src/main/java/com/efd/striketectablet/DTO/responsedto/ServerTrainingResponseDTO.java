package com.efd.striketectablet.DTO.responsedto;

import android.os.Parcel;

import java.util.List;

public class ServerTrainingResponseDTO extends HasSuccess {

    boolean last;
    List<DBTrainingSessionDTO> trainingSession;
    List<DBTrainingPunchDetailDTO> trainingPunchDetail;
    List<DBTrainingPunchStatDTO> trainingPunchStats;
    List<DBTrainingPlanResultDTO> trainingPlanResults;

   public List<DBTrainingSessionDTO> getTrainingSession(){
       return trainingSession;
   }

    public List<DBTrainingPunchDetailDTO> getTrainingPunchDetail(){
        return trainingPunchDetail;
    }

    public List<DBTrainingPunchStatDTO> getTrainingPunchStats(){
        return trainingPunchStats;
    }

    public List<DBTrainingPlanResultDTO> getTrainingPlanResults(){
        return trainingPlanResults;
    }

    public boolean getLast(){
        return last;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected ServerTrainingResponseDTO(Parcel in) {
        this.last = in.readByte() != 0;
        this.trainingSession = in.createTypedArrayList(DBTrainingSessionDTO.CREATOR);
        this.trainingPunchDetail = in.createTypedArrayList(DBTrainingPunchDetailDTO.CREATOR);
        this.trainingPunchStats = in.createTypedArrayList(DBTrainingPunchStatDTO.CREATOR);
        this.trainingPlanResults = in.createTypedArrayList(DBTrainingPlanResultDTO.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.last? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.trainingSession);
        dest.writeTypedList(this.trainingPunchDetail);
        dest.writeTypedList(this.trainingPunchStats);
        dest.writeTypedList(this.trainingPlanResults);
    }

    public static final Creator<ServerTrainingResponseDTO> CREATOR = new Creator<ServerTrainingResponseDTO>() {
        @Override
        public ServerTrainingResponseDTO createFromParcel(Parcel source) {
            return new ServerTrainingResponseDTO(source);
        }

        @Override
        public ServerTrainingResponseDTO[] newArray(int size) {
            return new ServerTrainingResponseDTO[size];
        }
    };
}

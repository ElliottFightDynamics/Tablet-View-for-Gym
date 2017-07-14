package com.efd.striketectablet.DTO.responsedto;

import android.os.Parcel;

public class QuestionDTO extends HasId {

    private String questionText;

    public QuestionDTO() {}

    protected QuestionDTO(Parcel in) {
        this.questionText = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.questionText);
    }

    public String getQuestionText(){
        return questionText;
    }

    public void setQuestionText(String questionText){
        this.questionText = questionText;
    }

    public static final Creator<QuestionDTO> CREATOR = new Creator<QuestionDTO>() {
        @Override
        public QuestionDTO createFromParcel(Parcel source) {
            return new QuestionDTO(source);
        }

        @Override
        public QuestionDTO[] newArray(int size) {
            return new QuestionDTO[size];
        }
    };
}

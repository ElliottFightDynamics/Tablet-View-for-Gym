package com.efd.striketectablet.DTO.responsedto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class QuestionListDTO implements Parcelable {

    private List<QuestionDTO> questionList;

    public QuestionListDTO() {}

    protected QuestionListDTO(Parcel in) {
        this.questionList = in.createTypedArrayList(QuestionDTO.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.questionList);
    }

    public List<QuestionDTO> getQuestionList(){
        return questionList;
    }

    public void setQuestionList(List<QuestionDTO> questionList){
        this.questionList = questionList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QuestionListDTO> CREATOR = new Creator<QuestionListDTO>() {
        @Override
        public QuestionListDTO createFromParcel(Parcel source) {
            return new QuestionListDTO(source);
        }

        @Override
        public QuestionListDTO[] newArray(int size) {
            return new QuestionListDTO[size];
        }
    };
}

package com.efd.striketectablet.api;

import com.efd.striketectablet.DTO.responsedto.AnalyzeCSVDTO;
import com.efd.striketectablet.DTO.responsedto.AuthenticationDTO;
import com.efd.striketectablet.DTO.responsedto.PunchWindowPlottingDTO;
import com.efd.striketectablet.DTO.responsedto.ResetpwdDTO;
import com.efd.striketectablet.DTO.responsedto.SessionPunchinfosDTO;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface CSVRest {

//    @FormUrlEncoded
//    @POST(RestUrl.UPLOAD_CSV)
//    Call<AnalyzeCSVDTO> uploadCSV(@Field("data") String filename);

    @Multipart
    @POST(RestUrl.UPLOAD_CSV)
    Call<AnalyzeCSVDTO> uploadCSV(@Part MultipartBody.Part file);

    @GET(RestUrl.GET_INFO)
    Call<SessionPunchinfosDTO> getSessionPunchInfos(@QueryMap Map<String, String> infomap);

    @GET(RestUrl.GET_PLOTTING_LOG)
    Call<PunchWindowPlottingDTO> getPlottingLogs (@Query("fileName") String fileName,
                                                      @Query("punchWindowStart") String punchWindowStart,
                                                      @Query("punchWindowEnd") String punchWindowEnd);
}

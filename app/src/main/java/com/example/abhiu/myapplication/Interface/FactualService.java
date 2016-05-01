package com.example.abhiu.myapplication.Interface;

import com.example.abhiu.myapplication.entity.FactualResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface FactualService {
  @GET("t/healthcare-providers-us?KEY=BXlykcLo11hzxSISFp92ZIfLceXLyEDy0Ir51VA9")
  Call<FactualResponse> getHealthcare(@QueryMap Map<String, String> queryParams);
}

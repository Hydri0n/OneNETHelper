package xyz.hydrion.onenethelper.net.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import xyz.hydrion.onenethelper.net.bean.DataPoints;
import xyz.hydrion.onenethelper.net.bean.DataStreams;
import xyz.hydrion.onenethelper.net.bean.Status;

/**
 * Created by Hydrion on 2018/7/23.
 */
public interface DeviceService {
    @GET("devices/status")
    Call<Status> getDeviceStatus(@Header("api-key") String apiKey,
                                 @Query("devIds") String deviceId);

    @GET("datapoints")
    Call<DataPoints> getCurrentDataPoint(@Header("api-key") String apiKey,
                                         @Query("devIds") String deviceId,
                                         @Query("datastream_id") String dataStreamId);

    @GET("datapoints")
    Call<DataPoints> getDataPointsWithinLimit(@Header("api-key") String apiKey,
                                   @Query("devIds") String deviceId,
                                   @Query("datastream_id") String dataStreamId,
                                   @Query("limit") Integer limit);

    @GET("devices/{devId}/datapoints")
    Call<DataPoints> getDataPoints(@Header("api-key") String apiKey,
                                   @Path("devId") String deviceId,
                                   @Query("datastream_id") String dataStreamId,
                                   @Query("limit") Integer limit,
                                   @Query("start") String start,
                                   @Query("end") String end,
                                   @Query("duration") Integer duration,
                                   @Query("cursor")Integer cursor,
                                   @Query("sort") String sort);

    @GET("devices/{devId}/datastreams")
    Call<DataStreams> getDataStreams(@Header("api-key") String apiKey,
                                     @Path("devId") String deviceId,
                                     @Query("datastream_ids") String dataStreamIds);


}

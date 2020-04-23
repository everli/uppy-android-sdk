package it.supermercato24.uppy;

import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface UppyService {
    @GET("")
    void checkLatestVersion(@Body String currentVersion, Callback<Object> callback);
}

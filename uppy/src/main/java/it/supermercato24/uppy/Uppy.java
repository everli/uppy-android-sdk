package it.supermercato24.uppy;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class Uppy {

    private static volatile Uppy instance;

    private final String serverUrl;

    private final OkHttpClient client;
    private final Retrofit retrofit;
    private final UppyService uppyService;

    private Uppy(String serverUrl) {
        this.serverUrl = serverUrl;

        client = new OkHttpClient.Builder()
                .addInterceptor(new UserAgentInterceptor())
                .build();

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(serverUrl)
                .build();

        uppyService = retrofit.create(UppyService.class);

        instance = this;
    }

    public static synchronized void getInstance(String serverUrl) {
        if (instance == null) {
            synchronized (Uppy.class) {
                if (instance == null) {
                    instance = new Uppy(serverUrl);
                }
            }
        }
    }

    public static void isLatestVersion() {
        throw new RuntimeException("Not implemented Yet");
        // TODO
        // instance.uppyService.checkLatestVersion("", null);
    }
}

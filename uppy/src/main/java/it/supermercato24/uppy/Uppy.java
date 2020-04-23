package it.supermercato24.uppy;

import retrofit2.Retrofit;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Uppy {

    private static volatile Uppy instance;

    private final String serverUrl;

    private final Retrofit retrofit;
    private final UppyService uppyService;

    private Uppy(String serverUrl) {
        this.serverUrl = serverUrl;

        retrofit = new Retrofit.Builder()
                .baseUrl(serverUrl)
                .build();

        uppyService = retrofit.create(UppyService.class);

        instance = this;
    }

    public static synchronized void init(String serverUrl) {
        if (instance == null) {
            synchronized (Uppy.class) {
                if (instance == null) {
                    instance = new Uppy(serverUrl);
                }
            }
        }
    }

    public static void isLatestVersion() {
        throw new NotImplementedException();
        // TODO
        // instance.uppyService.checkLatestVersion("", null);
    }
}

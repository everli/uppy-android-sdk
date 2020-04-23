package it.supermercato24.uppy;

public class Uppy {

    private static volatile Uppy INSTANCE;

    private final String serverUrl;
    private final String appToken;

    private Uppy(String serverUrl, String appToken) {
        this.serverUrl = serverUrl;
        this.appToken = appToken;
        INSTANCE = this;
    }

    public synchronized Uppy getInstance(String serverUrl, String appToken) {
        if (INSTANCE == null) {
            synchronized (Uppy.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Uppy(serverUrl, appToken);
                }
            }
        }
        return INSTANCE;
    }

    public
}

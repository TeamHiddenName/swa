package hska.iwi.eShopMaster.model.ms;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class BaseDeleteRequest {

    private static final Logger LOG = LogManager.getLogger();
    private final String url;

    public BaseDeleteRequest(String url) {
        this.url = url;
    }

    public void delete() {

        try {
            long startTime = System.currentTimeMillis();
            deleteInternal();
            long stopTime = System.currentTimeMillis();
            long duration = stopTime - startTime;
            LOG.info("Delete request for url " + url + " took ms: " + duration);

        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    private void deleteInternal() throws IOException {
        URL requestUrl = new URL(url);
        HttpURLConnection con = (HttpURLConnection) requestUrl.openConnection();
        con.setRequestMethod("DELETE");
        con.connect();
        System.out.println("Delete result = " + con.getResponseCode());
    }
}

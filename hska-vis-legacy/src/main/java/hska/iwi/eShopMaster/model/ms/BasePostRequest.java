package hska.iwi.eShopMaster.model.ms;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class BasePostRequest<T> {

    private static final Logger LOG = LogManager.getLogger();
    private final String url;
    private final Gson gson = new Gson();

    public BasePostRequest(String url) {
        this.url = url;
    }

    public Optional<T> postAsJson(Object body, Class<T> clazz) {

        try {
            long startTime = System.currentTimeMillis();
            Optional<T> result = postInternal(gson.toJson(body), clazz);
            long stopTime = System.currentTimeMillis();
            long duration = stopTime - startTime;
            LOG.info("Post request for url " + url + " took ms: " + duration);
            return result;
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<T> post(String body, Class<T> clazz) {
        try {
            long startTime = System.currentTimeMillis();
            Optional<T> result = postInternal(body, clazz);
            long stopTime = System.currentTimeMillis();
            long duration = stopTime - startTime;
            LOG.info("Post request for url " + url + " took ms: " + duration);
            return result;
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


    private Optional<T> postInternal(String body, Class<T> clazz) throws IOException {
        byte[] out = body.getBytes(StandardCharsets.UTF_8);
        URL requestUrl = new URL(url);
        HttpURLConnection con = (HttpURLConnection) requestUrl.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.setFixedLengthStreamingMode(out.length);
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        con.connect();
        try (OutputStream os = con.getOutputStream()) {
            os.write(out);
        }
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return Optional.of(gson.fromJson(content.toString(), clazz));
    }
}

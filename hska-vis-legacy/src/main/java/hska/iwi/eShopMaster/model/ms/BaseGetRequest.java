package hska.iwi.eShopMaster.model.ms;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

public class BaseGetRequest<T> {

    private final String url;
    private final Gson gson = new Gson();

    public BaseGetRequest(String url) {
        this.url = url;
    }

    public Optional<T> get(Class<T> clazz) {

        try {
            long startTime = System.currentTimeMillis();
            Optional<T> result = getInternal(clazz);
            long stopTime = System.currentTimeMillis();
            long duration = stopTime - startTime;
            System.out.println("Get request for url " + url + " took ms: " + duration);
            return result;
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private Optional<T> getInternal(Class<T> clazz) throws IOException {
        URL requestUrl = new URL(url);
        HttpURLConnection con = (HttpURLConnection) requestUrl.openConnection();
        con.setRequestMethod("GET");
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

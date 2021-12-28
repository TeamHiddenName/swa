package hska.iwi.eShopMaster.model.ms;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class BaseDeleteRequest {

    private final String url;
    private final Gson gson = new Gson();

    public BaseDeleteRequest(String url) {
        this.url = url;
    }

    public void delete() {

        try {
            deleteInternal();
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

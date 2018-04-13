import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.exceptions.OAuthException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.wall.responses.GetResponse;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.vk.api.sdk.queries.wall.WallGetFilter.OWNER;

public class Main {

    public static final int APP_ID = ;
    public static String CLIENT_SECRET = "";
    public static String REDIRECT_URI = "https://oauth.vk.com/authorize";
    public static String code = "";

    public static void main(String[] args) throws IOException {
        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(transportClient);
        /*String url = "https://oauth.vk.com/authorize?client_id=6446474&display=page&redirect_uri=https://oauth.vk.com/authorize&scope=friends,photoes&response_type=code&v=5.74";
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());*/


        UserAuthResponse authResponse = null;
        try {
            authResponse = vk.oauth()
                    .userAuthorizationCodeFlow(APP_ID, CLIENT_SECRET, REDIRECT_URI, code)
                    .execute();
        } catch (OAuthException e) {
            e.getRedirectUri();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }

        UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
        GetResponse getResponse = null;
        try {
            getResponse = vk.wall().get(actor)
                    .ownerId(1)
                    .count(100)
                    .offset(5)
                    .filter(OWNER)
                    .execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }

        System.out.println(getResponse);
    }

}

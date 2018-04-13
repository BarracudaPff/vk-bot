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

    public static final int APP_ID = 6446474;
    public static String CLIENT_SECRET = "LKq7ltJISObNZOmxoIih";
    public static String REDIRECT_URI = "https://oauth.vk.com/authorize";
    public static String code = "3eddcb7d1821228e62";

    public static void main(String[] args) throws IOException {
        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(transportClient);

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

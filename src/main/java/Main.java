import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.exceptions.OAuthException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.wall.WallPostFull;
import com.vk.api.sdk.objects.wall.responses.GetResponse;

import java.io.*;
import java.util.Scanner;

public class Main {

    private static final int APP_ID = ;
    private static String CLIENT_SECRET = "";
    private static String REDIRECT_URI = "https://oauth.vk.com/authorize";
    private static String code = "944489413cfbc4a8a2";

    private static Scanner reader = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        System.out.print("Enter code: ");
        code = reader.next();

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

        System.out.print("Enter group name (next folder name): ");
        String groupName = reader.next();
        System.out.print("Enter group id: ");
        int groupId = reader.nextInt() * (-1);
        System.out.print("Enter count of notes: ");
        int count = reader.nextInt();

        UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
        GetResponse getResponse = null;
        try {
            getResponse = vk.wall().get(actor)
                    .ownerId(groupId)
                    .domain(groupName)
                    .count(count)
                    .execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }

        for (WallPostFull wallPostFull : getResponse.getItems()) {
            String res = wallPostFull.getText();

            FileWriter fileWriter = new FileWriter("data-set/" + groupName + ".txt", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(res);
            printWriter.println();
            printWriter.println();
            printWriter.close();
        }
        System.out.println("done");
    }

}

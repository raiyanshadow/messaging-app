package app;

import SendBirdAPI.ChannelCreator;
import entity.User;
import io.github.cdimascio.dotenv.Dotenv;
import java.sql.*;

public class Main {
        public static void main(String[] args) {
            Dotenv dotenv = Dotenv.configure()
                    .directory("./assets")
                    .filename("env")
                    .load();

            String url = dotenv.get("DB_URL");
            String user = dotenv.get("DB_USER");
            String password = dotenv.get("DB_PASSWORD");

            String appId = dotenv.get("MSG_APP_ID");
            String apiToken = dotenv.get("MSG_TOKEN");

            User user1 = new User(3, "Greg", "abc", "English");
            User user2 = new User(4, "Dave", "def", "English");

            ChannelCreator channelCreator = new ChannelCreator(appId);
            String channelUrl = channelCreator.SendbirdChannelCreator(apiToken, "example", user1, user2);
            System.out.println("Channel URL: " + channelUrl);
        }
}

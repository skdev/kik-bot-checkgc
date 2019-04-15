package sk96.dev.kik.bot.command.persistence;

import sk96.dev.kik.bot.Main;
import sk96.dev.kik.bot.utils.logging.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

public class GroupUtils {
    private static final Logger L = Logger.getTextFileLogger("GroupUtils");

    public static boolean groupExists(String groupId) {
        final List<?> result = HibernateUtil.queryDatabase("from Group WHERE groupId=:groupId", new HashMap<String, Object>() {{
            put("groupId", groupId);
        }});
        return result.size() > 0;
    }

    public static Group getGroup(String groupId) {
        final List<?> result = HibernateUtil.queryDatabase("from Group WHERE groupId=:groupId", new HashMap<String, Object>() {{
            put("groupId", groupId);
        }});
        if (result.size() <= 0) {
            System.out.println("Group " + groupId + " not found.");
            return null;
        }
        return (Group) result.get(0);
    }

    public static List<ActiveMember> getActiveMembers(String groupId) {
        return (List<ActiveMember>)HibernateUtil.queryDatabase("from ActiveMember WHERE groupId=:groupId", new HashMap<String, Object>() {{
            put("groupId", groupId);
        }});
    }

    public static List<GroupMember> getMembers(String groupId) {
        return (List<GroupMember>)HibernateUtil.queryDatabase("from GroupMember WHERE groupId=:groupId", new HashMap<String, Object>() {{
            put("groupId", groupId);
        }});
    }

    public static boolean memberExists(String chatId, String from) {
        final List<?> result = HibernateUtil.queryDatabase("from GroupMember WHERE groupId=:groupId AND username=:username", new HashMap<String, Object>() {{
            put("groupId", chatId);
            put("username", from);
        }});
        return result.size() > 0;
    }

    public static void clearMembers(String chatId) {
        for(GroupMember member : getMembers(chatId)) {
            HibernateUtil.delete(member);
        }
    }

    public static void addNewActiveMember(String chatId, String from) {
        ActiveMember member = new ActiveMember();
        member.setGroupId(chatId);
        member.setUsername(from);
        HibernateUtil.save(member);
    }

    public static String requestUserProfile(String name) {
        final String url = "https://api.kik.com/v1/user/" + name;
        final String base64Key = Base64.getEncoder().encodeToString((Main.configuration.botName + ":" + Main.configuration.apiKey).getBytes(StandardCharsets.UTF_8));

        try {
            final URL url2 = new URL(url);
            final HttpURLConnection http = (HttpURLConnection)url2.openConnection();
            http.setRequestMethod("GET");
            http.setRequestProperty("Authorization", "Basic " + base64Key);
            http.setRequestProperty("Content-Type", "application/json");
            http.setDoOutput(true);

            final Reader reader = new BufferedReader(new InputStreamReader(http.getInputStream(), "UTF-8"));
            final StringBuilder response = new StringBuilder();

            int read;
            while((read = reader.read()) >= 0) {
                response.append((char) read);
            }
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            L.error("Failed to send POST to '" + url + "' - Error: " + e.getMessage());
        }
        return "";
    }
}
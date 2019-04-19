package sk96.dev.kik.bot.command;

import sk96.dev.kik.bot.command.persistence.*;
import sk96.dev.kik.bot.message.TextMessage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class ResetCommand extends Command<TextMessage, TextMessage> {
    private final String host = "35.238.75.15";
    private final String database = "checkgc";
    private final String username = "root";
    private final String password = "zq5rw-E3";

    @Override
    public TextMessage run(TextMessage message) {
        Group group = GroupUtils.getGroup(message.chatId);

        if(null == group) {
            group = new Group();
        }

        GroupUtils.clearMembers(group.getGroupId());

        group.setGroupId(message.chatId);
        group.setStartTime(String.valueOf(System.currentTimeMillis()));
        group.setSize(message.participants.length);

        HibernateUtil.save(group);

        List<GroupMember> members = new ArrayList<>();

        for (String participant : message.participants) {
            GroupMember m = new GroupMember();
            if(message.to.equalsIgnoreCase(participant)) {
                m.setActive(1);
            } else {
                m.setActive(0);
            }
            m.setGroupId(group.getGroupId());
            m.setUsername(participant);
            members.add(m);
        }

        try {
            removeMembers(group.getGroupId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new TextMessage(message.chatId, message.to, "Monitoring has reset\\nSay '@CheckGC Check' when you are ready.");
    }

    private void removeMembers(String groupId) throws Exception {
        final String query = "DELETE FROM members WHERE groupId = ?";
        final Connection connection = createConnection();
        final PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, groupId);
        statement.execute();
    }

    private Connection createConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            return DriverManager.getConnection("jdbc:mysql://" + host + "/"
                    + database, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

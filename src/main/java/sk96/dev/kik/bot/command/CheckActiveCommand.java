package sk96.dev.kik.bot.command;

import sk96.dev.kik.bot.command.persistence.*;
import sk96.dev.kik.bot.message.MultiTextMessage;
import sk96.dev.kik.bot.message.TextMessage;
import sk96.dev.kik.bot.utils.JsonSearch;
import sk96.dev.kik.bot.utils.logging.Logger;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CheckActiveCommand extends Command<MultiTextMessage, TextMessage> {
    private static final Logger L = Logger.getTextFileLogger("CheckActiveCommand");

    @Override
    public MultiTextMessage run(TextMessage message) {
        Group group;
        if(!GroupUtils.groupExists(message.chatId)) {
            group = new Group();
            group.setGroupId(message.chatId);
            group.setStartTime(String.valueOf(System.currentTimeMillis()));
            group.setSize(message.participants.length);
            HibernateUtil.save(group);

            ActiveMember active = new ActiveMember();
            active.setGroupId(message.chatId);
            active.setUsername(message.to);
            HibernateUtil.save(active);

            for(String participant : message.participants) {
                GroupMember m = new GroupMember();
                m.setGroupId(message.chatId);
                m.setUsername(participant);
                HibernateUtil.save(m);
            }

            L.info("Cached new group: " + message.chatId + ", size: " + message.participants.length);
        } else {
            group = GroupUtils.getGroup(message.chatId);
        }

        if(null == group) {
            L.error("Could not check group activity, group was null: " + message.chatId);
            return new MultiTextMessage(message.chatId, message.to, "An internal error occurred, please try again later.");
        }

        group.setSize(message.participants.length);
        HibernateUtil.save(group);

        GroupUtils.clearMembers(group.getGroupId());

        for(String participant : message.participants) {
            GroupMember m = new GroupMember();
            m.setGroupId(group.getGroupId());
            m.setUsername(participant);
            HibernateUtil.save(m);
        }

        long start = Long.parseLong(group.getStartTime());
        long elapsed = System.currentTimeMillis() - start;
        long seconds = TimeUnit.SECONDS.convert(elapsed, TimeUnit.NANOSECONDS);
        long mins = TimeUnit.MINUTES.convert(elapsed, TimeUnit.NANOSECONDS);
        long hours = TimeUnit.HOURS.convert(elapsed, TimeUnit.NANOSECONDS);
        long days = TimeUnit.DAYS.convert(elapsed, TimeUnit.NANOSECONDS);

        final StringBuilder sb = new StringBuilder();
        if (seconds < 60) {
            sb.append("I was activated " + seconds + " seconds ago");
        } else if (mins < 60) {
            sb.append("I was activated " + mins + " minutes ago");
        } else if(days > 48){
            sb.append("I was activated " + days + " days ago");
        } else {
            sb.append("I was activated " + hours + " hours ago");
        }

        sb.append("\\n\\nHere are results since then:\\n\\n");

        final List<ActiveMember> members = GroupUtils.getActiveMembers(group.getGroupId());
        for(ActiveMember m : members) {
            final String json = GroupUtils.requestUserProfile(m.getUsername());
            final String firstName = JsonSearch.get(json, "firstName");
            final String lastName = JsonSearch.get(json, "lastName");
            sb.append(firstName + " " + lastName + "\\n");
        }

        sb.append("\\nTotal " + members.size() + " active out of " + group.getSize());

        return new MultiTextMessage(message.chatId, message.to, sb.toString());
    }
}

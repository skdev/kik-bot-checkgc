package sk96.dev.kik.bot.command;

import sk96.dev.kik.bot.command.persistence.*;
import sk96.dev.kik.bot.message.MultiTextMessage;
import sk96.dev.kik.bot.message.TextMessage;
import sk96.dev.kik.bot.utils.JsonSearch;
import sk96.dev.kik.bot.utils.logging.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CheckActiveCommand extends Command<MultiTextMessage, TextMessage> {
    private static final Logger L = Logger.getTextFileLogger("CheckActiveCommand");

    private MultiTextMessage start(TextMessage message) {
        final Group group = new Group();
        group.setGroupId(message.chatId);
        group.setStartTime(String.valueOf(System.currentTimeMillis()));
        group.setSize(message.participants.length);

        List<GroupMember> members = new ArrayList<>();
        List<GroupMemberContainer> membersCtx = new ArrayList<>();

        try {
            for (String participant : message.participants) {
                GroupMember m = new GroupMember();
                if(message.to.equalsIgnoreCase(participant)) {
                    m.setActive(1);
                }
                m.setGroupId(message.chatId);
                m.setUsername(participant);
                members.add(m);
                final String json = GroupUtils.requestUserProfile(m.getUsername());
                final String firstName = JsonSearch.get(json, "firstName");
                final String lastName = JsonSearch.get(json, "lastName");
                GroupMemberContainer ctx = new GroupMemberContainer(message.chatId, m.getUsername(), firstName, lastName, m.getActive());
                membersCtx.add(ctx);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            HibernateUtil.save(group);
            HibernateUtil.save(members);
        }).start();

        long start = Long.parseLong(group.getStartTime());
        long elapsed = System.currentTimeMillis() - start;
        long seconds = TimeUnit.SECONDS.convert(elapsed, TimeUnit.MILLISECONDS);
        long mins = TimeUnit.MINUTES.convert(elapsed, TimeUnit.MILLISECONDS);
        long hours = TimeUnit.HOURS.convert(elapsed, TimeUnit.MILLISECONDS);
        long days = TimeUnit.DAYS.convert(elapsed, TimeUnit.MILLISECONDS);

        final StringBuilder sb = new StringBuilder();

        sb.append("~Here are the results\\n\\n- Check started -\\n");

        if (seconds < 60) {
            sb.append(seconds + " seconds ago.");
        } else if (mins < 60) {
            sb.append(mins + " minutes ago.");
        } else if(mins >= 60){
            sb.append(hours + " hours ago");
        } else {
            sb.append(days + " days ago.");
        }

        int activeCounter = 0;
        int inactiveCounter = 0;

        sb.append("\\n\\n - Active - \\n");
        for(GroupMemberContainer ctx : membersCtx) {
            if(ctx.active >= 1) {
                sb.append(ctx.firstName + " " + ctx.lastName + "\\n");
                activeCounter++;
            }
        }

        sb.append("\\n- Inactive -\\n");
        for(GroupMemberContainer ctx : membersCtx) {
            if(ctx.active <= 0) {
                sb.append(ctx.firstName + " " + ctx.lastName + "\\n");
                inactiveCounter++;
            }
        }

        sb.append("\\nTotal " + activeCounter + " active out of " + group.getSize() + "\\n#CheckGC");

        return new MultiTextMessage(message.chatId, message.to, sb.toString());
    }


    @Override
    public MultiTextMessage run(TextMessage message) {
        Group group = GroupUtils.getGroup(message.chatId);
        if(null == group) {
            return start(message);
        }

        if(group.getSize() != message.participants.length) {
            group.setSize(message.participants.length);
        }

        List<GroupMember> currentMembers = GroupUtils.getMembers(group.getGroupId());
        List<GroupMember> members = new ArrayList<>();
        List<GroupMemberContainer> membersCtx = new ArrayList<>();

        for(String participant : message.participants) {
            boolean memberExist = false;
            int active = 0;

            for(GroupMember m : currentMembers) {
                if(m.getUsername().equalsIgnoreCase(participant)) {
                    memberExist = true;
                    active = m.getActive();
                    members.add(m);
                    break;
                }
            }

            if(!memberExist) {
                GroupMember m = new GroupMember();
                if(participant.equalsIgnoreCase(message.to)) {
                    m.setActive(1);
                }
                m.setGroupId(message.chatId);
                m.setUsername(participant);
                active = m.getActive();
                members.add(m);
            }

            final String json = GroupUtils.requestUserProfile(participant);
            final String firstName = JsonSearch.get(json, "firstName");
            final String lastName = JsonSearch.get(json, "lastName");
            GroupMemberContainer ctx = new GroupMemberContainer(message.chatId, participant, firstName, lastName, active);
            membersCtx.add(ctx);

        }

        new Thread(() -> {
            HibernateUtil.save(group);
            GroupUtils.clearMembers(message.chatId);
            HibernateUtil.save(members);
        }).start();


        long start = Long.parseLong(group.getStartTime());
        long elapsed = System.currentTimeMillis() - start;
        long seconds = TimeUnit.SECONDS.convert(elapsed, TimeUnit.MILLISECONDS);
        long mins = TimeUnit.MINUTES.convert(elapsed, TimeUnit.MILLISECONDS);
        long hours = TimeUnit.HOURS.convert(elapsed, TimeUnit.MILLISECONDS);
        long days = TimeUnit.DAYS.convert(elapsed, TimeUnit.MILLISECONDS);

        final StringBuilder sb = new StringBuilder();

        sb.append("~Here are the results\\n\\n- Check started -\\n");

        if (seconds < 60) {
            sb.append(seconds + " seconds ago.");
        } else if (mins < 60) {
            sb.append(mins + " minutes ago.");
        } else if(mins >= 60){
            sb.append(hours + " hours ago");
        } else {
            sb.append(days + " days ago.");
        }

        int activeCounter = 0;
        int inactiveCounter = 0;

        sb.append("\\n\\n - Active - \\n");
        for(GroupMemberContainer ctx : membersCtx) {
            if(ctx.active >= 1) {
                sb.append(ctx.firstName + " " + ctx.lastName + "\\n");
                activeCounter++;
            }
        }

        sb.append("\\n- Inactive -\\n");
        for(GroupMemberContainer ctx : membersCtx) {
            if(ctx.active <= 0) {
                sb.append(ctx.firstName + " " + ctx.lastName + "\\n");
                inactiveCounter++;
            }
        }

        sb.append("\\nTotal " + activeCounter + " active out of " + group.getSize() + "\\n#CheckGC");
        return new MultiTextMessage(message.chatId, message.to, sb.toString());
    }
}

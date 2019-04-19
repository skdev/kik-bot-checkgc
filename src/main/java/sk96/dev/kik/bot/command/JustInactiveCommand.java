package sk96.dev.kik.bot.command;

import sk96.dev.kik.bot.command.persistence.*;
import sk96.dev.kik.bot.message.MultiTextMessage;
import sk96.dev.kik.bot.message.TextMessage;
import sk96.dev.kik.bot.utils.JsonSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class JustInactiveCommand extends Command<MultiTextMessage, TextMessage> {
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
                m.setUsername(participant);
                if (message.to.equalsIgnoreCase(participant)) {
                    m.setActive(1);
                } else {
                    final String json = GroupUtils.requestUserProfile(m.getUsername());
                    final String firstName = JsonSearch.get(json, "firstName");
                    final String lastName = JsonSearch.get(json, "lastName");
                    GroupMemberContainer ctx = new GroupMemberContainer(message.chatId, m.getUsername(), firstName, lastName, m.getActive());
                    membersCtx.add(ctx);
                }
                m.setGroupId(message.chatId);
                m.setUsername(participant);
                members.add(m);
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

        sb.append("\\n\\n - Inactive - \\n");
        for(GroupMemberContainer ctx : membersCtx) {
            if(ctx.active <= 0) {
                sb.append(ctx.firstName + " " + ctx.lastName + "\\n");
                activeCounter++;
            }
        }

        sb.append("\\nTotal " + activeCounter + " inactive out of " + group.getSize() + "\\n#CheckGC");

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
            GroupMember member = new GroupMember();
            if(participant.equalsIgnoreCase(message.to)) {
                member.setActive(1);
            }
            member.setGroupId(message.chatId);
            member.setUsername(participant);

            for(GroupMember m : currentMembers) {
                if(m.getUsername().equalsIgnoreCase(participant)) {
                    member = m;
                    break;
                }
            }

            System.out.println("Added member: " + member);
            members.add(member);

            if(member.getActive() <= 0) {
                final String json = GroupUtils.requestUserProfile(participant);
                final String firstName = JsonSearch.get(json, "firstName");
                final String lastName = JsonSearch.get(json, "lastName");
                GroupMemberContainer ctx = new GroupMemberContainer(message.chatId, participant, firstName, lastName, member.getActive());
                membersCtx.add(ctx);
            }

        }

        System.out.println("Pari:" + message.participants.length + ", Mems: " + members.size() + " Ctx: " + membersCtx.size());

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

        sb.append("\\n\\n - Inctive - \\n");
        for(GroupMemberContainer ctx : membersCtx) {
            if(ctx.active <= 0) {
                sb.append(ctx.firstName + " " + ctx.lastName + "\\n");
                activeCounter++;
            }
        }

        sb.append("\\nTotal " + activeCounter + " inactive out of " + group.getSize() + "\\n#CheckGC");
        return new MultiTextMessage(message.chatId, message.to, sb.toString());
    }
}

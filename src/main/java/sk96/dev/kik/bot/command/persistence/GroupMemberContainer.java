package sk96.dev.kik.bot.command.persistence;

public class GroupMemberContainer {
    public final String groupId;
    public final String username;
    public final String firstName;
    public final String lastName;
    public final int active;

    public GroupMemberContainer(String groupId, String username, String firstName, String lastName, int active) {
        this.groupId = groupId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.active = active;
    }
}

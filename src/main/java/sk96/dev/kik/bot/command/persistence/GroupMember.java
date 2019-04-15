package sk96.dev.kik.bot.command.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "members")
public class GroupMember {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "groupId")
    private String groupId;

    @Column(name = "username")
    private String username;

    public GroupMember() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
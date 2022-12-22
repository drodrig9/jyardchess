package ca.watier.echechess.models;

import javax.persistence.*;

@Entity
@Table(name="userGame", uniqueConstraints = @UniqueConstraint(columnNames= {"uuid","id"}))
public class UserGame {

    @Id
    @Column(name="uuid")
    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable=false)
    private UserInformation user;

    public UserGame() {

    }

    public UserGame(String uuid, UserInformation user) {
        this.uuid = uuid;
        this.user = user;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public UserInformation getUser() {
        return user;
    }

    public void setUser(UserInformation user) {
        this.user = user;
    }
}

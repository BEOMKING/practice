package jpabook.jpashop.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "matchi.club")
@Embeddable
public class Club implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int club_id;

    private String name;
    private LocalDateTime create_date;
    private String bio;
    private int member_count;
    private int max_count;
    private int activity_point;
    private String repository;
    private String team_chat;
    private boolean is_active;

    public Club() {
    }

    public Club(String name, LocalDateTime create_date, String bio, int member_count, int max_count,
        int activity_point, String repository, String team_chat, boolean is_active) {
        this.name = name;
        this.create_date = create_date;
        this.bio = bio;
        this.member_count = member_count;
        this.max_count = max_count;
        this.activity_point = activity_point;
        this.repository = repository;
        this.team_chat = team_chat;
        this.is_active = is_active;
    }
}
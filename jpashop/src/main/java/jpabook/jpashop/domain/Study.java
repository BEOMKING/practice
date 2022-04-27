package jpabook.jpashop.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "matchi.study")
@Embeddable
public class Study implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int study_id;

    private String name;
    private LocalDateTime create_date;
    private String bio;
    private int member_count;
    private int max_count;
    private int activity_point;
    private String repository;
    private String team_chat;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club_id;

    private boolean is_active;
    private boolean is_participate;

    public Study() {
    }

    public Study(String name, LocalDateTime create_date, String bio, int member_count,
        int max_count,
        int activity_point, String repository, String team_chat, Club club_id, boolean is_active,
        boolean is_participate) {
        this.name = name;
        this.create_date = create_date;
        this.bio = bio;
        this.member_count = member_count;
        this.max_count = max_count;
        this.activity_point = activity_point;
        this.repository = repository;
        this.team_chat = team_chat;
        this.club_id = club_id;
        this.is_active = is_active;
        this.is_participate = is_participate;
    }
}
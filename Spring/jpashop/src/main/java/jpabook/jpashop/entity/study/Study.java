package jpabook.jpashop.entity.study;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import jpabook.jpashop.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "matching.study")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String schedule;
    private int period;
    private String bio;
    @Column(name = "member_count")
    private int memberCount;
    @Column(name = "max_count")
    private int maxCount;
    @Column(name = "create_Date")
    private LocalDateTime createDate;
    @Column(name = "modify_Date")
    private LocalDateTime modifyDate;

    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "is_public")
    private Boolean isPublic;
    @Column(name = "is_participate")
    private Boolean isParticipate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id")
    private Member member;


    public void addMember(){
        this.memberCount++;
    }

    public void removeMember(){
        this.memberCount--;
    }



}
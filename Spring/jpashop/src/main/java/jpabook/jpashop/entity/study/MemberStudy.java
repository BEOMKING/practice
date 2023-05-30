package jpabook.jpashop.entity.study;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "matching.member_study")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberStudy {

    @EmbeddedId
    private CompositeMemberStudy compositeMemberStudy;

    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "register_date")
    private LocalDateTime registerDate;

    public void activation() {
        this.isActive = true;
    }

    public void deActivation() {
        this.isActive = false;
    }

    @Builder
    public MemberStudy(CompositeMemberStudy compositeMemberStudy, LocalDateTime registerDate) {
        this.compositeMemberStudy = compositeMemberStudy;
        this.registerDate = registerDate;
    }
}
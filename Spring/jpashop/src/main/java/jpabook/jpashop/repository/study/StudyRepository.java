package jpabook.jpashop.repository.study;

import jpabook.jpashop.entity.study.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {


}
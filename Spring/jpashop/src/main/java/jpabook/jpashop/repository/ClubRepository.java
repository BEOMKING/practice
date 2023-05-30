package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Integer> {

}
package jpabook.jpashop.repository;

import jpabook.jpashop.domain.DBFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DBFileRepository extends JpaRepository<DBFile, String> {

}
package com.hellospring.startboot;

import com.hellospring.startboot.repository.JdbcMemberRepository;
import com.hellospring.startboot.repository.JdbcTemplateMemberRepository;
import com.hellospring.startboot.repository.MemberRepository;
import com.hellospring.startboot.repository.MemoryMemberRepository;
import com.hellospring.startboot.service.MemberService;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new JdbcTemplateMemberRepository(dataSource);
    }

}

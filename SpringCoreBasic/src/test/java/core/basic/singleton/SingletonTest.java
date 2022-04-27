package core.basic.singleton;

import core.basic.AppConfig;
import core.basic.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();
        MemberService memberService1 = appConfig.memberService();
        MemberService memberService2 = appConfig.memberService();

        Assertions.assertThat(memberService1).isNotSameAs(memberService2);
    }
    
    @Test
    @DisplayName("싱글톤")
    void singletonServiceTest() {
    	SingletonService instance1 = SingletonService.getInstance();
    	SingletonService instance2 = SingletonService.getInstance();
    	
    	Assertions.assertThat(instance1).isSameAs(instance2);
    	Assertions.assertThat(instance1).isEqualTo(instance2);
    }
    
    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void singleton() {
    	ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    	MemberService bean1 = ac.getBean("memberService", MemberService.class);
    	MemberService bean2 = ac.getBean("memberService", MemberService.class);
    	System.out.println(bean1);
    	System.out.println(bean2);
    	Assertions.assertThat(bean1).isSameAs(bean2);
    }
}

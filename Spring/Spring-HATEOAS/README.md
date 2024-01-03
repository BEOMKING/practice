[백기선님의 강의](https://www.inflearn.com/course/spring_rest-api/dashboard)를 듣고 실습한 저장소입니다.

`실습 환경`

강의와 좀 다른 설정을 가져갔습니다.

Spring Boot 3.0.4

Java 17

### `@WebMvcTest`

웹 관련된 빈만 등록해서 컨트롤러쪽만 테스트할 수 있게 도와주는 애노테이션

이와 비슷하게 `@DataJpaTest` 같은 레포지토리만 테스트할 수 있게 도와주는 애노테이션이 있다.

컨트롤러만 테스트할 수 있게 해주기 때문에 단위 테스트라고 생각했었는데 디스패처 서블릿 같은 웹과 관련한 의존성이 있기 때문에 완전한 단위 테스트라고 보기는 어렵고 슬라이스 테스트라고 봐야 한다.

### `ModelMapper`

객체 간의 매핑을 도와주는 라이브러리

DTO와 Entity를 매핑할 때 귀찮게 변환 메소드를 만들지 않아도 된다.

다만 의존성이 추가된다는 단점이 있고 변환 과정이 Reflection을 이용하기 때문에 성능을 고려해야 한다.

### `@AutoConfigureMockMvc`

실습 환경에서 구현체의 너무 의존하는 Mocking의 단점이 나타나 통합 테스트로 전환하는 과정을 진행했다.

이 과정에서 `@WebMvcTest`를 `@SpringBootTest`로 변경하고 `@AutoConfigureMockMvc`를 추가했다.

`@AutoConfigureMockMvc`는 `@SpringBootTest`와 함께 사용되며 HTTP 요청과 응답을 Mocking 해주는 `MockMvc` 객체를 자동구성해준다.

### `Validation`

객체의 유효성을 검증하는데 도움을 주는 라이브러리

`@Valid`를 사용하면 `@NotNull` `@NotEmpty` `@NotBlank`.. 등 더 많은 검증 애노테이션이 붙은 객체의 필드에 대한 검증을 할 수 있다.

또한, `Validator` 클래스를 직접 구현해서 사용할 수 있는데 `Validator` 인터페이스를 구현하는 방법과 `Custom Validator`를 만드는 방법이 있다.

`Validator` 인터페이스를 구현하는 방법은 인터페이스의 메소드를 모두 구현해야 하며 스프링 프레임워크에서 제공하는 다른 기능들과 연동해야 할 경우에 사용하는 것이 좋다. (Ex. `@InitBinder`)

`Custom Validator`를 사용하는 방법은 개인의 필요에 따라 구현할 수 있기 때문에 더 유연하게 사용할 수 있고 필요한 검증 시점에 사용해서 명시적으로 시점을 지정할 수 있다.

`Validation` 관련 테스트를 작성하면서 드는 생각은 `Request`에 대한 테스트 케이스들이 굉장히 많다는 것인데 과연 이 모두를 테스트를 작성해야 할까?

만약 테스트를 작성한다면 이를 통합 테스트말고 단위 테스트로 작성하는 것은 어떤가? 라는 생각이 들었다.

현재 드는 생각이 정말 비즈니스적으로 우선순위가 높은 것들만 테스트를 작성하고 하더라도 단위 테스트로 충분할 것 같다.

### `Spring Rest Docs`

`MockMvc` `RestAssured` 같은 API에 테스트 라이브러리를 이용해 작성된 스니펫을 기반으로 API 문서를 작성해주는 라이브러리

기본적으로 테스트 코드가 강제되는 것도 장점이고 테스트 코드가 통과하지 않으면 빌드가 실패하기 때문에 테스트 코드로 문서를 검증할 수 있는 것도 장점이 될 수 있다.

`Swagger` 와 달리 프로덕션 코드에 애노테이션이 추가되는 것은 아니기 때문에 가독성에 해를 끼치지 않는다.

Gradle 기준으로 설정은 다음과 같이 필요하다.

Spring Boot 3.0.4, Gradle 7.6.1 버전을 기준으로 하기 때문에 Maven이나 하위 버전은 변동이 크다.

```groovy
// build.gradle 기본적으로 사용되는 설정들이 더 있으나 여기서는 Rest Docs 관련 설정들만 기입

plugins {
		// Gradle 7.0 이상부터는 org.asciidoctor.convert 말고 아래와 같이 사용
    id 'org.asciidoctor.jvm.convert' version '3.3.2'
}

sourceCompatibility = '17'

configurations {
    asciidoctorExt
}

repositories {
    mavenCentral()
}

ext {
    set('snippetsDir', file("build/generated-snippets"))
}

dependencies {
    // Asciidoctor
    asciidoctorExt 'org.springframework.restdocs:spring-restdocs-asciidoctor'

    // MockMvc RestDocs
    testImplementation 'org.springframework.restdocs:spring-restdocs-mockmvc'
}

tasks.named('test') {
    outputs.dir snippetsDir
    useJUnitPlatform()
}

tasks.named('asciidoctor') {
    inputs.dir snippetsDir
    configurations 'asciidoctorExt'
		// 두 개의 configurations는 다른 설정이다. 위처럼 해야 한다.
		//configurations {
		//	'asciidoctorExt'
		//}
    dependsOn test
}

bootJar {
    dependsOn asciidoctor
    from ("${asciidoctor.outputDir}/html5") {
        into 'static/docs'
    }
}
```

src/docs/asciidoc 의 하위에 adoc 파일을 생성해야 한다. 이건 해당 디렉터리 참조
package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        // Configuration이 붙는 것들을 빼주겠다고 설정함.
        // 왜냐하면 @Configuration 안을 보면 @Component 설정이 되어 있기에 그것들도 자동등록 돼 버린다.
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
        // xml에서 설정해주는 것과 동일하다. 베이스 패키지를 설정해서 그 아래 모든 것들에 영향을 주게 설정할 수 있다.
        // 패키지 위치는 보통 패키지를 지정하지 않고 최상단에 두는 편이다.
        // @SpringBootApplication 을 프로젝트 시작 루트 위치에 두는 것이 관례이다.
        // 이 어노테이션에 컴포넌트 스캔이 들어있기 때문이다. 따라서 ComponentScan를 굳이 쓸 필요 없다.
        , basePackages = "hello.core.member"
)
public class AutoAppConfig {

    /*
    * @ComponentScan는 @Component이 붙은 모든 어노테이션을 bean으로 등록해준다.
    * @ComponentScan.Filter는 자동 등록할 때 제외할 것들을 지정해주는 것이다.
    * */
}

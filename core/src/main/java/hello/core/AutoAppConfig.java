package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        // Configuration이 붙는 것들을 빼주겠다고 설정함.
        // 왜냐하면 @Configuration 안을 보면 @Component 설정이 되어 있기에 그것들도 자동등록 돼 버린다.
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

    /*
    * @ComponentScan는 @Component이 붙은 모든 어노테이션을 bean으로 등록해준다.
    * @ComponentScan.Filter는 자동 등록할 때 제외할 것들을 지정해주는 것이다.
    * */
}

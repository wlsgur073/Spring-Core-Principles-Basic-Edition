package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // ThreadA : A 사용자 10000원 주문
        int userAPrice = statefulService1.order("userA", 10000);
        // ThreadB : B 사용자 10000원 주문
        int userBPrice = statefulService2.order("userB", 20000);

        // ThreadA : A 사용자 주문 금액 조회
//        int price = statefulService1.getPrice();
        System.out.println("price = " + userAPrice); // statefulService1을 썼으니 10000원이 나와야 하는데 20000이 나옴

        /*
        * statefulService1와 statefulService2는 같은 StatefulService의 인스턴스이다.
        * 따라서 전역변수 price의 값이 계속 초기화 되는 것이다.
        * */
//        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);

        // order 안에서 price를 반환하면 statefulService1과 statefulService2는 지역변수니까 다르게 작동한다.
    }

    static class TestConfig {

        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }

}
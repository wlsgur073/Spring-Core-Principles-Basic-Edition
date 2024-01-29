package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Provider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ProtoypeBean.class);

        ProtoypeBean protoypeBean1 = ac.getBean(ProtoypeBean.class);
        protoypeBean1.addCount();
        assertThat(protoypeBean1.getCount()).isEqualTo(1);

        ProtoypeBean protoypeBean2 = ac.getBean(ProtoypeBean.class);
        protoypeBean2.addCount();
        assertThat(protoypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUesPrototype() {
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(ClientBean.class, ProtoypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);
    }

    @Scope("singleton")
    static class ClientBean {
//        private final ProtoypeBean protoypeBean; // 생성 시점에 주입

//        @Autowired
//        ClientBean(ProtoypeBean protoypeBean) {
//            this.protoypeBean = protoypeBean;
//        }

        /*
        * DL(Dependency LookUp) 스프링 컨테이너를 통해 해당 빈을 찾아서 반환하는 작업.
        * 이전에는 provider를 매번 만들어서 썼지만 스프링 부트에서는 provider를 제공해주고 있다.
        * getObject()를 사용하면 프로토타입 빈을 생성하여 스프링 빈에 속한 객체들을 매번 새롭게 생성해준다.
        *
        * 특징
        * ObjectFactory: 기능이 단순, 별도의 라이브러리 필요 없음, 스프링에 의존
        * ObjectProvider: ObjectFactory 상속, 옵션, 스트림 처리등 편의 기능이 많고, 별도의 라이브러리 필요 없음, 스프링에 의존
        * */
//        @Autowired
//        private ObjectProvider<ProtoypeBean> protoypeBeanProvider;
//        private ObjectFactory<ProtoypeBean> protoypeBeanFactory; 팩토리도 있지만 프로바이더가 편의 기능이 더 추가 제공됨

        @Autowired
        private Provider<ProtoypeBean> protoypeBeanProvider; // 자바 표준이라 스프링에 의존하지 않는다. 대신 라이브러리 필요

        public int logic() {
//            ProtoypeBean protoypeBean = protoypeBeanProvider.getObject();
            ProtoypeBean protoypeBean = protoypeBeanProvider.get();
            protoypeBean.addCount();
            return protoypeBean.getCount();
        }
    }

    @Scope("prototype")
    static class ProtoypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("ProtoypeBean.init " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("ProtoypeBean.destroy");
        }
    }
}

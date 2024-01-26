package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.*;

public class SingletonWithPrototypeTest1 {

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
        assertThat(count2).isEqualTo(2);
    }

    @Scope("singleton")
    static class ClientBean {
        private final ProtoypeBean protoypeBean; // 생성 시점에 주입

        @Autowired
        ClientBean(ProtoypeBean protoypeBean) {
            this.protoypeBean = protoypeBean;
        }

        public int logic() {
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

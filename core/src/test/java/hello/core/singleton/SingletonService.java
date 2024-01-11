package hello.core.singleton;

public class SingletonService {

    /*
    * 싱글톤 패턴 구현 방법은 여러 방법이 있다.
    * 아래의 싱글톤 패턴 구현 방법은 객체를 미리 생성해두는 가장 단순하고 안전한 방법이다.
    * */

    // static 영역에 SingletonService 타입의 instance 라는 변수가 하나만 존재하게 된다.
    private static final SingletonService instance = new SingletonService();

    // instance 를 참조할 수 있는 방법은 오직 getInstance() 를 호출하는 방법밖에 없다.
    // 이것이 싱글톤이다.
    public static SingletonService getInstance() {
        return instance;
    }

    // new 키워드로 생성자를 호출하고 싶어도 private이기에 호출할 수 없다.
    private SingletonService() {}

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}

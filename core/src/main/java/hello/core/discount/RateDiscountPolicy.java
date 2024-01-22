package hello.core.discount;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
// 이전에는 @Qualifier("mainDiscountPolicy")처럼 썼지만 "mainDiscountPolicy" 문자열이기에 실수가 생길 수 있다.
// 그래서 직접 어노테이션을 만들어서 사용할수도 있다. 권장까지는 아님.
@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy{
    private final int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return price * discountPercent / 100;
        } else {
            return 0;
        }
    }
}

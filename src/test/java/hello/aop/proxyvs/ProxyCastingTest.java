package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
public class ProxyCastingTest {

    @Test
    public void jdkProxy() throws Exception {
        final MemberServiceImpl target = new MemberServiceImpl();
        final ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false); // JDK 동적 프록시

        // 프록시를 인터페이스로 캐스팅 성공
        final MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        assertThatThrownBy(() -> {
            final MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
        }).isInstanceOf(ClassCastException.class);
    }

    @Test
    public void cglibProxy() throws Exception {
        final MemberServiceImpl target = new MemberServiceImpl();
        final ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true); // CGLIB 프록시

        // 프록시를 인터페이스로 캐스팅 성공
        final MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        final MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
    }
}

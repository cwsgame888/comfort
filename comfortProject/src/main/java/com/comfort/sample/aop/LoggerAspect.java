/**
 | ---------------------------------------------------------------------------------------------------------
 | Aspect(관점)			- 공통적으로 적용될 기능을 의미합니다. 횡단 관심사의 기능이라고도 할 수 있으며 한 개 이상의 포인트컷과 어드바이스의 조합으로 만들어집니다.
 | Advice(어드바이스)		- 관점의 구현체로 조인포인트에 삽입되어 동작하는 것을 의미합니다. 스프링에서 사용하는 어드바이스는 동작하는 시점에 따라 다섯 종류로 구분됩니다.
 | Joinpoint(조인포인트)	- 어드바이스를 적용하는 지점을 의미합니다. 스프링 프레임워크에서 조인포인트는 항상 메서드 실행 단계만 가능합니다.
 | Pointcut(포인트컷)		- 어드바이스를 적용할 조인포인트를 선별하는 과정이나 그 기능을 정의한 모듈을 의미합니다. 
 |						    정규표현식이나 AspectJ의 문법을 이용해서 어떤 조인포인트를 사용할 것인지 결정합니다.
 | Target(타깃)			- 어드바이스를 받을 대상을 의미합니다.
 | Weaving(워빙)			- 어드바이스를 적용하는 것을 의미합니다. 즉, 공통 코드를 원하는 대상에 삽입하는 것을 뜻합니다.
 | ---------------------------------------------------------------------------------------------------------
 | # Advice 종류
 | @Before(조인포인트 전에 실행 )
 | 대상 메서드가 실행되기 전에 적용할 어드바이스를 정의합니다.
 | @AfterReturning(조인포인트에서 성공적으로 리턴 된 후 실행)
 | 대상 메서드가 성공적으로 실행되고 결과값을 반환한 후 적용할 어드바이스를 정의합니다.
 | @AfterThrowing(예외가 발생하였을 경우 실행)
 | 대상 메서드에서 예외가 발생했을 때 적용할 어드바이스를 정희합니다. try/catch문의 catch와 비슷한 역할을 합니다.
 | @After(조인포인트에서 메서드의 실행결과에 상관없이 무조건 실행)
 | 대상 메서드의 정상적인 수행 여부와 상관없이 무조건 실행되는 어드바이스를 정의합니다. 즉 예외가 발생하더라도 실행되기 때문에 자바의 finally와 비슷한 역할을 합니다.
 | @Around(조인포인트의 전 과정(전, 후)에 수행)
 | 대상 메서드의 호출 전후, 예외 발생 등 모든 시점에 적용할 수 있는 어드바이스를 정의합니다. 가장 범용적으로 사용할 수 있는 어드바이스입니다.
 | ---------------------------------------------------------------------------------------------------------
 | # execution 포인트컷 예
 | execution([수식어] [리턴타입] [클래스이름] [이름]([파라미터])
 | ---------------------------------------------------------------------------------------------------------
 */
package com.comfort.sample.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggerAspect {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Around("execution(* com.comfort.sample..controller.*Controller.*(..))"
			+ " or execution(* com.comfort.sample..service.*Impl.*(..))"
			+ " or execution(* com.comfort.sample..mapper.*Mapper.*(..))")
	public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable {
		String type = "";
		String name = joinPoint.getSignature().getDeclaringTypeName();
		if(name.indexOf("Controller") > -1) {
			type = "Controller \t: ";
		} else if(name.indexOf("Service") > -1) {
			type = "Service \t: ";
		} else if(name.indexOf("Mapper") > -1) {
			type = "Mapper \t\t: ";
		}
		log.debug(type + name + "." + joinPoint.getSignature().getName() + "()");
		return joinPoint.proceed();
	}
}

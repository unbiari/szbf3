package com.kidmobile.csr.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

//@Around("bean(*Controller)")
//public Object AopTimeCalc(ProceedingJoinPoint joinPoint) throws Throwable {
// long start = System.currentTimeMillis();
// 
// Object returnPoint = joinPoint.proceed();
// 
// long finish = System.currentTimeMillis();
// logger.debug("["+ joinPoint.getSignature().getName()+" Turnaround Time] : " + (finish -start)  + "ms" );
//  
// return returnPoint;
//}


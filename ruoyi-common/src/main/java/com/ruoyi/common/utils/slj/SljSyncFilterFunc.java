package com.ruoyi.common.utils.slj;

@FunctionalInterface
public interface SljSyncFilterFunc<T1,T2,Boolean> {
    boolean test(T1 t1,T2 t2,Boolean isAdd);
}

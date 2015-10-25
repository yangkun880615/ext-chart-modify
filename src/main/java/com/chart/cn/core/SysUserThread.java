package com.chart.cn.core;

import com.chart.cn.entity.resconfig.SysUser;



public class SysUserThread {
    private static ThreadLocal<SysUser> instance = new ThreadLocal<SysUser>();

    public static SysUser get() {
        return instance.get();
    }

    public static void set(SysUser loginSysUser) {
        instance.set(loginSysUser);
    }

    public static void remove() {
        instance.remove();
    }
}

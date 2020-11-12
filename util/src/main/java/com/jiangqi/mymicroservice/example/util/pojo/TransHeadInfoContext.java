package com.jiangqi.mymicroservice.example.util.pojo;

public class TransHeadInfoContext {
	private static ThreadLocal<TransHeadInfo> transHeadInfo = new ThreadLocal<TransHeadInfo>();
    public static String KEY_TRANSINFO_IN_HTTP_HEADER = "X-AUTO-FP-TRANSINFO";

    public TransHeadInfoContext() {
    }

    public static TransHeadInfo getTransHeadInfo(){
        return (TransHeadInfo)transHeadInfo.get();
    }

    public static void setTransHeadInfo(TransHeadInfo transHeadInfo){
    	TransHeadInfoContext.transHeadInfo.set(transHeadInfo);
    	return;
    }
}

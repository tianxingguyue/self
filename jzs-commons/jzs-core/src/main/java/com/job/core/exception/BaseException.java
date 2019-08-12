package com.job.core.exception;

/**
 * 异常接口类
 * 
 * @author lizhiliang
 * @version 1.0,
 */
public interface BaseException {

	/**
	 * 返回异常信息
	 * @return
	 */
	String getMessage();

	/**
	 * 返回异常编码
	 * @return
	 */
	int getCode();

}

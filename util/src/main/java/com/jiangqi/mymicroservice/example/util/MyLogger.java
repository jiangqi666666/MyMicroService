package com.jiangqi.mymicroservice.example.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.jiangqi.mymicroservice.example.util.pojo.TransHeadInfo;
import com.jiangqi.mymicroservice.example.util.pojo.TransHeadInfoContext;

/**
 * 自定义日志处理类<br>
 * 为日志添加交易头信息
 * 
 * @author Administrator
 *
 */
@Component
public class MyLogger {
	
	@Autowired
	private Environment env;

	/**
	 * 将堆栈的消息变成一行信息，便于kiana查询，每行用 \\ 分割
	 * 
	 * @param e
	 * @return
	 */
	private String getExceptionMessage(Exception e) {
		StringWriter sw = null;
		PrintWriter pw = null;
		try {
			sw = new StringWriter();
			pw = new PrintWriter(sw);
			// 将出错的栈信息输出到printWriter中
			e.printStackTrace(pw);
			pw.flush();
			sw.flush();
		} finally {
			if (sw != null) {
				try {
					sw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (pw != null) {
				pw.close();
			}
		}

		String tmp = sw.toString();

		if (env.getProperty("OsType").equals("win") == true)
			tmp = tmp.replace("\r\n", "\\ ");
		else
			tmp = tmp.replace("\n", "\\ ");

		return tmp;
	}

	/**
	 * 创建写日志的内容<br>
	 * 在msg基础上，添加了消息头信息，便于跨服务交易的追踪
	 * 
	 * @param msg
	 * @return
	 */
	private String creatMsg(String msg) {
		TransHeadInfo trans = TransHeadInfoContext.getTransHeadInfo();

		if (trans == null) {
			trans = new TransHeadInfo();
			trans.setCall("null");
			trans.setId("null");
			trans.setType("null");
		}

		return String.format("transType=%s;transId=%s;callSrv=%s;logmsg=%s", trans.getType(), trans.getId(),
				trans.getCall(), msg);
	}

	public void info(Logger logger, String msg) {
		logger.info(creatMsg(msg));
	}

	public void debug(Logger logger, String msg) {
		if (logger.isDebugEnabled())
			logger.debug(creatMsg(msg));
	}

	public void error(Logger logger, @Nullable String msg, Exception e) {
		logger.error(creatMsg(msg));
	}

	public void error(Logger logger, Exception e) {
		logger.error(creatMsg(getExceptionMessage(e)));
	}

	public void warn(Logger logger, String msg) {
		if (logger.isWarnEnabled())
			logger.warn(creatMsg(msg));
	}

	public void trace(Logger logger, String msg) {
		logger.trace(creatMsg(msg));
	}
}

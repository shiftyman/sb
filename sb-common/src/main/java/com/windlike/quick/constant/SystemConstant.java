package com.windlike.quick.constant;

public class SystemConstant {
	/**
	 * 文本分隔符
	 */
	public static final String LINE_SEPARATOR;
	
	static{
		LINE_SEPARATOR = System.getProperty("line.separator");
	}
}

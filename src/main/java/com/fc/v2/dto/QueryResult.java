package com.fc.v2.dto;

/**
 * @ClassName: QueryResult
 * @Description:查询结果
 * @author: reisen
 * @date: 2024-10-16 18:20:36
 *
 */
public class QueryResult<T> {
	private boolean success;
	private String exception;
	private T data;
	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}
	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	/**
	 * @return the exception
	 */
	public String getException() {
		return exception;
	}
	/**
	 * @param exception the exception to set
	 */
	public void setException(String exception) {
		this.exception = exception;
	}
	/**
	 * @return the data
	 */
	public T getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(T data) {
		this.data = data;
	}
}

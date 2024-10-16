package com.fc.v2.dto;

/**
 * @ClassName: ChatDataDto
 * @Description:图表对象
 * @author: reisen
 * @date: 2024-10-16 18:20:36
 * 
 */
public class ChartDataDto {
	private String[] xAxisData;
	private String[] seriesData;

	public String[] getxAxisData() {
		return xAxisData;
	}

	public void setxAxisData(String[] xAxisData) {
		this.xAxisData = xAxisData;
	}

	public String[] getSeriesData() {
		return seriesData;
	}

	public void setSeriesData(String[] seriesData) {
		this.seriesData = seriesData;
	}
}

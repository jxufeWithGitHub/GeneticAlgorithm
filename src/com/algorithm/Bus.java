package com.algorithm;

/**
 * 公交实体
 *
 * @author wanyuhui on 2019/12/30 16:45
 * @modify {原因}  wanyuhui on 2019/12/30 16:45
 * @since v1.0
 */

public class Bus {

    /**
     * 车辆类型，0 微公交，1 常规公交
     */
    private Integer type;

    /**
     * 最大载客数
     */
    private Double maximumCapacity;

    /**
     * 当前载客数
     */
    private Double currentCapacity;

    /**
     * 当前载客率
     */
    private Double currentRate;

    /**
     * 平均载客率
     */
    private Double averageRate;

    /**
     * 距上一辆车的发车间隔
     */
    private Double departureTime;

    /**
     * 当前所处站台
     */
    private Integer stationIndex;

    /**
     * 到达下一站还需多少距离
     */
    private Double arrivalDistance;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getMaximumCapacity() {
        return maximumCapacity;
    }

    public void setMaximumCapacity(Double maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
    }

    public Double getCurrentCapacity() {
        return currentCapacity;
    }

    public void setCurrentCapacity(Double currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    public Double getCurrentRate() {
        return currentRate;
    }

    public void setCurrentRate(Double currentRate) {
        this.currentRate = currentRate;
    }

    public Double getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(Double averageRate) {
        this.averageRate = averageRate;
    }

    public Double getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Double departureTime) {
        this.departureTime = departureTime;
    }

    public Integer getStationIndex() {
        return stationIndex;
    }

    public void setStationIndex(Integer stationIndex) {
        this.stationIndex = stationIndex;
    }

    public Double getArrivalDistance() {
        return arrivalDistance;
    }

    public void setArrivalDistance(Double arrivalDistance) {
        this.arrivalDistance = arrivalDistance;
    }
}

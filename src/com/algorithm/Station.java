package com.algorithm;

import java.util.List;
import java.util.Map;

/**
 * TODO...
 *
 * @author wanyuhui on 2019/12/31 08:29
 * @modify {原因}  wanyuhui on 2019/12/31 08:29
 * @since v1.0
 */

public class Station {

    /**
     * 站台人数
     */
    private Double number;

    /**
     * 下车人数
     */
    private Double getOffNumber;

    /**
     * 不同时短的每分钟增长率/下车率
     */
    private Map<String, List<Integer>> changeRate;

    /**
     * 站台权重
     */
    private Double weight;

    /**
     * 距离上一个站台的距离
     */
    private Double distance;

    public Double getNumber() {
        return number;
    }

    public void setNumber(Double number) {
        this.number = number;
    }

    public Double getGetOffNumber() {
        return getOffNumber;
    }

    public void setGetOffNumber(Double getOffNumber) {
        this.getOffNumber = getOffNumber;
    }

    public Map<String, List<Integer>> getChangeRate() {
        return changeRate;
    }

    public void setChangeRate(Map<String, List<Integer>> changeRate) {
        this.changeRate = changeRate;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}

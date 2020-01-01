package com.algorithm;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    // 公交总班次
    public final static Integer TOTAL_BUS_NUM = 70;

    // 公交平均车速，单位 KM/m
    public final static Double BUS_SPEED = 0.5d;

    // 线路总站台数
    public final static Integer TOTAL_STATIONS = 10;

    // 每条线路的发车时间
    public final static String START_TIME = "06:00:00";

    // 每条线路的停班时间
    public final static String END_TIME = "21:00:00";

    // 初始族群中成员数量
    public final static Integer GROUP_NUM = 100;

    // 交叉概率
    public final static Double CROSSOVER_PROBABILITY = 0.51d;

    // 变异概率
    public final static Double MUTATION_PROBABILITY = 0.001d;

    // 最大繁衍次数
    public final static Long MAX_ITERATION = 120000L;

    // 日期格式化规则
    public final static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

    public static void main(String[] args) throws ParseException {
        Integer count = 0;  // 迭代次数

        // 1 初始化第一代族群及其他条件因素
        List<List<Bus>> groups = new ArrayList<>();
        for (int i = 0; i < GROUP_NUM; i++) {
            List<Bus> group = new ArrayList<>();
            for (int j = 0; j < TOTAL_BUS_NUM; j++) {
                Bus bus = new Bus();
                bus.setType(1);
                bus.setMaximumCapacity(79d);
                bus.setCurrentCapacity(0d);
                bus.setCurrentRate(0d);
                bus.setAverageRate(0d);
                bus.setDepartureTime(13d);
                bus.setStationIndex(-1);
                bus.setArrivalDistance(BUS_SPEED * 0.5);
                group.add(bus);
            }
            groups.add(group);
        }

        // 2 开始迭代繁衍
        do {
            // 2.1 计算染色体适应值
            simulatedOperation(groups);
            Collections.sort(groups, (list1, list2) -> {
                Double fitness1 = 0d;
                Double fitness2 = 0d;
                for (int i = 0; i < list1.size(); i++) {
                    fitness1 += list1.get(i).getAverageRate();
                }
                fitness1 = fitness1 / list1.size();
                for (int i = 0; i < list2.size(); i++) {
                    fitness2 += list2.get(i).getAverageRate();
                }
                fitness2 = fitness2 / list2.size();
                System.out.println(fitness1);
                System.out.println(fitness2);
                return fitness1 - fitness2 > 0d ? 1 : -1;
            });
            // 2.2 最优法选择

            // 2.2 交叉繁衍

            // 2.3 个体变异

            // 2.4 优胜劣汰

            count++;
        } while (count <= MAX_ITERATION);  // 满足该条件则推出繁衍

        // 3 得出最优解
    }

    /**
     * 初始化站台
     *
     * @return
     */
    public static List<Station> initializationStations() {
        // 获取站台json数据
        List<HashMap> json = JSONObject.parseArray(readJsonFile(), HashMap.class);

        List<Station> stationList = new ArrayList<>();
        for (int i = 0; i < TOTAL_STATIONS; i++) {
            Station station = new Station();
            station.setDistance(Double.valueOf(json.get(i).get("distance").toString()));
            station.setNumber(0d);
            station.setGetOffNumber(0d);
            station.setWeight(1d);
            station.setChangeRate((Map<String, List<Integer>>) json.get(i).get("changeRate"));
            stationList.add(station);
        }
        return stationList;
    }

    /**
     * 获取站台json数据
     *
     * @return
     */
    public static String readJsonFile() {
        String jsonStr;
        try {
            File jsonFile = new File("src/com/algorithm/resources/station.json");
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date addMin(String time, int second) {
        Date date = null;
        try {
            date = SIMPLE_DATE_FORMAT.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, second);
        date = cal.getTime();
        cal = null;
        return date;
    }

    public static void simulatedOperation(List<List<Bus>> groups) throws ParseException {
        for (int i = 0; i < groups.size(); i++) {
            List<Bus> group = groups.get(i);

            // 1 初始化站台
            List<Station> stationList = initializationStations();

            // 2 初始化起始时间
            Integer second = -(10 * 60);
            Date currentTime = addMin(START_TIME, second);
            for (int j = 0; j < 1800; j++) {
                // 时间增加 30 秒
                second += 30;
                currentTime = addMin(START_TIME, second);

                // 每过一分钟，站台人数进行增加
                if ((Math.abs(second) > 0 && (Math.abs(second) / 30) % 2 == 0) || second == 0) {
                    for (int k = 0; k < stationList.size(); k++) {
                        Station station = stationList.get(k);
                        Map<String, List<Integer>> rate = station.getChangeRate();
                        Object[] keys = rate.keySet().toArray();
                        for (int l = 0; l < keys.length; l++) {
                            String curKey = keys[l].toString();
                            if (l + 1 == keys.length) {
                                break;
                            }
                            String preKey = keys[l + 1].toString();
                            // 根据当前时间该站台的人数增长率进行人数增加
                            if (SIMPLE_DATE_FORMAT.parse(curKey).after(currentTime)) {
                                station.setNumber(rate.get(curKey).get(0) + station.getNumber());
                                station.setGetOffNumber(rate.get(curKey).get(1) + station.getNumber());
                                break;
                            }
                            if (SIMPLE_DATE_FORMAT.parse(curKey).before(currentTime) && SIMPLE_DATE_FORMAT.parse(preKey).after(currentTime)) {
                                station.setNumber(rate.get(preKey).get(0) + station.getNumber());
                                station.setGetOffNumber(rate.get(preKey).get(1) + station.getNumber());
                                break;
                            }
                            if (SIMPLE_DATE_FORMAT.parse(preKey).before(currentTime))
                                continue;
                        }
                        stationList.set(k, station);
                    }
                }

                // 每过 30 秒，车辆的状态进行变更
                if (SIMPLE_DATE_FORMAT.parse(START_TIME).before(currentTime) || START_TIME.equals(SIMPLE_DATE_FORMAT.format(currentTime))) {
                    for (int k = 0; k < group.size(); k++) {
                        Bus bus = group.get(k);

                        // 判断车辆是否已到终点站
                        if (bus.getStationIndex() == TOTAL_STATIONS - 1)
                            continue;

                        bus.setArrivalDistance(bus.getArrivalDistance() - BUS_SPEED * 0.5);

                        // 判断车辆是否已到站
                        if (bus.getArrivalDistance() <= 0) {
                            bus.setStationIndex(bus.getStationIndex() + 1);

                            // 判断车辆是否已到终点站
                            if (bus.getStationIndex() == TOTAL_STATIONS - 1)
                                continue;
                            bus.setArrivalDistance(stationList.get(bus.getStationIndex() + 1).getDistance() + BUS_SPEED * 0.5);

                            // 计算上下车人数
                            Double waitingNum = stationList.get(bus.getStationIndex()).getNumber();
                            Double onBoard = 1.2 * bus.getMaximumCapacity() - bus.getCurrentCapacity();
                            Double getOff = stationList.get(bus.getStationIndex()).getGetOffNumber();
                            if (onBoard > waitingNum) {
                                onBoard = waitingNum;
                                waitingNum = 0d;
                            } else
                                waitingNum -= onBoard;
                            bus.setCurrentCapacity(bus.getCurrentCapacity() + onBoard - getOff);
                            stationList.get(bus.getStationIndex()).setNumber(waitingNum);
                            stationList.get(bus.getStationIndex()).setGetOffNumber(0d);

                            // 计算满载率
                            Double currentRate = bus.getCurrentCapacity() / bus.getMaximumCapacity();
                            bus.setAverageRate((bus.getCurrentRate() + currentRate) / (bus.getStationIndex() + 1));
                            bus.setCurrentRate(currentRate);
                        }
                        group.set(k, bus);
                    }
                }
            }
        }
    }
}

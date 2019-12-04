/*
 * File 	: TeamData.java
 * Date		: 20191204
 * Author	: choisohyun
 * 
 * https://github.com/choisohyun/baseballGame
 */

package codeSquad;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

public class TeamData extends BaseBallGame {
	static Scanner sc = new Scanner(System.in);
	static Map<String, List<String>> data = new LinkedHashMap<>();

	public static void main(String[] args) {
		start();
	}
	
	public static void start() {
		System.out.println(
				"신나는 야구시합\r\n" + 
				"1. 데이터 입력\r\n" + 
				"2. 데이터 출력\r\n" + 
				"3. 시합 시작\r\n" + 
				"\r\n" + 
				"메뉴선택 (1 - 3) ");
		choice(sc.nextInt());
	}
	
	public static void choice(int n) {
		if (n == 1) inData();
		else if (n == 2) outData();
		else startGame();		
	}

	public static void inData() {
		sc.nextLine();
		IntStream.range(1, 3).forEachOrdered(k -> {
		    System.out.print(k + "팀의 이름을 입력하세요> ");
		    String key = sc.nextLine();
			List<String> value = new ArrayList<String>();
		    
			IntStream.range(1, 10).forEachOrdered(v -> {
			    System.out.print(v + "번 타자 정보 입력> ");
			    value.add(sc.nextLine());
			});
		    System.out.println();
		    data.put(key, value);
		});
		start();
	}
	
	public static void outData() {
		for (String key : data.keySet()) {
			System.out.println(key + " 팀 정보");
			List<String> value = data.get(key);
		    for (String val : value) {
		    	System.out.println(value.indexOf(val)+1 + "번 " + val);
		    }
		    System.out.println();
		}
		System.out.print("\n\n");
		start();
	}
	
	public static void startGame() {
		Object team1 = data.keySet().toArray()[0];
		Object team2 = data.keySet().toArray()[1];
		int[] score = new int[2];
		System.out.print(team1 + " VS "
				+ team2 + "의 시합을 시작합니다.\n\n");
		
		for (int i = 1; i <= 6; i++) {
			System.out.println(i + "회초 " + team1 + "의 공격");
			attack(team1);
			System.out.println(i + "회말 " + team2 + "의 공격");
			attack(team2);
		}
		last(score);
	}
	
	public static void attack(Object obj) {
		List<String> value = data.get(obj);
	    for (String val : value) {
	    	String[] info = val.split(", ");
	    	System.out.println(value.indexOf(val)+1 + "번  " + info[0]);
	    	batting(Double.parseDouble(info[1]));
	    }
	}

	public static void batting(Double num) {
		Double[] per = percentage(num);
		Double rand = new Random().nextDouble();
		Double start = 0.0, end = 0.0;
		int result = 0;
		
		for (int i = 0; i < per.length - 1; i++) {
			start += per[i];
	      	end += per[i + 1];
	      	if (rand >= start && rand <= end) {
	      		result = i;
	      		break;
	      	}
		}
		System.out.println(randList[result]);
		sbo(result);
	}
	
	public static Double[] percentage(Double h) {
		Double[] per = new Double[4];
		per[1] = (1 - h) / 2 - 0.05;
		per[2] = (1 - h) / 2 - 0.05;
		per[3] = 0.1;
		per[0] = 1 - (per[1] + per[2] + per[3]);
		
		return per;
	}
	
	public static void sbo(int num) {
		
		Map<String, Integer> stateSBO = new HashMap<>();

		if (num == 0)	// 스트라이크
			strike(num);
		else if (num == 1)  // 볼
			ball(num);
		else if (num == 2)  // 안타
			anta();
		else if (num == 3)  // 아웃
			out(stateSBO);
		stateSBO.put("S", state[0]);
		stateSBO.put("B", state[1]);
		stateSBO.put("O", state[2]);
		
		result(stateSBO);
	}
	
	public static void strike(int num) {
		state[num]++;
		if (state[num] == 3) { // 아웃
			state[2]++;
			state[num] = 0;
			System.out.print("\n" + randList[3] + MESSAGE_NEXT);
		}
	}
	
	public static void ball(int num) {
		state[num]++;
		if (state[num] == 4) { // 스트라이크
			state[0]++;
			state[num] = 0;
			System.out.print(MESSAGE_NEXT);
		}
	}
	
	public static void anta() {
		state[0] = 0;
		state[1] = 0;
		countAnta++;
		System.out.print(MESSAGE_NEXT);
	}
	
	public static void out(Map<String, Integer> stateSBO) {
		state[0] = 0;
		state[1] = 0;
		state[2]++;
		if (state[2] == 3) { // 종료
			state[2] = 0;
			
		}
		else {
			System.out.print(MESSAGE_NEXT);
		}
	}
	
	public static void last(int[] score) {
		System.out.println("경기 종료\n");
		System.out.println(data.keySet().toArray()[0] 
				+ " VS " + data.keySet().toArray()[1]);
		System.out.println(score[0] + " : " + score[1]);
		System.out.println("Thank you!");
	}
}

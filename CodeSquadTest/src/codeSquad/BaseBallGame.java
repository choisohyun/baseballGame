/*
 * File 	: BaseBallGame.java
 * Date		: 20191203
 * Author	: choisohyun
 * 
 * https://github.com/choisohyun/baseballGame
 */

package codeSquad;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BaseBallGame {

	static final String MESSAGE_START = "신나는 야구 게임!\r\n첫 번째 타자가 타석에 입장했습니다.\n";
	static final String MESSAGE_NEXT = " 다음 타자가 타석에 입장했습니다.";
	static final String[] randList = {"스트라이크!", "볼!", "안타!", "아웃!"};
	static final int[] state = new int[3];
	static int countAnta = 0;
	
	public static void main(String[] args) {
		System.out.println(MESSAGE_START);
		while (true) {
			rand(randList);
		}
	}

	public static void rand(String[] array) {
		Random random = new Random();
		int idx = random.nextInt(array.length);
		
		System.out.print(array[idx]);
		sbo(idx);
	}
	
	public static void sbo(int num) {
		Map<String, Integer> stateSBO = new HashMap<>();

		if (num == 0) {	// 스트라이크
			state[num]++;
			if (state[num] == 3) { // 아웃
				state[2]++;
				state[num] = 0;
				System.out.print("\n" + randList[3] + MESSAGE_NEXT);
			}
		}
		else if (num == 1) { // 볼
			state[num]++;
			if (state[num] == 4) { // 스트라이크
				state[0]++;
				state[num] = 0;
				System.out.print(MESSAGE_NEXT);
			}
		}
		else if (num == 2) { // 안타
			state[0] = 0;
			state[1] = 0;
			countAnta++;
			System.out.print(MESSAGE_NEXT);
		}
		else if (num == 3) { // 아웃
			state[0] = 0;
			state[1] = 0;
			state[2]++;
			if (state[2] == 3) { // 종료
				stateSBO.put("S", state[0]);
				stateSBO.put("B", state[1]);
				stateSBO.put("O", state[2]);
				result(stateSBO);
				action();
			}
			else {
				System.out.print(MESSAGE_NEXT);
			}
		}
		stateSBO.put("S", state[0]);
		stateSBO.put("B", state[1]);
		stateSBO.put("O", state[2]);
		
		result(stateSBO);
	}
	
	public static void action() {
		last(countAnta);
	}

	public static void result(Map<String, Integer> stateSBO) {
		System.out.println();
		stateSBO.entrySet().forEach(entry->{
		    System.out.print(entry.getKey() + entry.getValue() + " ");  
		 });
		System.out.print("\n\n");
	}
	
	public static void last(int anta) {
		System.out.println("최종 안타수: " + anta); 
		System.out.println("GAME OVER");
		System.exit(0);
	}
}

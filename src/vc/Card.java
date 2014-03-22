package vc;

public class Card {	
	
	public final static int EXP_CHART[] = {
		0, 100, 207, 321, 444, 575, 715, 865, 1026, 1198, //1 - 10
		1382, 1578, 1789, 2014, 2255, 2513, 2789, 3084, 3400, 3738, // 11 - 20
		
		4100, 4487, 4901, 5344, 5818, 6325, 6868, 7448, 8070, 8735, // 21 - 30
		9446, 10207, 11022, 11893, 12826, 13824, 14891, 16034, 17256, 18564, // 31 - 40
		
		19964, 21461, 23063, 24778, 26612, 28575, 30675, 32922, 35327, 37900, // 41 - 50
		40653, 43599, 46750, 50123, 53732, 57593, 61724, 66145, 70875, 75936, // 51 - 60
		
		81352, 87147, 93347, 99981, 107080, 114676, 122803, 131499, 140804, 150760, // 61, 70 
		161413, 172812, 185009, 198060, 212024, 226966, 242953, 260060, 278364, 297950 // 71 - 80
	};
	
	public final static int N = 0;
	public final static int HN = 1;
	public final static int R = 2;
	public final static int SLIME = 3;
	public final static int METAL_SLIME = 4;
	
	private int level;
	private int type;
	
	public Card(int level, int type){
		this.level = level;
		this.type = type;
	}
	
	public int getLevel() {
		return level;
	}


	public int getType() {
		return type;
	}
	
	public static int getNeededExp(int level){
		return EXP_CHART[level - 1];
	}
	
	public static int nExp(int level){
		
		double result = 100 + (getNeededExp(level) * 0.25);
		return (int)result; 
	}
	
	public static int hnExp(int level){
		double result = 150 + (getNeededExp(level) * 0.375);
		return (int)result; 
	}

	public static int rExp(int level){
		double result = 200 + (getNeededExp(level) * 0.5);
		return (int)result; 
	}

	public static int slimeExp(int level){
		double result = 1000 + (getNeededExp(level) * 1.25);

		return (int)result; 
	}

	public static int mslimeExp(int level){
		double result = 3000 + (getNeededExp(level) * 3.75);
		return (int)result; 
	}
	
	public static Integer[] getLevelsArray(int max){
		Integer[] lvls = new Integer[max];
		for(int i = 1; i <= lvls.length; i++){
			lvls[i-1] = i;
		}
		
		return lvls;
	}
	
	public static Integer[] getLevelsArray(){
		return getLevelsArray(80);
	}
}

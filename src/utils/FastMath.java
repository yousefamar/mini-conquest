package utils;

public class FastMath {

	private static float sinBuffer[] = loadSinBuffer();
	private static float cosBuffer[] = loadCosBuffer();

	// TODO: Investigate modulus operator for negative numbers.
	
	public static float sin(float degree) {
		try {
			return sinBuffer[(int)(degree*100)];
		} catch (ArrayIndexOutOfBoundsException e) {
			while (degree < 0)
				degree += 360;
			while (degree >= 360)
				degree -= 360;
			return sinBuffer[(int)(degree*100)];
		}
	}
	
	public static float cos(float degree) {
		try {
			return cosBuffer[(int)(degree*100)];
		} catch (ArrayIndexOutOfBoundsException e) {
			while (degree < 0)
				degree += 360;
			while (degree >= 360)
				degree -= 360;
			return cosBuffer[(int)(degree*100)];
		}
	}

	private static float[] loadSinBuffer() {
		float sinBuffer[] = new float[36000];
		for (int i = 0; i < sinBuffer.length; i++)
			sinBuffer[i] = (float) Math.sin(Math.toRadians(((float)i)/100));
		return sinBuffer;
	}

	private static float[] loadCosBuffer() {
		float cosBuffer[] = new float[36000];
		for (int i = 0; i < cosBuffer.length; i++)
			cosBuffer[i] = (float) Math.cos(Math.toRadians(((float)i)/100));
		return cosBuffer;
	}
	
	public static float abs(float num) {
		if (num<0) num*=-1;
		return num;
	}

	public static int floor(float num) {
		return (int) num;
	}
	
	public static int ceil(float num) {
		return floor(num)+1;
	}
	
	public static double pow(float num, int pow) {
		//TODO: Find a faster algorithm.
		return Math.pow(num, pow);
	}

	public static String mergeCoords(int x, int y, int z) {
		return x+""+y+""+z;
	}
	
	public static String mergeCoords(int x, int z) {
		return x+""+z;
	}
}

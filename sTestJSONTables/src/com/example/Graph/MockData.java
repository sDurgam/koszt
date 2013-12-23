package com.example.Graph;

import java.util.Random;

public class MockData {

	public static Point getDataFromReceiver(int x)
	{
		return new Point(x, getRandomData());
	}

	private static int getRandomData() {
		// TODO Auto-generated method stub
		Random random = new Random();
		return random.nextInt(40);
	}

}

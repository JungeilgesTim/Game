package terrain;

import java.util.Random;

public class HeightsGenerator {
	
	private static final float AMPLITUDE = 70f;
	private Random random = new Random();
	public static int seed;     
	
	public HeightsGenerator()
	{
		HeightsGenerator.seed = random.nextInt(1000000000);
	}
	
	/*
	 * uses method below to create random smoothed heights for each vertex
	 */
	
	public float generateHeight(int x, int z)
	{
		
		if(x <= 2.5f || x >= 253.5f)
			{
				float f1 = getInterpolatedNoise(0 / 32f, z / 32f) * (AMPLITUDE);
				f1 += getInterpolatedNoise(0 / 8f, z / 8f) * (AMPLITUDE /3);
				float f2 = getInterpolatedNoise(256 / 32f, z / 32f) * (AMPLITUDE);
				f2 += getInterpolatedNoise(256 / 8f, z / 8f) * (AMPLITUDE /3);
				
				float result = (f1 + f2)/2;

				if(x <= 2.5f || x >= 253.5f)
				{
					return result;

				}

			}
		
		if(z <= 2.5f)
		{
			
		}
		
		if(z >= 253.5f)
		{
			
		}
			float total =  getInterpolatedNoise(x / 32f, z / 32f) * (AMPLITUDE);
			total += getInterpolatedNoise(x / 8f, z / 8f) * (AMPLITUDE /3);
			//System.out.println(total);
			return total;
	}
	
	
	/*
	 * combines all below methods to create smooth interpolated noise
	 */

	private float getInterpolatedNoise(float x, float z)
	{
		int intX = (int) x;
		int intZ = (int) z;
		float fracX = x -intX;
		float fracZ = z -intZ;
		
		float v1 = getSmoothNoise(intX,intZ);
		float v2 = getSmoothNoise(intX + 1,intZ);
		float v3 = getSmoothNoise(intX,intZ + 1);
		float v4 = getSmoothNoise(intX + 1,intZ + 1);
		
		float i1 = interpolate(v1, v2, fracX);
		float i2 = interpolate(v3, v4, fracX);
		
		return interpolate(i1, i2, fracZ);
		
		
		
	}
	
	/*
	 * interpolates between the values for each vertex
	 */
	
	private float interpolate(float a, float b, float blend)
	{
		double theta = blend * Math.PI;
		float f = (float) ((1f - (float) Math.cos(theta)) * 0.5);
		return a * (1f -f) +b * f;
		
	}
	
	/*
	 * method for generating smoothed noise
	 */
	
	private float getSmoothNoise(int x, int z)
	{
		float corners = (getNoise(x-1, z-1) + getNoise(x-1, z+1) + getNoise(x+1, z+1) + getNoise(x+1, z-1)) /16f; //for the four cornes of a 3x3 matrix the getNoise() method 
	 	                                                                                                          //is used to create a random height, then it is divided by 16 to make its
																											      //impact less
		float sides = (getNoise(x, z-1) + getNoise(x, z+1) + getNoise(x+1, z) + getNoise(x-1, z)) /8f;			  //same as above just for the four sides, divided by 8 bc more important
		float center = (getNoise(x, z))/4f;																		  //same as above but even more important as the middle vertex
		
		return corners + sides + center;
	}
	
	/*
	 * method that creates simple perlin noise
	 */
	
	private float getNoise(int x, int z)
	{
		random.setSeed(x * 1231 + z * 5343 + seed); //sets random seed for each launch
		return random.nextFloat() * 2f - 1f; //returns value for terrain height
	}

}

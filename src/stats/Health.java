package stats;

public class Health {
	
	/*
	 * function to regenerate health
	 */
	
	public float regenerateHealth(float health, float rate)
	{
		if(health < 100)
		{
			health+=rate;
			return health;
		}
		return 100;
	}
	
	/*
	 * function to decrease health
	 */
	
	public float decreaseHealth(float health, float amount)
	{
		if(health > 0 + amount)
		{
			health-=amount;
			return health;
		}
		else
		{
			return 100;
		}
		
	}
}

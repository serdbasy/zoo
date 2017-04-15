package program;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AnimalProperty
{
	private Eatable type;
	private AnimalLifeType lifeType;
	
	private int ageSpeed;
	private int maxAge = 5;
	
	private int moveSpeed; // 2v, 3v, 4v
	private double moveLostWeight; // 1, 1.5, 2
	
	private Map<Eatable, Double> feedingAnimals = new HashMap<Eatable, Double>();
	private Set<Eatable> avoidAnimals = new HashSet<Eatable>();
	

	public AnimalProperty(Eatable type, int maxAge, int moveSpeed, double moveLostWeight,
			Map<Eatable, Double> feedingAnimals) 
	{
		this.type = type;
		if(this.type instanceof DomesticAnimalType)
		{
			this.ageSpeed = 7;
			feedingAnimals.put(PlantType.BAIT, 0.7);
			this.lifeType = AnimalLifeType.DOMESTIC;
		}
		else if(this.type instanceof WildAnimalType)
		{
			this.ageSpeed = 6;
			this.lifeType = AnimalLifeType.WILD;
		}
		

		this.maxAge = maxAge;
		this.moveSpeed = moveSpeed;
		this.moveLostWeight = moveLostWeight;
		this.feedingAnimals = feedingAnimals;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AnimalProperty other = (AnimalProperty) obj;
		if (type != other.type)
			return false;
		return true;
	}

	
	public Eatable getType()
	{
		return type;
	}

	public int getAgeSpeed()
	{
		return ageSpeed;
	}

	public int getMaxAge()
	{
		return maxAge;
	}

	public int getMoveSpeed()
	{
		return moveSpeed;
	}

	public double getMoveLostWeight()
	{
		return moveLostWeight;
	}

	public Map<Eatable, Double> getFeedingAnimals()
	{
		return feedingAnimals;
	}
	

	public Set<Eatable> getAvoidAnimals()
	{
		return avoidAnimals;
	}

	public void setAvoidAnimal(Set<Eatable> avoidAnimals)
	{
		this.avoidAnimals = avoidAnimals;
	}
	
	public void addAvoidAnimal(Eatable eatable)
	{
		this.avoidAnimals.add(eatable);
	}
	

	public AnimalLifeType getLifeType()
	{
		return lifeType;
	}

	public void setLifeType(AnimalLifeType lifeType)
	{
		this.lifeType = lifeType;
	}

	@Override
	public String toString()
	{
		return "AnimalProperty [type=" + type + ", lifeType=" + lifeType + ", ageSpeed=" + ageSpeed + ", maxAge="
				+ maxAge + ", moveSpeed=" + moveSpeed + ", moveLostWeight=" + moveLostWeight + ", feedingAnimals="
				+ feedingAnimals + ", avoidAnimals=" + avoidAnimals + "]";
	}	
	
	
}

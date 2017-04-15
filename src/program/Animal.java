package program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Animal {
	private static final int DEFAULT_CATCH_DISTANCE = 5;
	private UUID id;
	private Eatable type;
	private Gender gender;
	private int age = 0;
	private double weight = 0;
	private Location location;
	private List<Location> locationTrack = new ArrayList<Location>();

	
	private Map<Animal, Double> targetAnimalList = new HashMap<Animal, Double>();
	
	// under attack
	private Map<Animal, Double> avoidAnimalList = new HashMap<Animal, Double>();


	public Animal(Eatable type, Gender gender, Location location)
	{
		this.id = UUID.randomUUID();
		this.type = type;
		this.gender = gender;
		setLocation(new Location(location.getX(), location.getY()), true);
		System.out.println("Birth: " + this);
	}


	public UUID getId()
	{
		return id;
	}

	public boolean willLive(int maxAge)
	{
		/*
		 * http://www.acikbilim.com/2014/02/dosyalar/yasama-ve-olume-istatistiksel-bir-bakis.html
		 */
		double percent = this.age / (double)maxAge;
		int multiplier = 0;

		if(percent <= 0.0166) // 0-2/120
		{
			multiplier = 100;
		}
		else if (percent <= 0.0416) // 3-5/120
		{
			multiplier = 10;
		}
		else if (percent <= 0.1) // 6-12/120
		{
			multiplier = 1;
		}
		else if (percent <= 0.1666) // 13-20/120
		{
			multiplier = 10;
		}
		else if (percent <= 0.3333) // 21-40/120
		{
			multiplier = 30;
		}
		else if (percent <= 0.4166) // 41-50/120
		{
			multiplier = 70;
		}
		else if (percent <= 0.5) // 51-60/120
		{
			multiplier = 100;
		}
		else if (percent <= 0.5833) // 61-70/120
		{
			multiplier = 500;
		}
		else if (percent <= 0.6666) // 71-80/120
		{
			multiplier = 800;
		}
		else if (percent <= 0.75) // 81-90/120
		{
			multiplier = 1000;
		}
		else if (percent <= 0.8333) // 91-100/120
		{
			multiplier = 4000;
		}
		else if (percent <= 0.9166) // 101-110/120
		{
			multiplier = 8000;	
		}
		else if (percent <= 0.9916) // 111-119/120
		{
			multiplier = 9500;
		}
		else // if (percent <= 1.0) // 120/120
		{
			multiplier = 10000;
		}
		multiplier = 10000 - multiplier;
		return (int)(Math.random() * multiplier) != 0;
	}

	public void aging(int cycle, int ageSpeed)
	{
		if(cycle % ageSpeed == 0)
		{
			this.age++;
		}
	}
	
	public boolean isMovable(double moveLostWeight)
	{
		return this.weight >= moveLostWeight;
	}

	
	
	public Eatable getType()
	{
		return type;
	}


	public int getAge()
	{
		return age;
	}


	public Location getLocation()
	{
		return location;
	}


	public void setLocation(Location location, boolean isInitialize)
	{
		this.location = location;
		setLocationTrack(this.location);
	}
	
	public void setLocation(Location location, double lostWeight)
	{
		gainWeight(-lostWeight);
		setLocation(location, false);
	}

	
	public List<Location> getLocationTrack()
	{
		return locationTrack;
	}


	public void setLocationTrack(Location location)
	{
		this.locationTrack.add(location);
	}


	public double getWeight()
	{
		return weight;
	}


	public void gainWeight(double weight)
	{
		this.weight += weight;
	}


	public Map<Animal, Double> getTargetAnimalList()
	{
		return targetAnimalList;
	}

	public void setTargetAnimalList(Map<Animal, Double> targetAnimalList)
	{
		this.targetAnimalList = targetAnimalList;
	}

	public void addTargetAnimalList(Animal targetAnimal, Double distance)
	{
		this.targetAnimalList.put(targetAnimal, distance);
	}
	
	public void setEmptyTargetAnimalList()
	{
		this.targetAnimalList = new HashMap<Animal, Double>();
	}


	public Map<Animal, Double> getAvoidAnimalList()
	{
		return avoidAnimalList;
	}


	public void setAvoidAnimalList(Map<Animal, Double> avoidAnimalList)
	{
		this.avoidAnimalList = avoidAnimalList;
	}


	public void addAvoidAnimalList(Animal avoidAnimal, Double distance)
	{
		this.avoidAnimalList.put(avoidAnimal, distance);
	}
	
	public void setEmptyAvoidAnimalList()
	{
		this.avoidAnimalList = new HashMap<Animal, Double>();
	}



	public boolean isCatchable(Animal target, AllAnimalPropertyList allAnimalPropertyList)
	{
		// toplamMesafe + KovalayanınAlacağıYol - KaçanınAlacağıYol
		return ((int)this.getLocation().getDistanceByLocation(target.getLocation())
				+ allAnimalPropertyList.getAnimalProperty(target.getType()).getMoveSpeed()
				- allAnimalPropertyList.getAnimalProperty(this.getType()).getMoveSpeed() <= DEFAULT_CATCH_DISTANCE
		);
	}


	@Override
	public String toString()
	{
		return "Animal [id=" + id + ", type=" + type + ", gender=" + gender + ", age=" + age
				+ ", weight=" + weight + ", location=" + location + "]";
	}
	
}

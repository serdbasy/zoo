package program;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Zoo
{
	
	private static final int DEFAULT_ZOO_WEIGHT = 50;
	private static final int DEFAULT_ZOO_HEIGHT = 50;
	private static final int DEFAULT_GRASS_AREA_RATIO = 40;
	private static final long DEFAULT_CYCLE_TIME = 1; // millisecond
	
	private int width;
	private int height;
	
	private List<Animal> animals = new ArrayList<>();
	private List<Animal> deadAnimals = new ArrayList<>();
	
	
	private Integer domesticAnimalCount;
	private Integer wildAnimalCount;
	
	
	// otluk/mera alanı
	private int grassAreaRatio;
	private List<Location> grassAreas = new ArrayList<>();
	
	AllAnimalPropertyList allAnimalPropertyList = new AllAnimalPropertyList();
	
	
	public Zoo(int width, int height, Integer domesticAnimalCount, Integer wildAnimalCount, int grassAreaRatio) 
	{
		this.width = width;
		this.height = height;
		this.domesticAnimalCount = domesticAnimalCount;
		this.wildAnimalCount = wildAnimalCount;
		this.grassAreaRatio = grassAreaRatio;
		
		setGrassArea();
		createAnimals();
		
		int cycleCounter = 0;
		Animal currentAnimal;
		AnimalProperty currentAnimalProperty;
		
		Set<Eatable> currentAvoidAnimalList;
		Animal currentAvoidAnimal;
		
		Map<Eatable, Double> currentFeedingAnimalList;		
		Animal currentTargetAnimal;
		Sorter<Animal, Double> animalSorter = new Sorter<Animal, Double>();
		
		while(this.animals.size() > 0)
		{
			cycleCounter++;
			try {
				// life cycle
				Thread.sleep(DEFAULT_CYCLE_TIME);
				
				
				for(int x = 0; x < this.animals.size(); x++)
				{
					currentAnimal = this.animals.get(x);
					currentAvoidAnimalList = allAnimalPropertyList.getAnimalProperty(currentAnimal.getType()).getAvoidAnimals();
					
					// en yakındaki düşmanlar belirleniyor
					if(currentAvoidAnimalList.size() > 0)
					{
						for(int i = 0; i < this.animals.size(); i++)
						{
							currentAvoidAnimal = this.animals.get(i);
							// avcı avını yakalayabilecek uzaklıkta mı
							if(!currentAnimal.getType().equals(currentAvoidAnimal.getType())
								&& currentAvoidAnimalList.contains(currentAvoidAnimal.getType())
								&& currentAvoidAnimal.isCatchable(currentAnimal, allAnimalPropertyList)
							)
							{
								currentAnimal.addAvoidAnimalList(currentAvoidAnimal,
										currentAnimal.getLocation().getDistanceByLocation(currentAvoidAnimal.getLocation()));
							}
						}
						
						if(currentAnimal.getAvoidAnimalList().size() > 1)
						{
							// yakınlığa göre sırala
							currentAnimal.setAvoidAnimalList(animalSorter.sortByValue(currentAnimal.getAvoidAnimalList()));
						}
					}
					
					// en yakındaki besin kaynağı
					currentFeedingAnimalList = allAnimalPropertyList.getAnimalProperty(currentAnimal.getType()).getFeedingAnimals();
					if(currentFeedingAnimalList.size() > 0)
					{
						for(int i = 0; i < this.animals.size(); i++)
						{
							currentTargetAnimal = this.animals.get(i);
							// avcı avını yakalayabilecek uzaklıkta mı
							if(!currentAnimal.getType().equals(currentTargetAnimal.getType())
								&& currentFeedingAnimalList.containsKey(currentTargetAnimal.getType())
								&& currentAnimal.isCatchable(currentTargetAnimal, allAnimalPropertyList)
							)
							{
								currentAnimal.addTargetAnimalList(currentTargetAnimal,
										currentAnimal.getLocation().getDistanceByLocation(currentTargetAnimal.getLocation()));
							}
						}
					}
				}
			
				boolean isLife;
				for(int i = 0; i < this.animals.size(); i++)
				{
					isLife = true;
					currentAnimal = this.animals.get(i);
					currentAnimalProperty = allAnimalPropertyList.getAnimalProperty(currentAnimal.getType());
					
					// domestic animals
					if(currentAnimalProperty.getLifeType().equals(AnimalLifeType.DOMESTIC))
					{
						currentAnimal.gainWeight(0.7);
					}
					
					// Vole: eats grass
					if(currentAnimal.getType().equals(WildAnimalType.VOLE) && this.grassAreas.contains(currentAnimal.getLocation()))
					{
						this.grassAreas.remove(currentAnimal.getLocation());
						currentAnimal.gainWeight(0.5);
					}
					
					// bu hayvanı yiyebilecek hayvan var mı?
					if(currentAnimal.getAvoidAnimalList().size() > 0)
					{
						Iterator<Entry<Animal, Double>> iterator = currentAnimal.getAvoidAnimalList().entrySet().iterator();
						Entry<Animal, Double> iteratorAnimal;
						while(iterator.hasNext())
						{
							iteratorAnimal = iterator.next();
							if(this.animals.contains(iteratorAnimal.getKey()))
							{
								iteratorAnimal.getKey().setLocation(
										currentAnimal.getLocation(),
										allAnimalPropertyList.getAnimalProperty(iteratorAnimal.getKey().getType()).getMoveLostWeight()); // yakalandı
								
								animalHunted(iteratorAnimal.getKey(), currentAnimal, iteratorAnimal.getValue()); // en yakın mesafedeki tarafından yenildi
								isLife = false;
								break;
							}
						}
					}
					else
					{
						// hayvan tehlikede olmadığı için varsa avlayabileceği bir hayvan anlanacak
						if(currentAnimal.getTargetAnimalList().size() > 0)
						{
							Iterator<Entry<Animal, Double>> iterator = currentAnimal.getTargetAnimalList().entrySet().iterator();
							Entry<Animal, Double> iteratorAnimal;
							while(iterator.hasNext())
							{
								iteratorAnimal = iterator.next();
								if(this.animals.contains(iteratorAnimal.getKey()))
								{
									currentAnimal.setLocation(
											iteratorAnimal.getKey().getLocation(),
											allAnimalPropertyList.getAnimalProperty(currentAnimal.getType()).getMoveLostWeight()); // yakaladı
									animalHunted(currentAnimal, iteratorAnimal.getKey(), iteratorAnimal.getValue()); // en yakındakini yedi
									break;
								}
							}
						}
					}
					
					if(isLife)
					{
						if(currentAnimal.isMovable(currentAnimalProperty.getMoveLostWeight()))
						{
							Location nextLocation = setProbableLocation(
									currentAnimal.getLocation(),
									allAnimalPropertyList.getAnimalProperty(currentAnimal.getType()).getMoveSpeed());
							
							if(nextLocation != null)
							{
								currentAnimal.setLocation(
										nextLocation,
										allAnimalPropertyList.getAnimalProperty(currentAnimal.getType()).getMoveLostWeight());
							}
						}

						currentAnimal.aging(cycleCounter, currentAnimalProperty.getAgeSpeed());
						if(!currentAnimal.willLive(currentAnimalProperty.getMaxAge()))
						{
							killAnimal(currentAnimal);
							System.out.println("Dead: " + currentAnimal);
						}
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			cycleCounter = cycleCounter % 42;
		}
	}
	
	public Zoo(int width, int height, int animalCount, int grassAreaRatio) 
	{
		this(width, height, (animalCount / 2), (animalCount - animalCount / 2), grassAreaRatio );		
	}
	
	public Zoo(int width, int height, int animalCount) 
	{
		this(width, height, animalCount, DEFAULT_GRASS_AREA_RATIO );		
	}
	
	public Zoo(int width, int height) 
	{
		this(width, height, 20 );		
	}
	
	public Zoo() 
	{
		this(DEFAULT_ZOO_WEIGHT, DEFAULT_ZOO_HEIGHT);		
	}


	/*
	 * hayvanat bahçesinin ne kadarı besin kaynağı ot ile doldurulacağı
	 * işlemleri burada yapılıyor
	 */
	private void setGrassArea()
	{
		Location coordinate;
		int counter = 0;
		int grassAreaCount = 0;
		
		if(grassAreaRatio <= 50)
		{
			grassAreaCount = width * height * grassAreaRatio / 100;
			
			while (counter < grassAreaCount)
			{
				coordinate = new Location(this.width, this.height);
				if(!this.grassAreas.contains(coordinate))
				{
					this.grassAreas.add(coordinate);
					counter++;
				}
			}
		}
		else
		{
			// tüm alan otla kaplanacak önce
			for(int x = 0; x < width; x++)
			{
				for(int y = 0; y < height; y++)
				{
					grassAreas.add(new Location(x, y));
				}
			}
			
			if(grassAreaRatio != 100){
				grassAreaCount = width * height * (100 - grassAreaRatio) / 100;

				while (counter < grassAreaCount)
				{
					coordinate = new Location(this.width, this.height);
					if(this.grassAreas.contains(coordinate))
					{
						this.grassAreas.remove(coordinate);
						counter++;
					}
				}
			}
		}
	}

	private void createAnimals()
	{
		List<Gender> genders = new ArrayList<>();
		genders.add(Gender.FEMALE);
		genders.add(Gender.MALE);
		int genderSize = genders.size();
		int randomGenderIndex;
		
		if(this.domesticAnimalCount > 0)
		{
			Object domesticAnimals = DomesticAnimalType.values();
			int domesticAnimalSize = Array.getLength(domesticAnimals);
			int randomDomesticAnimalIndex;
			DomesticAnimalType domesticAnimalType;
			for(int i = 0; i < this.domesticAnimalCount; i++)
			{
				randomGenderIndex = (int)(Math.random() * genderSize);
				randomDomesticAnimalIndex = (int)(Math.random() * domesticAnimalSize);
				domesticAnimalType = (DomesticAnimalType) Array.get(domesticAnimals, randomDomesticAnimalIndex);
				this.animals.add(
					new Animal(
						domesticAnimalType,
						genders.get(randomGenderIndex),
						new Location(this.width,  this.height)
					)
				);
			}
			
		}
		
		if(this.wildAnimalCount > 0)
		{
			Object wildAnimals = WildAnimalType.values();
			int wildAnimalSize = Array.getLength(wildAnimals);
			int randomWildAnimalIndex;
			WildAnimalType wildAnimalType;
			
			for(int i = 0; i < this.wildAnimalCount; i++)
			{
				randomGenderIndex = (int)(Math.random() * genderSize);
				randomWildAnimalIndex = (int)(Math.random() * wildAnimalSize);
				wildAnimalType = (WildAnimalType) Array.get(wildAnimals, randomWildAnimalIndex);
				
				randomWildAnimalIndex = (int)(Math.random() * wildAnimalSize);
				randomGenderIndex = (int)(Math.random() * genderSize);
				this.animals.add(
					new Animal(
						wildAnimalType,
						genders.get(randomGenderIndex),
						new Location(this.width,  this.height)
					)
				);
			}
		}
	}

	private void animalHunted(Animal hunter, Animal hunted, Double weight)
	{
		hunted.setEmptyAvoidAnimalList();
		hunted.setEmptyTargetAnimalList();
		
		hunter.setEmptyAvoidAnimalList();
		hunter.setEmptyTargetAnimalList();
		hunter.gainWeight(weight);
		killAnimal(hunted);
		System.out.println("Hunted: (Hunter:" + hunter.getId() + " ) "  + hunted);
		
		
	}
	
	private void killAnimal(Animal animal)
	{
		this.animals.remove(animal);
		this.deadAnimals.add(animal);
	}

	private Location setProbableLocation(Location currentLocation, double distance)
	{
		List<Location> locations = new ArrayList<Location>();
		int x, y, newX, newY;
		for (int i = 0; i <= 360; i+=30) {
		    x = (int)(100 * Math.cos(distance * Math.PI / 180F));
		    y = (int)(100 * Math.sin(distance * Math.PI / 180F));
		    newX = currentLocation.getX();
		    newY = currentLocation.getY();
		    newX += x;
		    newY += y;
		    
		    if(newX > 0 && newX <= this.width && newY > 0 && newY <= this.height)
		    {
		    	locations.add(new Location(newX, newY));
		    }
		}
		if(locations.size() > 0)
		{
			int random = (int)(Math.random() * locations.size());
			return locations.get(random);
		}
		return null;
	}
}

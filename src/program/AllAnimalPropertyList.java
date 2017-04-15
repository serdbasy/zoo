package program;

import java.util.HashMap;
import java.util.Map;

public class AllAnimalPropertyList
{
	private Map<Eatable, AnimalProperty> configs = new HashMap<Eatable, AnimalProperty>();

	public AllAnimalPropertyList()
	{
		//	SHEEP
		Map<Eatable, Double> sheepFeedingList = new HashMap<Eatable, Double>();
		configs.put(
			DomesticAnimalType.SHEEP,
			new AnimalProperty(DomesticAnimalType.SHEEP, 350, 2, 1, sheepFeedingList)
		);
		
		//	DOG
		Map<Eatable, Double> dogFeedingList = new HashMap<Eatable, Double>();
		configs.put(
			DomesticAnimalType.DOG,
			new AnimalProperty(DomesticAnimalType.DOG, 350, 3, 1.5, dogFeedingList)
		);
		
		//	CAT
		Map<Eatable, Double> catFeedingList = new HashMap<Eatable, Double>();
		catFeedingList.put(DomesticAnimalType.FANCY_MOUSE, 1.0);
		catFeedingList.put(WildAnimalType.VOLE, 1.0);
		configs.put(
			DomesticAnimalType.CAT,
			new AnimalProperty(DomesticAnimalType.CAT, 350, 3, 1.5, catFeedingList)
		);
		
		//	CHICKEN
		Map<Eatable, Double> chickenFeedingList = new HashMap<Eatable, Double>();
		configs.put(
			DomesticAnimalType.CHICKEN,
			new AnimalProperty(DomesticAnimalType.CHICKEN, 400, 2, 1, chickenFeedingList)
		);
		
		//	FANCY_MOUSE
		Map<Eatable, Double> fancyMouseFeedingList = new HashMap<Eatable, Double>();
		configs.put(
			DomesticAnimalType.FANCY_MOUSE,
			new AnimalProperty(DomesticAnimalType.FANCY_MOUSE, 400, 3, 1.5, fancyMouseFeedingList)
		);
		
		//	VOLE
		Map<Eatable, Double> voleFeedingList = new HashMap<Eatable, Double>();
		voleFeedingList.put(PlantType.GRASS, 0.5);
		configs.put(
			WildAnimalType.VOLE,
			new AnimalProperty(WildAnimalType.VOLE, 400, 3, 1.5, voleFeedingList)
		);
		
		//	FOX
		Map<Eatable, Double> foxFeedingList = new HashMap<Eatable, Double>();
		foxFeedingList.put(DomesticAnimalType.CHICKEN, 1.0);
		foxFeedingList.put(DomesticAnimalType.FANCY_MOUSE, 1.0);
		foxFeedingList.put(WildAnimalType.VOLE, 1.0);
		configs.put(
			WildAnimalType.FOX,
			new AnimalProperty(WildAnimalType.FOX, 490, 3, 1.5, foxFeedingList)
		);
		
		//	CROCODILE
		Map<Eatable, Double> crocodileFeedingList = new HashMap<Eatable, Double>();
		crocodileFeedingList.put(WildAnimalType.VOLE, 2.0);
		crocodileFeedingList.put(DomesticAnimalType.CHICKEN, 2.0);
		crocodileFeedingList.put(DomesticAnimalType.FANCY_MOUSE, 2.0);
		crocodileFeedingList.put(DomesticAnimalType.SHEEP, 2.0);
		configs.put(
			WildAnimalType.CROCODILE,
			new AnimalProperty(WildAnimalType.CROCODILE, 490, 4, 2, crocodileFeedingList)
		);
		
		setAvoidAnimal(this.configs);
		
	}

	public Map<Eatable, AnimalProperty> getConfigs()
	{
		return configs;
	}

	public void setConfigs(Map<Eatable, AnimalProperty> configs)
	{
		this.configs = configs;
	}

	public AnimalProperty getAnimalProperty(Eatable eatable)
	{
		return configs.get(eatable);
	}
	
	private void setAvoidAnimal(Map<Eatable, AnimalProperty> configs)
	{
		for(Map.Entry<Eatable, AnimalProperty> config : configs.entrySet())
		{
			for(Map.Entry<Eatable, AnimalProperty> subConfig : configs.entrySet())
			{
				if(!config.getKey().equals(subConfig.getKey()) && 
					subConfig.getValue().getFeedingAnimals().containsKey(config.getValue().getType()))
				{
					config.getValue().addAvoidAnimal(subConfig.getKey());
				}
			}	
		}
	}

	@Override
	public String toString()
	{
		return "AllAnimalPropertyList [configs=" + configs + "]";
	}

}

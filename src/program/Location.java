package program;

public class Location
{
	private int x;
	private int y;
	
	public Location(int width, int height, boolean isRandom)
	{
		if(isRandom)
		{
			this.x = (int)(Math.random() * width);
			this.y = (int)(Math.random() * height);	
		}
		else
		{
			this.x = width;
			this.y = height;
		}
	}
	
	public Location(int width, int height)
	{
		this(width, height, true);
	}
	

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
		Location other = (Location) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}


	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}
	
	public Location getCloserDistance(Location firstLocation, Location secondLocation)
	{
		if(firstLocation == null)
		{
			return secondLocation;
		}
		else if(secondLocation == null)
		{
			return firstLocation;
		}
		else
		{
			if(getDistanceByLocation(firstLocation) > getDistanceByLocation(secondLocation))
			{
				return secondLocation;
			}
			else
			{
				return firstLocation;
			}
		}
	}
	
	public double getDistanceByLocation(Location target)
	{
		if(target != null)
		{
			return Math.sqrt((Math.pow((this.x - target.x), 2) + Math.pow((this.y - target.y), 2)));
		}
		else
		{
			return 0;
		}
	}

	@Override
	public String toString()
	{
		return "Location [x=" + x + ", y=" + y + "]";
	}
}

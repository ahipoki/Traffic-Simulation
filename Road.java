import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Road extends JPanel
{
	final int LANE_HEIGHT = 120;
	//The lane's height is 120 units
	final int ROAD_WIDTH = 800;
	//The road's width is 800 units
	ArrayList<Vehicle> cars = new ArrayList<Vehicle>();
	int carCount = 0;
	
	public Road()
	{//Road method
		super();
	}
	
	public void addCar(Vehicle v)
	{//Method to add cars to the road
		cars.add(v);
		//Add car "v" to the road
	}
	
	public void paintComponent(Graphics g)
	{//Method to paint the road
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		//Set the color of the road to black
		g.fillRect(0,0,getWidth(),getHeight());
		//Fill in the road
		g.setColor(Color.WHITE);
		//Set the current color to white
		for (int a = LANE_HEIGHT; a < LANE_HEIGHT*4; a=a+LANE_HEIGHT)
		{//Which lane
			for (int b = 0; b < getWidth(); b=b+40)
			{//Which line
				g.fillRect(b, a, 30, 5);
				//Fill in the lines
			}
		}
		for (int a = 0; a < cars.size(); a++)
		{//Draw cars
			cars.get(a).paintMe(g);
		}
	}
	
	public void step()
	{//Method to take a step
		for (int a = 0; a < cars.size(); a++)
		{//For the car's length
			Vehicle v = cars.get(a);
			//Which car
			if (collision(v.getX()+v.getSpeed(), v.getY(), v) == false)
			{//If there's no collision
				v.setX(v.getX() + v.getSpeed());
				//Set the car's X value to the X value plus the speed
				if (v.getX() > ROAD_WIDTH)
				{//If the X value is bigger than the road's width
					if (collision(0, v.getY(), v) == false)
					{//If there's no collision
						v.setX(0);
						//Set X to 0
						carCount++;
					}
				}
			}
			else
			{//If there's a car ahead
				if ((v.getY() > 40) && (collision(v.getX(), v.getY()-LANE_HEIGHT, v) == false))
				{//If there is a lane to your right open
						v.setY(v.getY()-LANE_HEIGHT);
						//Move to your right
				}
				else if ((v.getY() < 40 + 3 * LANE_HEIGHT) && (collision(v.getX(), v.getY()+LANE_HEIGHT, v) == false))
				{//If you can't go right and your left is open
					v.setY(v.getY()+LANE_HEIGHT);
					//Move to your left
				}
			}
		}
	}
	
	public boolean collision(int x, int y, Vehicle v)
	{//Method for collisions
		for (int a = 0; a < cars.size(); a++)
		{//For the car's size
			Vehicle u = cars.get(a);
			//Which car is it
			if (y == u.getY())
			{//If I'm in the same lane
				if (u.equals(v) == false)
				{//If I'm not checking myself
					if (x < u.getX() + u.getWidth() && //My left side is left of his right side
							x + v.getWidth() > u.getX()) {//My right side is right of his left side
						return true;
						//There was a collision
					}
				}
			}
		}
		return false;
		//There wasn't a collision
	}
  
	public int getCarCount() {
		return carCount;
	}
  
	public void resetCarCount() {
		carCount = 0;
	}
}

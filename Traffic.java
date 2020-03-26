import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Traffic implements ActionListener, Runnable
{
	JFrame frame = new JFrame("Traffic Simulation");
	//Screen for the simulation
	Road road = new Road();
	//The road for the cars
	//South container
	JButton start = new JButton("Start");
	JButton stop = new JButton("Stop");
	JLabel throughput = new JLabel("Throughput: 0");
	//JLabel to track througput
	Container south = new Container();
	//West container
	JButton semi = new JButton("Add Semi");
	JButton suv = new JButton("Add SUV");
	JButton sports = new JButton("Add Sports");
	Container west = new Container();
	boolean running = false;
	//Is the simulation running?
	int carCount = 0;
	//Number of cars on the road
	long startTime = 0;
	//Start time
	
	public Traffic()
	{//Method for the traffic
		frame.setSize(1000, 550);
		//Set the frame size
		frame.setLayout(new BorderLayout());
		//Set the layout to the fram
		frame.add(road, BorderLayout.CENTER);
		//Add the road
		
		south.setLayout(new GridLayout(1,2));
		//Set up the south container
		south.add(start);
		//Add a button to start the simulation
		start.addActionListener(this);
		//Add an action listener to the button
		south.add(stop);
		//Add a button to stop the simulation
		stop.addActionListener(this);
		//Add an action listener to the button
		frame.add(south, BorderLayout.SOUTH);
		//Add the south container to the layout
		
		west.setLayout(new GridLayout(3,1));
		//Set up the west container
		west.add(semi);
		//Add a button to add semis
		semi.addActionListener(this);
		//Add an action listener to the button
		west.add(suv);
		//Add a button to add SUV's
		suv.addActionListener(this);
		//Add an action listener to the button
		west.add(sports);
		//Add a button to add sports cars
		sports.addActionListener(this);
		//Add an action listener to the button
		frame.add(west, BorderLayout.WEST);
		//Add the west container to the layout
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		frame.repaint();
		//Repaint the frame
	}
	
	public static void main(String[] args) 
	{//Main method
		new Traffic();
	}

	@Override
	public void actionPerformed(ActionEvent event)
	{//Action performed method
		if (event.getSource().equals(start))
		{//If the start button was pressed
			if (running == false)
			{//If the simulation is not running
				running = true;
				//Start the simulation
				road.resetCarCount();
				startTime = System.currentTimeMillis();
				Thread t = new Thread(this);
				t.start();
			}
		}
		if (event.getSource().equals(stop))
		{//If the stop button was pressed
			running = false;
			//Stop the simulation
		}
		if (event.getSource().equals(semi))
		{//If the button to add a semi was pressed
			Semi semi = new Semi(0, 30);
			//Create a new semi
			road.addCar(semi);
			//Add the semi to the road
			for (int x = 0; x < road.ROAD_WIDTH; x = x+20)
			{//For the car's X position
				for (int y = 40; y < 480; y = y+120)
				{//For the car's Y position
					semi.setX(x);
					//Place the car at its X position
					semi.setY(y);
					//Place the car at its Y position
					if (road.collision(x, y, semi) == false)
					{//If there will not be a collision
						frame.repaint();
						//Repaint the frame
						return;
						//Return
					}
				}
			}
		}
		if (event.getSource().equals(suv))
		{//If the button to add an SUV was pressed
			SUV suv = new SUV(0, 30);
			//Create a new SUV
			road.addCar(suv);
			//Add the SUV to the road
			for (int x = 0; x < road.ROAD_WIDTH; x = x+20)
			{//For the car's X position
				for (int y = 40; y < 480; y = y+120)
				{//For the car's Y position
					suv.setX(x);
					//Place the car at its X position
					suv.setY(y);
					//Place the car at its Y position
					if (road.collision(x, y, suv) == false)
					{//If there will not be a collision
						frame.repaint();
						//Repaint the frame
						return;
						//Return
					}
				}
			}
		}
		if (event.getSource().equals(sports))
		{//If the button to add a sports was pressed
			Sports sports = new Sports(0, 30);
			//Create a new sports
			road.addCar(sports);
			//Add the sports to the road
			for (int x = 0; x < road.ROAD_WIDTH; x = x+20)
			{//For the car's X position
				for (int y = 40; y < 480; y = y+120)
				{//For the car's Y position
					sports.setX(x);
					//Place the car at its X position
					sports.setY(y);
					//Place the car at its Y position
					if (road.collision(x, y, sports) == false)
					{//If there will not be a collision
						frame.repaint();
						//Repaint the frame
						return;
						//Return
					}
				}
			}
		}
	}

	@Override
	public void run()
	{//Run method
		while (running == true)
		{//While the simulation is running
			road.step();
			//Take a step
			carCount = road.getCarCount();
			double throughput = carCount / (double)(System.currentTimeMillis() - startTime);
			frame.repaint();
			//Repaint the frame
			try {
				Thread.sleep(100);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}

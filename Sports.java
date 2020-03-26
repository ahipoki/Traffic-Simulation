import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class Sports extends Vehicle
{
  Image myimage;
	
	public Sports(int newx, int newy)
	{
	  super(newx, newy);
		width = 100;
		//Width of the car
		height = 20;
		//Height of the car
		speed = 12;
		//Speed of the car
		try {
		  myimage = ImageIO.read(new File("sports.png"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void paintMe(Graphics g)
	{
		g.drawImage(myimage, x, y, null);
		//Draw the car's image
	}
}

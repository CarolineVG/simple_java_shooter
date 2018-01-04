import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Spell extends GameObject {
	
	private Handler handler; 

	public Spell(int x, int y, ID id, Handler handler, int mx, int my) {
		super(x, y, id);
		this.handler = handler; 
		
		velX = (mx - x) / 100; //100 = speed of the spell
		velY = (my - y) / 100; 
		
	}

	@Override
	public void tick() {
		
		x += velX;
		y += velY;
		
		// don't shoot through walls
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i); 
			
			if (tempObject.getId() == ID.Block){
				if(getBounds().intersects(tempObject.getBounds())){
					handler.removeObject(this); // remove spell if hit wall
				}
			}
			
		}
	}

	@Override
	public void render(Graphics g) {
		
		g.setColor(Color.green);
		g.fillOval(x, y, 8, 8);
		
	}

	@Override
	public Rectangle getBounds() {
		
		return new Rectangle(x,y,8,8);
	}
	
}

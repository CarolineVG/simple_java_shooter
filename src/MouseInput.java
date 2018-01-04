import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter{
	
	private Handler handler; 
	private Camera camera; 
	
	public MouseInput(Handler handler, Camera camera){
		this.handler = handler; 
		this.camera = camera; 
	}
	
	public void mousePressed(MouseEvent e){
		// when click: create spell / bullet
		// x and y coordinates of mouse when clicked
		int mx = (int) (e.getX() + camera.getX());
		int my = (int) (e.getY() + camera.getY()); 
		
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i); 
			
			// if object is player
			if (tempObject.getId() == ID.Player) {
				// make new spell: mx my -> where does the spell want to go
				handler.addObject(new Spell(tempObject.getX()+16, tempObject.getY()+24, ID.Spell, handler, mx, my));
			}
		}
	}
}

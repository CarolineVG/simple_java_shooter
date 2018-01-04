
import java.awt.Graphics;
import java.util.LinkedList;

// create class that run through loop and updates all game objects
public class Handler {
        
    // LinkedList is array of objects
    LinkedList<GameObject> object = new LinkedList<GameObject>(); 
    
    private boolean up = false, down = false, right = false, left = false; 
    
    public void tick(){
        // run through all game objects
        for (int i = 0; i < object.size(); i++) {
            // stores object in temporary object
            GameObject tempObject = object.get(i);
            
            // tick every object in list 
            tempObject.tick();
        }
    }
    
    public void render(Graphics g){
        // run through all game objects
        for (int i = 0; i < object.size(); i++) {
            // stores object in temporary object
            GameObject tempObject = object.get(i);
            
            // renders every object in list 
            tempObject.render(g);
        }
    }
    
    public void addObject(GameObject tempObject){
        object.add(tempObject); 
    }
    
    public void removeObject(GameObject tempObject){
        object.remove(tempObject); 
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }
}
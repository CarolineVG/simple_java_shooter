
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;


public class Game extends Canvas implements Runnable {
    
    private static final long serialVersionUID = 1L; 
    
    // vars
    private boolean isRunning = false;
    private Thread thread;
    private Handler handler; // from custom class handler
    private Camera camera; 
    
    private BufferedImage level = null; 
    
    // constructor
    public Game(){
        new Window(1000, 563, "Wizard Game", this); /* width, height, title, game */
        start(); 
        
        handler = new Handler();
        camera = new Camera(0,0); 
        
        // key listener
        this.addKeyListener(new KeyInput(handler)); // add keylistener
        
        // mouse listener
        this.addMouseListener(new MouseInput(handler, camera));
                
        /* generate background */
        BufferedImageLoader loader = new BufferedImageLoader(); 
        level = loader.loadImage("/world.png");

        loadLevel(level);
    }
    
    // start thread
    private void start(){
        isRunning = true;
        thread = new Thread(this); // call run method, in this class
        thread.start();
    }
    
    // stop thread
    private void stop(){
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // required bc of implements Runnable
    public void run() {
        // game loop
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0; // updates window 60 times/sec
        double ns = 100000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        
        while(isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                tick();
                //updates++;
                delta--;
            }
            render();
            frames++;
            
            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
                //updates = 0;
            }
            
        }
        stop();
    }
    
    // update everything in game (60x/sec)
    public void tick(){
    	
    	// which gameobject is player
    	for(int i = 0; i < handler.object.size(); i++) {
    		if(handler.object.get(i).getId() == ID.Player){
    			camera.tick(handler.object.get(i));
    		}
    	}
    	
        handler.tick(); 
        
    }
    
    // render everything in game (updates 1000x/sec)
    public void render(){
        // first created: bs is null
        BufferStrategy bs = this.getBufferStrategy(); //
        // if bs = null, create bs with 3 args
        if (bs == null) {
            // jframe constructs BufferStrategy, preloading frames behind the window
            // 3 frames already loading before it shows
            this.createBufferStrategy(3); 
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        // convert graphics g to 2d
        Graphics2D g2d = (Graphics2D)g; 
        
        
        // bg
        g.setColor(Color.red);
        g.fillRect(0, 0, 1000, 563);
        
        // code between this lines is translated to 2d camera 
        /////////////////////////////////
        
        g2d.translate(-camera.getX(), -camera.getY());
 
        
        /* under bg! */
        handler.render(g);

        g2d.translate(camera.getX(), camera.getY());
                
        ////////////////////////////////
        
        g.dispose();
        bs.show(); 
                
    }
    
    // loading the level
    private void loadLevel(BufferedImage image){
        int w = image.getWidth();
        int h = image.getHeight(); 
        
        for (int xx = 0; xx < w; xx++) {
            for (int yy = 0; yy < h; yy++) {
            	// bitcodes
                int pixel = image.getRGB(xx, yy); 
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;
                
                // what are values?
                if (red==255) {
                    handler.addObject(new Block(xx*32, yy*32, ID.Block));  // 32px each block                   
                }
                
                 if (blue==255) {
                    handler.addObject(new Wizard(xx*32, yy*32, ID.Player, handler));                    
                }
            }
        }
    }
    
    
    
    public static void main(String args[]){
        new Game();
    }
}

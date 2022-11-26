package projetofinal;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import projetofinal.ProjetoFinal.MyPanel;



public class MenuPanel extends JPanel implements MouseListener, ActionListener,Runnable, MouseMotionListener  {

	BufferedImage image;
	BufferedImage texture;
	
	float angleRoatation = 0f;
	AffineTransform at = new AffineTransform();
	
	AffineTransform at1 = new AffineTransform();

	int firstX = 0;
	int firstY = 0;
	int deltaX = 0;
	int deltaY = 0;
	
	boolean selected = false;
	boolean collision = false;
	
	Shape rectInt1  = new Rectangle2D.Double(400,20,60,60);
	Shape rectInt2 = new Rectangle2D.Double(400,140,60,60);
	
	//TODO: Alterar cor 
	
	int[] rules = {AlphaComposite.CLEAR, AlphaComposite.SRC_OVER,
		    AlphaComposite.DST_OVER, AlphaComposite.SRC_IN,
		    AlphaComposite.DST_IN, AlphaComposite.SRC_OUT,
		    AlphaComposite.DST_OUT, AlphaComposite.SRC,
		    AlphaComposite.DST, AlphaComposite.SRC_ATOP,
		    AlphaComposite.DST_ATOP, AlphaComposite.XOR};
		  int ruleIndex = 0;
	
	public MenuPanel() {
		setBackground(Color.DARK_GRAY);
		
		Thread thread = new Thread(this);
		thread.start();
		
		URL url = getClass().getClassLoader().getResource("images/logo.jpg");
        try {
			image = ImageIO.read(url);
		} catch(IOException ex) {
			ex.printStackTrace();
		}
        URL url1 = getClass().getClassLoader().getResource("images/texture.jpg");
        try {
			texture = ImageIO.read(url1);
		} catch(IOException ex) {
			ex.printStackTrace();
		}
        
        addMouseListener(this);
		addMouseMotionListener(this);

		
	}
	
	Shape s2 = null;
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		 // -------------  Put Image
		
		//g2.drawImage(image, 281, 281,219,219, null);
		
		g2.setColor(Color.white);
		
		// -------  Make buttons 
		s2 = new Rectangle2D.Double(170,100, 160, 60);
		
		g2.draw(s2);
		
		Font font1 = new Font("Arial", Font.PLAIN, 21);
	    g2.setFont(font1);
	    g2.setColor(Color.WHITE);
	    g2.drawString("Make", 225, 125 );
		g2.drawString("Graphic", 213, 150 );
		GradientPaint gp1 = new GradientPaint (120, 300,Color.GREEN, 120, 500, Color.WHITE, true);
		g2.setPaint(gp1);
		
		//  Interação entre shapes
		
//        g2.setColor(Color.BLUE);
//        g2.fill(rectInt1);
        
      //  rectInt2 = new Rectangle2D.Double(400,140,60,60);
//        g2.setColor(Color.YELLOW);
//        g2.fill(rectInt2);
		
		if (collision)
			g2.setColor(Color.RED);
		else
			g2.setColor(Color.BLUE);
		g2.fill(rectInt2);
		g2.draw(rectInt2.getBounds());

		if (collision)
			g2.setColor(Color.RED);
		else
			g2.setColor(Color.GREEN);
		g2.fill(rectInt1);
		g2.draw(rectInt1.getBounds());
	
		
		
		// ******* Colors *********
	    
	    Color castanho = new Color(52,38,7);
	    Color roxo = new Color(73,17,115);
	    Color anil = new Color(38,41,160);
	    Color azul = new Color(14,114,249);
	    Color verde = new Color(46,187,68);
	    Color laranja = new Color(255,150,0);
	    
	    // *************************
	    
       // Down Animation graphics
	    
		//g2.setColor(Color.BLACK);
	    //g2.fillRect(0,310,50,190);
	    //g2.setColor(castanho);
	    //g2.fillRect(50,380,50,120);
	    g2.setColor(roxo);
	    //Random rand = new Random();
	    //
	    Random rand = new Random();
	    double min = 200;
	    double max =300;
	    Rectangle2D rect2d = new Rectangle2D.Double(0,300 + rand.nextDouble(150), 40, 450);
        g2.setColor(roxo);
        g2.fill(rect2d);
        Rectangle2D rect2d1 = new Rectangle2D.Double(40,300 + rand.nextDouble(150), 40, 450);
        g2.setColor(azul);
        g2.fill(rect2d1);
        
        // ----------- Gradient Paint
        
        Rectangle2D rect2d2 = new Rectangle2D.Double(80,300 + rand.nextDouble(150), 40, 450);
        g2.setPaint(gp1);
        g2.fill(rect2d2);
        
        
        Rectangle2D rect2d3 = new Rectangle2D.Double(120,300 + rand.nextDouble(150), 40, 450);
        g2.setPaint(Color.YELLOW);
        g2.fill(rect2d3);
        
        
        
        Rectangle2D rect2d5 = new Rectangle2D.Double(200,300 + rand.nextDouble(150), 40, 450);
        g2.setColor(Color.RED);
        g2.fill(rect2d5);
        
        // ---------------- Texture Paint
        
        Rectangle2D rect2d6 =  new Rectangle2D.Double(240,300 + rand.nextDouble(150), 40, 450);
	    TexturePaint tp1 = new TexturePaint(texture, new Rectangle2D.Double(240,300 + rand.nextDouble(150), 40, 450));
		g2.setPaint(tp1);
		g2.fill(rect2d6);
	    
	    // ------------ Stroke -> Primitive Line
	    
		g2.setColor(Color.WHITE);
	    Stroke stroke = new BasicStroke(5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND);
	    g2.setStroke(stroke);
	    g2.drawLine(0, 499, 500,499);
	    g2.drawLine(280, 499, 280,280);
	    g2.drawLine(279, 281, 500,281);	    
	    
	    // ------------ Animation -> General Path
	    
	    GeneralPath path2 = new GeneralPath(GeneralPath.WIND_NON_ZERO);
        path2.moveTo(70,70); 
        path2.quadTo(85,20,100,70); 
        path2.quadTo(150,85,100,100);
        path2.quadTo(85,150,70,100);
        path2.quadTo(20,85,70,70);
        path2.closePath();
        
        AffineTransform at1 = new AffineTransform();
        at.rotate(Math.toRadians(-angleRoatation), 85, 85);
        Shape s1 = at.createTransformedShape(path2); 
        
        g2.setColor(Color.WHITE);
        g2.fill(s1);
	   
		GeneralPath path = new GeneralPath(GeneralPath.WIND_NON_ZERO);
		path.moveTo(70,70); 
        path.quadTo(100,70,85,20); 
        path.quadTo(100,100,150,85);
        path.quadTo(70,100,85,150);
        path.quadTo(70,70,20,85);
        path.closePath();
        
		AffineTransform at = new AffineTransform();
        at.rotate(Math.toRadians(-angleRoatation), 85, 85);
        Shape s = at.createTransformedShape(path); 
        
        GradientPaint gp2 = new GradientPaint (85, 85,Color.BLUE, 90, 100, anil, true);
        g2.setPaint(gp2);
        g2.fill(s);
        
        
        // ---------- Text

        Stroke stroke2 = new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke2);
        g2.setColor(Color.white);
	    Font font3 = new Font("Serif", Font.BOLD, 40);
	    g2.setFont(font3);
	    g2.drawString("Graphics 2D", 148,50);
        
	    // ----------  drag-Line
	    
	    g2.drawLine(430, 90, 430, 130);
	    g2.drawLine(430, 130, 441, 122);
	    g2.drawLine(430, 130, 419, 122);
	    g2.setFont(new Font("arial", Font.PLAIN, 14));
	    g2.drawString("Drag", 435, 110);
	    
	    
	    // ----------------- AlphaComposite
	    
	    AlphaComposite ac = AlphaComposite.getInstance(rules[ruleIndex], 0.9f);
        g2.setComposite(ac);
        Rectangle2D rect2d4 = new Rectangle2D.Double(160,300 + rand.nextDouble(150), 40, 450);
        g2.setColor(laranja);
        g2.fill(rect2d4);
        
        
        
       
        // ******** Clip **********
        
        GeneralPath pathClip = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
		pathClip.moveTo(340,360);
		pathClip.lineTo(340,450);
	    pathClip.quadTo(390, 510, 440, 450);
	    pathClip.lineTo(440,360);
	    pathClip.quadTo(388, 245, 340, 360);
		pathClip.closePath();
		  
	    g2.setColor(Color.BLACK);
	     
	    g2.clip(pathClip);
	    g2.fill(pathClip);
	    
	    g2.setFont(new Font("Serif", Font.BOLD, 50));
	    g2.setColor(Color.WHITE);
	    g2.drawString("Clip",345,345);
	    
	    
	    
        
	   
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		ruleIndex++;
	    ruleIndex %= 12;
	    repaint();
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(s2.contains(e.getX(), e.getY())) {
			ProjetoFinal.cardlayout.show(ProjetoFinal.mainPanel, "graphic");
		}else if (rectInt1.contains(e.getX(), e.getY())) {
				selected = true;
				firstX = e.getX();
				firstY = e.getY();
			} else
				selected = false;
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		selected = false;
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		while(true) {
			angleRoatation = (angleRoatation + 20.0f) % 360;
			repaint();
		
		try {
	        Thread.sleep(400);
	      } catch (InterruptedException ex) {
	    	  ex.printStackTrace();
	      }
		}
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
			if (selected) {
				deltaX = e.getX() - firstX;
				deltaY = e.getY() - firstY;
	
			updateLocationAndColisionDetection();
			}
	}

			private void updateLocationAndColisionDetection() {
				at1.setToTranslation(deltaX, deltaY);
				rectInt1= at1.createTransformedShape(rectInt1);

				firstX = firstX + deltaX;
				firstY = firstY + deltaY;

				deltaX = 0;
				deltaY = 0;
				
				//if (s1.intersects(s2.getBounds()))
					//colision = true;
				//else
					//colision = false;

				double x = rectInt1.getBounds().getX() + rectInt1.getBounds().getWidth();
				double y = rectInt1.getBounds().getY() + rectInt1.getBounds().getHeight() / 2;
				Point2D p = new Point2D.Double(x, y);
				if(rectInt2.contains(p))
					collision = true;
				else
					collision = false;
				
				//repaint();
			}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}

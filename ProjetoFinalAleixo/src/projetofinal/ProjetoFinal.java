package projetofinal;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.WritableRaster;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;




public class ProjetoFinal extends JFrame implements ActionListener, Runnable{
	
	public static Color colorA = new Color(234,84,64);
	public static Color colorB = new Color(210,65,60);
	public static Color colorC = new Color(190,44,45);
	
	static BufferedImage photo;
	
	Shape rect1 = null;
	Shape elips1 = null;
	
	AffineTransform at = new AffineTransform();
	float angleShape = 0f;
	AffineTransform at1 = new AffineTransform();
	float angleShapeE = 0f;
	
	public static void main(String[] args) {
		JFrame frame = new ProjetoFinal();
		frame.setTitle("ProjetoFinal");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//JPanel panel = new MyPanel();
		//	frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);	
	
		
	}

	static JPanel mainPanel;
    MyPanel graphicPanel;
    static CardLayout cardlayout;
    static MenuPanel menuPanel;
    PrinterJob pj;
    
    	
public ProjetoFinal() {
		cardlayout = new CardLayout();
    	mainPanel = new JPanel(cardlayout);
    	
    	BorderLayout borderlayout = new BorderLayout();
    	
    	URL url = getClass().getClassLoader().getResource("images/photo.jpeg");
        try {
			photo = ImageIO.read(url);
		} catch(IOException ex) {
			ex.printStackTrace();
		}
        
    	graphicPanel = new MyPanel();
    	menuPanel = new MenuPanel();
    	
    	mainPanel.add(menuPanel, "menu");
    	mainPanel.add(graphicPanel, "graphic");
    	
    	mainPanel.addMouseListener(menuPanel);
    	
    	add(mainPanel);
    	
    	pj = PrinterJob.getPrinterJob();
		pj.setPrintable(graphicPanel);
    	
    	JMenuBar mBar = new JMenuBar();
		setJMenuBar(mBar);
		
		JMenu menuProcessing = new JMenu("Process Graphic Photo ");
		mBar.add(menuProcessing);
		
		JMenu menuColor = new JMenu("Colors");
		mBar.add(menuColor);
		
		JMenuItem mColorRed = new JMenuItem("Red");
		mColorRed.addActionListener(this);
		menuColor.add(mColorRed);
		
		JMenuItem mColorBlue = new JMenuItem("Blue");
		mColorBlue.addActionListener(this);
		menuColor.add(mColorBlue);
		
		JMenuItem mColorGreen = new JMenuItem("Green");
		mColorGreen.addActionListener(this);
		menuColor.add(mColorGreen);
		
		JMenu menu = new JMenu("File");
		JMenuItem mItem = new JMenuItem("Print");
		mItem.addActionListener(this);
		menu.add(mItem);
		menu.addSeparator();
		
		mItem = new JMenuItem("Exit");
		mItem.addActionListener(this);
		
		menu.add(mItem);
		mBar.add(menu);
		
		JMenuItem mItem2 = new JMenuItem("RGBtoGray2");
		mItem2.addActionListener(this);
		menuProcessing.add(mItem2);
		
		mItem = new JMenuItem("Grayscale");
		mItem.addActionListener(this);
		menuProcessing.add(mItem);
		
		JMenuItem mItem1 = new JMenuItem("Rotate");
		mItem1.addActionListener(this);
		menuProcessing.add(mItem1);
		
		JMenuItem mItem3 = new JMenuItem("Sharpen");
		mItem3.addActionListener(this);
		menuProcessing.add(mItem3);
		
		
		Thread thread = new Thread(this);
		thread.start();
		
}


@Override
public void actionPerformed(ActionEvent e) {
	String cmd = e.getActionCommand();
	if("Exit".equals(cmd)) {
		System.exit(0);
	}else if("Red".equals(cmd)) {
		colorA= new Color(234,84,64);
		colorB = new Color(210,65,60);
		colorC= new Color(190,44,45);
		repaint();
	}else if("Blue".equals(cmd)) {
		colorA= new Color(2,146,243);
		colorB = new Color(0,85,194);
		colorC= new Color(1,49,147);
		repaint();
	}else if("Green".equals(cmd)) {
		colorA= new Color(13,156,38);
		colorB = new Color(10,118,29);
		colorC= new Color(0,74,13);
		repaint();
	}else if("Print".equals(cmd) && pj.printDialog()) {
		try {
			pj.print();
		}catch(PrinterException ex) {
			ex.printStackTrace();
		}
	}else if("Grayscale".equals(cmd)) {
		MyPanel.GrayScale();
		repaint();
	}else if("Rotate".equals(cmd)) {
		MyPanel.Rotate();
		repaint();
	}else if("RGBtoGray2".equals(cmd)) {
		MyPanel.RGB2Gray(photo);
		repaint();
	}else if("Sharpen".equals(cmd)) {
		MyPanel.Sharpen();
		repaint();
	}
}

class MyPanel extends JPanel implements Printable,MouseListener, ActionListener{
	
	
	//// ---------- Inputs Variables
	
	JButton jbutton;
	JTextField tfA;
	JTextField tfB;
	JTextField tfC;
	
	
	// -------   Values
	int A = 1310;
	int B = 372;
	int C = 883;
	int max = 0;
	
	
	float angleA = 0;
	float angleB = 0;
	float angleC = 0;
    
	
	// -------   Drawing Circles
	Shape s1 = new Ellipse2D.Double(40,40,40,40);
    Shape s3 = new Ellipse2D.Double(0,0,120,120);
    Shape s5 = new Ellipse2D.Double(-40,-40,200,200);
    Shape s7 = new Ellipse2D.Double(-80,-80,280,280);
    
    Color colorA;
    Color colorB;
    Color colorC;
    
    public MyPanel() {
        setPreferredSize(new Dimension(500, 500));
        setBackground(Color.WHITE);
        
        JButton jbutton = new JButton("Back");
    	jbutton.addActionListener(this);
    	add(jbutton);
    	
    	// ------  INPUTS Fields and buttton
    	
    	
    	jbutton= new JButton("Submit");
    	jbutton.addActionListener(this);
    	
    	tfA = new JTextField();
    	tfA.setPreferredSize(new Dimension(65,25));
    	tfB = new JTextField();
    	tfB.setPreferredSize(new Dimension(65,25));
    	tfC = new JTextField();
    	tfC.setPreferredSize(new Dimension(65,25));
    	
    	this.add(jbutton);
    	this.add(tfA);
    	this.add(tfB);
    	this.add(tfC);	
    	
    }
    
    Shape btn1 = null;
    //static Color colorA = new Color(234,84,64);

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        // ------- Image
        
        if(a ==null) {
        	g2.drawImage(ProjetoFinal.photo, 2, 368,220,130, null);
        }else {
        	g.drawImage(MyPanel.a, 2, 368, 220, 130, null);
        }
        
        
     // ------- Stroke lines arround the photo
        
        g2.setColor(Color.BLACK);
	    Stroke stroke = new BasicStroke(7f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND);
	    g2.setStroke(stroke);
	    g2.drawLine(0, 367, 0,500);
	    g2.drawLine(0, 499, 221,499);
	    g2.drawLine(221, 500,221,367);
	    g2.drawLine(0, 367, 225,367);
	    
	    
	    // Translate XY 
        g2.translate(190, 140);
        g2.setColor(Color.BLACK);
        
        
        Stroke strokeDefault = new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND);
	    g2.setStroke(strokeDefault);
	    
	   // ----------- FieldLines and Text
	    Font font1 = new Font("Arial", Font.BOLD, 20);
	    g2.drawString("Value A", 44, -98);
	    g2.drawString("Value B", 113, -98);
	    g2.drawString("Value C", 182, -98);
        
        // -------   Calculate Angle
        angleA = (A * -180 / 800);
        angleB = (B * -180 / 800);
        angleC = (C * -180 / 800);
        
        
        // -------   Creating Arcs2D
        Arc2D arc1 = new Arc2D.Float(-70f, -70f, 260f, 260f, 90f, angleA,Arc2D.PIE);
    	Arc2D arc2 = new Arc2D.Float(-50f, -50f, 220f, 220f, 90f, angleA ,Arc2D.PIE);
    	Arc2D arc3 = new Arc2D.Float(-30f, -30f, 180f, 180f, 90f, angleB,Arc2D.PIE);
		Arc2D arc4 = new Arc2D.Float(-10f, -10f, 140f, 140f, 90f, angleB ,Arc2D.PIE);
		Arc2D arc5 = new Arc2D.Float(10f, 10f, 100f, 100f, 90f, angleC,Arc2D.PIE);
		Arc2D arc6 = new Arc2D.Float(30f,30f, 60f, 60f, 90f, angleC ,Arc2D.PIE);
		
    	
        // -------   Making Areas
        Area a1 = new Area(arc1);
        Area a2 = new Area(arc2);
        Area a3 = new Area(arc3);
        Area a4 = new Area(arc4);
        Area a5 = new Area(arc5);
        Area a6 = new Area(arc6);
        
        
       // -------  Guide Lines
        // 12.5%
        g2.drawLine(74, 44, 158, -40);
        // 37.5%
       // 25%
        g2.drawLine(80, 60, 200, 60);
        // 37.5%
        g2.drawLine(74, 73, 160, 160);
        // 50%
        g2.drawLine(60,80, 60, 200);
        // 62.5%
        g2.drawLine(45,75, -40, 160);
        // 75%
        g2.drawLine(-80,60, 40, 60);
        // 87.5%
        g2.drawLine(-40,-38, 46, 46);
        
        g2.setColor(Color.GREEN);
        g2.drawLine(60,-80,60,40);
        g2.setColor(Color.BLACK);
        
        // -------   Draw Circles
        g2.draw(s1);
        g2.draw(s3);
        g2.draw(s5);
        g2.draw(s7);
 
     // -------   Filling the Areas
        
        
        colorA = new Color(234,84,64);
        colorB = new Color(210,65,60);
        colorC = new Color (190,44,45);
        
        g2.setColor(ProjetoFinal.colorA);
        a1.subtract(a2);
        g2.fill(a1);
        g2.setColor(ProjetoFinal.colorB);
        a3.subtract(a4);
        g2.fill(a3);
        g2.setColor(ProjetoFinal.colorC);
        a5.subtract(a6);
        g2.fill(a5);
        
        // Guide Line -> 0%
        g2.setColor(Color.BLACK);
        g2.drawLine(60,-80, 60, 40);
        
        g2.drawString("0", 57, -82);
        g2.drawString("400", 202, 65);
        g2.drawString("800", 52, 215);
        g2.drawString("1200", -110, 65);
     
        
     // -------   Points General Path (Cutting the graph according to the max value)
        
        GeneralPath path = new GeneralPath(GeneralPath.WIND_NON_ZERO);
        float x0 = 60.0f;
        float y0 = -82.0f;
        float x1 = 60.0f;
        float y1 = 40.0f;
        float x2 = 0.0f;
        float y2 = 0.0f;
        float x3 = 0.0f;
        float y3 = 0.0f;
        float x4 = 0.0f;
        float y4 = 0.0f;    
        
        if(A >= B) {
    		max = A;
    		if(A >= C){
    			max = A;
    		}else if(C>=A) {
    			max = C;
    		}
    	}else {
    		max = B;
    		if(B >= C){
    			max = B;
    		}else if(C>=A) {
    			max = C;
    		}
    	}
        
//        if(A >= C) {
//    		max = A;
//    	}else {
//    		max = C;
//    	}
//        if(B >= C) {
//    		max = B;
//    	}else {
//    		max = C;
//    	}
        
        
        
        if(max < 1200) {
        	if(max >= 800) {
        		x2 = 40.0f;
                y2 = 60.0f;
                x3 = -82.0f;
                y3 = 60.0f;
                x4 = -79.0f;
                y4 = -99.0f;
        	}else if(max < 800 && max >= 400) {
        		
	        	x2 = 60.0f;
	            y2 = 40.0f;
	            x3 = 60.0f;
	            y3 = 210.0f;
	            x4 = -850.0f;
	            y4 = 60.0f;
        	}else{
        		x2 = 60.0f;
	            y2 = 40.0f;
	            x3 = 60.0f;
	            y3 = 210.0f;
	            x4 = -850.0f;
	            y4 = 60.0f;
	            path.moveTo(x0,y0); 
	            path.lineTo(x1,y1); 
	            path.lineTo(x2,y2);
	            path.lineTo(x3,y3);
	            path.curveTo(x3,y3,x4,y4,x0,y0);
	            AffineTransform tr = new AffineTransform();
	            tr.setToScale(1,1);
	            Shape s = tr.createTransformedShape(path); 
	            g2.setColor(Color.WHITE);
	            g2.fill(s);
	            x0 = 200.0f;
	            y0 = 61.0f;
	            x1 = 80.0f;
	            y1 = 61.0f;
	            x2= 60.0f;
	            y2 = 80.0f;
	            x3 = 60.0f;
	            y3 = 200.0f;
	            x4 = 450.0f;
	            y4 = 380.0f;
        	}
        	 path.moveTo(x0,y0); 
             path.lineTo(x1,y1); 
             path.lineTo(x2,y2);
             path.lineTo(x3,y3);
             path.curveTo(x3,y3,x4,y4,x0,y0);

             AffineTransform tr = new AffineTransform();
             tr.setToScale(1,1);
             Shape s = tr.createTransformedShape(path);
             
             g2.setColor(Color.WHITE);
             g2.fill(s);
     		
        }
        
        
        
        
        
        // ---------- animattion rect
        
        rect1 = new Rectangle2D.Double(200,200,40,40);
        
        g2.setColor(ProjetoFinal.colorB);
        at.setToTranslation(50, 50);
        at.rotate(Math.toRadians(angleShape), 260, 260);
        rect1 = at.createTransformedShape(rect1);
        g2.fill(rect1);
        
        elips1 = new Ellipse2D.Double(200,246,50,50);
        at1.rotate(Math.toRadians(angleShapeE), 190, 260);
        elips1 = at1.createTransformedShape(elips1);
        g2.fill(elips1);
        
         
        g2.translate(-190, -140);
        
        
        // Primitivas
        
        g2.fillArc(15, 20, 50, 80, 180, 180);
        g2.setColor(ProjetoFinal.colorC);
        g2.fillRect(0, 50, 90, 10);
        g2.fillRect(39, 100, 5, 40);
        
        g2.setColor(ProjetoFinal.colorA);
        g2.fillOval(21, 120, 40, 40);
        
        
        // max
        g2.setColor(Color.BLACK);
        g2.drawString("Max = 1400", 432, 23);
        
        // -------  Make Back Button 
       /* g2.setColor(Color.BLACK);
 		btn1 = new Rectangle2D.Double(50, 50, 80, 30);
 		g2.draw(btn1);
 		Font font1 = new Font("Arial", Font.PLAIN, 11);
	    g2.setFont(font1);
	    g2.setColor(Color.BLACK);
	    g2.drawString("BACK", 100, 80);*/
	    
	    
	    // ------------  DESENHAR SETA
	    
//	    g2.drawLine(-155, -160, -140, -160);
//	    g2.drawLine(-155, -160, -150, -165);
//	    g2.drawLine(-155, -160, -150, -155);
	    
	    // ---------------- Image
	    
	    /*AffineTransform af = new AffineTransform();
	    af.setToIdentity();
	    btn1 = af.createTransformedShape(btn1);
	    g2.draw(btn1);*/
	    

    }
    


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
		String cmd = e.getActionCommand();
		if("Back".equals(cmd)) {
			ProjetoFinal.cardlayout.show(ProjetoFinal.mainPanel, "menu"	);
		}else if("Submit".equals(cmd)) {
			
		//// ---------- String to Integir (KeyBoard)
			A = Integer.parseInt(tfA.getText());
			B = Integer.parseInt(tfB.getText());
			C = Integer.parseInt(tfC.getText());
			repaint();
		}
			
		
	}
	static BufferedImage a;
	public static void GrayScale() {
		BufferedImageOp op = null;
		op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
		BufferedImage bi = op.filter(ProjetoFinal.photo, null);
		a = bi;
	}
	public static void Sharpen() {
		BufferedImageOp op = null;
		float[] data = { 0f, -1f, 0f, -1f, 5f, -1f, 0f, -1f, 0f };
		Kernel k = new Kernel(3, 3, data);
		op = new ConvolveOp(k);
		BufferedImage bi = op.filter(ProjetoFinal.photo, null);
		a = bi;
	}
	
	public static void Rotate() {
		BufferedImageOp op = null;
		AffineTransform at = new AffineTransform();
		at.setToRotation(Math.PI /8);
		op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		BufferedImage bi = op.filter(ProjetoFinal.photo, null);
		a = bi;
	}
	
	public static BufferedImage RGB2Gray(BufferedImage photo) {
		photo = ProjetoFinal.photo;
		WritableRaster rasterImgIn = ProjetoFinal.photo.getRaster();
		WritableRaster rasterImgOut = ProjetoFinal.photo.getRaster();
		int[] rgba = new int[4];
		for (int y = 0; y < ProjetoFinal.photo.getHeight(); y++) {
			for (int x = 0; x < ProjetoFinal.photo.getWidth(); x++) {
				rasterImgIn.getPixel(x, y, rgba);
				int gray = (int) ((rgba[0] + rgba[1] + rgba[2]) / 3f);
				rgba[0] = rgba[1] = rgba[2] = gray;
				rasterImgOut.setPixel(x, y, rgba);
			}
		}
		return photo;
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		switch (pageIndex) {
		case 0:
			paintComponent(graphics);
			break;
		case 1:
			graphics.translate(-(int) pageFormat.getImageableWidth(), 0);
			paintComponent(graphics);
			break;
		default:
			return NO_SUCH_PAGE;
		}
		return PAGE_EXISTS;
		
	}

}

@Override
public void run() {
	while(true) {
		angleShape = (angleShape + 30.0f) % 360;
		angleShapeE = (angleShape + 10.0f) % 360;
		repaint();
	
	try {
        Thread.sleep(400);
      } catch (InterruptedException ex) {
    	  ex.printStackTrace();
      }
	}
	
}
}

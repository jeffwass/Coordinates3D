/*****************************************************************************

    Coordinates3D - Cylindrical

    Copyright 2004, 2012 Jeffrey L. Wasserman
    
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

*****************************************************************************/

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.lang.Math.*;
import java.util.Random;

public class cylindrical extends JApplet {

    public cylindricalFrame fr;
	
    public void init() {
	setName ("Spherical-JApplet");
	fr = new cylindricalFrame();
	//fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void start() {
	fr.cylindricalInit();
    }

    public void stop() {
    }

    public void destroy() {
    }
}

class cylindricalFrame extends JFrame {

    cylindricalPanel panDraw;
    JPanel panCont;
    Container cont, boxCont, sliderCont, infoCont;
    Container cartCont, cylCont, viewCont, topCont;
    JLabel xLab, yLab, zLab;
    JLabel rLab, phiLab,zzLab;
    JLabel alphaLab,betaLab,gammaLab;
    JScrollBar xScroll, yScroll, zScroll;
    JScrollBar rScroll, phiScroll, zzScroll;
    JScrollBar alphaScroll, betaScroll, gammaScroll;

    public cylindricalFrame (){

	super("Cylindrical Coordinates Visualization Applet");
	panDraw = new cylindricalPanel();
	panCont = new JPanel();

	xLab = new JLabel(" ");
	yLab = new JLabel(" ");
	zLab = new JLabel(" ");
	rLab = new JLabel(" ");
	phiLab = new JLabel(" ");
	zzLab = new JLabel(" ");
	alphaLab = new JLabel(" ");
	betaLab = new JLabel(" ");
	gammaLab = new JLabel(" ");

	float pi=(float)Math.PI;
	xScroll = new JScrollBar (JScrollBar.HORIZONTAL, Math.round(panDraw.x),
				  1,-Math.round(panDraw.maxLength),
				  Math.round(panDraw.maxLength));
	yScroll = new JScrollBar (JScrollBar.HORIZONTAL, Math.round(panDraw.y),
				  1,-Math.round(panDraw.maxLength),
				  Math.round(panDraw.maxLength));
	zScroll = new JScrollBar (JScrollBar.HORIZONTAL, Math.round(panDraw.z),
				  1,-Math.round(panDraw.maxLength),
				  Math.round(panDraw.maxLength));
	rScroll = new JScrollBar (JScrollBar.HORIZONTAL, Math.round(panDraw.spinLength),
				  1,1,Math.round(panDraw.maxLength));
        phiScroll = new JScrollBar (JScrollBar.HORIZONTAL,
				      Math.round(panDraw.phi/pi*180),
				      1, 0, 360);
        zzScroll = new JScrollBar (JScrollBar.HORIZONTAL,Math.round(panDraw.zz),
				   1, -Math.round(panDraw.maxLength),
				   Math.round(panDraw.maxLength));
        alphaScroll = new JScrollBar (JScrollBar.HORIZONTAL,
				      Math.round(panDraw.alpha/pi*180),
				      1, 0, 360);
        betaScroll = new JScrollBar (JScrollBar.HORIZONTAL,
				      Math.round(panDraw.beta/pi*180),
				      1, 0, 360);
        gammaScroll = new JScrollBar (JScrollBar.HORIZONTAL,
				      Math.round(panDraw.gamma/pi*180),
				      1, 0, 360);

	boxCont = Box.createVerticalBox();
	sliderCont = Box.createHorizontalBox();
	infoCont = Box.createVerticalBox();
	cartCont = Box.createVerticalBox();
	cylCont = Box.createVerticalBox();
	viewCont = Box.createVerticalBox();
	topCont = Box.createHorizontalBox();

	cartCont.add(new JLabel("Cartesian Coordinates"));
	cartCont.add(xLab);
	cartCont.add(xScroll);
	cartCont.add(yLab);
	cartCont.add(yScroll);
	cartCont.add(zLab);
	cartCont.add(zScroll);
     
	cylCont.add(new JLabel("Spherical Coordinates"));
	cylCont.add(rLab);
	cylCont.add(rScroll);
	cylCont.add(phiLab);
	cylCont.add(phiScroll);
	cylCont.add(zzLab);
	cylCont.add(zzScroll);

	viewCont.add(new JLabel("Viewing Angle"));
	viewCont.add(alphaLab);
	viewCont.add(alphaScroll);
	viewCont.add(betaLab);
	viewCont.add(betaScroll);
	viewCont.add(gammaLab);
	viewCont.add(gammaScroll);

	sliderCont.add(cartCont);
	sliderCont.add(Box.createHorizontalStrut(10));
	sliderCont.add(cylCont);
	sliderCont.add(Box.createHorizontalStrut(10));
	sliderCont.add(viewCont);

	infoCont.add(new JLabel("Demonstration part of :"));
	infoCont.add(new JLabel("Virtual Quantum Mechanics"));
	infoCont.add(new JLabel("Johns Hopkins University"));
	infoCont.add(new JLabel("CER TF 2003-2004"));
	infoCont.add(new JLabel("www.pha.jhu.edu/~javalab"));

	boxCont.add(sliderCont);
	topCont.add(panDraw);
	topCont.add(Box.createHorizontalStrut(20));
	topCont.add(infoCont);
	topCont.add(Box.createHorizontalStrut(20));

	cont = getContentPane();
	cont.setLayout(new BorderLayout());
	cont.add(topCont, BorderLayout.NORTH);
	cont.add(boxCont, BorderLayout.SOUTH);

	pack();
	setVisible(true);

	xScroll.addAdjustmentListener (new AdjustmentListener() {
		public void adjustmentValueChanged(AdjustmentEvent e) {
		    panDraw.cartLock=true;
		    float tx=(float)xScroll.getValue();
		    float ty=panDraw.y;
		    float tr=panDraw.maxLength;
		    if ((tx*tx+ty*ty)>=tr*tr) {
			tx = panDraw.x;
			xScroll.setValue(Math.round(tx));
		    }
		    panDraw.x = tx;
		    xLab.setText ("X "+Math.round(tx));
		    if (!panDraw.cylLock) 
			panDraw.cylUpdate();
		    panDraw.repaint();
		    panDraw.cartLock=false;
		}
	    });
	yScroll.addAdjustmentListener (new AdjustmentListener() {
		public void adjustmentValueChanged(AdjustmentEvent e) {
		    panDraw.cartLock=true;
		    float ty=(float)yScroll.getValue();
		    float tx=panDraw.x;
		    float tr=panDraw.maxLength;
		    if ((tx*tx+ty*ty)>=tr*tr) {
			ty = panDraw.y;
			yScroll.setValue(Math.round(ty));
		    }
		    panDraw.y = ty;
		    yLab.setText ("Y "+Math.round(ty));
		    if (!panDraw.cylLock)
			panDraw.cylUpdate();
		    panDraw.repaint();
		    panDraw.cartLock=false;
		}
	    });
	zScroll.addAdjustmentListener (new AdjustmentListener() {
		public void adjustmentValueChanged(AdjustmentEvent e) {
		    panDraw.cartLock=true;
		    float tz = (float)zScroll.getValue();
		    panDraw.z=tz;
		    zLab.setText ("Z "+Math.round(tz));
		    if (!panDraw.cylLock)
			panDraw.cylUpdate();
		    panDraw.repaint();
		    panDraw.cartLock=false;
		}
	    });

	rScroll.addAdjustmentListener (new AdjustmentListener() {
		public void adjustmentValueChanged(AdjustmentEvent e) {
		    panDraw.cylLock=true;
		    int rVal=rScroll.getValue();
		    panDraw.spinLength = rVal;
		    rLab.setText("Radius = "+rVal);
		    if (!panDraw.cartLock)
			panDraw.cartUpdate();
		    panDraw.repaint();
		    panDraw.cylLock=false;
		}
	    });
	phiScroll.addAdjustmentListener (new AdjustmentListener() {
		public void adjustmentValueChanged(AdjustmentEvent e) {
		    panDraw.cylLock=true;
		    int phiDeg=phiScroll.getValue();
		    panDraw.phi = (float)Math.PI*phiDeg/180;
		    phiLab.setText("Phi = "+phiDeg);
		    if (!panDraw.cartLock)
			panDraw.cartUpdate();
		    panDraw.repaint();
		    panDraw.cylLock=false;
		}
	    });
	zzScroll.addAdjustmentListener (new AdjustmentListener() {
		public void adjustmentValueChanged(AdjustmentEvent e) {
		    panDraw.cylLock=true;
		    int zz=zzScroll.getValue();
		    panDraw.zz = (float)zz;
		    zzLab.setText("Z = "+zz);
		    if (!panDraw.cartLock)
			panDraw.cartUpdate();
		    panDraw.repaint();
		    panDraw.cylLock=false;
		}
	    });
	alphaScroll.addAdjustmentListener (new AdjustmentListener() {
		public void adjustmentValueChanged(AdjustmentEvent e) {
		    int alphaDeg=alphaScroll.getValue();
		    panDraw.alpha = (float)Math.PI*alphaDeg/180;
		    alphaLab.setText("Alpha = "+alphaDeg);
		    panDraw.repaint();
		}
	    });
	betaScroll.addAdjustmentListener (new AdjustmentListener() {
		public void adjustmentValueChanged(AdjustmentEvent e) {
		    int betaDeg=betaScroll.getValue();
		    panDraw.beta = (float)Math.PI*betaDeg/180;
		    betaLab.setText("Beta = "+betaDeg);
		    panDraw.repaint();
		}
	    });		    
	gammaScroll.addAdjustmentListener (new AdjustmentListener() {
		public void adjustmentValueChanged(AdjustmentEvent e) {
		    int gammaDeg=gammaScroll.getValue();
		    panDraw.gamma = (float)Math.PI*gammaDeg/180;
		    gammaLab.setText("Gamma = "+gammaDeg);
		    panDraw.repaint();
		}
	    });		    

    }

    public void cylindricalInit () {
	panDraw.numberOfSpins=1;
	panDraw.spinInit();
	panDraw.cartUpdate();
	panDraw.cylUpdate();
	panDraw.paramUpdate();
	panDraw.repaint();
    }

}

class cylindricalPanel extends JPanel {

    public float bord=10.0f;
    public float axSpace=50.0f;
    public float fontSpace=20.0f;
    public int fontSize=12;
    public int panXSize;
    public int panYSize;

    public float maxLength=150.0f;
    public float spinLength=maxLength*0.8f;

    public float pi=(float)Math.PI;
    public float alpha=pi/8;
    public float beta=1.95f*pi;
    public float gamma=0;
    public float phi=pi/3;
    public float zz=maxLength*0.5f;
    public float x=(float)(spinLength*Math.cos(phi));
    public float y=(float)(spinLength*Math.sin(phi));
    public float z=zz;

    public float measDirLength=50.0f;
    public float oldSpinLength=75.0f;
    public float axLength=axSpace/2f;
    public float measDirSep=2.0f;
    float spinArrowLength=15.0f;
    float spinArrowAngle=(float)(Math.PI/8.0);
    float angleResolution=(float)(Math.PI*3.0f/180.0f);
    float hiLiteRange=8.0f;

    public boolean cylLock=false;
    public boolean cartLock=false;

    public float[] spinAngle = {0,0};
    public float[] measAngle = {0,0};
    public float[] oldSpinAngle = {0,0};
    float[] centerX = {0,0};
    float[] centerY = {0,0};
    float axX=bord+axLength;
    float axY;
    int axOv=2;
    public int angRef=1;
    public int numberOfSpins=1;
    float[] dotline = {2f,2f};

    public boolean[] hiSpinAngle = {false,false};
    public boolean[] hiMeasAngle = {false,false};
    public boolean[] moveSpinAngle = {false,false};
    public boolean[] moveMeasAngle = {false,false};

    Color backCol = new Color (0,0,0);
    Color spinCol = new Color (0xDD,0xDD,0);
    Color spinHiCol = new Color (0xFF,0xFF,0);
    Color measDirCol = new Color (0,0xDD,0);
    Color measDirHiCol = new Color (0,0xFF,0);
    Color oldSpinCol = new Color (0x77,0x77,0x77);
    Color circCol = new Color (0xAA,0xAA,0xAA);
    Color ringCol = new Color (0xAA,0xAA,0);
    Color axesCol = new Color (0,0xaa,0xaa);
    Color equatorCol = new Color (0,0x77,0x77);
    Color guideCol = new Color (0x77,0x77,0x77);
    Color fontCol = new Color (0xFF,0xFF,0xFF);
    Color statCol = new Color (0xFF,0x33,0x33);

    public cylindricalPanel () {
	panXSize=Math.round(3.0f*bord+fontSpace+axSpace+2.0f*maxLength);
	panYSize=Math.round(3.0f*bord+fontSpace+2.0f*maxLength);
	setPreferredSize (new Dimension(panXSize,panYSize));
	axY=bord+panYSize/2;
    }

    public void spinInit () {
	int n;

	for (n=0; n<numberOfSpins; n++) {
	    spinAngle[n]=0f;
	    measAngle[n]=(float) Math.PI/2.0f;
	}
	centerX[0] = axSpace + (2f*bord) + maxLength;
	centerY[0] = bord + maxLength;
	paramUpdate();
	repaint();
    }

    public void paramUpdate () {
	cylindricalFrame dummy = (cylindricalFrame) getTopLevelAncestor();
	dummy.alphaLab.setText("Alpha "+Math.round(alpha*180/pi));
	dummy.betaLab.setText("Beta "+Math.round(beta*180/pi));
	dummy.gammaLab.setText("Gamma "+Math.round(gamma*180/pi));
    }

    public void cartUpdate () {
	cylindricalFrame dummy = (cylindricalFrame) getTopLevelAncestor();
	x=(float)(spinLength*Math.cos(phi));
	y=(float)(spinLength*Math.sin(phi));
	z=zz;
	dummy.xScroll.setValue(Math.round(x));
	dummy.xLab.setText("X "+Math.round(x));
	dummy.yScroll.setValue(Math.round(y));
	dummy.yLab.setText("Y "+Math.round(y));
	dummy.zScroll.setValue(Math.round(z));
	dummy.zLab.setText("Z "+Math.round(z));
    }

    public void cylUpdate () {
	cylindricalFrame dummy = (cylindricalFrame) getTopLevelAncestor();
	spinLength=(float)Math.sqrt(x*x+y*y);
	phi=(float)(Math.atan(y/(x+0.000001)));
	zz=z;
	if (x < 0) phi += pi;
	if (phi < 0) phi += 2*pi;
	dummy.rScroll.setValue (Math.round(spinLength));
	dummy.phiScroll.setValue (Math.round(phi*180/pi));
	dummy.zzScroll.setValue (Math.round(zz));
	dummy.rLab.setText("Radius "+Math.round(spinLength));
	dummy.phiLab.setText("Phi "+Math.round(phi*180/pi));
	dummy.zzLab.setText("Z "+Math.round(zz));
    }
	    	
    public float thetaPhiToX (float th,
			      float ph) {

	double xx=(-Math.sin(gamma)*Math.cos(beta)*Math.cos(alpha)-
		   Math.cos(gamma)*Math.sin(alpha))*Math.sin(th)*Math.cos(ph)+
	    (-Math.sin(gamma)*Math.cos(beta)*Math.sin(alpha)+
	     Math.cos(gamma)*Math.cos(alpha))*Math.sin(th)*Math.sin(ph)+
	    Math.sin(gamma)*Math.sin(beta)*Math.cos(th);
	return (float)xx;
    }

    public float thetaPhiToY (float th,
			    float ph) {
	double yy = Math.sin(beta)*Math.cos(alpha)*Math.sin(th)*Math.cos(ph) +
	    Math.sin(beta)*Math.sin(alpha)*Math.sin(th)*Math.sin(ph)+
	    Math.cos(beta)*Math.cos(th);
	return (float)yy;
    }

    public void drawCylLatitude (Graphics2D g,
			    float centX,
			    float centY,
			    float r,
			    float z) {

	int st=30;
	float rr=(float)Math.sqrt(r*r+z*z);
	float th=(float)Math.atan((r/(z+0.0001f)));
	if (z<0) th+=pi;

	for (int i=0;i<st;i++) {
	    g.drawLine ((int) Math.round(centX+rr*thetaPhiToX(th,i*2f*pi/st)),
			(int) Math.round(centY-rr*thetaPhiToY(th,i*2f*pi/st)),
			(int) Math.round(centX+rr*thetaPhiToX(th,(i+1)*2f*pi/st)),
			(int) Math.round(centY-rr*thetaPhiToY(th,(i+1)*2f*pi/st)));
	}
    }
  
    public void paint (Graphics g) {
	paintWrapper (g);
    }

    public void paintWrapper (Graphics g) {
	Graphics2D g2 = (Graphics2D) g;
	int n;
	String ax;

	// Draw the background window
	g2.setPaint (backCol);
	g2.fillRect (0,0,panXSize-1,panYSize-1);

	Font font = new Font("SansSerif",Font.PLAIN,fontSize);
        g2.setFont (font);
        FontRenderContext frc = g2.getFontRenderContext();
	


	// Draw the Axes and Labels
	g2.setPaint (fontCol);
	g2.drawLine (Math.round(axX),
		     Math.round(axY),
		     Math.round(axX+axLength*thetaPhiToX(0,0)),
		     Math.round(axY-axLength*thetaPhiToY(0,0)));
	g2.drawLine (Math.round(axX),
		     Math.round(axY),
		     Math.round(axX+axLength*thetaPhiToX(pi/2,0)),
		     Math.round(axY-axLength*thetaPhiToY(pi/2,0)));
	g2.drawLine (Math.round(axX),
		     Math.round(axY),
		     Math.round(axX+axLength*thetaPhiToX(pi/2,pi/2)),
		     Math.round(axY-axLength*thetaPhiToY(pi/2,pi/2)));
        ax = "x";
        float fx=(float)font.getStringBounds(ax,frc).getWidth();
        float fy=(float)font.getStringBounds(ax,frc).getHeight();
        g2.drawString ("z",Math.round(axX+(axLength+2*fx)*thetaPhiToX(0,0)-fx),
                       Math.round(axY-(axLength+2*fx)*thetaPhiToY(0,0)));
        g2.drawString ("x",Math.round(axX+(axLength+2*fx)*thetaPhiToX(pi/2,0)-fx),
                       Math.round(axY-(axLength+2*fx)*thetaPhiToY(pi/2,0)));
        g2.drawString ("y",Math.round(axX+(axLength+2*fx)*thetaPhiToX(pi/2,pi/2)-fx),
                       Math.round(axY-(axLength+2*fx)*thetaPhiToY(pi/2,pi/2)));


		 
	for (n=0; n<numberOfSpins; n++) {

	    // Draw the Cylinder Guide
	    g2.setPaint (circCol);
	    drawCylLatitude (g2,centerX[n],centerY[n],spinLength,z);
	    g2.setPaint (equatorCol);
	    drawCylLatitude (g2,centerX[n],centerY[n],spinLength,0);
	    /*
	    g2.drawLine (Math.round(centerX[n]-spinLength),Math.round(centerY[n]-z),
	    		 Math.round(centerX[n]-spinLength),Math.round(centerY[n]));
	    g2.drawLine (Math.round(centerX[n]+spinLength),Math.round(centerY[n]-z),
			 Math.round(centerX[n]+spinLength),Math.round(centerY[n]));
	    */

	    // Draw the Labels
	    g2.setPaint (fontCol);
	    ax = "Cylindrical Representation of Vector";
	    g2.drawString (ax, Math.round
			   (centerX[n]-font.getStringBounds
			    (ax,frc).getWidth()/2),
			   Math.round
			   (panYSize-bord-fontSpace/2+
			    font.getStringBounds(ax,frc).getHeight()/2));

	    float rr=(float)Math.sqrt(spinLength*spinLength+zz*zz);
	    float th=(float)(Math.atan(spinLength/(zz+0.00001)));
	    if (th<0) th+=pi;
	    float ph=phi;

	    // Draw the Axes and Projections onto Axes
	    g2.setPaint (axesCol);
	    g2.drawLine (Math.round(centerX[n]),
			 Math.round(centerY[n]),
			 Math.round(centerX[n]+z*thetaPhiToX(0,0)),
			 Math.round(centerY[n]-z*thetaPhiToY(0,0)));
	    g2.drawLine (Math.round(centerX[n]),
			 Math.round(centerY[n]),
			 Math.round(centerX[n]+spinLength*thetaPhiToX(pi/2,0)),
			 Math.round(centerY[n]-spinLength*thetaPhiToY(pi/2,0)));
	    g2.drawLine (Math.round(centerX[n]),
			 Math.round(centerY[n]),
			 Math.round(centerX[n]+spinLength*thetaPhiToX(pi/2,pi/2)),
			 Math.round(centerY[n]-spinLength*thetaPhiToY(pi/2,pi/2)));
	    g2.fillOval (Math.round(centerX[n]+x*thetaPhiToX(pi/2,0)-axOv),
			 Math.round(centerY[n]-x*thetaPhiToY(pi/2,0)-axOv),
			 2*axOv,2*axOv);
	    g2.fillOval (Math.round(centerX[n]+y*thetaPhiToX(pi/2,pi/2)-axOv),
			 Math.round(centerY[n]-y*thetaPhiToY(pi/2,pi/2)-axOv),
			 2*axOv,2*axOv);
	    g2.fillOval ((int)Math.round(centerX[n]+z*thetaPhiToX(0,0)-axOv),
			 (int)Math.round(centerY[n]-z*thetaPhiToY(0,0)-axOv),
			 2*axOv,2*axOv);

	    // Draw the Equator and Guide Latitude
	    //g2.setPaint (equatorCol);
	    //drawCylLatitude (g2, centerX[n], centerY[n], maxLength, 0);
	    //g2.setPaint (guideCol);
	    //drawCylLatitude (g2, centerX[n], centerY[n], maxLength, z);

	    // Draw the Equatorial Projection and Connecting Line
	    g2.setPaint (circCol);
	    g2.drawLine (Math.round(centerX[n]),
			 Math.round(centerY[n]),
			 (int)Math.round(centerX[n]+
					 Math.sin(th)*rr*thetaPhiToX(pi/2,ph)),
			 (int)Math.round(centerY[n]-
					 Math.sin(th)*rr*thetaPhiToY(pi/2,ph)));
	    g2.setStroke (new BasicStroke(1f, BasicStroke.CAP_BUTT, 
					  BasicStroke.JOIN_MITER, 1f, dotline, 0f));
	    g2.setPaint (equatorCol);
	    g2.drawLine (Math.round(centerX[n]+rr*thetaPhiToX(th,ph)),
			 Math.round(centerY[n]-rr*thetaPhiToY(th,ph)),
			 (int)Math.round(centerX[n]+
					 Math.sin(th)*rr*thetaPhiToX(pi/2,ph)),
			 (int)Math.round(centerY[n]-
					 Math.sin(th)*rr*thetaPhiToY(pi/2,ph)));
	    /*
	    g2.setPaint (guideCol);
	    g2.drawLine ((int)Math.round(centerX[n]+
					 Math.sin(theta)*spinLength*thetaPhiToX(pi/2,phi)),
			 (int)Math.round(centerY[n]-
					 Math.sin(theta)*spinLength*thetaPhiToY(pi/2,phi)),
			 Math.round(centerX[n]+x*thetaPhiToX(pi/2,0)),
			 Math.round(centerY[n]-x*thetaPhiToY(pi/2,0)));
	    g2.drawLine ((int)Math.round(centerX[n]+
					 Math.sin(theta)*spinLength*thetaPhiToX(pi/2,phi)),
			 (int)Math.round(centerY[n]-
					 Math.sin(theta)*spinLength*thetaPhiToY(pi/2,phi)),
			 Math.round(centerX[n]+y*thetaPhiToX(pi/2,pi/2)),
			 Math.round(centerY[n]-y*thetaPhiToY(pi/2,pi/2)));
	    g2.drawLine (Math.round(centerX[n]+spinLength*thetaPhiToX(theta,phi)),
			 Math.round(centerY[n]-spinLength*thetaPhiToY(theta,phi)),
			 (int)Math.round(centerX[n]+z*thetaPhiToX(0,0)),
			 (int)Math.round(centerY[n]-z*thetaPhiToY(0,0)));
	    */

	    g2.setStroke (new BasicStroke());
	    
	    // Draw the Vector
	    g2.setPaint (spinCol);
	    g2.setStroke (new BasicStroke(2));
	    g2.drawLine (Math.round(centerX[n]),
			 Math.round(centerY[n]),
			 Math.round(centerX[n]+rr*thetaPhiToX(th,ph)),
			 Math.round(centerY[n]-rr*thetaPhiToY(th,ph)));
	    g2.setStroke (new BasicStroke());

	}
    }
    
}

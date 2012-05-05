/*****************************************************************************

    Coordinates3D - Spherical

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

public class spherical extends JApplet {

    public sphericalFrame fr;
	
    public void init() {
	setName ("Spherical-JApplet");
	fr = new sphericalFrame();
	//fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void start() {
	fr.sphericalInit();
    }

    public void stop() {
    }

    public void destroy() {
    }
}

class sphericalFrame extends JFrame {

    spherePanel panDraw;
    JPanel panCont;
    Container cont, boxCont, sliderCont, infoCont;
    Container cartCont, sphereCont, viewCont, topCont;
    JLabel xLab, yLab, zLab;
    JLabel rLab, thetaLab,phiLab;
    JLabel alphaLab,betaLab,gammaLab;
    JScrollBar xScroll, yScroll, zScroll;
    JScrollBar rScroll, thetaScroll, phiScroll;
    JScrollBar alphaScroll, betaScroll, gammaScroll;

    public sphericalFrame (){

	super("Spherical Coordinates Visualization Applet");
	panDraw = new spherePanel();
	panCont = new JPanel();

	xLab = new JLabel(" ");
	yLab = new JLabel(" ");
	zLab = new JLabel(" ");
	rLab = new JLabel(" ");
	thetaLab = new JLabel(" ");
	phiLab = new JLabel(" ");
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
        thetaScroll = new JScrollBar (JScrollBar.HORIZONTAL,
				      Math.round(panDraw.theta/pi*180),
				      1, 0, 180);
        phiScroll = new JScrollBar (JScrollBar.HORIZONTAL,
				      Math.round(panDraw.phi/pi*180),
				      1, 0, 360);
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
	sphereCont = Box.createVerticalBox();
	viewCont = Box.createVerticalBox();
	topCont = Box.createHorizontalBox();

	cartCont.add(new JLabel("Cartesian Coordinates"));
	cartCont.add(xLab);
	cartCont.add(xScroll);
	cartCont.add(yLab);
	cartCont.add(yScroll);
	cartCont.add(zLab);
	cartCont.add(zScroll);
     
	sphereCont.add(new JLabel("Spherical Coordinates"));
	sphereCont.add(rLab);
	sphereCont.add(rScroll);
	sphereCont.add(thetaLab);
	sphereCont.add(thetaScroll);
	sphereCont.add(phiLab);
	sphereCont.add(phiScroll);

	viewCont.add(new JLabel("Viewing Angle"));
	viewCont.add(alphaLab);
	viewCont.add(alphaScroll);
	viewCont.add(betaLab);
	viewCont.add(betaScroll);
	viewCont.add(gammaLab);
	viewCont.add(gammaScroll);

	sliderCont.add(cartCont);
	sliderCont.add(Box.createHorizontalStrut(10));
	sliderCont.add(sphereCont);
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
		    float xx=(float)xScroll.getValue();
		    float yy=panDraw.y;
		    float zz=panDraw.z;
		    float rr=panDraw.maxLength;
		    if ((xx*xx+yy*yy+zz*zz)>=rr*rr) {
			xx = panDraw.x;
			xScroll.setValue(Math.round(xx));
		    }
		    panDraw.x = xx;
		    xLab.setText ("X "+Math.round(xx));
		    if (!panDraw.sphereLock) 
			panDraw.sphereUpdate();
		    panDraw.repaint();
		    panDraw.cartLock=false;
		}
	    });
	yScroll.addAdjustmentListener (new AdjustmentListener() {
		public void adjustmentValueChanged(AdjustmentEvent e) {
		    panDraw.cartLock=true;
		    float yy=(float)yScroll.getValue();
		    float xx=panDraw.x;
		    float zz=panDraw.z;
		    float rr=panDraw.maxLength;
		    if ((xx*xx+yy*yy+zz*zz)>=rr*rr) {
			yy = panDraw.y;
			yScroll.setValue(Math.round(yy));
		    }
		    panDraw.y = yy;
		    yLab.setText ("Y "+Math.round(yy));
		    if (!panDraw.sphereLock)
			panDraw.sphereUpdate();
		    panDraw.repaint();
		    panDraw.cartLock=false;
		}
	    });
	zScroll.addAdjustmentListener (new AdjustmentListener() {
		public void adjustmentValueChanged(AdjustmentEvent e) {
		    panDraw.cartLock=true;
		    float zz=(float)zScroll.getValue();
		    float yy=panDraw.y;
		    float xx=panDraw.x;
		    float rr=panDraw.maxLength;
		    if ((xx*xx+yy*yy+zz*zz)>=rr*rr) {
			zz = panDraw.z;
			zScroll.setValue(Math.round(zz));
		    }
		    panDraw.z = zz;
		    zLab.setText ("Z "+Math.round(zz));
		    if (!panDraw.sphereLock)
			panDraw.sphereUpdate();
		    panDraw.repaint();
		    panDraw.cartLock=false;
		}
	    });

	rScroll.addAdjustmentListener (new AdjustmentListener() {
		public void adjustmentValueChanged(AdjustmentEvent e) {
		    panDraw.sphereLock=true;
		    int rVal=rScroll.getValue();
		    panDraw.spinLength = rVal;
		    rLab.setText("Radius "+rVal);
		    if (!panDraw.cartLock)
			panDraw.cartUpdate();
		    panDraw.repaint();
		    panDraw.sphereLock=false;
		}
	    });
	thetaScroll.addAdjustmentListener (new AdjustmentListener() {
		public void adjustmentValueChanged(AdjustmentEvent e) {
		    panDraw.sphereLock=true;
		    int thetaDeg=thetaScroll.getValue();
		    panDraw.theta = (float)Math.PI*thetaDeg/180;
		    thetaLab.setText("Theta "+thetaDeg);
		    if (!panDraw.cartLock)
			panDraw.cartUpdate();
		    panDraw.repaint();
		    panDraw.sphereLock=false;
		}
	    });
	phiScroll.addAdjustmentListener (new AdjustmentListener() {
		public void adjustmentValueChanged(AdjustmentEvent e) {
		    panDraw.sphereLock=true;
		    int phiDeg=phiScroll.getValue();
		    panDraw.phi = (float)Math.PI*phiDeg/180;
		    phiLab.setText("Phi "+phiDeg);
		    if (!panDraw.cartLock)
			panDraw.cartUpdate();
		    panDraw.repaint();
		    panDraw.sphereLock=false;
		}
	    });
	alphaScroll.addAdjustmentListener (new AdjustmentListener() {
		public void adjustmentValueChanged(AdjustmentEvent e) {
		    int alphaDeg=alphaScroll.getValue();
		    panDraw.alpha = (float)Math.PI*alphaDeg/180;
		    alphaLab.setText("Alpha "+alphaDeg);
		    panDraw.repaint();
		}
	    });
	betaScroll.addAdjustmentListener (new AdjustmentListener() {
		public void adjustmentValueChanged(AdjustmentEvent e) {
		    int betaDeg=betaScroll.getValue();
		    panDraw.beta = (float)Math.PI*betaDeg/180;
		    betaLab.setText("Beta "+betaDeg);
		    panDraw.repaint();
		}
	    });		    
	gammaScroll.addAdjustmentListener (new AdjustmentListener() {
		public void adjustmentValueChanged(AdjustmentEvent e) {
		    int gammaDeg=gammaScroll.getValue();
		    panDraw.gamma = (float)Math.PI*gammaDeg/180;
		    gammaLab.setText("Gamma "+gammaDeg);
		    panDraw.repaint();
		}
	    });		    

    }

    public void sphericalInit () {
	panDraw.numberOfSpins=1;
	panDraw.spinInit();
	panDraw.cartUpdate();
	panDraw.sphereUpdate();
	panDraw.paramUpdate();
	panDraw.repaint();
    }

}

class spherePanel extends JPanel {

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
    public float theta=pi/4;
    public float phi=pi/4;
    public float x=(float)(spinLength*Math.sin(theta)*Math.cos(phi));
    public float y=(float)(spinLength*Math.sin(theta)*Math.sin(phi));
    public float z=(float)(spinLength*Math.cos(theta));

    public float measDirLength=50.0f;
    public float oldSpinLength=75.0f;
    public float axLength=axSpace/2f;
    public float measDirSep=2.0f;
    float spinArrowLength=15.0f;
    float spinArrowAngle=(float)(Math.PI/8.0);
    float angleResolution=(float)(Math.PI*3.0f/180.0f);
    float hiLiteRange=8.0f;

    public boolean sphereLock=false;
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

    public spherePanel () {
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
	sphericalFrame dummy = (sphericalFrame) getTopLevelAncestor();
	dummy.alphaLab.setText("Alpha "+Math.round(alpha*180/pi));
	dummy.betaLab.setText("Beta "+Math.round(beta*180/pi));
	dummy.gammaLab.setText("Gamma "+Math.round(gamma*180/pi));
    }

    public void cartUpdate () {
	sphericalFrame dummy = (sphericalFrame) getTopLevelAncestor();
	x=(float)(spinLength*Math.sin(theta)*Math.cos(phi));
	y=(float)(spinLength*Math.sin(theta)*Math.sin(phi));
	z=(float)(spinLength*Math.cos(theta));
	dummy.xScroll.setValue(Math.round(x));
	dummy.xLab.setText("X "+Math.round(x));
	dummy.yScroll.setValue(Math.round(y));
	dummy.yLab.setText("Y "+Math.round(y));
	dummy.zScroll.setValue(Math.round(z));
	dummy.zLab.setText("Z "+Math.round(z));
    }

    public void sphereUpdate () {
	sphericalFrame dummy = (sphericalFrame) getTopLevelAncestor();
	spinLength=(float)Math.sqrt(x*x+y*y+z*z);
	theta=(float)(Math.acos((0.000001+z)/spinLength));
	phi=(float)(Math.atan(y/(x+0.000001)));
	if (x < 0) phi += pi;
	if (phi < 0) phi += 2*pi;
	if (theta < 0) theta += 2*pi;
	dummy.rScroll.setValue (Math.round(spinLength));
	dummy.thetaScroll.setValue (Math.round(theta*180/pi));
	dummy.phiScroll.setValue (Math.round(phi*180/pi));
	dummy.rLab.setText("Radius "+Math.round(spinLength));
	dummy.thetaLab.setText("Theta "+Math.round(theta*180/pi));
	dummy.phiLab.setText("Phi "+Math.round(phi*180/pi));
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

    public void drawLatitude (Graphics2D g,
			    float centX,
			    float centY,
			    float len,
			    float th) {

	int st=30;

	for (int i=0;i<st;i++) {
	    g.drawLine ((int) Math.round(centX+len*thetaPhiToX(th,i*2f*pi/st)),
			(int) Math.round(centY-len*thetaPhiToY(th,i*2f*pi/st)),
			(int) Math.round(centX+len*thetaPhiToX(th,(i+1)*2f*pi/st)),
			(int) Math.round(centY-len*thetaPhiToY(th,(i+1)*2f*pi/st)));
	}
    }
  
    public void paint (Graphics g) {
	paintWrapper (g);
    }

    public void paintWrapper (Graphics g) {
	Graphics2D g2 = (Graphics2D) g;
	int n;
	String ax;

        Font font = new Font("SansSerif",Font.PLAIN,fontSize);
        g2.setFont (font);
        FontRenderContext frc = g2.getFontRenderContext();

	// Draw the background window
	g2.setPaint (backCol);
	g2.fillRect (0,0,panXSize-1,panYSize-1);

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

	    // Draw the Spin Circles
	    g2.setPaint (circCol);
	    g2.drawOval (Math.round(centerX[n]-spinLength-1), 
			 Math.round(centerY[n]-spinLength-1),
			 Math.round(2*spinLength+2),
			 Math.round(2*spinLength+2));

	    // Draw the Labels
	    g2.setPaint (fontCol);
	    ax = "Spherical Representation of Vector";
	    g2.drawString (ax, Math.round
			   (centerX[n]-font.getStringBounds
			    (ax,frc).getWidth()/2),
			   Math.round
			   (panYSize-bord-fontSpace/2+
			    font.getStringBounds(ax,frc).getHeight()/2));

	    // Draw the Axes and Projections onto Axes
	    g2.setPaint (axesCol);
	    g2.drawLine (Math.round(centerX[n]),
			 Math.round(centerY[n]),
			 Math.round(centerX[n]+spinLength*thetaPhiToX(0,0)),
			 Math.round(centerY[n]-spinLength*thetaPhiToY(0,0)));
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
	    g2.setPaint (equatorCol);
	    drawLatitude (g2, centerX[n], centerY[n], spinLength, pi/2);
	    g2.setPaint (guideCol);
	    drawLatitude (g2, centerX[n], centerY[n], spinLength, theta);

	    // Draw the Equatorial Projection and Connecting Line
	    g2.setPaint (circCol);
	    g2.drawLine (Math.round(centerX[n]),
			 Math.round(centerY[n]),
			 (int)Math.round(centerX[n]+
					 Math.sin(theta)*spinLength*thetaPhiToX(pi/2,phi)),
			 (int)Math.round(centerY[n]-
					 Math.sin(theta)*spinLength*thetaPhiToY(pi/2,phi)));
	    g2.setStroke (new BasicStroke(1f, BasicStroke.CAP_BUTT, 
					  BasicStroke.JOIN_MITER, 1f, dotline, 0f));
	    g2.setPaint (equatorCol);
	    g2.drawLine (Math.round(centerX[n]+spinLength*thetaPhiToX(theta,phi)),
			 Math.round(centerY[n]-spinLength*thetaPhiToY(theta,phi)),
			 (int)Math.round(centerX[n]+
					 Math.sin(theta)*spinLength*thetaPhiToX(pi/2,phi)),
			 (int)Math.round(centerY[n]-
					 Math.sin(theta)*spinLength*thetaPhiToY(pi/2,phi)));
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

	    /*// Draw the Arcs
	    g2.setPaint (spinCol);
	    float arcl= (spinLength>20f) ? 10f : spinLength/2;
	    int i;
	    for (i=0;i<=30;i++) {
		g2.drawLine*/

	    // Draw the Vector
	    g2.setPaint (spinCol);
	    g2.setStroke (new BasicStroke(2));
	    g2.drawLine (Math.round(centerX[n]),
			 Math.round(centerY[n]),
			 Math.round(centerX[n]+spinLength*thetaPhiToX(theta,phi)),
			 Math.round(centerY[n]-spinLength*thetaPhiToY(theta,phi)));
	    g2.setStroke (new BasicStroke());

	}
    }
    
}

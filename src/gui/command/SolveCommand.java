package gui.command;

import java.awt.*;
import javax.swing.JFrame;
import java.awt.geom.*;
import java.util.*;
import model.geo.*;
import gui.*;
import model.discrete.*;

/**
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since October 2005
 */
public class SolveCommand implements Command {
    
    private Interface parent;
	private GeometricModel gmodel;
	private DiscreteModel dmodel;
	
	private ArrayList discElms;
	private ArrayList discSbars;
	
	private double dim = 0;
	private double sin = 0;
	private double cos = 0;
	
	private int iteration = 0;
	private int maxIteration = 0;
	
	private double x0 = 0;
	private double x100 = 0;
	private double incr = 0;
	
	private double dn = 0;
	private double h = 0;
	private double d = 0;
	
	private boolean doNotResist = false;
	
	private double tol = 0.000001;
	
//*****************************************************************************
	
    /** Creates new SolveCommand */
    public SolveCommand(JFrame parent) {
        this.parent = (Interface)parent;
    }
    
//*****************************************************************************
	
	/** Execute method. */
	public void execute() {
		
		//Setting variables value to 0 or false
		dim = 0;
		maxIteration = 0;
		
		doNotResist = false;
		
		long startTime = System.currentTimeMillis();
		
		gmodel = parent.getModel().getGModel();
		
		dmodel = parent.getModel().getDiscreteModel();
		dmodel.getDiagramPointsList().clear();
		discElms = dmodel.getDiscElementsList();
		discSbars = dmodel.getDiscSteelBarsList();
		maxIteration = parent.getModel().getState().maxIteration;
		
		DiscretizeCommand cmd = new DiscretizeCommand(parent); 
		cmd.execute();
		
		double theta = 0;
		
		while (theta <= 360) {
			
			System.out.println(" ");
			System.out.println("teta = " + theta);
			
			x0 = 0;
			x100 = 0;
			incr = 0;
			dn = 0;
			h = 0;
			d = 0;
			sin = 0;
			cos = 0;
			iteration = 0;
			boolean resultOK = false;
			
			changeCoords(theta);
			
			while (!resultOK) {
				iteration++;
				calculateSectionProperties();
				calculateTensions();
				double nd[] = calculateNd();
				resultOK = checkNd(nd);
				
				/*
				if (iteration == 500) {
					System.out.println("d = " + d);
					System.out.println("dn = " + dn);
					System.out.println("h = " + h);
					System.out.println("ecd = " + dmodel.getEpsilonCD());
				}
				
				if (theta == 330 && iteration==500) {
					System.out.println("X0 = " + x0);
					ListIterator des = discElms.listIterator();
					System.out.println("ELEMENTO DE CONCRETO");
					while (des.hasNext()) {
						DiscreteElement de = (DiscreteElement) des.next();
						System.out.println("ei = " + de.getEpsilonI());
						System.out.println("sigmaCD = " + de.getSigmaCD());
						System.out.println("sigmaSD = " + de.getSigmaSD());
					}
					System.out.println("BARRAS DE AÇO");
					ListIterator dsbs = discSbars.listIterator();
					while (dsbs.hasNext()) {
						DiscreteSteelBar dsb = (DiscreteSteelBar) dsbs.next();
						System.out.println("ei = " + dsb.getEpsilonI());
						System.out.println("sigmaCD = " + dsb.getSigmaCD());
						System.out.println("sigmaSD = " + dsb.getSigmaSD());
					}
				}
				*/
			}
			
			
			System.out.println("iteration = " + iteration);
			System.out.println("x0 de equilibrio = " + x0);
			System.out.println(" ");
			
			
			IPoint p = calculateMxMy();
			dmodel.addDiagramPoint(p);
			
			theta += parent.getModel().getState().deltaTheta;
			
		}
		
		this.parent.getDiagramView().getController().compose();
		this.parent.getDiagramView().getDrawingArea().setZoom(parent.getDiagramView().getViewState().getLimits());
		this.parent.getDiagramView().getDrawingArea().repaint();
		
		this.parent.problemSolved();
		
		long endTime = System.currentTimeMillis();
		long dif = (endTime - startTime);
		if (dif == 0) {
			parent.addOutputText("Problem solved in 1 milisecond.");
		} else {
			dif = dif/1000;
			parent.addOutputText("Problem solved in " + dif + " seconds.");
		}
		
	}
    
//*****************************************************************************
	
	public void changeCoords(double theta) {
		
		//Calculating new coordinates in relation to rotated axis
		sin = Math.sin(Math.toRadians(theta));
		cos = Math.cos(Math.toRadians(theta));
		double xmin = 0;
		double xmax = 0;
		
		ListIterator des = discElms.listIterator();
		while (des.hasNext()) {
			DiscreteElement de = (DiscreteElement) des.next();
			dim = de.getDimension();
			double x2 = cos*de.getXGC() + sin*de.getYGC();
			double y2 = -sin*de.getXGC() + cos*de.getYGC();
			de.setX2(x2);
			de.setY2(y2);
			if(x2<xmin) xmin=x2;
			if(x2>xmax) xmax=x2;
		}
		ListIterator dsbs = discSbars.listIterator();
		while (dsbs.hasNext()) {
			DiscreteSteelBar dsb = (DiscreteSteelBar) dsbs.next();
			double x2 = cos*dsb.getXGC() + sin*dsb.getYGC();
			double y2 = -sin*dsb.getXGC() + cos*dsb.getYGC();
			dsb.setX2(x2);
			dsb.setY2(y2);
		}
		
		x0 = xmin - (dim/2) * (Math.abs(sin) + Math.abs(cos));
		x100 = xmax + (dim/2) * (Math.abs(sin) + Math.abs(cos));
		incr = (x100 - x0)/(maxIteration/3);
		
	}
	
//*****************************************************************************
	
	public void calculateSectionProperties(){
		
		double dimin = 0;
		double dimax = 0;
		double diSmin1 = 0;
		double diSmin2 = 0;
		boolean firstSteel = true;
		
		//Calculating di, dimax, dimin, h and dn
		ListIterator des = discElms.listIterator();
		while (des.hasNext()) {
			DiscreteElement de = (DiscreteElement) des.next();
			double di = (de.getX2()-x0);
			di = di/100; //Converting from centimetres to metres
			de.setDi(di);
			
			if (des.previousIndex() == 0) {
				dimin = di;
				dimax = di;
			}
			if (firstSteel && de.getType()==de.STEEL) {
				firstSteel = false;
				diSmin1 = di;
			}
			
			if(lessThan(di, dimin)) dimin=di;
			if(greaterThan(di,dimax)) dimax=di;
			if(de.getType()==de.STEEL && lessThan(di,diSmin1)) diSmin1=di;
		}
		ListIterator dsbs = discSbars.listIterator();
		while (dsbs.hasNext()) {
			DiscreteSteelBar dsb = (DiscreteSteelBar) dsbs.next();
			double di = (dsb.getX2()-x0);
			di = di/100; //Converting from centimetres to metres
			dsb.setDi(di);
			
			if (dsbs.previousIndex() == 0) {
				diSmin2 = di;
			}
			if(lessThan(di,diSmin2)) diSmin2=di;
		}
		double diSmin;
		if (firstSteel) diSmin = diSmin2;
		else  {
			diSmin = diSmin1;
			if (lessThan(diSmin2,diSmin)) diSmin = diSmin2;
		}
		
		dn = dimax + (dim/200) * (Math.abs(sin) + Math.abs(cos));
		h = dimax - dimin + dim/100 * (Math.abs(sin) + Math.abs(cos));
		d = dimax - diSmin + (dim/200) * (Math.abs(sin) + Math.abs(cos));
	}
	
//*****************************************************************************
	
	public void calculateTensions() {
		
		double ecd = 0;
		
		//Calculating EpsilonCD
		if(lessThan(dn,h)) {
			ecd = 0.01 /((d/dn) -1);
		} else {
			ecd = 0.002 /(1-((3*h)/(7*dn)));
		}
		if(ecd>0.0035) ecd = 0.0035;
		dmodel.setEpsilonCD(ecd);
		
		//Calculating EpsilonI, sigmaCD and sigmaSD for each element
		ListIterator des = discElms.listIterator();
		while (des.hasNext()) {
			DiscreteElement de = (DiscreteElement) des.next();
			if (de.getType() != de.NOTHING) {
				double ei = ecd * de.getDi()/dn;
				de.setEpsilonI(ei);
				if (de.getType() == de.CONCRETE) {
					double sigmaCD = 0;
					double fck = parent.getModel().getState().fck * 1000000; //Converting from MPa to Pa
					double fc = 0.85* fck / parent.getModel().getState().gamaC;
					if (ei<=0) sigmaCD = 0;
					if (0<ei && ei<0.002) sigmaCD = fc * (1- Math.pow((1- ei/0.002) ,2));
					if (ei>=0.002) sigmaCD = fc;
					de.setSigmaCD(sigmaCD);
				}
				if (de.getType() == de.STEEL) {
					double sigmaSD = 0;
					double fyk =  parent.getModel().getState().fyk * 1000000; //Converting from MPa to Pa
					double fyd = fyk / parent.getModel().getState().gamaS;
					double steelE = parent.getModel().getState().steelE * 1000000; //Converting from MPa to Pa
					double esMax = fyd / steelE;
					if (ei>(-esMax) && ei<esMax) sigmaSD = ei * steelE;
					if (ei>=esMax) sigmaSD = fyd;
					if (ei<=(-esMax)) sigmaSD = -fyd;
					de.setSigmaSD(sigmaSD);
				}
			}
		}
		
		ListIterator dsbs = discSbars.listIterator();
		while (dsbs.hasNext()) {
			DiscreteSteelBar dsb = (DiscreteSteelBar) dsbs.next();
			double ei = ecd * dsb.getDi()/dn;
			dsb.setEpsilonI(ei);
			double sigmaCD = 0;
			double fck = parent.getModel().getState().fck * 1000000; //Converting from MPa to Pa
			double fc = 0.85* fck / parent.getModel().getState().gamaC;
			if (ei<0) sigmaCD = 0;
			if (0<ei && ei<0.002) sigmaCD = fc * (1- Math.pow((1- ei/0.002) ,2));
			if (ei>=0.002) sigmaCD = fc;
			dsb.setSigmaCD(sigmaCD);
			double sigmaSD = 0;
			double fyk =  parent.getModel().getState().fyk * 1000000; //Converting from MPa to Pa
			double fyd = fyk / parent.getModel().getState().gamaS;
			double steelE = parent.getModel().getState().steelE * 1000000; //Converting from MPa to Pa
			double esMax = fyd / steelE;
			if (ei>(-esMax) && ei<esMax) sigmaSD = ei * steelE;
			if (ei>=esMax) sigmaSD = fyd;
			if (ei<=(-esMax)) sigmaSD = -fyd;
			dsb.setSigmaSD(sigmaSD);
		}
	}
	
//*****************************************************************************
	
	/**
	* @return A double vector which contains: [0] The normal force, [1] The compression parcel of the normal force, [2] The tration parcel of the normal force
	*/
	public double[] calculateNd(){
		
		double[] nd3 = new double[3];
		double nd = 0;
		double ndPos = 0;
		double ndNeg = 0;
		
		ListIterator des = discElms.listIterator();
		while (des.hasNext()) {
			DiscreteElement de = (DiscreteElement) des.next();
			double nd1 = 0;
			if (de.getType()==de.CONCRETE) {
				nd1 = Math.pow((de.getDimension()/100), 2) * de.getSigmaCD();
			}
			if (de.getType()==de.STEEL) {
				nd1 = Math.pow((de.getDimension()/100), 2) * de.getSigmaSD();
			}
			nd += nd1;
			if (nd1 > 0) ndPos += nd1;
			if (nd1 < 0) ndNeg -= nd1;
		}
		ListIterator dsbs = discSbars.listIterator();
		while (dsbs.hasNext()) {
			DiscreteSteelBar dsb = (DiscreteSteelBar) dsbs.next();
			double area = Math.pow((dsb.getDiameter()/1000), 2)  * Math.PI /4;
			double nd1 =  area * dsb.getSigmaSD();
			double nd2 = area * dsb.getSigmaCD();
			nd += nd1;
			nd -= nd2;
			if (nd1 > 0) { 
				ndPos += nd1;
				ndPos -= nd2;
			}
			if (nd1 < 0) { 
				ndNeg += nd1;
				ndNeg -= nd2;
			}
		}
		
		nd3[0] = nd;
		nd3[1] = ndPos;
		nd3[2] = ndNeg;
		
		return nd3;
		
	}
	
//*****************************************************************************
	
	public boolean checkNd(double[] nd2){
		
		boolean resultOK = false;
		double resultPrec = parent.getModel().getState().resultPrec / 100;
		double nd0 = parent.getModel().getState().nd0 * 1000;//Converting from kN to N
		double nd = nd2[0];
		
		if (nd0 == 0) {
			double error = Math.abs((-nd2[2] - nd2[1])/nd2[1]);
			if (error <= resultPrec) {
				resultOK = true;
			}
			if (error > resultPrec) {
				resultOK = false;
				x0 += incr;
			}
		}
		
		if (nd0 != 0 ) {
			double error = Math.abs((nd - nd0)/nd0);
			if (error <= resultPrec) {
				resultOK = true;
			}
			if (error > resultPrec) {
				resultOK = false;
				x0 += incr;
			}
		}
		
		
		if (iteration > maxIteration) {
			resultOK = true;
			doNotResist = true;
		}
		
		
		/*
		if (iteration == 250 || iteration == 500) {
			System.out.println("iteration = " + iteration);
			System.out.println("x0 = " + (x0-incr));
			System.out.println("nd = " + nd2[0]);
			System.out.println("nd+ = " + nd2[1]);
			System.out.println("nd- = " + nd2[2]);
		}*/
		
		return resultOK;
	}
	
//*****************************************************************************
	
	public IPoint calculateMxMy(){
		
		double mxd = 0;
		double myd = 0;
		
		ListIterator des = discElms.listIterator();
		while (des.hasNext()) {
			DiscreteElement de = (DiscreteElement) des.next();
			double mxd1 = 0;
			double myd1 = 0;
			if (de.getType()==de.CONCRETE) {
				mxd1 = Math.pow((de.getDimension()/100), 2) * de.getSigmaCD() * de.getYGC()/100;
				myd1 = Math.pow((de.getDimension()/100), 2) * de.getSigmaCD() * de.getXGC()/100;
			}
			if (de.getType()==de.STEEL) {
				mxd1 = Math.pow((de.getDimension()/100), 2) * de.getSigmaSD() * de.getYGC()/100;
				myd1 = Math.pow((de.getDimension()/100), 2) * de.getSigmaSD() * de.getXGC()/100;
			}
			mxd += mxd1;
			myd += myd1;
		}
		ListIterator dsbs = discSbars.listIterator();
			while (dsbs.hasNext()) {
			DiscreteSteelBar dsb = (DiscreteSteelBar) dsbs.next();
			double area = Math.pow((dsb.getDiameter()/1000), 2)  * Math.PI /4;
			
			double mxd1 = area * dsb.getSigmaSD() * dsb.getYGC()/100;
			double mxd2 = area * dsb.getSigmaCD() * dsb.getYGC()/100;
			double myd1 = area * dsb.getSigmaSD() * dsb.getXGC()/100;
			double myd2 = area * dsb.getSigmaCD() * dsb.getXGC()/100;
			mxd += mxd1;
			mxd -= mxd2;
			myd += myd1;
			myd -= myd2;
		}
		
		IPoint p = new IPoint(mxd, myd);
		return (p);
	}
	
//*****************************************************************************
	
    /**
     * Impossible to undo - not implemented.
     */
    public void undo() {}
    
//*****************************************************************************
	
    /**
     * Impossible to redo - not implemented.
     */
    public void redo() {}
    
//*****************************************************************************
	
	private boolean greaterThan(double a, double b) {
		
		boolean result = false;
		
		if (a>(b-tol)) result = true;
		
		return result;
	}
	
//*****************************************************************************
	
	private boolean lessThan(double a, double b) {
		
		boolean result = false;
		
		if (a<(b+tol)) result = true;
		
		return result;
	}
	
//*****************************************************************************
}

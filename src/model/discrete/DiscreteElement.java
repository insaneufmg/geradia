package model.discrete;

/** Class representing a discrete element.
* @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
* @since October 2005
*/
public class DiscreteElement implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public transient static final int NOTHING = 1;
	public transient static final int CONCRETE = 2;
	public transient static final int STEEL = 3;
	
	private double x;
	private double y;
	private double dimension;
	private int type;
	
	private double xgc;
	private double ygc;
	private double x2;
	private double y2;
	private double di;
	private double epsilonI;
	private double sigmaCD;
	private double sigmaSD;
	
//*****************************************************************************
	
	/** Default constructor.*/
	public DiscreteElement() {
	}
	
	/** Constructor.
	*@param x The central X coordinate.
	*@param y The central Y coordinate.
	*@param dim The dimension.
	*/
	public DiscreteElement(double x, double y, double dim) {
		this.x = x;
		this.y = y;
		this.dimension = dim;
	}
	
//*****************************************************************************
	
	/** Sets the central coordinates.
	*@param x The central X coordinate.
	*@param y The central Y coordinate.
	*/
	public void setCoords(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
//*****************************************************************************
	
	/** Sets the central X coordinate.
	*@param x The central X coordinate.
	*/
	public void setX(double x) {
		this.x = x;
	}
	
//*****************************************************************************
	
	/** Sets the central Y coordinate.
	*@param y The central Y coordinate.
	*/
	public void setY(double y) {
		this.y = y;
	}
	
//*****************************************************************************
	
	/** Gets this DiscreteElement's central X coordinate.
	*@return This DiscreteElement's central X coordinate.
	*/
	public double getX() {
		return x;
	}
	
//*****************************************************************************
	
	/** Gets this DiscreteElement's central Y coordinate.
	*@return This DiscreteElement's central Y coordinate.
	*/
	public double getY() {
		return y;
	}
	
//*****************************************************************************
	
	/** Sets the dimension.
	*@param dim The dimension.
	*/
	public void setDimension(double dim) {
		this.dimension = dim;
	}
	
//*****************************************************************************
	
	/** Gets this DiscreteElement's dimension.
	*@return This DiscreteElement's dimension.
	*/
	public double getDimension() {
		return dimension;
	}
	
//*****************************************************************************
	
	/** Sets the Type.
	*@param type The type.
	*/
	public void setType(int type) {
		this.type = type;
	}
	
//*****************************************************************************
	
	/** Gets this DiscreteElement's type.
	*@return This DiscreteElement's type.
	*/
	public int getType() {
		return type;
	}
	
//*****************************************************************************
	
	/** Sets the coordinate X in relation to the gravity center.
	*@param xgc The coordinate X in relation to the gravity center.
	*/
	public void setXGC(double xgc) {
		this.xgc = xgc;
	}
	
//*****************************************************************************
	
	/** Gets this DiscreteElement's coordinate X in relation to the gravity center.
	*@return This DiscreteElement's coordinate X in relation to the gravity center.
	*/
	public double getXGC() {
		return xgc;
	}
	
//*****************************************************************************
	
	/** Sets the coordinate X in relation to the gravity center and rotate by an angle.
	*@param x2 The coordinate X in relation to the gravity center and rotate by an angle.
	*/
	public void setX2(double x2) {
		this.x2 = x2;
	}
	
//*****************************************************************************
	
	/** Gets this DiscreteElement's coordinate X in relation to the gravity center and rotate by an angle.
	*@return This DiscreteElement's coordinate X in relation to the gravity center and rotate by an angle.
	*/
	public double getX2() {
		return x2;
	}
	
//*****************************************************************************
	
	/** Sets the coordinate Y in relation to the gravity center.
	*@param ygc The coordinate Y in relation to the gravity center.
	*/
	public void setYGC(double ygc) {
		this.ygc = ygc;
	}
	
//*****************************************************************************
	
	/** Gets this DiscreteElement's coordinate Y in relation to the gravity center.
	*@return This DiscreteElement's coordinate Y in relation to the gravity center..
	*/
	public double getYGC() {
		return ygc;
	}
	
//*****************************************************************************
	
	/** Sets the coordinate Y in relation to the gravity center and rotated by an angle.
	*@param ygc The coordinate Y in relation to the gravity center and rotated by an angle.
	*/
	public void setY2(double y2) {
		this.y2 = y2;
	}
	
//*****************************************************************************
	
	/** Gets this DiscreteElement's coordinate Y in relation to the gravity center and rotated by an angle.
	*@return This DiscreteElement's coordinate Y in relation to the gravity center and rotated by an angle.
	*/
	public double getY2() {
		return y2;
	}
	
//*****************************************************************************
	
	/** Sets the di variable.
	*@param di The di variable.
	*/
	public void setDi(double di) {
		this.di = di;
	}
	
//*****************************************************************************
	
	/** Gets this DiscreteElement's di variable.
	*@return This DiscreteElement's di variable.
	*/
	public double getDi() {
		return di;
	}
	
//*****************************************************************************
	
	/** Sets the epsilonI variable.
	*@param epsilonI The epsilonI variable.
	*/
	public void setEpsilonI(double epsilonI) {
		this.epsilonI = epsilonI;
	}
	
//*****************************************************************************
	
	/** Gets this DiscreteElement's epsilonI variable.
	*@return This DiscreteElement's epsilonI variable.
	*/
	public double getEpsilonI() {
		return epsilonI;
	}
	
//*****************************************************************************
	
	/** Sets the sigmaCD variable.
	*@param sigmaCD The sigmaCD variable.
	*/
	public void setSigmaCD(double sigmaCD) {
		this.sigmaCD = sigmaCD;
	}
	
//*****************************************************************************
	
	/** Gets this DiscreteElement's sigmaCD variable.
	*@return This DiscreteElement's sigmaCD variable.
	*/
	public double getSigmaCD() {
		return sigmaCD;
	}
	
//*****************************************************************************
	
	/** Sets the sigmaSD variable.
	*@param sigmaSD The sigmaSD variable.
	*/
	public void setSigmaSD(double sigmaSD) {
		this.sigmaSD = sigmaSD;
	}
	
//*****************************************************************************
	
	/** Gets this DiscreteElement's sigmaSD variable.
	*@return This DiscreteElement's sigmaSD variable.
	*/
	public double getSigmaSD() {
		return sigmaSD;
	}
	
//*****************************************************************************
}

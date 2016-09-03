package gui.dialog;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.Toolkit;
import java.text.*;
import java.util.StringTokenizer;
import java.lang.NumberFormatException;

/**
 * A class representing a field for decimal numbers.
 * For example:
 * <pre>
 *    DecimalField field = new DecimalField(10.25, 8, f);
 *    panel.add(field);
 * </pre>
 *
 * @see    javax.swing.JTextField
 */
public class DecimalField extends JTextField {
	
    /**
     * The number formater
     */
    private NumberFormat format;
	
//*****************************************************************************
	
    /**
     * Constructor method.
     *
     * @param    value    The field value.
     * @param    columns  The number os columns to show.
     * @param    f        The number formater.
     */
    public DecimalField(double value, int columns, NumberFormat f) {
        super(columns);
        format = f;
        setValue(value);
    }
	
//*****************************************************************************
	
    /**
     * Returns the field value - A double.
     *
     * @return   The field value.
     */
    public double getValue() {
        double retVal = 0.0;
		
        try {
			
			String str = getText();
			int ePos = 0;
			ePos = str.indexOf('e');
			if (ePos == -1)
				ePos = str.indexOf('E');
			if (ePos == -1) {
				retVal = format.parse(getText()).doubleValue();
			} else if (ePos == 0) {
				String str2 = str.substring(1);
				double val = Math.pow(10, Integer.parseInt(str2));
				String str3 = String.valueOf(val);
				retVal = format.parse(str3).doubleValue();
			}else {
				String str1 = str.substring(0, ePos);
				String str2 = str.substring(ePos+1);
				double val = Double.parseDouble(str1) * Math.pow(10, Integer.parseInt(str2));
				String str3 = String.valueOf(val);
				retVal = format.parse(str3).doubleValue();
			}
			
		} catch (ParseException e) {
            Toolkit.getDefaultToolkit().beep();
            setText("Invalid Data");
		} catch (NumberFormatException e) {
			Toolkit.getDefaultToolkit().beep();
			setText("Invalid Data");
		}
        return retVal;
    }
	
//*****************************************************************************
	
    /**
     * Sets the field value - A double.
     *
     * @param    value  The desired value to set.
     */
    public void setValue(double value) {
        setText(format.format(value));
    }
	
//*****************************************************************************
}

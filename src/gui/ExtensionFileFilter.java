package gui;

import java.io.File;
import java.util.Hashtable;
import java.util.Enumeration;
import javax.swing.*;
import javax.swing.filechooser.*;

/**
 * A convenience implementation of FileFilter that filters out
 * all files except for those type extensions that it knows about.
 * This is based on ExampleFileFilter of Sun Microsystems
 * The Java Tutorial (see&nbsp;
 * <a HREF="http://java.sun.com/docs/books/tutorial/uiswing/components/filechooser.html">
 * http://java.sun.com/docs/books/tutorial/uiswing/components/filechooser.html</a>).<br>
 *
 * Extensions are of the type ".foo", which is typically found on
 * Windows and Unix boxes, but not on Macinthosh. Case is ignored.<br><br>
 *
 * Example - create a new filter that filerts out all files
 * but gif and jpg image files:<br>
 *
 *<pre>
 *   JFileChooser chooser = new JFileChooser();
 *   ExampleFileFilter filter = new ExampleFileFilter(
 *                 new String{"gif", "jpg"}, "JPEG & GIF Images")
 *   chooser.addChoosableFileFilter(filter);
 *   chooser.showOpenDialog(this);
 *</pre>
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 */
public class ExtensionFileFilter extends FileFilter {
	
    private static String TYPE_UNKNOWN = "Type Unknown";
    private static String HIDDEN_FILE = "Hidden File";
	
    private Hashtable filters = null;
    private String description = null;
    private String fullDescription = null;
    private boolean useExtensionsInDescription = true;
	
//*****************************************************************************
	
    /**
     * Creates a file filter. If no filters are added, then all
     * files are accepted.
     *
     * @see #addExtension
     */
    public ExtensionFileFilter() {
	this.filters = new Hashtable();
    }
	
//*****************************************************************************
	
    /**
     * Creates a file filter that accepts files with the given extension.
     *
     * Example:<br>
     * <pre>
     * new ExampleFileFilter("jpg");
     * </pre>
     *
     * @see #addExtension
     * @param extension  The desired extension String. 
     */
    public ExtensionFileFilter(String extension) {
	this(extension,null);
    }
	
//*****************************************************************************
	
    /**
     * Creates a file filter that accepts the given file type.
     *
     * Example:<br>
     * <pre>
     * new ExampleFileFilter("jpg", "JPEG Image Images");
     * </pre>
     *
     * Note that the "." before the extension is not needed. If
     * provided, it will be ignored.
     *
     * @see #addExtension
     * @param extension The desired extension String. 
     * @param description The String describing the file type.  
     */
    public ExtensionFileFilter(String extension, String description) {
	this();
	if(extension!=null) addExtension(extension);
 	if(description!=null) setDescription(description);
    }
	
//*****************************************************************************
	
    /**
     * Creates a file filter from the given string array.
     *
     * Example:<br>
     * <pre>
     * new ExampleFileFilter(String {"gif", "jpg"});
     * </Pre>
     *
     * Note that the "." before the extension is not needed adn
     * will be ignored.
     *
     * @see #addExtension
     * @param filters  An array of String extensions.
     */
    public ExtensionFileFilter(String[] filters) {
	this(filters, null);
    }
	
//*****************************************************************************
	
    /**
     * Creates a file filter from the given string array and description.
     *
     * Example:<br>
     * <pre>
     * new ExampleFileFilter(String {"gif", "jpg"}, "Gif and JPG Images");
     * </pre>
     *
     * Note that the "." before the extension is not needed and will be ignored.
     *
     * @see #addExtension
     * @param filters An array of String extensions.
     * @param description The String describing the filter.
     */
    public ExtensionFileFilter(String[] filters, String description) {
	this();
	for (int i = 0; i < filters.length; i++) {
	    // add filters one by one
	    addExtension(filters[i]);
	}
 	if(description!=null) setDescription(description);
    }
	
//*****************************************************************************
	
    /**
     * Returns true if this file should be shown in the directory pane,
     * false if it shouldn't.
     *
     * Files that begin with "." are ignored.
     *
     * @see #getExtension
     * @param f  A file.
     * @see   java.io.File
     * @return  A boolean if accepted or not.
     */
    public boolean accept(File f) {
	if(f != null) {
	    if(f.isDirectory()) {
		return true;
	    }
	    String extension = getExtension(f);
	    if(extension != null && filters.get(getExtension(f)) != null) {
		return true;
	    };
	}
	return false;
    }
	
//*****************************************************************************
	
    /**
     * Returns the extension portion of the file's name .
     *
     * @see #getExtension
     * @see FileFilter#accept
     * @param f  A file.
     * @see   java.io.File
     * @return  The String extension.
     */
     public String getExtension(File f) {
	if(f != null) {
	    String filename = f.getName();
	    int i = filename.lastIndexOf('.');
	    if(i>0 && i<filename.length()-1) {
		return filename.substring(i+1).toLowerCase();
	    };
	}
	return null;
    }
	
//*****************************************************************************
	
    /**
     * Adds a filetype "dot" extension to filter against.
     *
     * For example: the following code will create a filter that filters
     * out all files except those that end in ".jpg" and ".tif":<br>
     *
     * <pre>
     *  ExampleFileFilter filter = new ExampleFileFilter();
     *  filter.addExtension("jpg");
     *  filter.addExtension("tif");
     * </pre>
     *
     * Note that the "." before the extension is not needed and will be ignored.
     * @param extension An String of the desired extension. 
     */
    public void addExtension(String extension) {
	if(filters == null) {
	    filters = new Hashtable(5);
	}
	filters.put(extension.toLowerCase(), this);
	fullDescription = null;
    }
	
//*****************************************************************************
	
    /**
     * Returns the human readable description of this filter.
     *
     * For example: "JPEG and GIF Image Files (*.jpg, *.gif)"
     *
     * @see #setDescription
     * @see #setExtensionListInDescription
     * @see #isExtensionListInDescription
     * @see FileFilter#getDescription
     * @return The String description of the filter.
     */
    public String getDescription() {
	if(fullDescription == null) {
	    if(description == null || isExtensionListInDescription()) {
 		fullDescription = description==null ? "(" : description + " (";
		// build the description from the extension list
		Enumeration extensions = filters.keys();
		if(extensions != null) {
		    fullDescription += "." + (String) extensions.nextElement();
		    while (extensions.hasMoreElements()) {
			fullDescription += ", " + (String) extensions.nextElement();
		    }
		}
		fullDescription += ")";
	    } else {
		fullDescription = description;
	    }
	}
	return fullDescription;
    }
	
//*****************************************************************************
	
    /**
     * Sets the human readable description of this filter.
     *
     * For example:<br>
     * <pre>
     * filter.setDescription("Gif and JPG Images");
     * </pre>
     *
     * @see #setDescription
     * @see #setExtensionListInDescription
     * @see #isExtensionListInDescription
     * @param description  The desired String description of the filter.
     */
    public void setDescription(String description) {
	this.description = description;
	fullDescription = null;
    }
	
//*****************************************************************************
	
    /**
     * Determines whether the extension list (.jpg, .gif, etc) should
     * show up in the human readable description.
     *
     * Only relevent if a description was provided in the constructor
     * or using setDescription();
     *
     * @see #getDescription
     * @see #setDescription
     * @see #isExtensionListInDescription
     * @param b A boolean to be set.
     */
    public void setExtensionListInDescription(boolean b) {
	useExtensionsInDescription = b;
	fullDescription = null;
    }
	
//*****************************************************************************
	
    /**
     * Returns whether the extension list (.jpg, .gif, etc) should
     * show up in the human readable description.
     *
     * Only relevent if a description was provided in the constructor
     * or using setDescription();
     *
     * @see #getDescription
     * @see #setDescription
     * @see #setExtensionListInDescription
     * @return A boolean if it is to show or not the extensions.
     */
    public boolean isExtensionListInDescription() {
	return useExtensionsInDescription;
    }
	
//*****************************************************************************

}
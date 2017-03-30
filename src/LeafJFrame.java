/**
 * The program would be a generator for life-like tree leaf images. The leaves would have variance within 
 * species, and would have the feature of a branching vein generation system. The leaves would be 
 * generated in similar structures as real-life leaves. The structures would for example include veins 
 * extending from one point on the leaf, veins branching from one midrib along the length of the leaf, 
 * and more. While veins are key features of leaves, this is put in place mainly to allow for the easier 
 * creation of the actual leaf. The veins would determine the path of the margin of the leaf, enclosing 
 * the middle of the leaf. The margins would also be customizable, with the margins able to be specified 
 * as a given pattern (smooth, finely-toothed, saw-toothed, etc.). 
 * 
 * This program would have applications mainly limited to research. The main inspiration for this program 
 * is my science fair project, where it was eventually shown that I needed a high quantity of leaf images 
 * to improve the accuracy of the leaf-identification program. This program would allow me to program in 
 * species of leaves and then batch-create hundreds, perhaps even thousands of leaf images. Other research 
 * projects that involve computer vision could also perhaps benefit from this project. Additionally, the 
 * program’s methods could perhaps be useful to graphic designers who need random leaves for their project. 
 * For example, the leaves could be used in a game to make trees look life-like.
 */

// import statements
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * The main JFrame itself. This is what you see when the program runs.
 * @author Patrick Thomas
 *
 */
public class LeafJFrame
{
	/**
	 * Initialize the variables of the JFrame components.
	 */
	private JFrame main_frame;
	private JLabel header_label;
	private JLabel status_label;
	private JPanel control_panel;
	private JLabel msg_label;
	
	/**
	 * Other variables
	 */
	private final String WINDOW_TITLE = "Leaf Generator";
	private final int DEFAULT_WIDTH = 400;
	private final int DEFAULT_HEIGHT = 400;
	
	private final WindowAdapter CLOSE_ADAPTER = new WindowAdapter() 
	{
		public void windowClosing(WindowEvent windowEvent)
		{
			System.exit(0);
		} // end windowClosing
	} /* end new WindowAdapter */;

	/**
	 * Constructor for the JFrame.
	 */
	public LeafJFrame()
	{
		prepareGUI();
	} // end constructor
	
	/**
	 * The main code for the Java code.
	 * @param args Any arguments passed to the Java interpreter
	 */
	public static void main(String[] args)
	{
		LeafJFrame swingContainer = new LeafJFrame();  
		swingContainer.showJFrame();
	} // end main
	
	/**
	 * Initialize all elements of the JFrame
	 */
	private void prepareGUI()
	{
		// create frame
		main_frame = new JFrame(WINDOW_TITLE);
		main_frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);	// set size
		main_frame.setLayout(new GridLayout(3, 1));			// set layout
      
		main_frame.addWindowListener(CLOSE_ADAPTER);		// add close window adapter
		
		header_label = new JLabel("", JLabel.CENTER);        
		status_label = new JLabel("", JLabel.CENTER);    
		status_label.setSize(350, 100);
		
		msg_label = new JLabel("", JLabel.CENTER);

		control_panel = new JPanel();
		control_panel.setLayout(new FlowLayout());

		main_frame.add(header_label);
		main_frame.add(control_panel);
		main_frame.add(status_label);
		main_frame.setVisible(true);  
	} // end prepareGui
	
	/**
	 * 
	 */
	private void showJFrame()
	{
		// set the text on the already-created labels
		msg_label.setText("Welcome to TutorialsPoint SWING Tutorial.");
		header_label.setText("Container in action: JFrame"); 
		
		// pop-up frame
		final JFrame frame = new JFrame();
		frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		frame.setLayout(new FlowLayout());       
		frame.add(msg_label);
		
		// make the frame able to be closed
		frame.addWindowListener(CLOSE_ADAPTER);
		
		// place button
		JButton okButton = new JButton("Open a Frame");
		okButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				status_label.setText("A Frame shown to the user.");
				frame.setVisible(true);
			}
		});
		control_panel.add(okButton);
		main_frame.setVisible(true);  
	} // end showJFrameDemo
} // end LeafJFrame
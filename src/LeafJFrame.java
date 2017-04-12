/*
 * LeafJFrame.java
 * 
 * Original project proposal:
 * 
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
import javax.swing.*;

public class LeafJFrame
{
    final static int windowWidth = 400;

    public void addComponentToPane(Container pane)
    {
    	JPanel instructions = new JPanel() {
        	// to statisfy warning
			private static final long serialVersionUID = 1L;

			// Make the panel wider than it really needs, so
            // the window's wide enough for the tabs to stay
            // in one row.
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width = windowWidth;
                return size;
            }
        };
        instructions.add(new JLabel("Instructions are here"));
    	
        // create the button panel
        JPanel buttonPanel = new JPanel();
        
        buttonPanel.add(new JButton("Button 1"));
        buttonPanel.add(new JButton("Button 2"));
        buttonPanel.add(new JButton("Button 3"));

        pane.add(instructions, BorderLayout.PAGE_START);
        pane.add(buttonPanel, BorderLayout.CENTER);
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("TabDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        LeafJFrame gui = new LeafJFrame();
        gui.addComponentToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        /* Use an appropriate Look and Feel */
        try {
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        
        
        double[] primaryVeinsParameters = {
        		2.0, 	// number of veins
        		30.0, 	// angle of veins
        		4.0, 	// length of vein 1
        		3.0		// length of vein 2
        		};
        
        LeafArrayGenerator arrayGen = new LeafArrayGenerator(
        		50,		// width 
        		15,		// height
        		0.60, 	// midrib length proportion
        		30, 	// midrib actual length (unused)
        		0.10,	// midrib start offset proportion
        		"pinnate",
        		primaryVeinsParameters
        		);
        arrayGen.printBoolean();
        
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
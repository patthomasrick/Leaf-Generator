/**
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
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
 
/* FrameDemo.java requires no other files. */
public class LeafJFrame
{
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI()
    {
        //Create and set up the window.
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Leaf Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Container menuControls = frame.getContentPane();
        JButton helloButton = new JButton("Hello");
        menuControls.add(helloButton);
 
//        JLabel emptyLabel = new JLabel("Hello");
//        emptyLabel.setPreferredSize(new Dimension(800, 600));
//        frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
        
        //Display the window.
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setSize(800, 600);
        frame.setVisible(true);
    } // end createAndShowGUI
 
    public static void main(String[] args)
    {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                createAndShowGUI();
            } // end run()
        });
    } // end main
} // end LeafJFrame
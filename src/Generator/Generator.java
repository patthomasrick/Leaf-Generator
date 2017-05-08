package Generator;

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
 * program's methods could perhaps be useful to graphic designers who need random leaves for their project.
 * For example, the leaves could be used in a game to make trees look life-like.
 */

// imports
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * Generates an array that displays a leaf
 *
 * @author Patrick Thomas
 */
public class Generator
{

    // Global variables per Generator
    public Boolean[][] leafArray;
    public Generator.Veins veins;
    public Generator.Lamina lamina;

    double[] primaryVeinGenParams;

    /**
     * Constructor for LeafArrayGenerator
     * @param width width of the resultant image
     * @param height height of the resultant image
     * @param midribLengthProportion how long the midrib is compared to the
     * width of the image
     * @param midribActualLength how long the midrib is supposed to be in units
     * of your choice
     * @param midribOffsetProportion how far the midrib is from the edge of the
     * image
     * @param primaryVeinsStyle the style of the primary veins
     * @param primaryVeinsParameters the parameters of the primary veins
     */
    public Generator(
            // image
            int width,
            int height,
            // midrib
            double midribLengthProportion,
            double midribActualLength,
            double midribOffsetProportion,
            // primary veins
            String primaryVeinsStyle,
            double[] primaryVeinsParameters,
            // lamina
            String laminaStyle,
            double[] laminaArgs)
    {
        // initialize the leaf array
        leafArray = new Boolean[height][width];
        this.primaryVeinGenParams = primaryVeinsParameters;

        for (int h = 0; h < leafArray.length; h += 1)
        {
            for (int w = 0; w < leafArray[0].length; w += 1)
            {
                leafArray[h][w] = false;
            } // end for w values
        } // end for h values

        // create the vein structures
        veins = new Generator.Veins(
                this.leafArray,
                midribLengthProportion,
                midribActualLength,
                midribOffsetProportion,
                primaryVeinsStyle);
        
        // create lamina
        this.lamina = new Generator.Lamina(
                leafArray, 
                this.veins.midrib, 
                laminaStyle, 
                laminaArgs
        );
        
        // update the boolean leaf itself by "casting" the leaf onto the array
        leafArray = veins.midrib.castMidrib(leafArray);
        leafArray = veins.primaryVeins.castVeins(leafArray, primaryVeinsParameters, veins.midrib);
    } // end LeafArrayGenerator constructor

    /**
     * Print the leaf array in text form to sys out
     */
    public void printBoolean()
    {
        for (int h = 0; h < leafArray.length; h += 1)
        {
            for (int w = 0; w < leafArray[0].length; w += 1)
            {
                if (leafArray[h][w] == true)
                {
                    System.out.print("#");
                } else
                {
                    System.out.print(".");
                }
            } // end for w values

            System.out.print("\n");
        } // end for h values
    } // end printBoolean

    /**
     * Creates a buffered image object for saving to the hard drive.
     * @return BufferedImage of generated leaf
     */
    public BufferedImage createBufferedImage()
    {
        // initialize the buffered image
        BufferedImage outputImage = new BufferedImage(
                leafArray[0].length,
                leafArray.length,
                BufferedImage.TYPE_INT_ARGB);
        
        // created the associated graphics object to draw to
        Graphics2D g2 = outputImage.createGraphics();

        // turn on antialiasing
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        // set the background's color
        g2.setColor(GenColor.lamina);
        g2.fillRect(0, 0, leafArray[0].length, leafArray.length);

        // get the data to draw the midrib from the midrib
        int[][] midribData = this.veins.midrib.getMidribPoints(leafArray);

        // draw midrib
        g2.setColor(GenColor.midrib);
        g2.drawPolyline(midribData[0], midribData[1], midribData.length);
        
        // draw primary veins using built in method
        this.veins.primaryVeins.drawVeins(leafArray, 
                this.primaryVeinGenParams, 
                this.veins.midrib, 
                this.lamina,
                g2);
        
        // draw margin of lamina using built in method
        g2.setColor(GenColor.lamina);
        this.lamina.styles.drawLinear(g2);
        
        // fill the rest of the image that isn't leaf
        g2.setColor(GenColor.background);
        // left and right sides of the image
        g2.fillRect(
                0, 
                0, 
                this.veins.midrib.startOffset, 
                leafArray.length
        );
        g2.fillRect(
                this.veins.midrib.startOffset + this.veins.midrib.length - 1, 
                0, 
                leafArray[0].length, 
                leafArray.length
        );
        
        // now top and bottom
        g2.fillRect(
                0,
                0,
                leafArray[0].length,
                (int) ((leafArray.length - lamina.args[0] * leafArray.length)/2)
        );
        g2.fillRect(
                0,
                (int) ((leafArray.length/2 + lamina.args[0] * leafArray.length / 2)),
                leafArray[0].length,
                leafArray.length
        );
        return outputImage;
    } // end createBufferedImage

    /**
     * A class/container for the various styles and types of veins within a 
     * leaf.
     */
    public class Veins
    {
        // have variables for the types of veins
        Generator.Veins.Midrib midrib;
        Generator.Veins.PrimaryVeins primaryVeins;

        /**
         * Constructor. Creates the types of veins needed in one interface
         * as long as the correct parameters are given in full.
         * @param leafArray The boolean array of the leaf 
         * @param midribLengthProportion The length of the midrib in proportion
         * to the width of the array
         * @param midribActualLength The actual length of the midrib. Unused.
         * @param midribOffsetProportion The size of the margin of the image
         * in proportion to the width of the array.
         * @param primaryVeinsStyle The style of the primary veins, should be a
         * string such as "pinnate" or "parallel"
         */
        public Veins(
                Boolean[][] leafArray,
                double midribLengthProportion,
                double midribActualLength,
                double midribOffsetProportion,
                String primaryVeinsStyle)
        {
            // create midrib
            midrib = new Generator.Veins.Midrib(
                    midribLengthProportion,
                    midribActualLength,
                    (int) leafArray[0].length,
                    midribOffsetProportion);
            
            // create primary veins
            primaryVeins = new Generator.Veins.PrimaryVeins(
                    leafArray,
                    midrib.startOffset,
                    primaryVeinsStyle);
        } // end Veins

        /**
         * Midrib class. Deals with the center vein down the center of the leaf.
         */
        public class Midrib
        {
            // variables
            double lengthProportion;
            double actualLength;        // unused
            int length;                 // length in pixels
 
            double startOffsetProportion;
            int startOffset;            // in pixels

            public final int DEFAULT_WIDTH = 1;

            /**
             * Constructor for midrib.
             *
             * @param lengthProportion Proportion of the length of the midrib to
             * the length of the actual image
             * @param actualLength The midrib's length in any unit
             * @param arrayWidth The width of the array
             * @param startOffsetProportion How far the midrib starts from the 
             * edge of the image
             */
            public Midrib(double lengthProportion, double actualLength, int arrayWidth,
                    double startOffsetProportion)
            {
                this.lengthProportion = lengthProportion;
                this.length = (int) (this.lengthProportion * arrayWidth);
                this.actualLength = actualLength;

                this.startOffsetProportion = startOffsetProportion;
                this.startOffset = (int) (this.startOffsetProportion * arrayWidth);
            } // end Midrib constructor

            /**
             * Takes the current midrib parameters and prints them on top of a
             * given array.
             *
             * @param leafArray Array to edit
             * @return Edited array
             */
            public Boolean[][] castMidrib(Boolean[][] leafArray)
            {
                int height = leafArray.length / 2;
                int startX = this.startOffset;
                int endX = this.startOffset + this.length;

                for (int h = 0; h < height + DEFAULT_WIDTH + 1; h += 1)
                {
                    for (int w = 0; w < leafArray[0].length; w += 1)
                    {
                        if (h > height)
                        {
                            if (w > startX && w < endX)
                            {
                                leafArray[h - 1][w] = true;
                            } // end if width is good
                        } // end if height is good
                    } // end for w values
                } // end for h values

                return leafArray;
            } // end castMidrib

            /**
             * Get the start and stop positions of the midrib for use with
             * drawing onto graphics objects.
             * @param leafArray boolean array of leaf
             * @return integers [][] of {{x1, x2}, {y1, y2}}
             */
            public int[][] getMidribPoints(Boolean[][] leafArray)
            {
                int height = leafArray.length / 2;
                int startX = this.startOffset;
                int endX = this.startOffset + this.length;

                int[][] output =
                {
                    {
                        startX, endX
                    },
                    {
                        height, height
                    }
                };

                return output;
            } // end getMidribPoints
        } // end midrib

        /**
         * Class to maintain primary veins
         */
        public class PrimaryVeins
        {
            /**
             * Possible styles: pinnate
             */
            String style;
            int startOffsetX;
            int startOffsetY;

            /**
             * Constructor for the primary veins.
             * @param leafArray Boolean array of leaf
             * @param startOffset How far the midrib starts from the edge
             * @param style String style of the primary veins, like "pinnate"
             */
            public PrimaryVeins(Boolean[][] leafArray, int startOffset, String style)
            {
                this.style = style;
                this.startOffsetX = startOffset;
                this.startOffsetY = leafArray[0].length;
            } // end PrimaryVeins constructor

            /**
             * Draws the veins to the leaf array.
             * @param leafArray The boolean array of the leaf
             * @param generationParameters Parameters
             * @param midrib the midrib object for the leaf
             * @return leaf array of boolean[][]
             */
            public Boolean[][] castVeins(
                    Boolean[][] leafArray,
                    double[] generationParameters,
                    Generator.Veins.Midrib midrib)
            {
                // choose style
                if ("pinnate".equals(style))
                {
                    /*
                     * Generation parameters:
                     * 0:	Number of branching veins on each side
                     * 1:	Angle (in degrees) of the branching veins
                     * 2+:	Lengths of the veins for each vein
                     */
                    int numBranchingVeins = (int) generationParameters[0];
                    double[] branchPositions = new double[numBranchingVeins];
                    double angleOfVeins = generationParameters[1];
                    double xUnit = Math.cos(Math.toRadians(angleOfVeins));
                    double yUnit = Math.sin(Math.toRadians(angleOfVeins));
                    double[] branchLengths = new double[numBranchingVeins];

                    for (int i = 0; i < numBranchingVeins; i++)
                    {
                        branchLengths[i] = generationParameters[i + 2] * midrib.length;

                        branchPositions[i] = midrib.startOffset
                                + ( // (i+1.0)*(midrib.length / ((float) (numBranchingVeins)))
                                (midrib.length / ((float) (numBranchingVeins + 1))) * (i + 1));
                    } // end for loop

                    // draw veins onto array
                    for (int i = 0; i < numBranchingVeins; i++)
                    {
                        int xStart = (int) Math.round(branchPositions[i]);
                        int yStart = leafArray.length / 2;

                        int xEnd = (int) Math.round(xStart + xUnit * branchLengths[i]);

                        for (int j = xStart; j < xEnd; j++)
                        {
                            int yDiff = (int) Math.round(yUnit * branchLengths[i]
                                    * ((float) j - xStart) / ((float) xEnd - xStart));

                            // draw onto array
                            try
                            {
                                leafArray[yStart + yDiff][j] = true;
                            } // end try
                            catch (ArrayIndexOutOfBoundsException ex) {}

                            try
                            {
                                leafArray[yStart - yDiff][j] = true;
                            } // end try
                            catch (ArrayIndexOutOfBoundsException ex) {} // end catch out of index
                        } // end for i between endpoints
                    } // end for loop
                } // end if pinnate
                
                else if ("parallel".equals(style))
                {
                    
                } // end else if style is "parallel"

                return leafArray;
            } // end castVeins
            
            /**
             * Draw the veins to a graphics object.
             * @param leafArray Boolean array of leaf
             * @param generationParameters Parameters that dictate the
             * generation of the leaf
             * @param midrib The midrib object
             * @param lamina Lamina object
             * @param g2 The graphics object to draw to
             * @return leaf array
             */
            public Boolean[][] drawVeins(
                    Boolean[][] leafArray,
                    double[] generationParameters,
                    Generator.Veins.Midrib midrib,
                    Lamina lamina,
                    Graphics2D g2)
            {
                // choose style
                if ("pinnate".equals(style))
                {
                    /*
					 * Generation parameters:
					 * 0:	Number of branching veins on each side
					 * 1:	Angle (in degrees) of the branching veins
					 * 2+:	Lengths of the veins for each vein
                     */
                    int numBranchingVeins = (int) generationParameters[0];
                    double[] branchPositions = new double[numBranchingVeins];
                    double angleOfVeins = generationParameters[1];
                    double xUnit = Math.cos(Math.toRadians(angleOfVeins));
                    double yUnit = Math.sin(Math.toRadians(angleOfVeins));
                    double[] branchLengths = new double[numBranchingVeins];

                    for (int i = 0; i < numBranchingVeins; i++)
                    {
                        branchLengths[i] = generationParameters[i + 2] * midrib.length;

                        branchPositions[i] = midrib.startOffset
                                + ( // (i+1.0)*(midrib.length / ((float) (numBranchingVeins)))
                                (midrib.length / ((float) (numBranchingVeins + 1))) * (i + 1));
                    } // end for loop
                    
                    
                    // find the relative breadth of the leaf
                    int relBreadth = (int) (leafArray.length/2 * lamina.args[0]);

                    // draw veins onto array
                    for (int i = 0; i < numBranchingVeins; i++)
                    {
                        int xStart = (int) Math.round(branchPositions[i]);
                        int yStart = leafArray.length / 2;

                        int xEnd = (int) Math.round(xStart + xUnit * branchLengths[i]);
                        int yEnd1 = (int) (yStart - yUnit * branchLengths[i]);
                        int yEnd2 = (int) Math.round(yStart + yUnit * branchLengths[i]);
                        
                        /*
                        To correct for the margin of the lamina, the breadth of 
                        the leaf must be known. Then, both the x and y components
                        of the line segment show be scaled. To scale, the y
                        component is scaled to the raw breadth first, then the
                        x component. Next, if the new x is in the range of the
                        rise or fall of the margin, a new y will be calculated
                        based on the ellipse formed.
                        */
                        
                        // if the end of the vein extends past the margin
                        if ((yStart-yEnd1) > relBreadth)
                        {
                            double oldXDiff = xEnd-xStart;
                            double newXDiff;
                            double oldYDiff = yEnd2-yStart;
                            double newYDiff = relBreadth;
                           
                            double changeFact = newYDiff/oldYDiff;
                            newXDiff = changeFact * oldXDiff;
                            
                            xEnd = (int) (xStart + newXDiff); 
                            yEnd1 = (int) (yStart - newYDiff + 1);
                            yEnd2 = (int) (yStart + newYDiff - 1);                            
                        } // end if the edge of the vein extends past the margin

                        g2.setColor(GenColor.veins);
                        g2.drawLine(xEnd, yEnd1, xStart, yStart);
                        g2.drawLine(xStart, yStart, xEnd, yEnd2);
                    } // end for loop

                } // end if pinnate

                return leafArray;
            } // end castVeins
        } // end PrimaryVeins
    } // end Veins
    
    /**
     * 
     */
    public class Lamina
    {
        // extra layer to differentiate between leaf blade and veins
        public boolean[][] laminaArray;
        public Generator.Lamina.Styles styles;
        
        // variables to store from superclass and such
        private Boolean[][] leafArray;
        private Generator.Veins.Midrib midrib;
        private String style;
        private double[] args;
        
        // ellipse stuff
        public EllipseMath.Ellipse riseEllipse;
        public EllipseMath.Ellipse fallEllipse;
        
        public Lamina(Boolean[][] leafArray, Generator.Veins.Midrib midrib, 
                String style, double[] args)
        {
            // stored variables from constructor
            this.leafArray = leafArray;
            this.midrib = midrib;
            this.style = style;
            this.args = args;
            
            styles = new Generator.Lamina.Styles();
        } // end constructor
        
        private class Styles
        {
            public void drawLinear(Graphics2D g2)
            {
                g2.setColor(GenColor.background);
                
                double breadth = args[0]*leafArray.length;
                double[] middleSection = {args[1], args[2]};
                /*
                Equation for an ellipse:
                x - x value
                y - y value
                h - h^2 is radius of ellipse on x axis
                k - k^2 is radius of ellipse on y axis
                y=-(k*sqrt(h^2-(x-h)^2))/h,y=(k*sqrt(h^2-(x-h)^2))/h
                */
                
                int riseLength = (int) (middleSection[0] * midrib.length);
                int fallLength = (int) ((1-middleSection[1]) * midrib.length);
                
                int distMarginToFall = (int) (midrib.startOffset + 
                        middleSection[1] * midrib.length);
                
                EllipseMath.Ellipse rise = new EllipseMath.Ellipse(
                        riseLength,
                        breadth / 2.0
                );
                
                EllipseMath.Ellipse fall = new EllipseMath.Ellipse(
                        fallLength,
                        breadth / 2.0
                );
                
                riseEllipse = rise;
                fallEllipse = fall;
                
                // draw the flat, linear sides of the leaf
                // bottom
                g2.drawLine(
                        (int) (midrib.startOffset + midrib.length * middleSection[0]),
                        (int) (leafArray.length/2 + breadth/2),
                        (int) (midrib.startOffset + midrib.length * middleSection[1]),
                        (int) (leafArray.length/2 + breadth/2)
                );
                // top
                g2.drawLine(
                        (int) (midrib.startOffset + midrib.length * middleSection[0]),
                        (int) (leafArray.length/2 - breadth/2),
                        (int) (midrib.startOffset + midrib.length * middleSection[1]),
                        (int) (leafArray.length/2 - breadth/2)
                );
                
                // draw rise curve
                for (double d = rise.getBounds()[0]+1; d < 0.0; d += 1.0)
                {
                    g2.drawLine(
                            (int) (midrib.startOffset + (d-rise.getBounds()[0]-1.0)),
                            (int) (leafArray.length/2 - rise.getValueAtX(d-1.0)),
                            (int) (midrib.startOffset + (d-rise.getBounds()[0])),
                            (int) (leafArray.length/2 - rise.getValueAtX(d))
                    );
                    g2.drawLine(
                            (int) (midrib.startOffset + (d-rise.getBounds()[0]-1.0)),
                            (int) (leafArray.length/2 + rise.getValueAtX(d-1.0)),
                            (int) (midrib.startOffset + (d-rise.getBounds()[0])),
                            (int) (leafArray.length/2 + rise.getValueAtX(d))
                    );
                    
                    // draw lines that clear the leaf's background
                    g2.drawLine(
                            (int) (midrib.startOffset + (d-rise.getBounds()[0])) - 1,
                            (int) (leafArray.length/2 - rise.getValueAtX(d-1.0)) - 1,
                            (int) (midrib.startOffset + (d-rise.getBounds()[0])) - 1,
                            (int) (0)
                    );
                    g2.drawLine(
                            (int) (midrib.startOffset + (d-rise.getBounds()[0])) - 1,
                            (int) (leafArray.length/2 + rise.getValueAtX(d-1.0)) + 1,
                            (int) (midrib.startOffset + (d-rise.getBounds()[0])) - 1,
                            (int) (leafArray.length)
                    );
                } // end for loop
                
                // draw fall curve
                for (double d = 0.0; d < fall.getBounds()[1]; d += 1.0)
                {
                    g2.drawLine(
                            (int) (distMarginToFall + d - 1.0),
                            (int) (leafArray.length/2 - fall.getValueAtX(d-1.0)),
                            (int) (distMarginToFall + d),
                            (int) (leafArray.length/2 - fall.getValueAtX(d))
                    );
                    g2.drawLine(
                            (int) (distMarginToFall + d - 1.0),
                            (int) (leafArray.length/2 + fall.getValueAtX(d-1.0)),
                            (int) (distMarginToFall + d),
                            (int) (leafArray.length/2 + fall.getValueAtX(d))
                    );
                    
                    // draw the lines that clear the backgroud
                    g2.drawLine(
                            (int) (distMarginToFall + d),
                            (int) (leafArray.length/2 - fall.getValueAtX(d-1.0)),
                            (int) (distMarginToFall + d),
                            (int) (0)
                    );
                    g2.drawLine(
                            (int) (distMarginToFall + d),
                            (int) (leafArray.length/2 + fall.getValueAtX(d-1.0)),
                            (int) (distMarginToFall + d),
                            (int) (leafArray.length)
                    );
                } // end for loop
            } // end linear
        } // end class Styles
    } // end class Lamina
} // end LeafArrayGenerator

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

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Generates an array that displays a leaf
 *
 * @author Patrick Thomas
 */
public class LeafArrayGenerator
{

    public Boolean[][] leafArray;
    public LeafArrayGenerator.Veins veins;

    double[] primaryVeinGenParams;

    public LeafArrayGenerator(
            int width,
            int height,
            double midribLengthProportion,
            double midribActualLength,
            double midribOffsetProportion,
            String primaryVeinsStyle,
            double[] primaryVeinsParameters)
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
        veins = new LeafArrayGenerator.Veins(
                this.leafArray,
                midribLengthProportion,
                midribActualLength,
                midribOffsetProportion,
                primaryVeinsStyle);

        leafArray = veins.midrib.castMidrib(leafArray);
        leafArray = veins.primaryVeins.castVeins(leafArray, primaryVeinsParameters, veins.midrib);
    } // end LeafArrayGenerator constructor

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

    public BufferedImage createBufferedImage()
    {
        BufferedImage outputImage = new BufferedImage(
                leafArray[0].length,
                leafArray.length,
                BufferedImage.TYPE_BYTE_BINARY);

        Graphics2D g2 = outputImage.createGraphics();

        int[][] midribData = this.veins.midrib.getMidribPoints(leafArray);

        g2.drawPolyline(midribData[0], midribData[1], midribData.length);
        this.veins.primaryVeins.drawVeins(leafArray, this.primaryVeinGenParams, this.veins.midrib, g2);

        return outputImage;
    } // end createBufferedImage

    public class Veins
    {

        LeafArrayGenerator.Veins.Midrib midrib;
        LeafArrayGenerator.Veins.PrimaryVeins primaryVeins;

        public Veins(
                Boolean[][] leafArray,
                double midribLengthProportion,
                double midribActualLength,
                double midribOffsetProportion,
                String primaryVeinsStyle)
        {
            // create midrib
            midrib = new LeafArrayGenerator.Veins.Midrib(
                    midribLengthProportion,
                    midribActualLength,
                    (int) leafArray[0].length,
                    midribOffsetProportion);

            primaryVeins = new LeafArrayGenerator.Veins.PrimaryVeins(
                    leafArray,
                    midrib.startOffset,
                    primaryVeinsStyle);
        } // end Veins

        public class Midrib
        {

            double lengthProportion;
            double actualLength;
            int length;

            double startOffsetProportion;
            int startOffset;

            public final int DEFAULT_WIDTH = 1;

            /**
             * Constructor for midrib.
             *
             * @param lengthProportion Proportion of the length of the midrib to
             * the length of the actual image
             * @param actualLength The midrib's length in any unit
             * @param arrayWidth The width of the array
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

        public class PrimaryVeins
        {

            /**
             * Possible styles: pinnate
             */
            String style;
            int startOffsetX;
            int startOffsetY;

            public PrimaryVeins(Boolean[][] leafArray, int startOffset, String style)
            {
                this.style = style;

                this.startOffsetX = startOffset;
                this.startOffsetY = leafArray[0].length;
            } // end PrimaryVeins constructor

            public Boolean[][] castVeins(
                    Boolean[][] leafArray,
                    double[] generationParameters,
                    LeafArrayGenerator.Veins.Midrib midrib)
            {
                // choose style
                if (style == "pinnate")
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
                        // int yEnd1 = (int) (yStart - yUnit * branchLengths[i]);
                        // int yEnd2 = (int) Math.round(yStart + yUnit * branchLengths[i]);

                        System.out.println(i + " of " + numBranchingVeins);

                        for (int j = xStart; j < xEnd; j++)
                        {
                            int yDiff = (int) Math.round(yUnit * branchLengths[i]
                                    * ((float) j - xStart) / ((float) xEnd - xStart));

                            // draw onto array
                            try
                            {
                                leafArray[yStart + yDiff][j] = true;
                            } // end try
                            catch (ArrayIndexOutOfBoundsException ex)
                            {
                                ex.printStackTrace();
                            } // end catch out of index

                            try
                            {
                                leafArray[yStart - yDiff][j] = true;
                            } // end try
                            catch (ArrayIndexOutOfBoundsException ex)
                            {
                                ex.printStackTrace();
                            } // end catch out of index
                        } // end for i between endpoints
                    } // end for loop

                } // end if pinnate

                return leafArray;
            } // end castVeins

            public Boolean[][] drawVeins(
                    Boolean[][] leafArray,
                    double[] generationParameters,
                    LeafArrayGenerator.Veins.Midrib midrib,
                    Graphics2D g2)
            {
                // choose style
                if (style == "pinnate")
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
                        int yEnd1 = (int) (yStart - yUnit * branchLengths[i]);
                        int yEnd2 = (int) Math.round(yStart + yUnit * branchLengths[i]);

                        g2.drawLine(xEnd, yEnd1, xStart, yStart);
                        g2.drawLine(xStart, yStart, xEnd, yEnd2);
                    } // end for loop

                } // end if pinnate

                return leafArray;
            } // end castVeins
        } // end PrimaryVeins
    } // end Veins
} // end LeafArrayGenerator

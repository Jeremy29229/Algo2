import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import edu.neumont.ui.Picture;

	public class SeamCarver
	{
		private int width;
		private int height;
		 ArrayList<List<Color>> imageBits;
		private Picture p;
	
		public SeamCarver(Picture pic)
		{
			p = pic;
			width = p.width();
			height = p.height();
			imageBits = new ArrayList<List<Color>>();
			
			for(int i = 0; i < p.width(); i++)
			{
				imageBits.add(new ArrayList<Color>());
				
				for(int j = 0; j < p.height(); j++)
				{
					imageBits.get(i).add(p.get(i, j));
				}
			}
		}
		
		Picture getPicture()
		{
			if(p.width() != width || p.height() != height)
			{
				p = new Picture(width, height);
				
				for(int i = 0; i < p.width(); i++)
				{	
					for(int j = 0; j < p.height(); j++)
					{
						p.set(i, j, imageBits.get(i).get(j));
					}
				}
			}
			
			p.save(new File("hiddenMessage.png"));
			return p;
		}
		
		int width()
		{
			return width;
		}
		
		int height()
		{
			return height;
		}
		
		double energy(int x, int y)
		{
			if(x < 0 || y < 0 || x >= width || y >= height)
			{
				throw new IndexOutOfBoundsException("Coordinates outside of picture: " + x + ", " + y);
			}
			
			int energyX = 0;
			if(x == 0)
			{
				energyX += Math.pow(imageBits.get(x + 1).get(y).getRed() - imageBits.get(width - 1).get(y).getRed(), 2) 
						+ Math.pow(imageBits.get(x + 1).get(y).getGreen() - imageBits.get(width - 1).get(y).getGreen(), 2)
						+ Math.pow(imageBits.get(x + 1).get(y).getBlue() - imageBits.get(width - 1).get(y).getBlue(), 2);
			}
			else if(x == width() - 1)
			{
				energyX += Math.pow(imageBits.get(0).get(y).getRed() - imageBits.get(x - 1).get(y).getRed(), 2) 
						+ Math.pow(imageBits.get(0).get(y).getGreen() - imageBits.get(x - 1).get(y).getGreen(), 2)
						+ Math.pow(imageBits.get(0).get(y).getBlue() - imageBits.get(x - 1).get(y).getBlue(), 2);
			}
			else
			{
				energyX += Math.pow(imageBits.get(x + 1).get(y).getRed() - imageBits.get(x - 1).get(y).getRed(), 2) 
						+ Math.pow(imageBits.get(x + 1).get(y).getGreen() - imageBits.get(x - 1).get(y).getGreen(), 2)
						+ Math.pow(imageBits.get(x + 1).get(y).getBlue() - imageBits.get(x - 1).get(y).getBlue(), 2);
			}
			
			int energyY = 0;
			if(y == 0)
			{
				energyY += Math.pow(imageBits.get(x).get(y + 1).getRed() - imageBits.get(x).get(height - 1).getRed(), 2) 
						+ Math.pow(imageBits.get(x).get(y + 1).getGreen() - imageBits.get(x).get(height - 1).getGreen(), 2)
						+ Math.pow(imageBits.get(x).get(y + 1).getBlue() - imageBits.get(x).get(height - 1).getBlue(), 2);
			}
			else if(y == height() - 1)
			{
				energyY += Math.pow(imageBits.get(x).get(0).getRed() - imageBits.get(x).get(y - 1).getRed(), 2) 
						+ Math.pow(imageBits.get(x).get(0).getGreen() - imageBits.get(x).get(y - 1).getGreen(), 2)
						+ Math.pow(imageBits.get(x).get(0).getBlue() - imageBits.get(x).get(y - 1).getBlue(), 2);
			}
			else
			{
				energyY += Math.pow(imageBits.get(x).get(y + 1).getRed() - imageBits.get(x).get(y - 1).getRed(), 2) 
						+ Math.pow(imageBits.get(x).get(y + 1).getGreen() - imageBits.get(x).get(y - 1).getGreen(), 2)
						+ Math.pow(imageBits.get(x).get(y + 1).getBlue() - imageBits.get(x).get(y - 1).getBlue(), 2);
			}
			
			return energyX + energyY;
		}
		
		int[] findHorizontalSeam()
		{
			int[] hSeam = new int[width];
			
			int lowestStarter = -1;
			double lowestStarterScore = Double.MAX_VALUE;
			for(int i = 0; i < height; i++)
			{
				if(energy(0, i) < lowestStarterScore)
				{
					lowestStarter = i;
					lowestStarterScore = energy(0, i);
				}
			}
			
			hSeam[0] = lowestStarter;
			
			for(int i = 1; i < width; i++)
			{
				hSeam[i] = nextRight(i, hSeam[i - 1]);
			}
			
			return hSeam;
		}
		
		private int nextRight(int column, int lastColumnsY)
		{
			int nextY = lastColumnsY;
			
			if(lastColumnsY != 0 && energy(column, lastColumnsY - 1) < energy(column, nextY))
			{
				nextY = lastColumnsY - 1;
			}
			
			if(lastColumnsY != height - 1 && energy(column, lastColumnsY + 1) < energy(column, nextY))
			{
				nextY = lastColumnsY + 1;
			}
			
			return nextY;
		}
		
		int[] findVerticalSeam()
		{
			int[] rSeam = new int[height];
			
			int lowestStarter = -1;
			double lowestStarterScore = Double.MAX_VALUE;
			for(int i = 0; i < width; i++)
			{
				if(energy(i, 0) < lowestStarterScore)
				{
					lowestStarter = i;
					lowestStarterScore = energy(i, 0);
				}
			}
			
			rSeam[0] = lowestStarter;
			
			for(int i = 1; i < height; i++)
			{
				rSeam[i] = nextDown(i, rSeam[i - 1]);
			}
			
			return rSeam;
		}
		
		private int nextDown(int row, int lastRowX)
		{
			int nextX = lastRowX;
			
			if(lastRowX != 0 && energy(lastRowX - 1, row) < energy(nextX, row))
			{
				nextX = lastRowX - 1;
			}
			
			if(lastRowX != width - 1 && energy(lastRowX + 1, row) < energy(nextX, row))
			{
				nextX = lastRowX + 1;
			}
			
			return nextX;
		}
		
		void removeHorizontalSeam(int[] indices)
		{
			if(indices.length != width)
			{
				throw new IllegalArgumentException("Incorrect number of indices in array: " + indices.length +  " -- should be: " + width);
			}
			
			if(height == 1)
			{
				throw new IllegalArgumentException("Image with width of 1 cannot have a horizontal seam removed");
			}
			
			for(int i = 0; i < width; i++)
			{
				if(indices[i] >= height || indices[i] < 0)
				{
					throw new IndexOutOfBoundsException("Coordinates outside of picture: " + i + ", " + indices[i]);
				}
				imageBits.get(i).remove(indices[i]);
			}
			
			height--;
		}
		
		void removeVerticalSeam(int[] indices)
		{
			if(indices.length != height)
			{
				throw new IllegalArgumentException("Incorrect number of indices in array: " + indices.length +  " -- should be: " + height);
			}
			
			if(height == 1)
			{
				throw new IllegalArgumentException("Image with height of 1 cannot have a vertical seam removed");
			}
			
			for(int i = 0; i < height; i++)
			{
				if(indices[i] >= width || indices[i] < 0)
				{
					throw new IndexOutOfBoundsException("Coordinates outside of picture: " + indices[i] + ", "  + i);
				}
				imageBits.get(indices[i]).set(i, null);
				shiftLeft(indices[i], i);
			}
			
			imageBits.remove(imageBits.size() - 1);
			width--;
		}
		
		private void shiftLeft(int x, int y)
		{
			int currentX = x;
			
			while(currentX < width - 1)
			{
				imageBits.get(currentX).set(y, imageBits.get(currentX + 1).get(y));
				currentX++;
			}
		}
	}

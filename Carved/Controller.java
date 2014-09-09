import edu.neumont.ui.Picture;

public class Controller
{

	public static void main(String[] args)
	{
		SeamCarver s = new SeamCarver(new Picture("overlayimagewithhiddenmessage.png"));
				
		for(int i = 0; i < 409; i++)
		{
			s.removeVerticalSeam(s.findVerticalSeam());
		}

		for(int i = 0; i < 170; i++)
		{
			s.removeHorizontalSeam(s.findHorizontalSeam());
		}
		s.getPicture();
	}

}


public class Debugz {

	static String[] sequence;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("The Array Is A Snake!");
		
		sequence = new String[1];
/*		sequence[0] = "_";
		sequence[1] = "a";
		sequence[2] = "b";
		sequence[3] = "c";
		sequence[4] = "d";
		sequence[5] = "e";
		sequence[6] = "f";
		sequence[7] = "g";
		sequence[8] = "h";
		sequence[9] = "i";*/
		
		sequence[0] = "" + 1;
		
		for(int i = 0; i < 10; i++)
		{
			moveToALongerArray();
			System.out.println("Repeat...");
		}
	}
	
	public static void moveDownAndReplace()
	{	
		
		for(String s: sequence)
		{
			System.out.println(s);
		}
		System.out.println("Moving indexes down...");
		for(int i = 0; i < sequence.length; i++)
		{
			if(i > 0)
			{
				sequence[i - 1] = sequence[i];
			}
		}
		System.out.println("Adding a new letter");
		sequence[sequence.length-1] = "" + sequence.length;
		for(String s: sequence)
		{
			System.out.println(s);
		}
	}
	
	public static void moveToALongerArray()
	{
		
		String[] sequence2 = new String[sequence.length];
		
		for(int i = 0; i < sequence.length; i++)
		{
			sequence2[i] = sequence[i];
			System.out.println(sequence2[i]);
		}
		
		System.out.println("...");
		
		sequence = new String[sequence.length+1];
		
		for(int i = 0; i < sequence2.length; i++)
		{
			sequence[i+1] = sequence2[i];
			
			System.out.println(sequence[i+1]);
		}
		
		System.out.println("...");
		moveDownAndReplace();
	}
}

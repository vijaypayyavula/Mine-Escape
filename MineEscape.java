import java.util.*;
class Landmines
{
	//Stores the movement of the person
	//{ Up, Down, Left, Right }
	int rowMov[] = {0, 0, -1, 1};
	int colMov[] = {-1, 1, 0, 0};

	int[][] matrix,soln;
	int original[][];

    //Stores the size of array
	int R,C;

    //Stores the number of steps it takes to reach the final
    int count=Integer.MAX_VALUE;

	//Checks if the position is valid position
	boolean validPosition( int x, int y ) 
	{
		if( x>=0 && y>=0 && x<R && y<C )
			return true;

		return false;		
	}

	//Checks if the position is bomb-free
	boolean isSafe( int x, int y )
	{
		if(matrix[x][y]==0 || soln[x][y]==1)
			return false;

		return true;
	}
    
	//Finding the path which the person can move without bombing himself
	boolean findPath( int x, int y, int dist ) 
	{
		if( y==C-1 && matrix[x][y]==1 )
		{
			soln[x][y]=1;

			if( dist < count )
				count = dist;

			return true;
		}

		if( validPosition( x, y ) && isSafe( x, y ))
		{
			soln[x][y]=1;
			dist++;

			for( int k=0 ; k < 4 ; k++ )
			{
				if(findPath( x + rowMov[k], y + colMov[k], dist))
					return true; 
			}

			//Implementing backtracking  
			soln[x][y]=0; 
			dist--;
		}
		return false;
	}

    //Coverts the positions around the bomb as dangers too
	void makeUnsafePositions(int i, int j )
    {
        if(matrix[i][j]==0)
            for(int k =0 ; k <4 ; k++)
			{   
                //Checks if the person is able to walk to that position
				if( validPosition( i+rowMov[k], j+colMov[k] ))
					matrix[i+rowMov[k]][j+colMov[k]]=0;
			}   
    }

    //Searches whether path is possible
	//If possible then prints the solution and the distance
	//Else prints an message
    void executeBombFind()
	{
		// for(int k=0 ; k < R ; k++)
		// {
			if( matrix[0][0] == 1 )
			{
				if(findPath( 0, 0, 0 ))
				{
					System.out.println("\nThe solution matrix is : \n");
					for(int i=0 ; i< R ;i++)
					{
						for(int j=0 ; j<C ; j++)
							System.out.print(soln[i][j] + " ");
						System.out.println();
					}		
					System.out.println();
				}					
			// }
		}

		if( count != Integer.MAX_VALUE)
			System.out.println("The path count is : "+ count );		 
		else
			System.out.println("Path is not possible!!");
		
		System.out.println();
	}

	//Inputs the user's entry
    void readInput()
	{

		Scanner sn = new Scanner(System.in);

        System.out.print("\nEnter the number of Rows : ");
        R = sn.nextInt();
        System.out.print("Enter ther number of Columns : ");
        C = sn.nextInt();

        //Allocating and initializing the matrix and solution array
        matrix = new int[R][C];
		soln = new int[R][C];
		original = new int[R][C];

        for(int i=0 ; i<R ; i++ ) 
        {
            for(int j=0 ; j<C ; j++ )
			{
                matrix[i][j] = 1;
				original[i][j] = 1;
				soln[i][j]=0;
			}
        }
		
		System.out.print("\nEnter the number of bombs you want to plant : ");
		int n = sn.nextInt();
        System.out.print("\nEnter the locations to plant bombs [R C]: ");

		while(n>=1)
	    {
			int rbomb = sn.nextInt();
			int cbomb = sn.nextInt();
			matrix[rbomb][cbomb]  = 0 ;
			original[rbomb][cbomb] = 0 ; 

			makeUnsafePositions( rbomb , cbomb );
			System.out.println("Bombs left : "+ --n);
			if(n!=0)
				System.out.print("Enter the position of the next bomb : ");
		}

		System.out.println("\nMatrix is : ");
        
        for(int i=0 ; i< R ;i++)
		{
			for( int j=0 ; j<C ; j++)
				System.out.print(original[i][j] + " ");
			System.out.println();
		}	
		sn.close();
	}

	//Flushes the screen using ANSI code
	void flushScreen()
	{
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static void main(String[] args)
	{
		Landmines bomb = new Landmines();

		bomb.flushScreen();
		bomb.readInput();
		bomb.executeBombFind();
	}
}
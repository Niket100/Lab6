import java.util.*;
import java.io.*;

class  NoCoordinateException extends Exception
{
	public  NoCoordinateException(String s)
	{
		super(s);
	}
}

class QueenFoundException extends Exception 
{
	public QueenFoundException(String s)
	{
		super(s);
	}
}

class StackEmptyException extends Exception
{
	public StackEmptyException(String s)
	{
		super(s);
	}
}


class KnightOverlap extends Exception
{
	public KnightOverlap(String s)
	{
		super(s);
	}
}

class NoException extends Exception
{
	public NoException(String s)
	{
		super(s);
	}
}




class Coordinate
{
	public int x;
	public int y;
	public Coordinate(int a,int b)
	{
		x=a;
		y=b;
	}
	public void change(Coordinate ob)
	{
		x=ob.getx();
		y=ob.gety();
	}
	public int getx()
	{
		return x;
	}
	public int gety()
	{
		return y;
	}
	
	
}

class Knight
{
	public String name;
	public Coordinate cor;
	public boolean p; 
	public Stack iter;
	public Knight(int a,int b,String s)
	{
		name=s;
		cor=new Coordinate(a,b);
		p=true;
		iter=new Stack();
	}
	public int check(Knight ob)
	{
		
		if(this.cor.getx()==ob.cor.getx() && this.cor.gety()==ob.cor.gety())
			{
				ob.p=false;
				return 1;
			}
		else
			return 0;
		
	}
	public int queen(Coordinate ob)
	{
		if(this.cor.getx()==ob.getx() && this.cor.gety()==ob.gety())
			return 1;
		else 
			return 0;
	}

}

public class lab6 {
	
	public static void ksort(Knight[] ob,int n)
	{
		int i,j;
		Knight tem;
		for(i=0;i<n;i++)
		{
			for(j=i+1;j<n;j++)
			{
				if(ob[i].name.compareTo(ob[j].name)>0)
				{
					tem=ob[j];
					ob[j]=ob[i];
					ob[i]=tem;
				}
			}
		}
	}

	public static void main(String[] args) throws  NoCoordinateException , KnightOverlap , QueenFoundException , StackEmptyException, NoException, IOException
	{
		//BufferedReader r=new BufferedReader(new FileReader(""));
		//String S1=r.readLine();
		//String[] S2=S1.trim().split("\\s+");
		Scanner r=new Scanner(System.in);
		PrintWriter p=new PrintWriter(new FileWriter("/home/niket/Desktop/out.txt"),true);
		
		int n,m,x,y,i,j,k,te,a,b;
		String s;
		float hi;
		n=r.nextInt();
		m=r.nextInt();
		x=r.nextInt();
		y=r.nextInt();
		Coordinate queen=new Coordinate(x,y);
		Knight[] ob=new Knight[n];
		for(i=0;i<n;i++)
		{
			InputStream inp=new FileInputStream("/home/niket/Desktop/"+(i+1)+".txt");
			Reader.init(inp);
			s=Reader.next();
			a=Reader.nextInt();
			b=Reader.nextInt();
			ob[i]=new Knight(a,b,s);
			te=Reader.nextInt();
			for(j=0;j<te;j++)
			{
				s=Reader.next();
				if(s.equals("Coordinate"))
				{
					a=Reader.nextInt();
					b=Reader.nextInt();
					ob[i].iter.add(new Coordinate(a,b));
				}
				else if(s.equals("String"))
				{
					s=Reader.next();
					ob[i].iter.add(s);
				}
				else if(s.equals("Integer"))
				{
					a=Reader.nextInt();
					ob[i].iter.add(a);
				}
				else if(s.equals("Float"))
				{
					hi=Reader.nextFloat();
					ob[i].iter.add(hi);
				}
			}
		}
		ksort(ob,n);
			
		int tem=0;
		for(i=0;i<m;i++)
		{
			
			try
			{
			if(!ob[tem%n].p)
			{
				tem++;
				i--;
				continue;
			}
			else
			{
				p.println((i+1)+" "+ob[tem%n].name+" "+ob[tem%n].cor.getx()+" "+ob[tem%n].cor.gety());
				if(ob[tem%n].iter.empty())
				{
					ob[tem%n].p=false;
					tem++;
					throw new StackEmptyException("StackEmptyException: Stack Empty exception");
				}
				
				Object temp=ob[tem%n].iter.pop();
				if(!(temp instanceof Coordinate))
				{
					if(temp instanceof Integer)
						temp=(int) temp;
					else if(temp instanceof Float)
						temp=(float) temp;
					else if(temp instanceof String)
						temp=(String) temp;
					tem++;
					throw new NoCoordinateException("NonCoordinateException: Not a coordinate Exception "+temp);
				}
				else
				{
					int flag=0;
					ob[tem%n].cor.change((Coordinate)temp);
					for(j=0;j<n;j++)
					{
						if(tem%n==j)
							continue;
						if(ob[tem%n].check(ob[j])==1)
							{
								flag=1;
								tem++;
								throw new KnightOverlap("OverlapException: Knights Overlap Exception "+ob[j].name);
							}
					}
					if(ob[tem%n].queen(queen)==1)
					{
						tem++;
						throw new QueenFoundException("QueenFoundException: Queen has been Found. Abort!");
					}
					else if(flag!=1)
					{
						throw new NoException("NoException: " + ob[tem%n].cor.getx()+" "+ob[tem%n].cor.gety()); 
					}
					
				}
				tem++;
			}
			}
			catch( NoCoordinateException E) 
			{
				p.println(E.getMessage());
			} 
			catch(KnightOverlap E) 
			{
				p.println(E.getMessage());
			} 
			catch (StackEmptyException E)
			{
				p.println(E.getMessage());
				
			} 
			catch(QueenFoundException E) 
			{
				p.println(E.getMessage());
				break;
			}
			catch(NoException E)
			{
				p.println(E.getMessage());
			}
			
			}
		}	
}

class Reader {
    static BufferedReader reader;
    static StringTokenizer tokenizer;
    /** call this method to initialize reader for InputStream */
    static void init(InputStream input) {
        reader = new BufferedReader(
                new InputStreamReader(input) );
        tokenizer = new StringTokenizer("");
    }
 
    /** get next word */
    static String next() throws IOException {
        while ( ! tokenizer.hasMoreTokens() ) {
            //TODO add check for if necessary
            tokenizer = new StringTokenizer(
                    reader.readLine() );
        }
        return tokenizer.nextToken();
    }
 
    static int nextInt() throws IOException {
        return Integer.parseInt( next() );
    }
    static float nextFloat() throws IOException {
        return Float.parseFloat( next() );
    }
    static double nextDouble() throws IOException {
        return Double.parseDouble( next() );
    }
}







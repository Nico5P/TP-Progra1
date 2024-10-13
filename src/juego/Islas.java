
public class Islas 
{
	public Rectangle[] dibujarIslas()
	{
		int ejeX=49;
		int ejeY=510;
		Rectangle[] islas = new Rectangle[14];
		for(int fila=0; fila<5; fila++)
		{
			int islasPorFila=5;
			for(int i=0; i<islasPorFila; i++)
			{
				islas[i] = new Rectangle(ejeX, ejeY, 138, 25);
				ejeX=ejeX+138+49;
			}
			ejeY=ejeY-100;
			islasPorFila--;
		}
		return islas;
	}
}

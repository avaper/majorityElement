package mayoritario;

/**
 * Clase que realiza el algoritmo Divide y vencer�s
 * 
 *
 */
public class Algoritmos 
{
	/**
	 * Para activar la traza del algoritmo
	 */
	private static boolean traza;
	
	/**
	 * M�todo que aplica el algoritmo Divide y vencer�s. Dividiremos el problema en mitades
	 * 
	 * @param posInicio Posici�n inicial del vector que estamos procesando
	 * @param posFin Posici�n final del vector que estamos procesando
	 * @param v Vector que estamos procesando
	 * @return El elemento mayoritario (int)
	 */
	public static int Divide_Y_Venceras(int posInicio, int posFin, int [] v) 
	{	
		int mitad, s1, s2;
		
		// El problema es simple
		if (posInicio == posFin) 
		{
			return v[posInicio];	 
		} 
		// El problema no es simple, descomponemos
		else 
		{
			if (traza) System.out.println("\tProblema: " + imprimirArray(posInicio, posFin, v));
			if (traza) System.out.println("\tDividiendo problema en mitades \n");
			
			// Determinamos la mitad del vector que representa al problema
			mitad = (posInicio + posFin) / 2;
			
			// Aplicamos el algoritmo recursivamente sobre las mitades
			s1 = Divide_Y_Venceras(posInicio, mitad, v);
			s2 = Divide_Y_Venceras((mitad+1), posFin, v);		
		}
		
		// Combinamos las soluciones
		return Combinar(posInicio, posFin, s1, s2, v); 
	}

	/**
	 * M�todo que combina las soluciones que va generando el algoritmo de Divide y vencer�s
	 * 
	 * @param posInicio Posici�n inicial del vector que estamos procesando
	 * @param posFin Posici�n final del vector que estamos procesando
	 * @param m1 Soluci�n simple 1
	 * @param m2 Soluci�n simple 2
	 * @param v Vector que estamos procesando
	 * @return El resultado de la combinaci�n de dos problemas simples (int)
	 */
	public static int Combinar(int posInicio, int posFin, int m1, int m2, int [] v) 
	{		
		if (traza) System.out.println("\t\tCombinando soluciones simples " + m1 + ", " + m2 + "\n");

		// Ning�n elemento es mayoritario
		if (m1 == -1 && m2 == -1) return -1; 	
		
		// El elemento 2 podr�a ser mayoritario
		else if (m1 == -1 && m2 != -1) return ComprobarMayoritario(posInicio, m2,v);

		// El elemento 1 podr�a ser mayoritario	
		else if (m1 != -1 && m2 == -1) return ComprobarMayoritario(posInicio, m1, v); 

		// Ambos elementos podr�an ser mayoritarios
		else if (m1 != -1 && m2 != -1) 
		{
			if (ComprobarMayoritario(posInicio, m1,v) == m1) 
			{
				// El primer elemento es mayoritario
				return m1;	
			} 
			else if (ComprobarMayoritario(posInicio, m2,v) == m2) 
			{
				// El segundo elemento es mayoritario
				return m2; 
			}
			// Ninguno de los elementos es mayoritario
			else  return -1; 			
		} 
		// No existe elemento mayoritario
		else return -1;	
	}
	
	/**
	 * M�todo que comprueba si el elemento es mayoritario
	 * 
	 * @param posInicio Posici�n inicial del vector que estamos procesando
	 * @param x Soluci�n simple a comprobar
	 * @param v Vector que estamos procesando
	 * @return El elemento si es mayoritario, o -1 en caso contrario (int)
	 */
	public static int ComprobarMayoritario (int posInicio, int x, int [] v) 
	{
		int c = 1; 
		
		// Se cuentan las apariciones restantes
		for (int k = posInicio; k < v.length; k++) 
		{
			if (v[k] == x) c = c + 1; 
		}
		
		// Si se cumple la condici�n, es mayoritario
		if (c > (v.length / 2) + 1) return x;
		
		else return -1; 
	} 
	
	/**
	 * Setter del atributo traza
	 * 
	 * @param Traza Traza del algoritmo
	 */
	public static void setTraza(boolean Traza) 
	{
		traza = Traza;
	}

	/**
	 * M�todo que crea una cadena de caracteres para representar el problema actual 
	 * 
	 * @param posInicio Posici�n inicial del vector que estamos procesando
	 * @param posFin Posici�n final del vector que estamos procesando
	 * @param v Vector que estamos procesando
	 * @return Versi�n horizontal del vector que estamos procesando
	 */
	public static String imprimirArray(int posInicio, int posFin, int[] v) 
	{	
		String array = "{";
		
		for (int i = posInicio; i <= posFin; i++) 
		{		
			if (i<posFin) array = array + v[i] + ", ";
			
			else array = array + v[i] + "}";	
		}
		return array;
	}
}


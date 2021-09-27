package mayoritario;

import java.io.IOException;

/**
 * Clase que calcula el elemento mayoritario en una lista 
 * de números enteros positivos distintos de 0
 * 
 *
 */
public class Mayoritario 
{
	/**
	 * Método principal
	 * 
	 * @param args Vector de cadenas de caracteres que indican las opciones
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException 
	{
		System.out.println("Programa Mayoritario\n");
		
		// Obtenemos los datos de la entrada de comandos
		int [] entrada = EntradaSalida.DatosInicio(args);
		
		if (entrada != null)
		{
			// Fijamos el modo de trazado del algoritmo
			Algoritmos.setTraza(EntradaSalida.getTraza());
			
			// Aplicamos el algoritmo con los datos
			int resultado = Algoritmos.Divide_Y_Venceras(0, (entrada.length-1), entrada);
			
			// Escribimos la salida
			EntradaSalida.Salida(resultado);
		}
	}
}
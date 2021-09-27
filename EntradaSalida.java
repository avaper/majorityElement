package mayoritario;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Clase que realiza las operaciones de entrada/salida
 * 
 *
 */
public class EntradaSalida 
{
	private static boolean traza = false;
	private static boolean ficheroEntrada = false;
	private static boolean ficheroSalida = false;
	private static String nombreEntrada = "";
	private static String nombreSalida = "";
	private static String[] vectorEntrada;
	private static boolean errorEntrada = false;
	private static boolean errorEntradaN = false;
	private static boolean errorEntradaV = false;
	private static boolean errorSalida = false;
	
	/**
	 * Método que obtiene la información sobre los argumentos pasados por línea de comandos
	 * 
	 * @param args Variable leída por línea de comandos
	 * @return Vector de enteros que representa el problema a procesar
	 * @throws IOException
	 */
	public static int [] DatosInicio(String [] args) throws IOException 
	{	
		String errN = "Tamaño de entrada inválido";
		String errV = "Vector de entrada inválido";
		
		// Analizamos los argumentos de entrada
		for (String argumento : args) 
		{
			switch (argumento) 
			{
				// Traza
				case "-t":
					traza = true;
					break;
				
				// Mostrar ayuda
				case "-h":
					System.out.println("\tSINTAXIS:");
					System.out.println("\tmayoritario [-t][-h] [fichero_entrada] [fichero_salida]");
					System.out.println("\t-t                      Traza las llamadas recursivas");
					System.out.println("\t-h                      Muestra esta ayuda");
					System.out.println("\tfichero_entrada         Nombre del fichero de entrada");
					System.out.println("\tfichero_salida          Nombre del fichero de salida\n");
					break;
				
				// Se ha introducido una cadena de caracteres
				default:
					
					File ficheroPrueba = new File(argumento);
					
					// Comprobamos si es un fichero de entrada
					if (!ficheroEntrada) 
					{
						ficheroEntrada = true;
						nombreEntrada = argumento;					
						
						if (ficheroPrueba.exists())
						{	
							FileReader fEntrada = new FileReader(argumento);
							BufferedReader bEntrada = new BufferedReader(fEntrada);		
							String lectura = bEntrada.readLine();
							
							// Validamos el tamaño de entrada
							if (comprobarN(lectura))
							{
								int nEntrada = Integer.parseInt(lectura);
								
								try
								{
									String elementosEntrada = bEntrada.readLine().trim();
									
									// Validamos el vector de entrada
									if (comprobarVector(elementosEntrada, nEntrada)) 
									{					
										vectorEntrada = elementosEntrada.split(" ");
									}
									else 
									{
										errorEntradaV = true;
									}
									
									fEntrada.close();
								}
								catch (NullPointerException e)
								{
									errorEntradaV = true;
								}
							}
							else 
							{
								errorEntradaN = true;
							}
						}
						else
						{
							errorEntrada = true;
						}
					}
					// Comprobamos si es un fichero de salida
					else 
					{				
						nombreSalida = argumento;
						
						if (ficheroPrueba.exists()) 
						{
							errorSalida = true;
						}
						else 
						{					
							ficheroSalida = true;
						}
					}
					break;
				}
			}
		
		System.out.println("\tFichero de entrada:  " + nombreEntrada + "\n");
		
		if (!ficheroEntrada) 
		{
			System.out.println("\t\tLa entrada de datos se realizará por teclado\n");
		}
		else
		{	
			if (!errorEntrada && !errorEntradaN && !errorEntradaV)
			{					
				System.out.println("\t\tNúmero de elementos: " + vectorEntrada.length);
				
				String elementos = "";
				
				for (String elemento : vectorEntrada) 
				{
					elementos += elemento + " ";
				}
				
				System.out.println("\t\tLista de elementos:  " + elementos + "\n");
			}
			else 
			{
				if(errorEntrada) System.out.println("\t\tError. El archivo no existe");
				if(errorEntradaN) System.out.println("\t\tError. " + errN);
				if(errorEntradaV) System.out.println("\t\tError. " + errV);
				
				System.out.println("\t\tLa entrada de datos se realizará por teclado\n");
			}
		}
		
		System.out.println("\tFichero de salida:   " + nombreSalida + "\n");
		
		if (!ficheroSalida)
		{
			if(errorSalida) System.out.println("\t\tError. El archivo de salida ya existe");
			
			System.out.println("\t\tLa salida de datos se mostrará por pantalla\n");
		}
		
		// Comprobamos si debemos realizar una entrada manual de datos
		if (!ficheroEntrada || errorEntrada)
		{
			Scanner entrada = new Scanner(System.in);
			int nTeclado = 0;
			String lectura = null;
			String elementosTeclado = null;
			
			System.out.println("Comenzando entrada manual de datos.\n");
			
			do
			{
				System.out.println("Introduce el número de elementos: ");
				lectura = entrada.nextLine().trim();
				
				if (!comprobarN(lectura)) 
				{
					System.out.println("\nError. " + errN + "\n");
				}
			}
			while (!comprobarN(lectura));

			nTeclado = Integer.parseUnsignedInt(lectura);

			do
			{
				System.out.println("\nIntroduce los elementos separados por espacios: ");
				elementosTeclado = entrada.nextLine().trim();
				
				if (!comprobarVector(elementosTeclado, nTeclado)) 
				{
					System.out.println("\nError. " + errV);
				}
			}
			while (!comprobarVector(elementosTeclado, nTeclado));
			
			System.out.println();
			vectorEntrada = elementosTeclado.split(" ");

			System.out.println("\tNúmero de elementos: " + nTeclado);
			System.out.println("\tLista de elementos:  " + elementosTeclado + "\n");
			
			entrada.close();
		}
		
		return CrearVector(vectorEntrada);
	}

	/**
	 * Método que comprueba el tamaño de la entrada mediante una expresión regular
	 * 
	 * @param n El tamaño del problema
	 * @return Verdadero en caso de que la entrada sea un número entero mayor que 0, 
	 * sin ceros a la izquierda
	 */
	public static boolean comprobarN(String n)
	{
		// Patrón para validar el tamaño
		// Acepta un número sin ceros a la izquierda
		String patron = "[1-9]+[0-9]*";
		
		try
		{
			return n.matches(patron);
		}
		catch (NullPointerException e)
		{
			return false;
		}
	}

	/**
	 * Método que comprueba el vector de entrada mediante una expresión regular
	 * 
	 * @param vector El vector de elementos
	 * @param n El tamaño del problema
	 * @return Verdadero en caso de que la entrada sea una secuencia de números 
	 * enteros mayores que 0, sin ceros a la izquierda y separados por espacios
	 */
	public static boolean comprobarVector(String vector, int n)
	{
		// Patrón para validar el vector
		// Acepta números sin ceros a la izquierda separados un espacio
		String patron = "([1-9]+[0-9]*\\s?){" + n + "}";
		
		return vector.matches(patron);
	}

	/**
	 * Método que genera un vector de enteros a partir de un vector de String
	 * 
	 * @param vectorEntrada String que contiene los enteros
	 * @return Vector de enteros para aplicar el algoritmo
	 */
	public static int [] CrearVector(String [] vectorEntrada) 
	{	
		int[] vectorEnteros = null;
		
		if (vectorEntrada != null)
		{
			vectorEnteros = new int[vectorEntrada.length];
			
			for (int indice = 0; indice < vectorEntrada.length; indice++) 
			{	
				vectorEnteros[indice] = Integer.parseInt(vectorEntrada[indice]);	
			}	
		}
		
		return vectorEnteros;		
	}

	/**
	 * Getter del atributo traza
	 * 
	 * @return Valor de la traza
	 */
	public static boolean getTraza() 
	{
		return traza;
	}

	/**
	 * Método que efectúa la salida de los datos
	 * 
	 * @param resultado El resultado de aplicar el algoritmo sobre el problema
	 * @throws IOException
	 */
	public static void Salida (int resultado) throws IOException 
	{
		// Impresión por pantalla
		if (!ficheroSalida) 
		{
			if (resultado == -1) System.out.println("El elemento mayoritario es: N\n");
			
			else System.out.println("El elemento mayoritario es: " + resultado + "\n");			
		}
		// Impresión en fichero 
		else 
		{	
			if (resultado == -1)
			{	
				FileWriter ficheroSalida = new FileWriter(nombreSalida);
				ficheroSalida.write("N");
				ficheroSalida.close();	
			} 
			else
			{
				FileWriter ficheroSalida = new FileWriter(nombreSalida);
				ficheroSalida.write(new Integer(resultado).toString());
				ficheroSalida.close();
			}
			
			System.out.println("Fichero de salida escrito\n");
		}
	}
}
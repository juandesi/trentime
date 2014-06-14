package com.tren.time;

public class DBStrings {
	
	//nombre de la base de datos fisica
	public static String DB_NAME = "DB_URQUIZA";
	
	// nombres de las tablas
	public static String C_TABLA_URQUIZA = "Horarios_Urquiza";	
	public static String C_TABLA_ESTACIONES = "ESTACIONES";
	
	//campo id de todas las tablas
	public static String C_COLUMNA_ID = "_id";
	
	// campos especiales de las tablas
	public static String C_COLUMNA_SENTIDO = "_sentido";
	public static String C_COLUMNA_NOMBRE_ESTACION = "estacion_nombre";
	
	// Arrays De Las Columnas
	public static String[] C_ARRAY_COLUMNAS_HORARIOS = {C_COLUMNA_ID, "e1","e2","e3","e4","e5","e6","e7","e8","e9","e10","e11",
													"e12","e13","e14","e15","e16","e17","e18","e19","e20","e21","e22","e23"};
	public static String[] C_ARRAY_COLUMNAS_ESTACIONES = {C_COLUMNA_ID, C_COLUMNA_NOMBRE_ESTACION};
	
}

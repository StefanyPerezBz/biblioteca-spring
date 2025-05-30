package com.spark.lms.common;

import java.util.ArrayList;
import java.util.List;

public class Constants {


	public static final String ROL_ADMIN = "Admin";
	public static final String ROL_BIBLIOTECARIO = "Bibliotecario";

	public static final String MIEMBRO_PADRE = "Padre";
	public static final String MIEMBRO_ESTUDIANTE = "Estudiante";
	public static final String MIEMBRO_OTRO = "Otro";
	public static final List<String> TIPOS_MIEMBROS = new ArrayList<String>() {{
		add(MIEMBRO_PADRE);
		add(MIEMBRO_ESTUDIANTE);
		add(MIEMBRO_OTRO);
	}};

	public static final Integer ESTADO_LIBRO_DISPONIBLE = 1;
	public static final Integer ESTADO_LIBRO_PRESTADO = 2;

	public static final Integer LIBRO_NO_DEVUELTO = 0;
	public static final Integer LIBRO_DEVUELTO = 1;

}

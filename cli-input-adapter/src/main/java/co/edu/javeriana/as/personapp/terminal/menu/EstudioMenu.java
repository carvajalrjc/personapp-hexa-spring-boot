package co.edu.javeriana.as.personapp.terminal.menu;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

import co.edu.javeriana.as.personapp.terminal.adapter.EstudioInputAdapterCli;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EstudioMenu {

	private static final int OPCION_REGRESAR_MODULOS = 0;
	private static final int PERSISTENCIA_MARIADB = 1;
	private static final int PERSISTENCIA_MONGODB = 2;

	private static final int OPCION_REGRESAR_MOTOR_PERSISTENCIA = 0;
	private static final int OPCION_VER_TODO = 1;
	private static final int OPCION_CREAR = 2;
	private static final int OPCION_BUSCAR = 3;
	private static final int OPCION_EDITAR = 4;
	private static final int OPCION_ELIMINAR = 5;
	private static final int OPCION_CONTAR = 6;

	public void iniciarMenu(EstudioInputAdapterCli estudioInputAdapterCli, Scanner keyboard) {
		boolean isValid = false;
		do {
			try {
				mostrarMenuMotorPersistencia();
				int opcion = leerOpcion(keyboard);
				switch (opcion) {
				case OPCION_REGRESAR_MODULOS:
					isValid = true;
					break;
				case PERSISTENCIA_MARIADB:
					estudioInputAdapterCli.setStudyOutputPortInjection("MARIA");
					menuOpciones(estudioInputAdapterCli, keyboard);
					break;
				case PERSISTENCIA_MONGODB:
					estudioInputAdapterCli.setStudyOutputPortInjection("MONGO");
					menuOpciones(estudioInputAdapterCli, keyboard);
					break;
				default:
					log.warn("La opción elegida no es válida.");
				}
			} catch (Exception e) {
				log.warn("Error: " + e.getMessage());
			}
		} while (!isValid);
	}

	private void menuOpciones(EstudioInputAdapterCli estudioInputAdapterCli, Scanner keyboard) {
		boolean isValid = false;
		do {
			try {
				mostrarMenuOpciones();
				int opcion = leerOpcion(keyboard);
				switch (opcion) {
				case OPCION_REGRESAR_MOTOR_PERSISTENCIA:
					isValid = true;
					break;
				case OPCION_VER_TODO:
					estudioInputAdapterCli.historial();
					break;
				case OPCION_CREAR:
					crearEstudio(estudioInputAdapterCli, keyboard);
					break;
				case OPCION_BUSCAR:
					buscarEstudio(estudioInputAdapterCli, keyboard);
					break;
				case OPCION_EDITAR:
					editarEstudio(estudioInputAdapterCli, keyboard);
					break;
				case OPCION_ELIMINAR:
					eliminarEstudio(estudioInputAdapterCli, keyboard);
					break;
				case OPCION_CONTAR:
					estudioInputAdapterCli.count();
					break;
				default:
					log.warn("La opción elegida no es válida.");
				}
			} catch (InputMismatchException e) {
				log.warn("Solo se permiten números.");
				keyboard.nextLine();
			}
		} while (!isValid);
	}

	private void mostrarMenuOpciones() {
		System.out.println("----------------------");
		System.out.println(OPCION_VER_TODO + " para ver todos los estudios");
		System.out.println(OPCION_CREAR + " para crear un estudio");
		System.out.println(OPCION_BUSCAR + " para buscar un estudio");
		System.out.println(OPCION_EDITAR + " para editar un estudio");
		System.out.println(OPCION_ELIMINAR + " para eliminar un estudio");
		System.out.println(OPCION_CONTAR + " para contar estudios");
		System.out.println(OPCION_REGRESAR_MOTOR_PERSISTENCIA + " para regresar");
	}

	private void mostrarMenuMotorPersistencia() {
		System.out.println("----------------------");
		System.out.println(PERSISTENCIA_MARIADB + " para MariaDB");
		System.out.println(PERSISTENCIA_MONGODB + " para MongoDB");
		System.out.println(OPCION_REGRESAR_MODULOS + " para regresar");
	}

	private void crearEstudio(EstudioInputAdapterCli estudioInputAdapterCli, Scanner keyboard) {
		try {
			System.out.print("Ingrese CC de la Persona: ");
			int ccPerson = keyboard.nextInt();
			keyboard.nextLine();
			System.out.print("Ingrese ID de la Profesión: ");
			int idProf = keyboard.nextInt();
			keyboard.nextLine();
			System.out.print("Ingrese Nombre de la Universidad: ");
			String college = keyboard.nextLine();
			System.out.print("Ingrese Fecha de Graduación (YYYY-MM-DD) o presione Enter para omitir: ");
			String dateStr = keyboard.nextLine();
			LocalDate date = dateStr.isEmpty() ? null : LocalDate.parse(dateStr);
			estudioInputAdapterCli.create(ccPerson, idProf, college, date);
			System.out.println("Estudio creado con éxito.");
		} catch (DateTimeParseException e) {
			log.warn("Formato de fecha inválido. Use YYYY-MM-DD");
		} catch (Exception e) {
			log.warn("Error al crear estudio: " + e.getMessage());
		}
	}

	private void buscarEstudio(EstudioInputAdapterCli estudioInputAdapterCli, Scanner keyboard) {
		try {
			System.out.print("Ingrese ID de la Profesión: ");
			int id = keyboard.nextInt();
			keyboard.nextLine();
			System.out.print("Ingrese CC de la Persona: ");
			int ccPerson = keyboard.nextInt();
			keyboard.nextLine();
			estudioInputAdapterCli.findOne(id, ccPerson);
		} catch (Exception e) {
			log.warn("Error al buscar estudio: " + e.getMessage());
		}
	}

	private void editarEstudio(EstudioInputAdapterCli estudioInputAdapterCli, Scanner keyboard) {
		try {
			System.out.print("Ingrese CC de la Persona: ");
			int ccPerson = keyboard.nextInt();
			keyboard.nextLine();
			System.out.print("Ingrese ID de la Profesión: ");
			int idProf = keyboard.nextInt();
			keyboard.nextLine();
			System.out.print("Ingrese Nuevo Nombre de la Universidad: ");
			String college = keyboard.nextLine();
			System.out.print("Ingrese Nueva Fecha de Graduación (YYYY-MM-DD) o presione Enter para omitir: ");
			String dateStr = keyboard.nextLine();
			LocalDate date = dateStr.isEmpty() ? null : LocalDate.parse(dateStr);
			estudioInputAdapterCli.edit(ccPerson, idProf, college, date);
		} catch (DateTimeParseException e) {
			log.warn("Formato de fecha inválido. Use YYYY-MM-DD");
		} catch (Exception e) {
			log.warn("Error al editar estudio: " + e.getMessage());
		}
	}

	private void eliminarEstudio(EstudioInputAdapterCli estudioInputAdapterCli, Scanner keyboard) {
		try {
			System.out.print("Ingrese ID de la Profesión: ");
			int id = keyboard.nextInt();
			keyboard.nextLine();
			System.out.print("Ingrese CC de la Persona: ");
			int ccPerson = keyboard.nextInt();
			keyboard.nextLine();
			estudioInputAdapterCli.drop(id, ccPerson);
		} catch (Exception e) {
			log.warn("Error al eliminar estudio: " + e.getMessage());
		}
	}

	private int leerOpcion(Scanner keyboard) {
		try {
			System.out.print("Ingrese una opción: ");
			return keyboard.nextInt();
		} catch (InputMismatchException e) {
			log.warn("Solo se permiten números.");
			keyboard.nextLine();
			return leerOpcion(keyboard);
		}
	}
}


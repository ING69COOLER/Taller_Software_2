package com.uniquindio.edu.co.Taller_Software_2.controller;

import com.uniquindio.edu.co.Taller_Software_2.domain.Book;
import com.uniquindio.edu.co.Taller_Software_2.domain.Calificacion;
import com.uniquindio.edu.co.Taller_Software_2.domain.Reseña;
import com.uniquindio.edu.co.Taller_Software_2.dto.BookDTO;
import com.uniquindio.edu.co.Taller_Software_2.infraestructure.LibroRepository;
import com.uniquindio.edu.co.Taller_Software_2.service.BookService;
import com.uniquindio.edu.co.Taller_Software_2.service.ReviewService;
import com.uniquindio.edu.co.Taller_Software_2.service.SearchService;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.TreeSet;

@Component
public class ConsoleController {

    private final SearchService searchService;
    private final BookService bookService;
    private final ReviewService reviewService;
    private final LibroRepository libroRepository;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleController(SearchService searchService,
            BookService bookService,
            ReviewService reviewService,
            LibroRepository libroRepository) {
        this.searchService = searchService;
        this.bookService = bookService;
        this.reviewService = reviewService;
        this.libroRepository = libroRepository;
    }

    // ================================================================
    // PUNTO DE ENTRADA: Menú Principal
    // ================================================================

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            imprimirMenuPrincipal();
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1" -> flujosBusquedaSimple();
                case "2" -> flujosBusquedaCombinada();
                case "3" -> flujosAgregarLibro();
                case "4" -> flujosListarTodosLosLibros();
                case "5" -> {
                    System.out.println("\n¡Hasta luego!\n");
                    salir = true;
                }
                default -> System.out.println("\n[!] Opción no válida. Intenta de nuevo.\n");
            }
        }
    }

    // ================================================================
    // MENÚ PRINCIPAL – Impresión
    // ================================================================

    private void imprimirMenuPrincipal() {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║   SISTEMA DE GESTIÓN DE LIBROS        ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║  1. Búsqueda simple                   ║");
        System.out.println("║  2. Búsqueda combinada                ║");
        System.out.println("║  3. Agregar nuevo libro               ║");
        System.out.println("║  4. Mostrar todos los libros          ║");
        System.out.println("║  5. Salir                             ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.print("Selecciona una opción: ");
    }

    // ================================================================
    // FLUJO 1: Búsqueda Simple
    // ================================================================

    private void flujosBusquedaSimple() {
        System.out.println("\n--- BÚSQUEDA SIMPLE ---");
        System.out.print("Ingresa el texto a buscar (título, autor, ISBN o año): ");
        String texto = scanner.nextLine().trim();

        if (texto.isBlank()) {
            System.out.println("[!] El texto de búsqueda no puede estar vacío.\n");
            return;
        }

        TreeSet<Book> resultados = searchService.busquedaSimple(texto);

        if (resultados.isEmpty()) {
            System.out.println("\nNo se encontraron libros con ese criterio.\n");
            return;
        }

        List<Book> lista = new ArrayList<>(resultados);
        imprimirResultadosNumerados(lista);
        seleccionarYMostrarDetalle(lista);
    }

    // ================================================================
    // FLUJO 2: Búsqueda Combinada
    // ================================================================

    private void flujosBusquedaCombinada() {
        System.out.println("\n--- BÚSQUEDA COMBINADA ---");
        System.out.println("(Deja en blanco los campos que no quieras usar)");

        System.out.print("Título:  ");
        String titulo = scanner.nextLine().trim();

        System.out.print("Autor:   ");
        String autor = scanner.nextLine().trim();

        System.out.print("ISBN:    ");
        String isbn = scanner.nextLine().trim();

        System.out.print("Año:     ");
        String ano = scanner.nextLine().trim();

        if (titulo.isBlank() && autor.isBlank() && isbn.isBlank() && ano.isBlank()) {
            System.out.println("[!] Debes ingresar al menos un criterio de búsqueda.\n");
            return;
        }

        TreeSet<Book> resultados = searchService.busquedaCombinada(titulo, autor, isbn, ano);

        if (resultados.isEmpty()) {
            System.out.println("\nNo se encontraron libros con esos criterios.\n");
            return;
        }

        List<Book> lista = new ArrayList<>(resultados);
        imprimirResultadosNumerados(lista);
        seleccionarYMostrarDetalle(lista);
    }

    // ================================================================
    // FLUJO 3: Agregar Nuevo Libro
    // ================================================================

    private void flujosAgregarLibro() {
        System.out.println("\n--- AGREGAR NUEVO LIBRO ---");

        System.out.print("Título:  ");
        String titulo = scanner.nextLine().trim();

        System.out.print("Autor:   ");
        String autor = scanner.nextLine().trim();

        System.out.print("ISBN:    ");
        String isbn = scanner.nextLine().trim();

        System.out.print("Año de publicación (YYYY-MM-DD o solo YYYY): ");
        String anoStr = scanner.nextLine().trim();

        if (titulo.isBlank() || autor.isBlank() || isbn.isBlank()) {
            System.out.println("[!] Título, autor e ISBN son obligatorios.\n");
            return;
        }

        LocalDate fecha = null;
        if (!anoStr.isBlank()) {
            try {
                // Permite "1967" o "1967-05-30"
                if (anoStr.length() == 4) {
                    fecha = LocalDate.of(Integer.parseInt(anoStr), 1, 1);
                } else {
                    fecha = LocalDate.parse(anoStr);
                }
            } catch (DateTimeParseException | NumberFormatException e) {
                System.out.println("[!] Formato de fecha inválido. Se guardará sin fecha.\n");
            }
        }

        BookDTO dto = new BookDTO();
        dto.setTitulo(titulo);
        dto.setAutor(autor);
        dto.setIsbn(isbn);
        dto.setAnoPublicacion(fecha);

        try {
            BookDTO guardado = bookService.crearLibro(dto);
            System.out.println("\n✔ Libro guardado con éxito. ID asignado: " + guardado.getId() + "\n");
        } catch (SQLException e) {
            System.out.println("[!] No se pudo guardar el libro (puede que el ISBN ya exista).\n");
        }
    }

    // ================================================================
    // RESULTADOS NUMERADOS
    // ================================================================

    private void imprimirResultadosNumerados(List<Book> libros) {
        System.out.println("\n══════ RESULTADOS ══════");
        for (int i = 0; i < libros.size(); i++) {
            Book libro = libros.get(i);
            System.out.printf("  %d. %-38s | Autor: %-24s | ISBN: %-13s | Año: %s | Similitud: %.0f%%%n",
                    i + 1,
                    truncar(libro.getTitulo(), 38),
                    truncar(libro.getAutor(), 24),
                    libro.getIsbn(),
                    libro.getAnoPublicacion() != null ? libro.getAnoPublicacion().getYear() : "N/A",
                    libro.getPorcentajeSimilitud() * 100);
        }
        System.out.println("════════════════════════\n");
    }

    // ================================================================
    // SELECCIÓN DE LIBRO Y VISTA DETALLADA
    // ================================================================

    private void seleccionarYMostrarDetalle(List<Book> libros) {
        System.out.print("Ingresa el número del libro para ver su detalle (0 para volver): ");
        String entrada = scanner.nextLine().trim();

        int seleccion;
        try {
            seleccion = Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            System.out.println("[!] Entrada inválida.\n");
            return;
        }

        if (seleccion == 0)
            return;

        if (seleccion < 1 || seleccion > libros.size()) {
            System.out.println("[!] Número fuera de rango.\n");
            return;
        }

        Long libroId = libros.get(seleccion - 1).getId();
        mostrarDetalleCompleto(libroId);
        menuAccionesLibro(libroId);
    }

    // ================================================================
    // FLUJO 4: Mostrar Todos los Libros
    // ================================================================

    private void flujosListarTodosLosLibros() {
        System.out.println("\n--- TODOS LOS LIBROS ---");
        List<Book> libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en la base de datos.\n");
            return;
        }

        // Reusamos imprimirResultadosNumerados pero sin similitud (será 0%)
        System.out.println("\n══════ LISTADO COMPLETO ══════");
        for (int i = 0; i < libros.size(); i++) {
            Book libro = libros.get(i);
            System.out.printf("  %d. %-38s | Autor: %-24s | ISBN: %-13s | Año: %s%n",
                    i + 1,
                    truncar(libro.getTitulo(), 38),
                    truncar(libro.getAutor(), 24),
                    libro.getIsbn(),
                    libro.getAnoPublicacion() != null ? libro.getAnoPublicacion().getYear() : "N/A");
        }
        System.out.println("══════════════════════════════\n");
        System.out.println("Total: " + libros.size() + " libro(s) registrado(s).\n");

        seleccionarYMostrarDetalle(libros);
    }

    // ================================================================
    // CONSULTA DETALLADA DEL LIBRO (con reseñas y calificaciones)
    // ================================================================

    private void mostrarDetalleCompleto(Long libroId) {
        // Dos queries separadas para evitar MultipleBagFetchException
        Optional<Book> optConReseñas = libroRepository.findByIdConReseñas(libroId);
        Optional<Book> optConCalificaciones = libroRepository.findByIdConCalificaciones(libroId);

        if (optConReseñas.isEmpty()) {
            System.out.println("[!] No se pudo cargar el libro seleccionado.\n");
            return;
        }

        Book libro = optConReseñas.get();
        optConCalificaciones.ifPresent(c -> libro.setCalificaciones(c.getCalificaciones()));

        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║                   DETALLE DEL LIBRO                 ║");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.printf("║  ID         : %-37d║%n", libro.getId());
        System.out.printf("║  Título     : %-37s║%n", truncar(libro.getTitulo(), 37));
        System.out.printf("║  Autor      : %-37s║%n", truncar(libro.getAutor(), 37));
        System.out.printf("║  ISBN       : %-37s║%n", libro.getIsbn());
        String año = libro.getAnoPublicacion() != null ? libro.getAnoPublicacion().toString() : "N/A";
        System.out.printf("║  Publicación: %-37s║%n", año);

        // ── Calificaciones
        List<Calificacion> cals = libro.getCalificaciones();
        if (cals == null || cals.isEmpty()) {
            System.out.println("║  Calificación   : Sin calificaciones aún               ║");
        } else {
            double promedio = cals.stream().mapToInt(Calificacion::getPuntaje).average().orElse(0);
            System.out.printf("║  Calificación   : %.1f/5 (%d votos)%-20s║%n",
                    promedio, cals.size(), "");
        }

        System.out.println("╠══════════════════════════════════════════════════════╣");

        // ── Reseñas
        List<Reseña> reseñas = libro.getReseñas();
        if (reseñas == null || reseñas.isEmpty()) {
            System.out.println("║  Reseñas: No hay reseñas todavía.                    ║");
        } else {
            System.out.println("║  RESEÑAS:                                            ║");
            for (int i = 0; i < reseñas.size(); i++) {
                System.out.printf("║  %d. %s%n", i + 1, reseñas.get(i).getDescripcion());
            }
        }

        System.out.println("╚══════════════════════════════════════════════════════╝\n");
    }

    // ================================================================
    // SUBMENÚ: ACCIONES SOBRE EL LIBRO SELECCIONADO
    // ================================================================

    private void menuAccionesLibro(Long libroId) {
        boolean regresar = false;
        while (!regresar) {
            System.out.println("┌──────────────────────────────────┐");
            System.out.println("│  ¿Qué deseas hacer con este libro?│");
            System.out.println("├──────────────────────────────────┤");
            System.out.println("│  1. Agregar reseña               │");
            System.out.println("│  2. Agregar calificación (1–5)   │");
            System.out.println("│  3. Volver al menú principal     │");
            System.out.println("└──────────────────────────────────┘");
            System.out.print("Opción: ");
            String op = scanner.nextLine().trim();

            switch (op) {
                case "1" -> flujosAgregarReseña(libroId);
                case "2" -> flujosAgregarCalificacion(libroId);
                case "3" -> regresar = true;
                default -> System.out.println("[!] Opción no válida.\n");
            }
        }
    }

    // ================================================================
    // FLUJO: Agregar Reseña
    // ================================================================

    private void flujosAgregarReseña(Long libroId) {
        System.out.println("\n--- AGREGAR RESEÑA ---");
        System.out.print("Escribe tu reseña: ");
        String texto = scanner.nextLine().trim();

        if (texto.isBlank()) {
            System.out.println("[!] La reseña no puede estar vacía.\n");
            return;
        }

        try {
            reviewService.agregarReseña(libroId, texto);
            System.out.println("✔ Reseña guardada con éxito.\n");
            mostrarDetalleCompleto(libroId); // refresca el detalle
        } catch (IllegalArgumentException e) {
            System.out.println("[!] " + e.getMessage() + "\n");
        } catch (RuntimeException e) {
            System.out.println("[!] Error al guardar la reseña: " + e.getMessage() + "\n");
        }
    }

    // ================================================================
    // FLUJO: Agregar Calificación
    // ================================================================

    private void flujosAgregarCalificacion(Long libroId) {
        System.out.println("\n--- AGREGAR CALIFICACIÓN ---");
        System.out.print("Ingresa tu calificación del 1 al 5: ");
        String entrada = scanner.nextLine().trim();

        int puntaje;
        try {
            puntaje = Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            System.out.println("[!] Debes ingresar un número entre 1 y 5.\n");
            return;
        }

        try {
            reviewService.agregarCalificacion(libroId, puntaje);
            System.out.println("✔ Calificación guardada con éxito.\n");
            mostrarDetalleCompleto(libroId); // refresca el detalle
        } catch (IllegalArgumentException e) {
            System.out.println("[!] " + e.getMessage() + "\n");
        } catch (RuntimeException e) {
            System.out.println("[!] Error al guardar la calificación: " + e.getMessage() + "\n");
        }
    }

    // ================================================================
    // UTILIDADES
    // ================================================================

    private String truncar(String texto, int maxLen) {
        if (texto == null)
            return "N/A";
        return texto.length() <= maxLen ? texto : texto.substring(0, maxLen - 3) + "...";
    }
}

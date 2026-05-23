package com.librarymanagement.ui;

import com.librarymanagement.enums.BookCategory;
import com.librarymanagement.model.Book;
import com.librarymanagement.service.BookService;
import com.librarymanagement.service.LoanService;
import com.librarymanagement.service.MemberService;

import java.util.Scanner;

public class LibraryMenu {

    private BookService bookService;
    private MemberService memberService;
    private LoanService loanService;

    private Scanner scanner;
    private int option;

    public LibraryMenu() {
        this.bookService = new BookService();
        this.memberService = new MemberService();
        this.loanService = new LoanService();

        this.scanner = new Scanner(System.in);
    }

    public void start() {

        do {

            System.out.println("\n--- GESTOR DE MANEJO DE LIBROS ---");
            System.out.println("1. Registrar libro");
            System.out.println("2. Registrar miembro");
            System.out.println("3. Solicitar préstamo libro");
            System.out.println("4. Devolver libro");
            System.out.println("5. Lista de libros");
            System.out.println("6. Salir");

            System.out.print("Elija una opción: ");

            if (scanner.hasNextInt()) {
                option = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Por favor, introduzca un número válido.");
                scanner.nextLine();
                continue;
            }

            if (option == 1) {

                System.out.print("Título: ");
                String title = scanner.nextLine();

                System.out.print("Autor: ");
                String author = scanner.nextLine();

                System.out.print("Categoría: ");
                String texto = scanner.nextLine().toUpperCase();

                BookCategory catValue = BookCategory.valueOf(texto);

                Book newBook = new Book(
                        title,
                        author,
                        catValue
                );

                bookService.registerBook(newBook);

                System.out.println("Libro registrado correctamente.");
            }

        } while (option != 6);


        System.out.print("Ingrese nombre del miembro: ");

    }
}
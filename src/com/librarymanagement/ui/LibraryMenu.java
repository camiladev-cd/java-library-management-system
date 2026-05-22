package com.librarymanagement.ui;

import com.librarymanagement.model.Book;
import com.librarymanagement.service.MemberService;
import com.librarymanagement.service.BookService;
import com.librarymanagement.service.LoanService;

import java.util.Scanner;

public class LibraryMenu {

    private BookService bookService;
    private MemberService memberService;
    private LoanService loanService;

    public LibraryMenu() {
        this.bookService = new BookService();
        this.memberService = new MemberService();
        this.loanService = new LoanService();
    }

    Scanner scanner = new Scanner(System.in);
    int option = 0;

    public void start (){

        do {
            System.out.println("\n--- GESTOR DE MANEJO DE LIBROS ---");
            System.out.println("1. Registrar libro");
            System.out.println("2. Registrar miembro");
            System.out.println("3. Solicitar prestamo libro");
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
                System.out.print("Ingrese Titulo del libro: ");
                String title = scanner.nextLine();
                System.out.print("Ingrese Autor del libro: ");
                String author = scanner.nextLine();
                System.out.print("Ingrese Categoria del libro: ");
                String category = scanner.nextLine();
                String texto = scanner.nextLine().toUpperCase();


                Book newBook = new Book(title, author, catvalue);
            }
        }
    }
}

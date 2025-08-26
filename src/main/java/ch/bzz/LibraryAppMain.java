package ch.bzz;
import java.util.Scanner;


public class LibraryAppMain {

    public static final Book BOOK1 = new Book(1, "978-3-8362-9544-4", "Java ist auch eine Insel", "Christian Ullenboom", 2023);
    public static final Book BOOK2 = new Book(2, "978-3-658-43573-8", "Grundkurs Java", "Dietmar Abts", 2024);

    public static void main(String[] args) {
        Scanner scanning = new Scanner(System.in);
        System.out.println(BOOK1.getTitle());
        while (true) {
            String userinput = scanning.nextLine();
            if (userinput.equals("quit")){
                break;
            } else if (userinput.equals("help")) {
                System.out.println("quit for quit, help for help");
            } else {
                System.out.println("command not found: " + userinput);
            }


        }
    }

}

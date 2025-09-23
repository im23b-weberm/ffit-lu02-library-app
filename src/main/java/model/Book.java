package model;

public class Book {
        private final int id;
        private final String isbn;
        private final String title;
        private final String author;
        private final int year;



        public Book(int id, String isbn, String title, String author, int year) {
            this.id = id;
            this.isbn = isbn;
            this.title = title;
            this.author = author;
            this.year = year;
        }

        public String getTitle() {
            return title;
        }

        public String getIsbn() {
            return isbn;
        }

        public int getId() {
            return id;
        }

        public String getAuthor() {
            return author;
        }

        public int getYear() {
            return year;
        }


    @Override
        public String toString() {
            return "ID: " + id + ", ISBN: " + isbn + ", Title: " + title + ", Author: " + author + ", Year: " + year;
        }



}


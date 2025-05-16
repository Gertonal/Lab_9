package by.ivan.laba9;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ModelLibrary {
    private List<Book> books;
    private static final String FILE_PATH = "C:/Users/vanya/IdeaProjects/laba9/library.json";
    private Gson gson;

    public ModelLibrary() {
        this.gson = new Gson();
        this.books = new ArrayList<>();
        loadBooks();
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public void addBook(Book book) {
        books.add(book);
        saveBooks();
    }

    private void loadBooks() {
        File file = new File(FILE_PATH);
        if (!file.exists() || file.length() == 0) {
            return;
        }
        try (Reader reader = new FileReader(FILE_PATH)) {
            books = gson.fromJson(reader, new TypeToken<List<Book>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveBooks() {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(books, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
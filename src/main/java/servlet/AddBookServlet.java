package servlet;

import manager.AuthorManager;
import manager.BookManager;
import model.Author;
import model.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/book", "/add"})
@MultipartConfig(
        fileSizeThreshold = 1024*1024*1, //1MB
        maxFileSize = 1024*1024*10,      //10MB
        maxRequestSize = 1024*1024*100   //100MB
)
public class AddBookServlet extends HttpServlet {
    BookManager bookManager = new BookManager();
    AuthorManager authorManager = new AuthorManager();

    private static final String IMAGE_PATH="/Users/appleadmin/IdeaProjects/myLibrary/projectImajes/";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Author> authorslist = authorManager.showAuthors();
        req.setAttribute("author", authorslist);
        req.getRequestDispatcher("/WEB-INF/addBook.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        double price = Double.parseDouble(req.getParameter("price"));
        int authorId = Integer.parseInt(req.getParameter("authorId"));
        Part profilePicPart = req.getPart("profilePic");

        String fileName=null;
        if(profilePicPart !=null){
            long nanoTime = System.nanoTime();
            fileName=nanoTime+ "_" +profilePicPart.getSubmittedFileName();

            profilePicPart.write(IMAGE_PATH+fileName);

        }

        Book book = Book.builder()
                .title(title)
                .description(description)
                .price(price)
                .author(authorManager.getById(authorId))
                .bookPic(fileName)
                .build();

        bookManager.addBook(book);
        resp.sendRedirect("/books");


    }
}

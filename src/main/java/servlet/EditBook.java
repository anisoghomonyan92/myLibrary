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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(urlPatterns = "/book/edit")
@MultipartConfig(
        fileSizeThreshold = 1024*1024*1, //1MB
        maxFileSize = 1024*1024*10,      //10MB
        maxRequestSize = 1024*1024*100   //100MB
)
public class EditBook extends HttpServlet {

    AuthorManager authorManager = new AuthorManager();
    BookManager bookManager = new BookManager();
    private static final String IMAGE_PATH="/Users/appleadmin/IdeaProjects/myLibrary/projectImajes/";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int bookId = Integer.parseInt(req.getParameter("bookId"));
        Book book = bookManager.getById(bookId);
        req.setAttribute("author", authorManager.showAuthors());

        req.setAttribute("book", book);
        req.getRequestDispatcher("/WEB-INF/editBook.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int bookId = Integer.parseInt(req.getParameter("bookId"));
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        double price = Double.parseDouble(req.getParameter("price"));
        int authorId = Integer.parseInt(req.getParameter("authorId"));
        Part bookPic = req.getPart("bookPic");

        String fileName=null;
        if(bookPic !=null){
            long nanoTime = System.nanoTime();
            fileName=nanoTime+ "_" +bookPic.getSubmittedFileName();

            bookPic.write(IMAGE_PATH+fileName);

        }

        String filePath=IMAGE_PATH+bookPic;
        File imageFile=new File(filePath);
        if(imageFile.exists())  {
            try(FileInputStream inStream=new FileInputStream(imageFile)){
                resp.setContentType("image/jpeg");
                resp.setContentLength((int) imageFile.length());


                OutputStream outputStream = resp.getOutputStream();

                byte[] buffer=new byte[4096];
                int bytesRead=-1;

                while ((bytesRead=inStream.read(buffer)) !=-1){
                    outputStream.write(buffer,0, bytesRead);
                }

            }catch(IOException e){
                e.printStackTrace();
            }
        }
        Book book = Book.builder()
                .id(bookId)
                .title(title)
                .description(description)
                .price(price)
                .author(authorManager.getById(authorId))
                .bookPic(fileName)
                .build();
        bookManager.editBook(book);
        resp.sendRedirect("/books");

    }
}

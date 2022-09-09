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

@WebServlet(urlPatterns = "/author/edit")
@MultipartConfig(
        fileSizeThreshold = 1024*1024*1, //1MB
        maxFileSize = 1024*1024*10,      //10MB
        maxRequestSize = 1024*1024*100   //100MB
)

public class EditAuthor extends HttpServlet {
    AuthorManager authorManager = new AuthorManager();
    BookManager bookManager = new BookManager();
    private static final String IMAGE_PATH="/Users/appleadmin/IdeaProjects/myLibrary/projectImajes/";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int authorId = Integer.parseInt(req.getParameter("authorId"));
        Author author = authorManager.getById(authorId);
        req.setAttribute("book", bookManager.showBooks());
        req.setAttribute("author", author);
        req.getRequestDispatcher("/WEB-INF/editAuthor.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int authorId = Integer.parseInt(req.getParameter("authorId"));
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        int age = Integer.parseInt(req.getParameter("age"));
        Part profilePicPart = req.getPart("profilePic");

        String fileName=null;
        if(profilePicPart !=null){
            long nanoTime = System.nanoTime();
            fileName=nanoTime+ "_" +profilePicPart.getSubmittedFileName();

            profilePicPart.write(IMAGE_PATH+fileName);

        }

        String filePath=IMAGE_PATH+profilePicPart;
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


        Author author = Author.builder()
                .id(authorId)
                .name(name)
                .surname(surname)
                .email(email)
                .age(age)
                .profilePic(fileName)
                .build();
        authorManager.editAuthor(author);
        resp.sendRedirect("/authors");

    }
}

package manager;

import db.DBConectionProvider;
import model.Author;
import model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookManager {

    private Connection connection = DBConectionProvider.getInstance().getConnection();
    AuthorManager authorManager=new AuthorManager();
    
    public void addBook(Book book) {
        String sql = "Insert into my_library.book (title,description,price,author_id,book_pic)Values(?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, 1);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getDescription());
            preparedStatement.setDouble(3, book.getPrice());
            preparedStatement.setInt(4, book.getAuthor().getId());
            preparedStatement.setString(5, book.getBookPic());

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                book.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public List<Book> showBooks(){
        String sql="SELECT * from my_library.book";
        List<Book> books=new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                books.add(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public Book getById(int id){
        String sql="SELECT * from my_library.book WHERE id="+id;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()){
                return getBookFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        Book book=new Book();
        book.setId(resultSet.getInt("id"));
        book.setTitle(resultSet.getString("title"));
        book.setDescription(resultSet.getString("description"));
        book.setPrice(resultSet.getDouble("price"));
        int authorId=resultSet.getInt("author_id");
        book.setBookPic(resultSet.getString("book_pic"));
        Author author = authorManager.getById(authorId);
        book.setAuthor(author);

        return book;
    }

    public void removeBookById(int id) {
        String sql = "DELETE from my_library.book WHERE id=" + id;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editBook(Book book){
        String sql="UPDATE my_library.book set title=?,description=?,price=?,author_id=?,book_pic=? Where id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, 1);

            preparedStatement.setString(1,book.getTitle());
            preparedStatement.setString(2,book.getDescription());
            preparedStatement.setDouble(3,book.getPrice());
            preparedStatement.setInt(4,book.getAuthor().getId());
            preparedStatement.setString(5,book.getBookPic());
            preparedStatement.setInt(6,book.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package manager;

import db.DBConectionProvider;
import model.Author;
import model.Book;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class AuthorManager {
    private Connection connection = DBConectionProvider.getInstance().getConnection();

    public AuthorManager() {
    }

    public void addAuthor(Author author) {
        String sql = "Insert into my_library.author (name,surname,email,age,profile_pic)Values(?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, 1);
            preparedStatement.setString(1, author.getName());
            preparedStatement.setString(2, author.getSurname());
            preparedStatement.setString(3, author.getEmail());
            preparedStatement.setInt(4, author.getAge());
            preparedStatement.setString(5, author.getProfilePic());

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                author.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Author> showAuthors() {
        String sql = "SELECT *  from my_library.author";
        List<Author> authors = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                authors.add(getAuthorFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }


    public Author getById(int id) {
        String sql = "SELECT * from my_library.author WHERE id=" + id;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return getAuthorFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Author getAuthorFromResultSet(ResultSet resultSet) throws SQLException {
        Author author = Author.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .surname(resultSet.getString("surname"))
                .email(resultSet.getString("email"))
                .age(resultSet.getInt("age"))
                .profilePic(resultSet.getString("profile_pic"))
                .build();

        return author;
    }

    public void removeAuthorById(int id) {
        String sql = "DELETE from my_library.author WHERE id=" + id;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editAuthor(Author author){
        String sql="UPDATE my_library.author set name=?,surname=?,email=?,age=?,profile_pic=? Where id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, 1);

            preparedStatement.setString(1,author.getName());
            preparedStatement.setString(2,author.getSurname());
            preparedStatement.setString(3,author.getEmail());
            preparedStatement.setInt(4,author.getAge());
            preparedStatement.setString(5,author.getProfilePic());
            preparedStatement.setInt(6,author.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

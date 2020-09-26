import java.sql.*;

public class Book
{
    private String name,author,issue_status,issue_date;
    private int id,student_id,fine;

    Book()
    {

    }

    public int getFine() {
        return fine;
    }
    public int getId() {
        return id;
    }
    public int getStudent_id() {
        return student_id;
    }
    public String getAuthor() {
        return author;
    }
    public String getIssue_date() {
        return issue_date;
    }
    public String getIssue_status() {
        return issue_status;
    }
    public String getName() {
        return name;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setIssue_date(String due_date) {
        this.issue_date = due_date;
    }
    public void setFine(int fine) {
        this.fine = fine;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setIssue_status(String issue_status) {
        this.issue_status = issue_status;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setStudent_id(int student_id) { this.student_id = student_id; }

    public  void print_book()
    {
        System.out.println("Book Name    :- " + name );
        System.out.println("Author Name  :- "   + author);
        System.out.println("Book ID      :- " + id);
        System.out.println("Book Status  :- " + issue_status);
        System.out.println("Student ID   :- " + student_id);
        System.out.println("Issued Date  :- " + issue_date);
        System.out.println("Fine         :- " + fine);
        System.out.println();
    }

    public void update_fine(int book_id,int fine) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/assign_2","yogesh","password");
        String update_fine_query = "update book set fine =? where id =?";
        PreparedStatement update = con.prepareStatement(update_fine_query);
        update.setString(1,Integer.toString(fine));
        update.setString(2,Integer.toString(book_id));
        update.executeUpdate();
        con.close();
    }
}

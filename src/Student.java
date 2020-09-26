import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
public class Student
{
    private String name;
    private int id;
    private ArrayList<Book> books_issued;


    Student()
    {
        books_issued = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setBooks_issued(ArrayList<Book> books_issued) {
        this.books_issued = books_issued;
    }
    public ArrayList<Book> getBooks_issued() {
        return books_issued;
    }
    public void addBook(Book b)
    {
        books_issued.add(b);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void update_books_issued()
    {

        books_issued.clear();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/assign_2", "yogesh", "password");
            Statement stmt = con.createStatement();
            Statement extra_stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from book where student_id =" + id);
            while (rs.next())
            {
                Book b = new Book();
                b.setName(rs.getString(1));
                b.setAuthor(rs.getString(2));
                b.setId(rs.getInt(3));
                b.setIssue_status(rs.getString(4));
                b.setStudent_id(rs.getInt(5));
                b.setIssue_date(rs.getString(6));

                long fine = 0;
                java.util.Date current_date = new java.sql.Date(System.currentTimeMillis());
                java.util.Date issue_date = rs.getDate(6);

                java.sql.Date issued_date = rs.getDate(6);
                if(issue_date != null)
                {
                    long between_days = Math.abs(issued_date.getTime() - current_date.getTime()) / (24*60*60*1000);
                    long extra_days = 0;
                    if(between_days>15)
                    {
                        extra_days = between_days-15;
                    }

                    fine = extra_days*5;
                }
                else
                    fine = 0;

                b.setFine((int) fine);
                b.update_fine(rs.getInt(3), (int) fine);
                addBook(b);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void view_status()
    {
        Book temp_book = new Book();
        for(int i=0;i<books_issued.size();i++)
        {
            temp_book = books_issued.get(i);
            temp_book.print_book();

        }
    }

    public void searchBook() throws ClassNotFoundException, SQLException {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/assign_2", "yogesh", "password");
            Statement stmt = con.createStatement();
            Scanner scan = new Scanner(System.in);
            int book_id;
            System.out.println("Please enter the book ID you want to search");
            book_id = scan.nextInt();
            ResultSet rs = stmt.executeQuery("select * from book where id =" + book_id);
            if(!rs.next())
            {
                System.out.println("There is no book present in the library with the given ID");
            }
            else
            {
                System.out.println("Book Name    :- " + rs.getString(1) );
                System.out.println("Author Name  :- "   + rs.getString(2));
                System.out.println("Book ID      :- " + rs.getInt(3));
                System.out.println("Book Status  :- " + rs.getString(4));
                System.out.println("Student ID   :- " + rs.getInt(5));
                System.out.println("Issued Date  :- " + rs.getString(6));
            }
            con.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}

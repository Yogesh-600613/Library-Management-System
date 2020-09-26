import java.sql.*;
import java.util.Scanner;
public class Admin
{
    private String password = "iamadmin";

    Admin()
    {

    }

    public String getPassword()
    {
        return password;

    }

    public void addBook_database()
    {
        Scanner scan = new Scanner(System.in);
        String temp;
        int tempo;
        Book new_book = new Book();

        System.out.print("Book Name :- ");
        temp=scan.next();
        new_book.setName(temp);

        System.out.print("Author Name :- ");
        temp=scan.next();
        new_book.setAuthor(temp);

        System.out.print("Book ID :- ");
        tempo = scan.nextInt();
        new_book.setId(tempo);



        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/assign_2", "yogesh", "password");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from book where id =" + tempo);
            if(rs.next())
            {
                System.out.println("A book with given ID is already present in library and hence cannot be added");
            }
            else
            {
                PreparedStatement adder = con.prepareStatement("insert into book(name,author,id) values(?,?,?)");
                adder.setString(1, new_book.getName());
                adder.setString(2, new_book.getAuthor());
                adder.setInt(3, new_book.getId());
                adder.execute();
                System.out.println("Book Successfully Added");
                con.close();
            }
        }
        catch(Exception e)
        {
            System.out.println("e");

        }
    }

    public void deleteBook_database(int bID) throws ClassNotFoundException, SQLException {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/assign_2", "yogesh", "password");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from book where id =" + bID);
            if(!rs.next())
            {
                System.out.println("No such book exists");
                con.close();
            }
            else if(rs.getString(4).equals("issued"))
            {
                System.out.println("The book is already issued to student " + rs.getInt(5) + " and hence cannot be deleted" );
                System.out.println("First get the book returned and then proceed again to delete it");
                con.close();
            }
            else
            {
                PreparedStatement deleter = con.prepareStatement("delete from book where id = ?");
                deleter.setString(1,Integer.toString(bID));
                deleter.executeUpdate();
                System.out.println("Book deleted successfully");
                con.close();
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public void getBook_status() throws ClassNotFoundException, SQLException
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/assign_2", "yogesh", "password");
            Statement stmt = con.createStatement();
            String book_id;
            System.out.println("Please enter book ID");
            Scanner scan = new Scanner(System.in);
            book_id = scan.next();
            ResultSet rs = stmt.executeQuery("select * from book where id=" + Integer.parseInt(book_id));
            if(!rs.next())
            {
                System.out.println("No such book exists");
            }
            else
            {
                System.out.println("Book Name    :- " + rs.getString(1));
                System.out.println("Author Name  :- " + rs.getString(2));
                System.out.println("Book ID      :- " + rs.getInt(3));
                System.out.println("Book Status  :- " + rs.getString(4));
                System.out.println("Student ID   :- " + rs.getInt(5));
                System.out.println("Issued Date  :- " + rs.getString(6));
                long fine = 0;
                java.util.Date current_date = new java.sql.Date(System.currentTimeMillis());
                java.util.Date issue_date = rs.getDate(6);

                Date issued_date = rs.getDate(6);
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

                System.out.println("Fine         :- " + fine);
                PreparedStatement updater = con.prepareStatement("update book set fine =? where id = ?");
                updater.setInt(1, (int) fine);
                updater.setInt(2, rs.getInt(3));
                updater.executeUpdate();
                con.close();
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public void updateBook_database() throws ClassNotFoundException, SQLException
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/assign_2", "yogesh", "password");
            Statement stmt = con.createStatement();
            String book_id;
            System.out.println("Please enter book ID");
            Scanner scan = new Scanner(System.in);
            book_id = scan.next();
            ResultSet rs = stmt.executeQuery("select * from book where id=" + Integer.parseInt(book_id));
            if(!rs.next())
            {
                System.out.println("No such book exists");
            }
            else
            {
                System.out.println("Current stats of the book are:-");
                System.out.println("Book Name    :- " + rs.getString(1));
                System.out.println("Author Name  :- " + rs.getString(2));
                System.out.println("Book ID      :- " + rs.getInt(3));
                System.out.println("Book Status  :- " + rs.getString(4));
                System.out.println("Student ID   :- " + rs.getInt(5));
                System.out.println("Issued Date  :- " + rs.getString(6));
                long fine = 0;
                java.util.Date current_date = new java.sql.Date(System.currentTimeMillis());
                java.util.Date issue_date = rs.getDate(6);

                Date issued_date = rs.getDate(6);
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
                System.out.println("Fine         :- " + fine);
                PreparedStatement updater = con.prepareStatement("update book set fine =? where id = ?");
                updater.setInt(1, (int) fine);
                updater.setInt(2, rs.getInt(3));
                updater.executeUpdate();


                System.out.println("Please enter the digit corresponding to the stat you want to update");
                System.out.println("1 for Book name");
                System.out.println("2 for author name");
                System.out.println("3 for ID");
                System.out.println("4 for status");
                System.out.println("5 for student ID");
                System.out.println("6 for issue date");
                int task;
                task = scan.nextInt();

                if(task ==1)
                {
                    String name;
                    System.out.println("please enter the new name. Please use the '_' convention for names");
                    name =scan.next();
                    PreparedStatement updater_1 = con.prepareStatement("update book set name =? where id = ?");
                    updater_1.setString(1, name);
                    updater_1.setInt(2, rs.getInt(3));
                    updater_1.executeUpdate();
                    System.out.println("Book Name updated successfully");
                }

                else if(task ==2)
                {
                    String name;
                    System.out.println("please enter the new name. Please use the '_' convention for names");
                    name =scan.next();
                    PreparedStatement updater_2 = con.prepareStatement("update book set author =? where id = ?");
                    updater_2.setString(1, name);
                    updater_2.setInt(2, rs.getInt(3));
                    updater_2.executeUpdate();
                    System.out.println("Author name updated successfully");
                }

                else if(task ==3)
                {
                    int id;
                    System.out.println("please enter the new book ID");
                    id = scan.nextInt();
                    PreparedStatement updater_3 = con.prepareStatement("update book set id =? where id = ?");
                    updater_3.setInt(1, id);
                    updater_3.setInt(2, rs.getInt(3));
                    updater_3.executeUpdate();
                    System.out.println("Book ID updated succesfully");
                }

                else if(task ==4)
                {
                    String name;
                    System.out.println("please enter the new status 'available' or 'issued' ");
                    name =scan.next();

                    if(name.equals("issued"))
                    {

                        int id;
                        System.out.println("please enter the new student ID to whom book is issued");
                        id = scan.nextInt();

                        Student temp_st = new Student();
                        temp_st.setId(id);
                        temp_st.update_books_issued();
                        int books_issued = 0;
                        books_issued = temp_st.getBooks_issued().size();


                        if(books_issued <4)
                        {
                            PreparedStatement updater_4d = con.prepareStatement("update book set student_id =? where id = ?");
                            updater_4d.setInt(1, id);
                            updater_4d.setInt(2, rs.getInt(3));
                            updater_4d.executeUpdate();

                            PreparedStatement updater_4a = con.prepareStatement("update book set status =? where id = ?");
                            updater_4a.setString(1, name);
                            updater_4a.setInt(2, rs.getInt(3));
                            updater_4a.executeUpdate();

                            PreparedStatement updater_4b = con.prepareStatement("update book set fine =? where id = ?");
                            updater_4b.setInt(1, 0);
                            updater_4b.setInt(2, rs.getInt(3));
                            updater_4b.executeUpdate();


                            PreparedStatement updater_4c = con.prepareStatement("update book set issue_date =? where id = ?");
                            updater_4c.setDate(1, (Date) current_date);
                            updater_4c.setInt(2, rs.getInt(3));
                            updater_4c.executeUpdate();
                            System.out.println("status updated to 'issued' successfully");

                        }
                        else
                        {
                            System.out.println("The student has already issued 4 books and hence cannot issue any book further");
                        }



                        ;
                    }

                    else if(name.equals("available"))
                    {
                        PreparedStatement updater_4a = con.prepareStatement("update book set status =? where id = ?");
                        updater_4a.setString(1, name);
                        updater_4a.setInt(2, rs.getInt(3));
                        updater_4a.executeUpdate();

                        PreparedStatement updater_4b = con.prepareStatement("update book set fine =? where id = ?");
                        updater_4b.setInt(1, 0);
                        updater_4b.setInt(2, rs.getInt(3));
                        updater_4b.executeUpdate();


                        PreparedStatement updater_4c = con.prepareStatement("update book set issue_date =? where id = ?");
                        updater_4c.setDate(1, null);
                        updater_4c.setInt(2, rs.getInt(3));
                        updater_4c.executeUpdate();

                        PreparedStatement updater_4d = con.prepareStatement("update book set student_id =? where id = ?");
                        updater_4d.setInt(1, 0);
                        updater_4d.setInt(2, rs.getInt(3));
                        updater_4d.executeUpdate();

                        System.out.println("Status updated to 'available' successfully");
                    }

                }


                else if(task ==5)
                {
                    int id;
                    System.out.println("please enter the new student ID");
                    id = scan.nextInt();


                    System.out.println("Do you want to just change student ID or issue it to new student ID");
                    System.out.println("Please enter");
                    System.out.println("1 for only student ID update");
                    System.out.println("2 for issue the book to new student ID");
                    int checker;
                    checker = scan.nextInt();
                    if(checker == 1)
                    {
                        if(rs.getString(6) != null)
                        {
                            Student temp_st = new Student();
                            temp_st.setId(id);
                            temp_st.update_books_issued();
                            int books_issued = 0;
                            books_issued = temp_st.getBooks_issued().size();

                            if(books_issued<4)
                            {
                                PreparedStatement updater_5 = con.prepareStatement("update book set student_id =? where id = ?");
                                updater_5.setInt(1, id);
                                updater_5.setInt(2, rs.getInt(3));
                                updater_5.executeUpdate();
                                System.out.println("Student ID updated successfully");
                            }
                            else
                            {
                                System.out.println("The student has already issued 4 books and hence cannot issue any book further");
                            }
                        }
                        else
                        {
                            System.out.println("since the book is not issued to anyone,it's student ID cannot be updated");
                        }
                    }
                    else if(checker == 2)
                    {
                        Student temp_st = new Student();
                        temp_st.setId(id);
                        temp_st.update_books_issued();
                        int books_issued = 0;
                        books_issued = temp_st.getBooks_issued().size();

                        if(books_issued<4)
                        {
                            PreparedStatement updater_5 = con.prepareStatement("update book set student_id =? where id = ?");
                            updater_5.setInt(1, id);
                            updater_5.setInt(2, rs.getInt(3));
                            updater_5.executeUpdate();
                            System.out.println("Student ID updated successfully");

                            PreparedStatement updater_4a = con.prepareStatement("update book set status =? where id = ?");
                            updater_4a.setString(1, "issued");
                            updater_4a.setInt(2, rs.getInt(3));
                            updater_4a.executeUpdate();

                            PreparedStatement updater_4c = con.prepareStatement("update book set issue_date =? where id = ?");
                            updater_4c.setDate(1, (Date) current_date);
                            updater_4c.setInt(2, rs.getInt(3));
                            updater_4c.executeUpdate();

                            PreparedStatement updater_4b = con.prepareStatement("update book set fine =? where id = ?");
                            updater_4b.setInt(1, 0);
                            updater_4b.setInt(2, rs.getInt(3));
                            updater_4b.executeUpdate();

                            System.out.println("Book issued and student ID updated successfully");
                        }
                        else
                        {
                            System.out.println("The student has already issued 4 books and hence cannot issue any book further");
                        }
                    }
                    else
                    {
                        System.out.println("You did not enter a valid choice");
                    }

                }

                else if(task ==6)
                {
                    int id;
                    System.out.println("please enter the new student ID to whom book is issued");
                    id = scan.nextInt();
                    Student temp_st = new Student();
                    temp_st.setId(id);
                    temp_st.update_books_issued();
                    int books_issued = 0;
                    books_issued = temp_st.getBooks_issued().size();

                    if(books_issued<4)
                    {
                        PreparedStatement updater_6c = con.prepareStatement("update book set student_id =? where id = ?");
                        updater_6c.setInt(1, id);
                        updater_6c.setInt(2, rs.getInt(3));
                        updater_6c.executeUpdate();

                        Date d;
                        String s;
                        System.out.println("Please enter issue date in the format YY-MM-DD");
                        s = scan.next();
                        d = Date.valueOf(s);
                        PreparedStatement updater_6a = con.prepareStatement("update book set issue_date =? where id = ?");
                        updater_6a.setDate(1, d);
                        updater_6a.setInt(2, rs.getInt(3));
                        updater_6a.executeUpdate();

                        PreparedStatement updater_6b = con.prepareStatement("update book set status =? where id = ?");
                        updater_6b.setString(1, "issued");
                        updater_6b.setInt(2, rs.getInt(3));
                        updater_6b.executeUpdate();

                        PreparedStatement updater_6d = con.prepareStatement("update book set fine =? where id = ?");
                        updater_6d.setInt(1, 0);
                        updater_6d.setInt(2, rs.getInt(3));
                        updater_6d.executeUpdate();

                        System.out.println("Issue date updated successfully" );
                        con.close();
                    }
                    else
                    {
                        System.out.println("since the book is not issued to anyone,it's student ID cannot be updated");
                    }

                }

                else
                {
                    System.out.println("You did not enter a valid choice");
                }
            }


        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public void issueBook_database() throws ClassNotFoundException, SQLException
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/assign_2", "yogesh", "password");
            Statement stmt = con.createStatement();
            Scanner scan = new Scanner(System.in);
            System.out.println("Please enter the book ID to be issued");
            int bk_ID;
            bk_ID = scan.nextInt();
            ResultSet rs = stmt.executeQuery("select * from book where id=" + bk_ID);
            if(!rs.next())
            {
                System.out.println("No such book exists");
            }

            else if(rs.getString(4).equals("issued"))
            {
                System.out.println("The book is already issued to Student " + rs.getInt(5));
            }
            else
            {

                int new_st_id;
                System.out.println("Please enter the Id of student to whom book is being issued");
                Scanner scany = new Scanner(System.in);
                Student temp_st = new Student();
                new_st_id = scany.nextInt();
                temp_st.setId(new_st_id);
                temp_st.update_books_issued();
                int books_issued = 0;
                books_issued = temp_st.getBooks_issued().size();

                if (books_issued < 4) {

                    PreparedStatement updater_4d = con.prepareStatement("update book set student_id =? where id = ?");
                    updater_4d.setInt(1, new_st_id);
                    updater_4d.setInt(2, bk_ID);
                    updater_4d.executeUpdate();

                    PreparedStatement updater_4a = con.prepareStatement("update book set status =? where id = ?");
                    updater_4a.setString(1, "issued");
                    updater_4a.setInt(2, bk_ID);
                    updater_4a.executeUpdate();

                    PreparedStatement updater_4b = con.prepareStatement("update book set fine =? where id = ?");
                    updater_4b.setInt(1, 0);
                    updater_4b.setInt(2, bk_ID);
                    updater_4b.executeUpdate();


                    java.util.Date current_date = new java.sql.Date(System.currentTimeMillis());
                    PreparedStatement updater_4c = con.prepareStatement("update book set issue_date =? where id = ?");
                    updater_4c.setDate(1, (Date) current_date);
                    updater_4c.setInt(2, bk_ID);
                    updater_4c.executeUpdate();


                    System.out.println("Book issued successfully");
                    con.close();
                }
                else
                {
                    System.out.println("The given student has already issued 4 books");
                }

            }
        }

        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public void returnBook_database()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/assign_2", "yogesh", "password");
            Statement stmt = con.createStatement();
            String book_id;
            System.out.println("Please enter book ID");
            Scanner scan = new Scanner(System.in);
            book_id = scan.next();
            ResultSet rs = stmt.executeQuery("select * from book where id=" + Integer.parseInt(book_id));
            if(!rs.next())
            {
                System.out.println("No such book exists");
            }
            else if(rs.getString(4).equals("available"))
            {
                System.out.println("The book is already available in library hence cannot be returned");
            }
            else
            {
                PreparedStatement updater_4a = con.prepareStatement("update book set status =? where id = ?");
                updater_4a.setString(1, "available");
                updater_4a.setInt(2, rs.getInt(3));
                updater_4a.executeUpdate();

                PreparedStatement updater_4b = con.prepareStatement("update book set fine =? where id = ?");
                updater_4b.setInt(1, 0);
                updater_4b.setInt(2, rs.getInt(3));
                updater_4b.executeUpdate();


                PreparedStatement updater_4c = con.prepareStatement("update book set issue_date =? where id = ?");
                updater_4c.setDate(1, null);
                updater_4c.setInt(2, rs.getInt(3));
                updater_4c.executeUpdate();

                PreparedStatement updater_4d = con.prepareStatement("update book set student_id =? where id = ?");
                updater_4d.setInt(1, 0);
                updater_4d.setInt(2, rs.getInt(3));
                updater_4d.executeUpdate();

                System.out.println("Book returned successfully");
                con.close();
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public void booksIssuedByStudent_database()
    {
        int id;
        Student st = new Student();
        String stu_id;
        System.out.println("Please enter student id");
        Scanner scan = new Scanner(System.in);
        stu_id =scan.next();

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/assign_2", "yogesh", "password");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from book where student_id =" + stu_id);
            if(!rs.next())
            {
                System.out.println("No such student exists");
            }

            else
            {
                st.setId(Integer.parseInt(stu_id));
                st.update_books_issued();
                st.view_status();
            }
        }

        catch (Exception e)
        {
            System.out.println(e);
        }



    }
}

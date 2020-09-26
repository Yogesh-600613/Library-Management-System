import java.sql.*;
import java.util.Scanner;


public class TestClass
{
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        try
        {
            // MySQL CONNECTION PART
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/assign_2", "yogesh", "password");
            Statement stmt = con.createStatement();

            //DECLARATIONS
            Scanner scan = new Scanner(System.in);
            String user;
            Student stu = new Student();
            Book b = new Book();
            Admin admin = new Admin();

            System.out.println();

            // USER SPECIFICATION
            System.out.println("Specify whether you are a ' STUDENT' or an 'ADMIN'(Password required) ");
            user = scan.next();

            //STUDENT
            if (user.equals("STUDENT") || user.equals("Student") || user.equals("student"))
            {
                System.out.println("please enter your student id");
                String stu_id = scan.next();
                Student st = new Student();
                int task = -1;
                System.out.println("Please enter the digit according to the task you want to perform");
                System.out.println("Show your issued books status :- 1");
                System.out.println("Search a book using it's ID   :- 2");
                task = scan.nextInt();
                if(task == 1)
                {
                    st.setId(Integer.parseInt(stu_id));
                    st.update_books_issued();
                    st.view_status();
                }
                else if(task == 2)
                {

                    st.searchBook();
                }
                else
                {
                    System.out.println("You did not enter a valid choice");
                }

            }

            //ADMIN
            else if(user.equals("ADMIN") || user.equals("Admin") || user.equals("admin"))
            {
                System.out.println("since you are an admin, please enter the password");
                String checker;
                checker = scan.next();
                if(checker.equals("iamadmin"))
                {
                    System.out.println("According to Library rules:- while adding any name please use '_'  instead of spaces.");
                    int task = -1 ;
                    System.out.println("Please enter the digit according to the task you want to perform");
                    System.out.println("Add    a book              :- 1");
                    System.out.println("Delete a book              :- 2");
                    System.out.println("Update a book              :- 3");
                    System.out.println("Issue  a book              :- 4");
                    System.out.println("Return a book              :- 5");
                    System.out.println("Books issued by a student  :- 6");
                    System.out.println("Get status of a book       :- 7");


                    task = scan.nextInt();

                    if(task == 1)
                    {
                        admin.addBook_database();
                    }

                    else if(task == 2)
                    {
                        int bID;
                        System.out.println("Please enter book ID to be deleted");
                        bID = scan.nextInt();
                        admin.deleteBook_database(bID);
                    }

                    else if(task == 3)
                    {
                        admin.updateBook_database();
                    }

                    else if(task == 4)
                    {
                        admin.issueBook_database();
                    }

                    else if(task == 5)
                    {
                        admin.returnBook_database();
                    }

                    else if(task ==6)
                    {
                        admin.booksIssuedByStudent_database();
                    }

                    else if(task == 7)
                    {
                        admin.getBook_status();
                    }

                    else
                    {
                        System.out.println("You did not enter a valid choice");
                    }


                }
                else
                {
                    System.out.println("You entered wrong password");
                }
            }
            else
            {
                System.out.println("You entered something wrong");
            }
            con.close();

        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}

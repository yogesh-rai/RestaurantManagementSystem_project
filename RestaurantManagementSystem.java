import java.util.*;

public class RestaurantManagementSystem {

    //global node pointers which we use throughout the program

    // node pointers for Admin
    private static Node AdminHead = null;
    private static Node AdminTail = null;

    // node pointers for customer
    private static Node CustomerHead = null;
    private static Node CustomerTail = null;

    // node pointers sales LinkedList to keep track of total sales throughout the day
    private static Node SalesHead = null;
    private static Node SalesTail = null;

   //template of node
    private static class Node{
        String foodName;
        int quantity;
        int price;
        int val;
        Node next;

        private Node(){     //constructor
        }
    }

    //this function adds node to admin linkedList or initialize admin LinkedList
    private static Node createAdmin(Node head, int val, String foodName, int price)
    {
        Node newNode = new Node();
        newNode.val = val;
        newNode.foodName = foodName;
        newNode.price = price;
        newNode.quantity = 0;
        newNode.next = null;

        Node temp = new Node();
        temp = head;

        if(head == null)
        {
            AdminHead = AdminTail = newNode;
        }
        else
        {
            while(temp.next != null)
            {
                temp = temp.next;
            }
            temp.next = newNode;
            AdminTail = newNode;
        }

        return AdminHead;
    }

    //this function adds node to customer linkedList or initialize customer LinkedList
    private static Node createCustomer(Node head, int val, int quantity)
    {
        Node newNode = new Node();

        Node temp1 = AdminHead;
        boolean flag = false;

        while(temp1 != null)
        {
            if(temp1.val == val){
                flag = true;
                break;
            }
            temp1 = temp1.next;
        }

        if(flag == true)
        {
            newNode.val = val;
            newNode.foodName = temp1.foodName;
            newNode.price = quantity * (temp1.price);
            newNode.quantity = quantity;
            newNode.next = null;

            Node temp = new Node();
            temp = head;

            if(head == null)
            {
                CustomerHead = CustomerTail = newNode;
            }
            else
            {
                while(temp.next != null)
                {
                    temp = temp.next;
                }
                temp.next = newNode;
                CustomerTail = newNode;
            }
        }
        else{
            System.out.println("\t This item is not present in menu !! \n\n");
        }

        return CustomerHead;
    }

    // this function will display the list whose head pointer passed to it
    private static void display(Node head)
    {
        Node temp = new Node();
        temp = head;

        if(temp == null){
            System.out.println("\t The list is empty !! \n\n");
        }
        else{
            while(temp != null)
            {
                if(temp.quantity == 0)
                {
                    System.out.println("\t "+temp.val+") "+temp.foodName+" , "+ temp.price);
                }
                else{
                    System.out.println("\t "+temp.val+") "+temp.foodName+" , "+temp.quantity+" , "+ temp.price);
                }
                temp = temp.next;
            }

            System.out.println("\n");
        }
    }

    // this function will delete the data or node from the list whose head pointer passed to it
    private static Node delete(int val, Node head)
    {
        if(head == null)
        {
            System.out.println("List is empty !! \n\n");
        }
        else
        {
            Node temp = new Node();
            if(head.val == val)
            {
                head = head.next;
            }

            else
            {
                temp = head;
                while(temp.next.val != val)
                {
                    temp = temp.next;
                }
                temp.next = temp.next.next;
            }

        }

        return head;
    }

    // this function will keep track of total sales made to each customer through out the day
    // by maintaining another LinkedList of SalesHead
    private static Node totalSales(int val, int quantity)
    {
        Node newNode = new Node();
        boolean flag = false;

        Node temp = AdminHead;

        while(temp.val != val)
        {
            temp = temp.next;
        }

        newNode.val = val;
        newNode.foodName = temp.foodName;
        newNode.price = quantity * (temp.price);
        newNode.quantity = quantity;
        newNode.next = null;

        Node temp1 = SalesHead;

        if(temp1 == null){
            SalesHead = newNode;
            SalesTail = newNode;
        }
        else if(SalesTail.val == val)
        {
            SalesTail.quantity  +=  newNode.quantity;
            SalesTail.price  +=  newNode.price;
        }
        else
        {
            while (temp1.next != null)
            {
                if(temp1.val == val)
                {
                    flag = true;
                    break;
                }
                temp1 = temp1.next;
            }

            if(flag == true)
            {
                temp1.quantity = temp1.quantity + newNode.quantity;
                temp1.price = temp1.price + newNode.price;
            }
            else{
                temp1.next = newNode;
                SalesTail = newNode;
            }
        }

        return SalesHead;
    }


    // this function will calculate total sales to each customer by accessing totalSales function
    public static void totalSalesCalculation()
    {
        Node temp = CustomerHead;
        while(temp != null)
        {
            SalesHead = totalSales(temp.val, temp.quantity);
            temp = temp.next;
        }
    }

    // this function will use to delete a food item from menu by admin
    // it will return a boolean value to check whether food item is deleted or not
    private static boolean delete_from_admin()
    {
        System.out.print("\t Enter the serial no. of the food item you want to delete :");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        Node temp = new Node();
        temp = AdminHead;

        while(temp != null)
        {
            if(temp.val == n){
                AdminHead = delete(n,AdminHead);
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    // this function will use to delete a food item from ordered list by customer
    // it will return a boolean value to check whether food item is deleted or not
    private static boolean delete_from_customer()
    {
        System.out.print("\t Enter the serial no. of the food item you want to delete :");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        Node temp = new Node();
        temp = CustomerHead;

        while(temp != null)
        {
            if(temp.val == n){
                CustomerHead = delete(n,CustomerHead);
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    // this function will delete an entire list whose head pointer passed to it
    // generally used to clear out the customer list for the next new customer at the of final billing
    private static Node deleteList(Node head)
    {
        if(head == null)
        {
            return null;
        }

        head = null;

        return head;
    }

    // this function will display the final bill and total cost to customer
    private static void displayBill()
    {
        display(CustomerHead);

        Node temp = new Node();
        temp = CustomerHead;
        int total = 0;

        while(temp != null)
        {
            total = total + temp.price;
            temp = temp.next;
        }

        System.out.println("\t Total cost : "+total+"\n\n");
    }

    // this function shows the options available for admin choose
    private static void adminMenu()
    {
        System.out.println("                   --------------------------------------------------------    ");
        System.out.println("                                         ADMIN PANEL                           ");
        System.out.println("                   --------------------------------------------------------     \n\n");
        System.out.println("\t 1. Add new item in the menu ");
        System.out.println("\t 2. Delete item from the menu ");
        System.out.println("\t 3. Display order menu ");
        System.out.println("\t 4. View total sales ");
        System.out.println("\t 5. Back to main panel \n");
    }

    // this function will open up the admin panel
    private static void AdminPanel()
    {

        Scanner sc = new Scanner(System.in);

        while(true)
        {
            adminMenu();
            System.out.print("\t  Enter your choice :-  ");
            int choice = sc.nextInt();
            System.out.println();

            if(choice == 5)
            {
                break;
            }

            switch (choice)
            {
                case 1:
                    int n = 0;
                    String foodName = "";
                    int price = 0;
                    boolean flag = false;
                    System.out.print("\t Enter the serial no. of the food item you want to add :");
                    n = sc.nextInt();
                    sc.nextLine();

                    Node temp = AdminHead;

                    while(temp != null)
                    {
                        if(temp.val == n)
                        {
                            System.out.println("Food item with given serial no. is already exists !! \n\n");
                            flag = true;
                            break;
                        }
                        temp = temp.next;
                    }

                    if(flag == true)
                    {
                        break;
                    }

                    System.out.print("\t Enter food item name you want to add :");
                    foodName = sc.nextLine();

                    System.out.print("\t Enter it's price :");
                    price = sc.nextInt();
                    sc.nextLine();

                    AdminHead = createAdmin(AdminHead,n,foodName,price);
                    System.out.println("\t New food item added to the menu !! \n\n");
                    break;


                case 2:
                    if(delete_from_admin())
                    {
                        System.out.println("\t Food item is now deleted from the menu\n");
                        System.out.println("\t Updated food items menu \n");
                        display(AdminHead);
                    }
                    else{
                        System.out.println("\t Food item with given serial no. doesn't exist !!");
                    }
                    break;

                case 3:
                      System.out.println("\n\t --------- MENU ---------- \n");
                      display(AdminHead);
                      break;

                case 4: display(SalesHead);
                        break;

                default:
                    System.out.println("\t WRONG INPUT, Please choose a valid option !!");
                    break;
            }
        }
    }

    // this function shows the options available for customer choose
    private static void customerMenu()
    {
        System.out.println("                   --------------------------------------------------------       ");
        System.out.println("                                         CUSTOMER PANEL                           ");
        System.out.println("                   --------------------------------------------------------     \n\n");
        System.out.println("\t 1. Place an order ");
        System.out.println("\t 2. View your ordered items ");
        System.out.println("\t 3. Delete an item from your order ");
        System.out.println("\t 4. View final bill ");
        System.out.println("\t 5. Back to main panel \n");

    }

    // this function will open up the customer panel
    private static void CustomerPanel()
    {
        Scanner sc = new Scanner(System.in);
        boolean ok = false;

        while(true)
        {
            customerMenu();
            System.out.print("\t  Enter your choice :-  ");
            int choice = sc.nextInt();
            System.out.println();

            if(choice == 5)
            {
                break;
            }

            switch (choice)
            {
                case 1:
                    int n = 0;
                    int quantity = 0;
                    System.out.println("\n\t --------- MENU ---------- \n");
                    display(AdminHead);
                    System.out.print("\t Enter the serial no. of item you want to order :");
                    n = sc.nextInt();

                    System.out.print("\t Enter Quantity :");
                    quantity = sc.nextInt();

                    CustomerHead = createCustomer(CustomerHead,n,quantity);
                    break;

                case 2:
                    System.out.println("\t Your ordered items : \n");
                    display(CustomerHead);
                    break;

                case 3:
                    if(delete_from_customer())
                    {
                        System.out.println("\t Food item is now deleted from your order\n");
                        System.out.println("\t Updated list of your food items \n");
                        display(CustomerHead);
                    }
                    else{
                        System.out.println("\t Food item with given serial no. doesn't exist !!");
                    }
                    break;

                case 4:
                    totalSalesCalculation();
                    System.out.println("\n\t     ---------  FINAL BILL  -------    \n");
                    displayBill();
                    CustomerHead = deleteList(CustomerHead);
                    System.out.println("\t PRESS 'r' TO RETURN TO MAIN MENU");
                    sc.nextLine();

                    char c = sc.next().charAt(0);
                    if(c == 'r'){
                        ok = true;
                        break;
                    }


                default:
                    System.out.println("\t WRONG INPUT, Please choose a valid option !!");
                    break;
            }

            if(ok == true)
            {
                break;
            }
        }
    }


    // this function will open up the main panel of restaurant management system
    private static void MainPanel()
    {
        System.out.println("                   --------------------------------------------------------     ");
        System.out.println("                                RESTAURANT  MANAGEMENT  SYSTEM                  ");
        System.out.println("                   --------------------------------------------------------     \n\n");
        System.out.println("\t 1.  ADMIN PANEL ");
        System.out.println("\t 2.  CUSTOMER PANEL ");
        System.out.println("\t 3.  EXIT  \n");

    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // here we initialize admin LinkedList to create our food menu
        AdminHead = createAdmin(AdminHead,1,"Chilli Paneer",250);
        AdminHead = createAdmin(AdminHead,2,"Dry Manchurian",180);
        AdminHead = createAdmin(AdminHead,3,"Spring Rolls",100);
        AdminHead = createAdmin(AdminHead,4,"Hot n Sour Soup",300);
        AdminHead = createAdmin(AdminHead,5,"Hakka Noodles",200);
        AdminHead = createAdmin(AdminHead,6,"Schezwan Fried Rice",250);

        while(true)
        {
            MainPanel();

            System.out.print("\t     ENTER YOUR CHOICE :- ");
            int choice = sc.nextInt();
            System.out.println();

            if(choice == 3)
            {
                System.out.println("\t -------------  THANK YOU :)  -------------- \n");
                break;
            }

            switch (choice)
            {
                case 1: AdminPanel();
                        break;

                case 2: CustomerPanel();
                        break;

                default:
                    System.out.println("\t WRONG INPUT, Please choose a valid option !!");
                    break;
            }
        }
    }
}

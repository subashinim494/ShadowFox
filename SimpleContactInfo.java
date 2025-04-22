import java.util.ArrayList;
import java.util.Scanner;
// import java.util.*; 

class Contact{
    String name;
    String phoneno;
    String email;

    Contact(String name,String phoneno,String email)
    {
        this.name=name;
        this.phoneno=phoneno;
        this.email=email;
    }

    public String toString()
    {
        return "Name: "+name+",PhoneNo: "+phoneno+",EmailID: "+email;
    }
}

public class SimpleContactInfo{
    private static final ArrayList<Contact> contacts=new ArrayList<>();
    private static final Scanner scanner=new Scanner(System.in);

    public static void main(String[] args)
    {
        while(true)
        {
            System.out.println("\n Contact management System");
            System.out.println("1.Add Contact");
            System.out.println("2.view Contact");
            System.out.println("3.update Contact");
            System.out.println("4.delete Contact");
            System.out.println("5.exit");
            System.out.println("choose an option");

            int choice=scanner.nextInt();
            scanner.nextLine();

            switch(choice)
            {
                case 1:
                    addContact();
                    break;

                case 2:
                    viewContact();
                    break;

                case 3:
                    updateContact();
                    break;

                case 4:
                    deleteContact();
                    break;

                case 5:
                    System.out.println("Exiting program...");
                    return;
                default:
                    System.out.println("invalid option. Try again");
                    break;                    
            }


        }
    }


    private static void addContact() {
        String name, phoneno, email;
    
        
        while (true) {
            System.out.print("Enter Name: ");
            name = scanner.nextLine();
            if (isValidName(name)) {
                break;
            } else {
                System.out.println("Invalid name. Please use only alphabets and spaces.");
            }
        }
    
        
        while (true) {
            System.out.print("Enter Phone: ");
            phoneno = scanner.nextLine();
            if (isValidPhone(phoneno)) {
                break;
            } else {
                System.out.println("Invalid phone number. Please enter exactly 10 digits.");
            }
        }
    
        
        while (true) {
            System.out.print("Enter Email: ");
            email = scanner.nextLine();
            if (isValidEmail(email)) {
                break;
            } else {
                System.out.println("Invalid email format. Please enter a valid email.");
            }
        }
    
        contacts.add(new Contact(name, phoneno, email));
        System.out.println("Contact added successfully.");
    }

    private static boolean isValidName(String name) {
        return name.matches("^[a-zA-Z\\s]+$");
    }
    
    private static boolean isValidPhone(String phone) {
        return phone.matches("\\d{10}");
    }
    
    private static boolean isValidEmail(String email) {
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
    }
    
    

    private static void viewContact()
    {
        if(contacts.isEmpty())
        {
            System.out.println("No contacts found");
        }
        else
        {
            System.out.println("\n the contact List:");
            for(int i=0;i<contacts.size();i++)
            {
                System.out.println((i+1)+","+contacts.get(i));
            }
        }
    }

   
    private static void updateContact() {
        viewContact();
        if (contacts.isEmpty()) {
            return;
        }
    
        System.out.println("Enter the contact number to update: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine(); 
    
        if (index >= 0 && index < contacts.size()) {
            String name, phoneno, email;
    
       
            while (true) {
                System.out.print("Enter New Name: ");
                name = scanner.nextLine();
                if (isValidName(name)) {
                    break;
                } else {
                    System.out.println("Invalid name. Please use only alphabets and spaces.");
                }
            }
    
           
            while (true) {
                System.out.print("Enter New Phone: ");
                phoneno = scanner.nextLine();
                if (isValidPhone(phoneno)) {
                    break;
                } else {
                    System.out.println("Invalid phone number. Please enter exactly 10 digits.");
                }
            }
    
            
            while (true) {
                System.out.print("Enter New Email: ");
                email = scanner.nextLine();
                if (isValidEmail(email)) {
                    break;
                } else {
                    System.out.println("Invalid email format. Please enter a valid email.");
                }
            }
    
            contacts.set(index, new Contact(name, phoneno, email));
            System.out.println("Contact updated successfully!");
        } else {
            System.out.println("Invalid contact number. Please enter a valid contact number to change.");
        }
    }
    
    private static void deleteContact()
    {
        viewContact();
        if(contacts.isEmpty())
        {
            return;
        }
        System.out.println("Enter contact number to delete");
        int index=scanner.nextInt()-1;
        scanner.nextLine();

        if(index>=0&&index<contacts.size())
        {
            contacts.remove(index);
            System.out.println("contact deleted successfully!");
        }
        else{
            System.out.println("Invalid contact number.");
        }
    }
}







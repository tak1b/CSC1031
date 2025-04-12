import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

interface Reader
{
    Scanner scanner = new Scanner(System.in);
}

abstract class Document implements Reader
{
    public List<String> content = new ArrayList<>();

    public final void generateDocument()
    {
        createHeader();
        createBody();
        createFooter();
        printDocument();
    }

    protected void createHeader()
    {
        System.out.print("Enter company name: ");
        String compName = scanner.nextLine();
        if(compName.isEmpty())
        {
           System.out.println("Error: Company name cannot be empty.");
           //throw new IllegalArgumentException("Error: Company name cannot be empty");
           System.exit(0);
        }
        System.out.print("Enter date (DD/MM/YYYY): ");

        String date = scanner.nextLine();
        if(date.isEmpty())
        {
            //System.out.println("Date cannot be empty.");
            throw new IllegalArgumentException("Date cannot be empty.");
        }

        content.add("=== " + getDocumentType() + " ===");
        content.add("Company: " + compName);
        content.add("Date: " + date);
    }

    public abstract void createBody();

    protected void createFooter()
    {
        content.add("Prepared by: " + getPreparedBy());
        content.add("Document Type: " + getDocumentType());
        content.add("=========================");
    }

    protected abstract String getDocumentType();
    protected abstract String getPreparedBy();

    void printDocument()
    {
        System.out.println("\n=== Printing Document ===");
        for(String line : content)
        {
            System.out.println(line);
        }
    }
}

class Invoice extends Document
{
    private double total;

    @Override
    public void createBody()
    {
        System.out.print("Enter total amount: ");
        try
        {
            total = Double.parseDouble(scanner.nextLine());
            if(total <= 0)
            {
                System.out.println("Error: Total amount must be positive.");
                //throw new IllegalArgumentException("Total amount must be positive.");
                System.exit(0);
            }
        }
        catch (NumberFormatException e)
        {
            System.out.println("Error: Total amount must be numeric.");
            //throw new IllegalArgumentException("Total amount must be positive");
            System.exit(0);
        }
        content.add("Total Due: €" + total);
    }

    @Override
    protected String getDocumentType()
    {
        return "INVOICE";
    }

    @Override
    protected String getPreparedBy()
    {
        return "AutoDoc System";
    }
}

class Report extends Document
{
    private String summary;

    @Override
    public void createBody()
    {
        System.out.print("Enter report summary: ");
        summary = scanner.nextLine();

        if(summary.isEmpty())
        {
            System.out.println("Warning: Summary is empty.");
        }

        content.add("Report Summary: " + summary);
    }

    @Override
    protected String getDocumentType()
    {
        return "REPORT";
    }

    @Override
    protected String getPreparedBy()
    {
        return "Management Department";
    }

    @Override
    public void createFooter()
    {
        content.add("Reviewed by: " + getPreparedBy());
        content.add("=========================");
    }
}

class Receipt extends Document
{
    private double paid;
    private int itemsCount;

    @Override
    protected String getDocumentType()
    {
        return "RECEIPT";
    }

    @Override
    protected String getPreparedBy()
    {
        return "AutoDoc System";
    }

    @Override
    public void createBody()
    {
        System.out.print("Enter amount paid: ");
        try
        {
            paid = Double.parseDouble(scanner.nextLine());
            if(paid < 0)
            {
                //System.out.println("Amount must be positive.");
                throw new IllegalArgumentException("Amount paid must be positive.");
            }
        }
        catch (NumberFormatException e)
        {
            //System.out.println("Invalid number format for amount paid.");
            throw new IllegalArgumentException("Invalid number format for amount paid.");
        }

        content.add("Total Paid: €" + paid);

        System.out.print("Enter number of items: ");
        try
        {
            itemsCount = Integer.parseInt(scanner.nextLine());
            if(itemsCount <= 0)
            {
                System.out.println("Error: Items count must be positive.");
                //throw new IllegalArgumentException("Items count must be positive.");
                System.exit(0);
            }
        }
        catch(NumberFormatException e)
        {
            //System.out.println("Invalid number format for items count.");
            throw new IllegalArgumentException("Items count must be positive.");
        }
        content.add("Items Purchased: " + itemsCount);

        double ppi = 0;
        if(itemsCount > 0)
        {
            ppi = paid/itemsCount;
        }
        else
        {
            throw new ArithmeticException("Cannot divide by zero.");
        }
        content.add("Price per Item: €" + ppi);
    }
}


public class DocumentGenerator implements Reader
{
    public static void main(String[] args)
    {
        try
        {
            System.out.println("Choose document type: (INV) Invoice, (REP) Report, (REC) Receipt");
            String choice = scanner.nextLine();
            Document document = switch(choice)
            {
                case "INV" -> new Invoice();
                case "REP" -> new Report();
                case "REC" -> new Receipt();
                default ->
                {
                    System.out.println("Invalid choice. Exiting.");
                    //throw new IllegalArgumentException("Invalid document choice.");
                    System.exit(0);
                    yield null;
                }
            };

            document.generateDocument();
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        finally
        {
            scanner.close();
        }
    }
}

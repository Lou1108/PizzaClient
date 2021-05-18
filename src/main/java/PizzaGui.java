import Requests.GetRequest;
import Requests.PostRequests;
import Requests.PutRequest;
import org.w3c.dom.ls.LSOutput;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;


public class PizzaGui extends JFrame
{
    //    private String url = "https://safe-savannah-12795.herokuapp.com/api/";
    private long customerID = 1;

    public PizzaGui()
    {
        System.out.println("Your Customer ID is: " + customerID);

        JFrame frame = new JFrame("Pizza order");

        //title for the frame
        JPanel title = new JPanel();
        JLabel titleName = new JLabel("Pizza order");
        titleName.setForeground(new Color(37, 150, 190));
        Font f = new Font("TIMESROMAN", Font.BOLD, 30);
        titleName.setFont(f);
        title.add(titleName);


        //new Jpanel to store the labels and textfields
        JPanel layout = new JPanel(new BorderLayout());
        JPanel top = new JPanel(new GridLayout(2,1));
        JPanel center = new JPanel(new GridLayout());
        top.add(title);
        top.add(pizzaMenu());
        layout.add(top, BorderLayout.NORTH);

        JPanel left = new JPanel(new FlowLayout(FlowLayout.CENTER));
            left.setBorder(BorderFactory.createLineBorder(new Color(37, 150, 190), 5));
            left.add(postOrder());

        JPanel right = new JPanel(new GridLayout(4,1));
            right.setBorder(BorderFactory.createLineBorder(new Color(37, 150, 190), 5));
            right.add(pizzaInformation());
            right.add(getDeliveryTime());
            right.add(cancelOrder());
            right.add(orderHistory());

        center.add(left); center.add(right);
        layout.add(center, BorderLayout.CENTER);
        frame.add(layout);

        //setting the frame options
        frame.setSize(850, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }


    JPanel pizzaInformation() {
        GetRequest request = new GetRequest();
        JPanel info = new JPanel();
        JLabel use = new JLabel("Enter a pizza id: ");

        JComboBox<String> user_1 = new JComboBox<>(request.pizzaIDs());

        JButton infoButton = new JButton("Get Pizza Information");

        infoButton.addActionListener(e -> {
            String[] word = String.valueOf(user_1.getSelectedItem()).split(" ");
            String input = word[1];
            System.out.println("pizza/" + input);
            String output = request.get("pizza/" + input);
            JOptionPane.showMessageDialog(null, request.parsePizzaInfo(output));
        });

        info.add(use);
        info.add(user_1);
        info.add(infoButton);

        return info;
    }

    JButton pizzaMenu() {
        // pizza menu
        JButton menu = new JButton("Get Pizza Menu");
        Font log = new Font("TIMESROMAN", Font.BOLD, 20);
        menu.setFont(log);
        menu.setBackground(new Color(37, 150, 190));
        menu.setOpaque(true);
        menu.setBorderPainted(true);

        GetRequest request = new GetRequest();
        menu.addActionListener(e -> {
            String output = request.get("pizza");
            JOptionPane.showMessageDialog(null, request.parsePizzaMenu(output));
        });

        return menu;
    }

    JPanel getDeliveryTime(){
        JPanel panel = new JPanel();
        JLabel use = new JLabel("Enter your order id to get the delivery time: ");
        JTextField userInput = new JTextField(10);

        JButton infoButton = new JButton("Get delivery time Information");

        GetRequest request = new GetRequest();
        infoButton.addActionListener(e -> {
            String input = userInput.getText();
            System.out.println("heyy");
            System.out.println("INOUT: " + input);
            String output = request.get("order/deliverytime/" + input);
            System.out.println(output);
            JOptionPane.showMessageDialog(null, output); //TODO request.parseOrderHistory(output)
        });

        panel.add(use);
        panel.add(userInput);
        panel.add(infoButton);

        return panel;
    }

    JPanel orderHistory(){
        JPanel panel = new JPanel();
        JLabel use = new JLabel("Enter your customer id to see previous orders: ");
        JTextField userInput = new JTextField(10);

        JButton infoButton = new JButton("Get order history");

        GetRequest request = new GetRequest();
        infoButton.addActionListener(e -> {
            String input = userInput.getText();
            String output = request.get("order/" + input);
            JOptionPane.showMessageDialog(null, request.parseOrderHistory(output));
        });

        panel.add(use);
        panel.add(userInput);
        panel.add(infoButton);

        return panel;
    }

    JPanel cancelOrder(){
        JPanel panel = new JPanel();
        JLabel use = new JLabel("Enter order ID to cancel the order: ");
        JTextField userIn = new JTextField(10);

        JButton infoButton = new JButton("cancel order");

        PutRequest request = new PutRequest();
        infoButton.addActionListener(e -> {
            String input = userIn.getText();
            String out = request.put(input);
            JOptionPane.showMessageDialog(null, out);
        });

        panel.add(use);
        panel.add(userIn);
        panel.add(infoButton);

        return panel;
    }

    JPanel postOrder(){
        JPanel panel = new JPanel(new GridLayout(14,2));
        Font f = new Font("TIMESROMAN", Font.BOLD, 20);
        JLabel title = new JLabel("New Order: "); title.setFont(f);
        title.setForeground(Color.BLACK);
        panel.add(title); panel.add(new JLabel(""));
        JLabel pizzas = new JLabel("Enter the pizza ids: ");
        JTextField input_pizzas = new JTextField(10);

        JLabel takeaway = new JLabel("Do you want takeaway? ");
        ButtonGroup buttons = new ButtonGroup();
            JRadioButton buttonYes = new JRadioButton("Yes", true);
                buttonYes.setActionCommand("true");
            JRadioButton buttonNo = new JRadioButton("No", false);
                buttonNo.setActionCommand("false");
            buttons.add(buttonYes);
            buttons.add(buttonNo);

        JLabel payment = new JLabel("How do you want to pay? ");
        JTextField input_pay = new JTextField(10);

        JLabel id = new JLabel("Enter your customer id: ");
        JTextField input_id = new JTextField(10);

        JLabel address = new JLabel("Enter your address: ");
        JLabel street = new JLabel("street name: ");
        JTextField input_street = new JTextField(10);
        JLabel city = new JLabel("city: ");
        JTextField input_city = new JTextField(10);
        JLabel country = new JLabel("country: ");
        JTextField input_country = new JTextField(10);
        JLabel zipCode = new JLabel("zipCode: ");
        JTextField input_zip = new JTextField(10);


        JLabel notes = new JLabel("Notes: ");
        JTextField input_note = new JTextField(10);

        JButton infoButton = new JButton("Submit order");

        PostRequests request = new PostRequests();
        infoButton.addActionListener(e -> {

            String [] input = { input_id.getText(),
                                String.valueOf(buttons.getSelection().getActionCommand()),
                                input_pay.getText(),
                                input_street.getText(),
                                input_city.getText(),
                                input_country.getText(),
                                input_zip.getText(),
                                input_pizzas.getText(),
                                input_note.getText()  };


            String out = request.post(input);

            JOptionPane.showMessageDialog(null, out);
        });


        panel.add(pizzas); panel.add(input_pizzas);
        panel.add(takeaway);  panel.add(new JLabel("")); panel.add(buttonYes); panel.add(buttonNo);
        panel.add(payment); panel.add(input_pay);
        panel.add(id); panel.add(input_id);
        panel.add(address); panel.add(new JLabel("")); panel.add(street); panel.add(input_street);
        panel.add(city); panel.add(input_city);
        panel.add(country); panel.add(input_country);
        panel.add(zipCode); panel.add(input_zip);
        panel.add(notes); panel.add(input_note);
        panel.add(infoButton);

        return panel;
    }
}


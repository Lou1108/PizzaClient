import Requests.GetRequest;
import Requests.PostRequests;
import Requests.PutRequest;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class PizzaGui extends JFrame
{
    JTextField userInput;
    JPanel layout;
    JFrame frame ;


    public PizzaGui()
    {
        frame = new JFrame();

        //title for the frame
        JPanel title = new JPanel();
        JLabel titleName = new JLabel("Pizza order");
        Font f = new Font("TIMESROMAN", Font.BOLD, 30);
        titleName.setForeground(Color.BLACK);
        titleName.setFont(f);
        title.add(titleName);



        //new Jpanel to store the labels and textfields
        layout = new JPanel(new GridLayout(7,2));
        layout.add(title);
        layout.add(pizzaMenu());
        layout.add(pizzaInformation());
        layout.add(getDeliveryTime());
        layout.add(orderHistory());
        layout.add(cancelOrder());
        layout.add(postOrder());
        frame.add(layout);

        //setting the frame options
        frame.setSize(800, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }


    JPanel pizzaInformation() {
        JPanel info = new JPanel();
        JLabel use = new JLabel("Enter a pizza id: ");
        JTextField user_1 = new JTextField(10);

        JButton infoButton = new JButton("Get Pizza Information");

        GetRequest request = new GetRequest();
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = user_1.getText();
                System.out.println("pizza/" + input);
                String output = request.get("pizza/" + input);
                JOptionPane.showMessageDialog(null, request.parsePizzaInfo(output));
            }
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
        menu.setBackground(Color.PINK);
        menu.setOpaque(true);
        menu.setBorderPainted(true);

        GetRequest request = new GetRequest();
        menu.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String output = request.get("pizza");
                JOptionPane.showMessageDialog(null, request.parsePizzaMenu(output));
            }
        });

        return menu;
    }

    JPanel getDeliveryTime(){
        JPanel panel = new JPanel();
        JLabel use = new JLabel("Enter your order id to get the delivery time: ");
        userInput = new JTextField(10);

        JButton infoButton = new JButton("Get delivery time Information");

        GetRequest request = new GetRequest();
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = userInput.getText();
                String output = request.get("order/deliverytime/" + input);
                JOptionPane.showMessageDialog(null, output);
            }
        });

        panel.add(use);
        panel.add(userInput);
        panel.add(infoButton);

        return panel;
    }

    JPanel orderHistory(){
        JPanel panel = new JPanel();
        JLabel use = new JLabel("Enter your customer id to see previous orders: ");
        userInput = new JTextField(10);

        JButton infoButton = new JButton("Get order history");

        GetRequest request = new GetRequest();
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = userInput.getText();
                String output = request.get("order/" + input);
                JOptionPane.showMessageDialog(null, request.parseOrderHistory(output));
            }
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
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = userIn.getText();
                request.put("order/" + input);
                // TODO change output to give back response
                JOptionPane.showMessageDialog(null, "Your order was cancelled.");
            }
        });

        panel.add(use);
        panel.add(userIn);
        panel.add(infoButton);

        return panel;
    }

    JPanel postOrder(){
        JPanel panel = new JPanel();
        //TODO make it more than one pizza
        JLabel pizzas = new JLabel("Enter the pizza ids: ");
        JTextField input_1 = new JTextField(10);

        JLabel takeaway = new JLabel("Do you want takeaway? ");
        JTextField input_2 = new JTextField(10);

        JLabel payment = new JLabel("How do you want to pay? ");
        JTextField input_3 = new JTextField(10);

        JLabel id = new JLabel("Enter your customer id: ");
        JTextField input_4 = new JTextField(10);

        JLabel adress = new JLabel("Enter your address: ");
        JTextField input_5 = new JTextField(50);


        JButton infoButton = new JButton("Submit order");

        PostRequests request = new PostRequests();
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String [] input = { input_1.getText(),
                        input_2.getText(),
                        input_3.getText(),
                        input_4.getText(),
                        input_5.getText()};


                request.post(input);
                // TODO change output to give back response
                JOptionPane.showMessageDialog(null, "Your Order was submitted");
            }
        });


        panel.add(pizzas); panel.add(input_1);
        panel.add(takeaway);  panel.add(input_2);
        panel.add(payment); panel.add(input_3);
        panel.add(id); panel.add(input_4);
        panel.add(adress); panel.add(input_5);
        panel.add(infoButton);

        return panel;
    }
}


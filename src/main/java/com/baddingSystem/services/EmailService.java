package com.baddingSystem.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.baddingSystem.entities.Bid;
import com.baddingSystem.entities.Shop;
import com.baddingSystem.entities.User;



@Service
public class EmailService {

    private final JavaMailSender mailSender;
    
    @Value("${spring.mail.properties.mail.smtp.from}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender, @Value("${spring.mail.properties.mail.smtp.from}") String fromEmail) {
        this.mailSender = mailSender;
        this.fromEmail = fromEmail;
    }

 
    public void sendWelcomeEmail(String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Welcome to Bidding System – Start Bidding Today!");

        message.setText(
            "Hello,\n\n" +
            "Welcome to Bidding System!\n\n" +
            "We’re excited to have you join our bidding platform. Here, admins list verified shops, " +
            "and users like you can place bids and compete for the best deals in a transparent and secure way.\n\n" +
            "With Bidding System, you can:\n" +
            "- Browse shops listed by admins\n" +
            "- Place bids easily and securely\n" +
            "- Track your bidding activity in real time\n" +
            "- Win shops at competitive prices\n\n" +
            "Log in to your account and start bidding today. Don’t miss the opportunity to grab amazing deals!\n\n" +
            "If you have any questions or need assistance, our support team is always here to help.\n\n" +
            "Happy Bidding!\n\n" +
            "Best regards,\n" +
            "The Bidding System Team"
        );

        mailSender.send(message);
    }

    
    
    /* ---------------- BID CONFIRMATION MAIL ---------------- */
    public void sendBidConfirmationEmail(User user, Shop shop, Bid bid) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(user.getEmail());
        message.setSubject("Bid Placed Successfully ✅");

        message.setText(
                "Hello " + user.getName() + ",\n\n" +
                "Your bid has been placed successfully.\n\n" +
                "📍 Shop Details:\n" +
                "Shop Name: " + shop.getShopName()  + "\n" +
                "Description: " + shop.getDescription() + "\n\n" +
                "💰 Bid Amount: ₹" + bid.getAmount() + "\n" +
                "🕒 Bid Time: " + bid.getBidTime() + "\n\n" +
                "You can increase your bid before bidding ends.\n\n" +
                "Best of luck! 🍀\n\n" +
                "– Badding System Team"
        );

        mailSender.send(message);
    }

    /* ---------------- WINNER MAIL ---------------- */
    public void sendWinnerEmail(User user, Shop shop, Bid bid) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(user.getEmail());
        message.setSubject("🎉 Congratulations! You Won the Bid");

        message.setText(
                "Congratulations " + user.getName() + " 🎉\n\n" +
                "You are the WINNER of the bidding!\n\n" +
                "🏆 Shop Details:\n" +
                "Shop Name: " + shop.getShopName()  + "\n" +
                "Description: " + shop.getDescription() + "\n\n" +
                "💰 Winning Amount: ₹" + bid.getAmount() + "\n\n" +
                "Our team will contact you shortly for next steps.\n\n" +
                "Thank you for using Badding System!\n\n" +
                "– Badding System Team"
        );

        mailSender.send(message);
    }
    
    
    
    public void sendBidAlertEmail(
            User receiver,
            User bidder,
            Shop shop,
            double amount
    ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(receiver.getEmail());
        message.setSubject("🔔 New Highest Bid Placed!");

        message.setText(
                "Hello " + receiver.getName() + ",\n\n" +
                "A new highest bid has been placed on a shop you might be interested in.\n\n" +
                "👤 Bidder: " + bidder.getName() + "\n" +
                "🏪 Shop: " + shop.getShopName() + "\n" +
                "💰 Amount: ₹" + amount + "\n\n" +
                "Place your bid before time runs out!\n\n" +
                "– Badding System Team"
        );

        mailSender.send(message);
    }


}

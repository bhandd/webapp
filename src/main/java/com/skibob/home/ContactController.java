package com.skibob.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContactController {

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/send-message")
    public String sendMessage(@RequestParam String name,
                              @RequestParam String email,
                              @RequestParam String message,
                              RedirectAttributes redirectAttributes) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom("bjorn.andersson@bhand.se");
            mail.setTo("bjorn.andersson@bhand.se"); // skickas till dig
            mail.setSubject("Nytt meddelande från din portfolio");
            mail.setText("Från: " + name + " (" + email + ")\n\n" + message);

            mailSender.send(mail);

            redirectAttributes.addFlashAttribute("success", "Tack! Ditt meddelande har skickats.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Kunde inte skicka meddelandet.");
        }

        return "redirect:/";
    }
}


package com.skibob.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class PageController {



    @Autowired
    private JavaMailSender mailSender;
    @Value("${cloudflare.turnstile.site-key}")
    private String turnstileSiteKey;

    @Value("${cloudflare.turnstile.secret}")
    private String turnstileSecret;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("name", "Björn Andersson");
        model.addAttribute("title", "Björn Andersson - Portfolio");
        model.addAttribute("turnstileSiteKey", turnstileSiteKey);
        return "index";
    }

    @GetMapping("/landmark-hunt")
    public String landmarkHunt() {
        return "landmark-hunt";
    }

    @PostMapping("/send-message")
    public String sendMessage(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("message") String message,
            @RequestParam(value = "bot_check", required = false) String botCheck,
            @RequestParam(value = "cf-turnstile-response", required = false) String turnstileResponse,
            RedirectAttributes redirectAttributes
    ) {
        //control bot-check
        if (botCheck != null && !botCheck.isEmpty()) {
            // Returnera success för att lura boten, men skicka inget mail
            redirectAttributes.addFlashAttribute("success", "Your message was sent");
            return "redirect:/";
        }
        if (!verifyTurnstile(turnstileResponse)) {
            redirectAttributes.addFlashAttribute("error", "Spam-protection failed.");
            return "redirect:/";
        }


        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom("bjorn.andersson@bhand.se");
            mail.setTo("bjorn.andersson@bhand.se");
            mail.setSubject("Nytt meddelande från din portfolio");
            mail.setText("Från: " + name + " (" + email + ")\n\n" + message);

            mailSender.send(mail);

            redirectAttributes.addFlashAttribute("success", "Your message was sent");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Could not send message");
        }

        return "redirect:/";
    }

    private boolean verifyTurnstile(String token) {
        if (token == null || token.isEmpty() || turnstileSecret == null || turnstileSecret.isEmpty()) return false;
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("secret", turnstileSecret);
            body.add("response", token);
            Map<String, Object> response = restTemplate.postForObject(
                    "https://challenges.cloudflare.com/turnstile/v0/siteverify",
                    new HttpEntity<>(body, headers), Map.class);
            return response != null && Boolean.TRUE.equals(response.get("success"));
        } catch (Exception e) { return false; }
    }
}

package com.scm.scm20.controllers;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.scm20.forms.ContactForm;

import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.scm20.entities.Contact;
import com.scm.scm20.entities.User;
import com.scm.scm20.helpers.Helper;
import com.scm.scm20.helpers.Message;
import com.scm.scm20.helpers.MessageType;
import com.scm.scm20.services.ContactService;
import com.scm.scm20.services.ImageService;
import com.scm.scm20.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    private Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    ContactService contactService;

    @Autowired
    UserService userService;

    @Autowired
    ImageService imageService;

    @RequestMapping("/add")
    public String addContactView(Model model) {
        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result, Authentication authentication, HttpSession session) {

        // process the form data
        // validate form
        if (result.hasErrors()) {
            session.setAttribute("message", Message.builder()
                    .content("Please correct the following error.")
                    .type(MessageType.red)
                    .build()
            );
            return "user/add_contact";
        }

        String username = Helper.getEmailOfLoggedInUser(authentication);

        // form --> contact
        User user = userService.getUserByEmail(username);

        // process the contact picture
        // logger.info("File information : {} ",contactForm.getContactImage().getOriginalFilename());

        String filename = UUID.randomUUID().toString();
        String fileURL = imageService.uploadImage(contactForm.getContactImage(), filename);


        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setFavorite(contactForm.isFavorite());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setUser(user);
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setPicture(fileURL);
        contact.setCloudinaryImagePublicId(filename);
        contactService.save(contact);
        session.setAttribute("message", Message.builder()
                .content("You have successfully added a new contact")
                .type(MessageType.green)
                .build()
        );
        return "redirect:/user/contacts/add";
    }
    
    @RequestMapping
    public String viewContacts(){
        return "user/contacts";
    }

}

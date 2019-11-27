package employees;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Controller
public class EmpController {
    private List<Emp> list;

    public EmpController() {
        list = new ArrayList<>();
        list.add(new Emp(1,"Jakub",35000f,"Trener", "mail@gmail.com"));
        list.add(new Emp(2,"Adam",25000f,"Siatkarz", "mail@gmail.com"));
        list.add(new Emp(3,"Robert",55000f,"YouTuber", "mail@gmail.com"));
    }

    @RequestMapping("/empform")
    public ModelAndView showform(){
        return new ModelAndView("empform","command", new Emp());
    }

    @RequestMapping(value="/save", method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute("emp") Emp emp){
        if(emp.getId() == 0) {
            System.out.println("New emp");
            emp.setId(list.size()+1);
            list.add(emp);
        } else {
            Emp emp1 = getEmpById(emp.getId());
            emp1.setDesignation(emp.getDesignation());
            emp1.setName(emp.getName());
            emp1.setSalary(emp.getSalary());
        }
        System.out.println(emp.getName()+" "+emp.getSalary()+" "+emp.getDesignation());
        return new ModelAndView("redirect:/viewemp");
    }

    @RequestMapping(value="/delete", method=RequestMethod.POST)
    public ModelAndView delete(@RequestParam String id){
        //Stream vs list
        /*
        for (Emp one : list) {
            if(one.getId() == Integer.parseInt(id))
            list.remove(one);
        }

        Emp emp = list.stream().filter(f -> f.getId() == Integer.parseInt(id)).findFirst().get();
        list.remove(emp);
         */
        list.remove(getEmpById(Integer.parseInt(id)));
        return new ModelAndView("redirect:/viewemp");
    }

    /*    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView delete(@ModelAttribute("emp") Emp emp) {
        for (Emp one : list) {
            if (one.getId() == Integer.parseInt(id))
                list.remove(one);
        }

        return new ModelAndView("redirect:/viewemp");
    }*/

    @RequestMapping(value="/edit", method=RequestMethod.POST)
    public ModelAndView edit(@RequestParam String id){
        Emp emp = getEmpById(Integer.parseInt(id));
        return new ModelAndView("empform","command", emp);
    }

    @RequestMapping(value="/test", method=RequestMethod.POST)
    public ModelAndView test(){
        System.out.println("Naciśnieto test");
        return new ModelAndView("redirect:/viewemp");
    }

    @RequestMapping("/viewemp")
    public ModelAndView viewemp(){
        return new ModelAndView("viewemp","list", list);
    }

    private Emp getEmpById(@RequestParam int id) {
        return list.stream().filter(f -> f.getId() == id).findFirst().get();
    }

    @RequestMapping(value="/email", method=RequestMethod.POST)
    public ModelAndView sentEmail(@RequestParam String email) {
        final String username = "test.kurs.123123@gmail.com";
        final String password = "Test1234@";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("Janek"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(email)
            );
            message.setSubject("Testing");
            message.setText("Hello");

            Transport.send(message);

            System.out.println("Sukces");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/viewemp");
    }
}
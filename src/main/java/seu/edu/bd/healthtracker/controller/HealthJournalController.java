package seu.edu.bd.healthtracker.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import seu.edu.bd.healthtracker.model.User;
import seu.edu.bd.healthtracker.model.UserDetails;
import seu.edu.bd.healthtracker.service.HealthJournalService;
import seu.edu.bd.healthtracker.service.UserService;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/health-journal")
public class HealthJournalController {

    private final HealthJournalService journalService;
    private final UserService userService;

    public HealthJournalController(HealthJournalService journalService, UserService userService) {
        this.journalService = journalService;
        this.userService = userService;
    }

    @GetMapping
    public String viewJournal(Authentication auth, Model model) {
        String email = auth.getName();
        User user = userService.findByEmail(email).orElseThrow();

        // Prepare empty/new entry object
        UserDetails newEntry = new UserDetails();
        newEntry.setUserName(user.getUserName());
        newEntry.setEntryDate(LocalDate.now());
        newEntry.setHealthIssues(new LinkedHashMap<>());

        List<UserDetails> journalEntries = journalService.findByUserName(user.getUserName());

        model.addAttribute("healthJournal", newEntry);
        model.addAttribute("issueList", getHealthIssueList());
        model.addAttribute("journalList", journalEntries);

        return "health_journal";
    }

    /*@PostMapping("/save")
    public String save(@ModelAttribute("healthJournal") UserDetails journal, Authentication auth) {
        String email = auth.getName();
        User user = userService.findByEmail(email).orElseThrow();

        if (journal.getHealthIssues() == null) {
            journal.setHealthIssues(new LinkedHashMap<>());
        }
        journal.setUserName(user.getUserName());

        if (journal.getEntryDate() == null) {
            journal.setEntryDate(LocalDate.now());
        }

        calculateAndSetBmi(journal);
        journalService.saveOrUpdate(journal);
        return "redirect:/health-journal";
    }*/

    @PostMapping("/save")
    public String save(@ModelAttribute("healthJournal") UserDetails journal, Authentication auth) {
        String email = auth.getName();
        User user = userService.findByEmail(email).orElseThrow();

        journal.setUserName(user.getUserName());
        journal.setEntryDate(LocalDate.now());

        if (journal.getHealthIssues() == null) {
            journal.setHealthIssues(new LinkedHashMap<>());
        }


        journal.setId(null); // force insert instead of update

        calculateAndSetBmi(journal);
        journalService.saveOrUpdate(journal);

        return "redirect:/health-journal";
    }


    @PostMapping("/update")
    public String update(@ModelAttribute("healthJournal") UserDetails journal, Authentication auth) {
        String email = auth.getName();
        User user = userService.findByEmail(email).orElseThrow();

        if (journal.getHealthIssues() == null) {
            journal.setHealthIssues(new LinkedHashMap<>());
        }
        journal.setUserName(user.getUserName());

        calculateAndSetBmi(journal);
        journalService.saveOrUpdate(journal);
        return "redirect:/health-journal";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("userName") String userName,
                         @RequestParam("entryDate") String entryDateStr) {
        try {
            LocalDate entryDate = LocalDate.parse(entryDateStr);
            journalService.delete(userName, entryDate);
        } catch (Exception e) {
            System.out.println("Invalid entryDate: " + entryDateStr);
        }
        return "redirect:/health-journal";
    }

    @GetMapping("/select")
    public String selectEntry(@RequestParam("entryDate") String entryDateStr,
                              Authentication auth, Model model) {
        String email = auth.getName();
        User user = userService.findByEmail(email).orElseThrow();

        LocalDate entryDate = LocalDate.parse(entryDateStr);

        UserDetails selectedEntry = journalService
                .findByUserNameAndEntryDate(user.getUserName(), entryDate)
                .orElseGet(() -> {
                    UserDetails empty = new UserDetails();
                    empty.setUserName(user.getUserName());
                    empty.setEntryDate(entryDate);
                    empty.setHealthIssues(new LinkedHashMap<>());
                    return empty;
                });

        List<UserDetails> journalEntries = journalService.findByUserName(user.getUserName());

        model.addAttribute("healthJournal", selectedEntry);
        model.addAttribute("issueList", getHealthIssueList());
        model.addAttribute("journalList", journalEntries);
        model.addAttribute("selectedDate", entryDate); // optional UI highlight

        return "health_journal";
    }

    private Map<String, String> getHealthIssueList() {
        Map<String, String> issues = new LinkedHashMap<>();
        issues.put("Diabetes", "");
        issues.put("High BP", "");
        issues.put("Kidney Disease", "");
        issues.put("Asthma", "");
        issues.put("Arthritis", "");
        issues.put("Migraine", "");
        issues.put("Ulcer", "");
        issues.put("Eye Problem", "");
        issues.put("PCOS", "");
        issues.put("Anxiety", "");
        issues.put("Acid Reflux", "");
        issues.put("Chronic Back Pain", "");
        return issues;
    }
    private void calculateAndSetBmi(UserDetails journal) {
        if (journal.getHeight() > 0 && journal.getWeight() > 0) {
            double heightMeters = journal.getHeight() / 100.0;
            double bmi = journal.getWeight() / (heightMeters * heightMeters);
            journal.setBmi(Math.round(bmi * 10.0) / 10.0); // rounded to 1 decimal
        }
    }



}

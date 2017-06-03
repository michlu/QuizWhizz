package com.pw.quizwhizz.controller;

import com.pw.quizwhizz.model.account.User;
import com.pw.quizwhizz.model.game.Category;
import com.pw.quizwhizz.service.CategoryService;
import com.pw.quizwhizz.service.QuestionService;
import com.pw.quizwhizz.service.RoleService;
import com.pw.quizwhizz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Kontroler obslugujacy żądania adminow aplikacji. Żądania maja prefix /admin/ dostepne jedynie dla uzytkownikow z rola admina.
 * @author Michał Nowiński
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final CategoryService categoryService;
    private final QuestionService questionService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, CategoryService categoryService, QuestionService questionService, RoleService roleService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.questionService = questionService;
        this.roleService = roleService;
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getCommonsMultipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(20971520);   // 20MB
        multipartResolver.setMaxInMemorySize(1048576);  // 1MB
        return multipartResolver;
    }

    /**
     * Obsluguje metode GET /adminadd
     * @param model przyjmuje liste kategorii, przyjmuje nowa kategorie uzupelniana w formularzu
     * @return zwraca strone panelu dodawania kategori oraz pytan z odpowiedziami
     */
    @GetMapping("/adminadd")
    public String getAddCategoryQuestion(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("categories", categoryService.findAll());
        return "admin_add";
    }

    /**
     * Obsluguje metode POST /adminadd. Przyjmuje kategorie i przekazuje do zapisania w bazie danych. Sprawdza czy został wysłany plik obrazu kategori, jesli nie
     * przekazuje do zapisania w brazem domyslnym.
     * @param category kategoria, walidacja uniklanosci nazwy kategori
     * @param file plik obrazu kategori
     * @param bindResult zwraca bledy walidacji
     * @param request potrzebny do ustalenia sciezki kontekstu aplikacji
     * @param model zawiera odpowiedz dla uzytkownika odnosnie zapisu kategorii
     * @return przekierowuje z powrotem do strony wyjsciowej /adminadd
     */
    @PostMapping("/addcategory")
    public String addCategory(
            @ModelAttribute @Valid Category category,
            @RequestParam MultipartFile file,
            BindingResult bindResult,
            HttpServletRequest request,
            Model model) {
        String saveDirectory = request.getSession().getServletContext().getRealPath("/")+"resources\\images\\";

        if (bindResult.hasErrors())
            return "admin_add";
        else {
            if (!file.isEmpty()) {
                try {
                    categoryService.addCategoryWithImage(category, file, saveDirectory);
                    model.addAttribute("message", "Kategoria została zapisana, z załadowanym obrazem: '" + file.getOriginalFilename() + "'");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                categoryService.addCategory(category);
                model.addAttribute("message", "Kategoria została zapisana z domyślnym obrazem.");
            }
            return "redirect:adminadd";
        }
    }

    /**
     * Obsluguje medote POST /addquestion. Zapisuje przekazane w formularzu pytanie w raz z odpowiedziami
     * @param categoryId numer id kategorii
     * @param inputQuestion tresc pytania
     * @param inputAnswer1 tresc pierwszej odpowiedz
     * @param inputAnswer2 tresc drugiej odpowiedz
     * @param inputAnswer3 tresc trzeciej odpowiedz
     * @param inputAnswer4 tresc czwartej odpowiedzi
     * @param answerCorrect ktora odpowiedz jest prawidlowa
     * @return przekierowuje z powrotem do strony wyjsciowej /adminadd
     */
    @PostMapping(value = "/addquestion", produces = "text/plain;charset=UTF-8")
    public String addQuestion(
            @RequestParam String categoryId,
            @RequestParam String inputQuestion,
            @RequestParam String inputAnswer1,
            @RequestParam String inputAnswer2,
            @RequestParam String inputAnswer3,
            @RequestParam String inputAnswer4,
            @RequestParam String answerCorrect) {
        questionService.addQuestion(categoryId, inputQuestion, inputAnswer1, inputAnswer2, inputAnswer3, inputAnswer4, answerCorrect);
        return "redirect:adminadd";
    }

    /**
     * Obsluguje metode GET /listcategory
     * @param model zawiera liste wszystkich kategorii
     * @return zwraca strone admin_list_categories.html zawieajaca liste wszystkich kategorii
     */
    @GetMapping("/listcategory")
    public String listCategory(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "admin_list_categories";
    }

    /**
     * Obsluguje metode GET /category/delete/{categoryId} zawierajaca numer kategorii ktora ma zostac skasowana
     * @param categoryId numer id kategorii
     * @return przekierowuje na strone z lista wysztskich kategorii
     */
    @GetMapping("/category/delete/{categoryId}")
    public String categoryDelete(@PathVariable String categoryId) {
        categoryService.deleteById(Long.parseLong(categoryId));
        return "redirect:/admin/listcategory";
    }

    /**
     * Obsluguje metode GET /category/edit/{categoryId} zawiera numer kategorii ktora ma zostac przekazana do edytowania
     * @param categoryId numer id kategorii
     * @param model zawira kategorie do edycji
     * @return zwraca strone admin_edit_category.html edycji kategorii
     */
    @GetMapping("/category/edit/{categoryId}")
    public String getCategoryEdit(@PathVariable String categoryId, Model model) {
        model.addAttribute("category", categoryService.findById(Long.parseLong(categoryId)));
        return "admin_edit_category";
    }

    /**
     * Obsluguje metode POST /category/edit przyjmuje kategorie z formularza. Sprawdza czy formularz zawiera plik obrazu dla kategorii
     * @param category kategoria
     * @param file plik obrazu kategorii
     * @param bindResult bledy walidacji
     * @param request potrzebny do ustalenia sciezki kontekstu aplikacji
     * @return przekirowuje na strone zawierajaca liste wszystkich kategorii
     */
    @PostMapping("/category/edit")
    public String postCategoryEdit(@ModelAttribute Category category,
            @RequestParam MultipartFile file,
            BindingResult bindResult,
            HttpServletRequest request) {
        String saveDirectory = request.getSession().getServletContext().getRealPath("/")+"resources\\images\\";

        if (bindResult.hasErrors())
            return "redirect:/admin/listcategory";
        else {
            if (!file.isEmpty()) {
                try {
                    categoryService.updateCategoryWithImage(category, file, saveDirectory);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                categoryService.updateCategory(category);
            }
            return "redirect:/admin/listcategory";
        }
    }

    /**
     * Obsluguja metode GET /listquestions/{categoryId} zawiera id kategorii, zwraca strone zawierajaca wszystkie pytania dla danej kategorii
     * @param categoryId numer id kategorii
     * @param model zawiera liste pytan wyszukanych po id danej kategorii
     * @return zwraca strone admin_list_questions.html z lista wysztskich pytan dla danej kategorii
     */
    @GetMapping("/listquestions/{categoryId}")
    public String listQuestion(@PathVariable String categoryId, Model model) {
        Category category = categoryService.findById(Long.parseLong(categoryId));
        model.addAttribute("questions", questionService.findAllByCategoryId(Long.parseLong(categoryId)));
        model.addAttribute("category", category);
        return "admin_list_questions";
    }

    /**
     * Obsluguje metode GET /question/edit/{questionId} zawiera id pytania, zwraca strone edycji pytania
     * @param questionId numer id pytania
     * @param model zawiera pytanie wyszukane po id
     * @return zwraca strone admin_edit_question.html edycji pytania
     */
    @GetMapping("/question/edit/{questionId}")
    public String getQuestionEdit(@PathVariable String questionId, Model model) {
        model.addAttribute("question", questionService.findById(Long.parseLong(questionId)));
        return "admin_edit_question";
    }

    /**
     * Obsluguje metode GET /question/edit/{questionId} zawiera id pytania, kasuje wybrane pytanie z bazy danych
     * @param questionId numer id pytania
     * @return przekierowuje na strone z lista pytan danej kategorii
     */
    @GetMapping("/question/delete/{questionId}")
    public String questionDelete(@PathVariable String questionId) {
        Long idCategoryByQuestion = questionService.findById(Long.parseLong(questionId)).getCategory().getId(); //pobiera id kategorii do jakiej nalezy pytanie
        questionService.deleteById(Long.parseLong(questionId));
        return "redirect:/admin/listquestions/" + idCategoryByQuestion.toString();
    }

    /**
     * Obsluguje metode POST /question/edit, przyjmuje dane z formularza edycji pytan
     * @param inputId numer id pytania
     * @param inputQuestion tresc pytania
     * @param categoryId numer id kategorii do jakiej nalezy pytanie
     * @param inputAnswer1 tresc pierwszej odpowiedz
     * @param inputAnswer2 tresc drugiej odpowiedz
     * @param inputAnswer3 tresc trzeciej odpowiedz
     * @param inputAnswer4 tresc czwartej odpowiedzi
     * @param answerCorrect ktora odpowiedz jest prawidlowa
     * @return przekirowuje na strone z lista pytan danej kategorii
     */
    @PostMapping("/question/edit")
    public String postQuestionEdit(
            @RequestParam String inputId,
            @RequestParam String inputQuestion,
            @RequestParam String categoryId,
            @RequestParam String inputAnswer1,
            @RequestParam String inputAnswer2,
            @RequestParam String inputAnswer3,
            @RequestParam String inputAnswer4,
            @RequestParam String answerCorrect) {
        questionService.updateQuestion(inputId, inputQuestion, inputAnswer1, inputAnswer2, inputAnswer3, inputAnswer4, answerCorrect);

        return "redirect:/admin/listquestions/" + categoryId;
    }

    /**
     * Obsluguje metode GET /listuser, zwraca strone z lista wszystkich zarejestrowanych uzytkownikow
     * @param model zawiera liste uzytkownikow
     * @return zwraca strone admin_list_users.html zawierjaca liste uzytkownkow
     */
    @GetMapping("/listuser")
    public String userList(Model model) {
        List<User> users = userService.findAll();
        if (!users.isEmpty())
            model.addAttribute("users", users);
        return "admin_list_users";
    }

    /**
     * Obsluguje metode GET /user/delete/{userId} zwiera id uzytkownika, kasuje danego uzytkownika z bazy danych
     * @param userId numer id uzytkownika
     * @return przekirowuje na strone z lista wszystkich uzytkownikow
     */
    @GetMapping("/user/delete/{userId}")
    public String userDelete(@PathVariable String userId) {
        userService.deleteById(Long.parseLong(userId));
        return "redirect:/admin/listuser";
    }

    /**
     * Obsluguje metode GET /user/edit/{userId} zwiera id uzytkownika, zwraca strone do edycji uzytkownika
     * @param userId numer id uzytkownika
     * @param model zawera uzytkownika wyszkunaego po id, oraz role
     * @return zwraca strone admin_edit_user.html edycji uztkownika
     */
    @GetMapping("/user/edit/{userId}")
    public String getUserEdit(@PathVariable String userId, Model model) {
        model.addAttribute("user", userService.findById(Long.parseLong(userId)));
        model.addAttribute("roles", roleService.findAll());
        return "admin_edit_user";
    }

    /**
     * Obsluguje metode POST /user/edit, przyjmuje dane z formularza edycji uzytkownika
     * @param user uzytkownik
     * @return przekirowuje na stone listy wysztskich zarejestrowanych uzytkownikow
     */
    @PostMapping("/user/edit")
    public String postUserEdit(@ModelAttribute User user) {
        userService.update(user);
        return "redirect:/admin/listuser";
    }

    /**
     * Obsluguje metode POST /user/role/{userId} zawiera id uzytkownika, przyjmuje dane z formularze edycji uzytkownia. Dotyczy przydzielania/usuwania roli dla uzytkownika
     * @param userId numer id uzytkownika
     * @param roles role
     * @param metod zawiera metode jaka zostala przekazana przez formularz addRole albo deleteRole
     * @return
     */
    @PostMapping("/user/role/{userId}")
    public String postUserEditRole(
            @PathVariable String userId,
            @RequestParam String[] roles,
            @RequestParam String metod) {
        if(metod.equals("addRole"))
            userService.addRoleToUser(userId, roles);
        if(metod.equals("deleteRole"))
            userService.removeRoleUser(userId, roles);
        return "redirect:/admin/user/edit/"+userId;
    }
}

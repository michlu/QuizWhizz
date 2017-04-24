package com.pw.quizwhizz.controller;

import com.pw.quizwhizz.model.entity.Category;
import com.pw.quizwhizz.model.account.User;
import com.pw.quizwhizz.service.CategoryService;
import com.pw.quizwhizz.service.QuestionService;
import com.pw.quizwhizz.service.RoleService;
import com.pw.quizwhizz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
public class AdminController {

    private UserService userService;
    private CategoryService categoryService;
    private QuestionService questionService;
    private RoleService roleService;

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }
    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/admin/adminadd")
    public String getAddCategoryQuestion(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("categories", categoryService.findAll());
        return "admin_add";
    }

    @PostMapping("/admin/addcategory")
    public String addCategory(@ModelAttribute @Valid Category category, BindingResult bindResult) {
        if (bindResult.hasErrors())
            return "admin_add";
        else {
            categoryService.addCategory(category);
            return "redirect:adminadd";
        }
    }

    @PostMapping(value = "/admin/addquestion", produces = "text/plain;charset=UTF-8")
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

    @GetMapping("/admin/listcategory")
    public String listCategory(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "admin_list_categories";
    }

    @GetMapping("/admin/category/delete/{categoryId}")
    public String categoryDelete(@PathVariable String categoryId) {
        categoryService.deleteById(Long.parseLong(categoryId));
        return "redirect:/admin/listcategory";
    }

    @GetMapping("/admin/category/edit/{categoryId}")
    public String getCategoryEdit(@PathVariable String categoryId, Model model) {
        model.addAttribute("category", categoryService.findById(Long.parseLong(categoryId)));
        return "admin_edit_category";
    }

    @PostMapping("/admin/category/edit")
    public String postCategoryEdit(@ModelAttribute Category category) {
        categoryService.updateCategory(category);
//        categoryService.updateCategoryById(category.getId(), category.getName(), category.getDescription(), category.getUrlImage());
        return "redirect:/admin/listcategory";
    }

    @GetMapping("/admin/listquestions/{categoryId}")
    public String listQuestion(@PathVariable String categoryId, Model model) {
        Category category = categoryService.findById(Long.parseLong(categoryId));
        model.addAttribute("questions", questionService.findAllByCategory(category));
        model.addAttribute("category", category);
        return "admin_list_questions";
    }

    @GetMapping("/admin/question/edit/{questionId}")
    public String getQuestionEdit(@PathVariable String questionId, Model model) {
        model.addAttribute("question", questionService.findById(Long.parseLong(questionId)));
        return "admin_edit_question";
    }

    @GetMapping("/admin/question/delete/{questionId}")
    public String questionDelete(@PathVariable String questionId) {
        Long idCategoryByQuestion = questionService.findById(Long.parseLong(questionId)).getCategory().getId();
        questionService.deleteById(Long.parseLong(questionId));

        return "redirect:/admin/listquestions/" + idCategoryByQuestion.toString();
    }

    @PostMapping("/admin/question/edit")
    public String postQuestionEdit(
            @RequestParam String inputId,
            @RequestParam String inputQuestion,
            @RequestParam String categoryId,
            @RequestParam String inputAnswer1,
            @RequestParam String answerId1,
            @RequestParam String inputAnswer2,
            @RequestParam String answerId2,
            @RequestParam String inputAnswer3,
            @RequestParam String answerId3,
            @RequestParam String inputAnswer4,
            @RequestParam String answerId4,
            @RequestParam String answerCorrect) {
        questionService.updateQuestion(inputId, inputQuestion, inputAnswer1, answerId1, inputAnswer2, answerId2, inputAnswer3, answerId3, inputAnswer4, answerId4, answerCorrect);

        return "redirect:/admin/listquestions/" + categoryId;
    }

    // === Users ===

    @GetMapping("/admin/listuser")
    public String userList(Model model) {
        List<User> users = userService.findAll();
        if (users != null)
            model.addAttribute("users", users);
        return "admin_list_users";
    }

    @GetMapping("/admin/user/delete/{userId}")
    public String userDelete(@PathVariable String userId) {
        userService.deleteById(Long.parseLong(userId));
        return "redirect:/admin/listuser";
    }

    @GetMapping("/admin/user/edit/{userId}")
    public String getUserEdit(@PathVariable String userId, Model model) {
        model.addAttribute("user", userService.findById(Long.parseLong(userId)));
        model.addAttribute("roles", roleService.findAll());
        return "admin_edit_user";
    }

    @PostMapping("/admin/user/edit")
    public String postUserEdit(@ModelAttribute User user) {
        System.out.println("User: " + user.toString());
        userService.update(user);
        return "redirect:/admin/listuser";
    }

    @PostMapping("/admin/user/role/{userId}")
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

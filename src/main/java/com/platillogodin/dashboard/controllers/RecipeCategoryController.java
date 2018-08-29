package com.platillogodin.dashboard.controllers;

import com.platillogodin.dashboard.domain.RecipeCategory;
import com.platillogodin.dashboard.exceptions.ExistingReferencesException;
import com.platillogodin.dashboard.services.RecipeCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Slf4j
@Controller
public class RecipeCategoryController {

    private final RecipeCategoryService recipeCategoryService;

    public RecipeCategoryController(RecipeCategoryService recipeCategoryService) {
        this.recipeCategoryService = recipeCategoryService;
    }

    private static final String FORM_URL = "categories/recipes/recipe_category_form";
    private static final String LIST_URL = "categories/recipes/list";

    @GetMapping("/categories/recipes")
    public String listRecipeCategories(Model model) {
        List<RecipeCategory> recipeCategoryList = recipeCategoryService.findAll();
        model.addAttribute("recipeCategoryList", recipeCategoryList);
        return LIST_URL;
    }

    @GetMapping("/categories/recipes/new")
    public String newRecipeCategory(Model model) {
        model.addAttribute("recipeCategory", new RecipeCategory());
        return FORM_URL;
    }

    @GetMapping("/categories/recipes/{id}/edit")
    public String updateCategoryRecipe(@PathVariable Long id, Model model) {
        RecipeCategory recipeCategory = recipeCategoryService.findById(id);
        model.addAttribute("recipeCategory", recipeCategory);
        return FORM_URL;
    }

    @GetMapping("/categories/recipes/{id}/delete")
    public String deleteCategoryRecipe(@PathVariable Long id, RedirectAttributes ra) {
        RecipeCategory rc = recipeCategoryService.findById(id);
        try {
            recipeCategoryService.delete(rc);
            ra.addFlashAttribute("deleteMessage", "La categoría " + rc.getName() + " fue eliminada correctamente");
        } catch (ExistingReferencesException ere) {
            ra.addFlashAttribute("deleteError",
                    "Error al eliminar " + rc.getName() + ", existen recetas asociadas a esta categoría.");

        }
        return "redirect:/categories/recipes";
    }

    @PostMapping("/categories/recipe")
    public String saveOrUpdateRecipeCategory(@ModelAttribute("recipeCategory") RecipeCategory recipeCategory) {
        log.info("Saving recipeCategory");
        recipeCategoryService.saveRecipeCategory(recipeCategory);
        return "redirect:/categories/recipes/";
    }
}

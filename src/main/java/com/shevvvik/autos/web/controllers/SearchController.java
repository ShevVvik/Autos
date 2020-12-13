package com.shevvvik.autos.web.controllers;

import com.shevvvik.autos.services.SearchService;
import com.shevvvik.autos.services.ServiceUtils;
import com.shevvvik.autos.services.entities.SearchClientEntity;
import com.shevvvik.autos.services.entities.SearchDealerEntity;
import com.shevvvik.autos.services.entities.SearchOfferEntity;
import com.shevvvik.autos.services.validation.SearchValidator;
import com.shevvvik.autos.web.forms.SearchClientsForm;
import com.shevvvik.autos.web.forms.SearchDealersForm;
import com.shevvvik.autos.web.forms.SearchOrdersForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private ServiceUtils serviceUtils;

    @Autowired
    private SearchService searchService;

    @Autowired
    private SearchValidator validator;

    @GetMapping("/search/clients")
    public String searchClients(Model model, SearchClientsForm searchForm) {
        String role = "ROLE_CLIENT";
        for(GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            role = grantedAuthority.getAuthority();
        }
        model.addAttribute("error", false);
        model.addAttribute("result", new ArrayList<SearchClientEntity>());
        model.addAttribute("cities", serviceUtils.getCities());
        model.addAttribute("role", role);
        model.addAttribute("searchClientsForm", searchForm);
        return "/dealers/search/searchClients";
    }

    @PostMapping("/search/clients")
    public String searchClientsPost(Model model, @ModelAttribute("searchClientsForm") SearchClientsForm searchForm) {
        String role = "ROLE_CLIENT";
        for(GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            role = grantedAuthority.getAuthority();
        }

        if (!validator.checkSearchClientForm(searchForm)) {
            model.addAttribute("error", true);
            model.addAttribute("result", new ArrayList<SearchClientEntity>());
            model.addAttribute("cities", serviceUtils.getCities());
            model.addAttribute("role", role);
            model.addAttribute("searchClientsForm", searchForm);
        } else {
            List<SearchClientEntity> result = searchService.searchClients(searchForm);
            model.addAttribute("error", false);
            model.addAttribute("result", result);
            model.addAttribute("cities", serviceUtils.getCities());
            model.addAttribute("role", role);
            model.addAttribute("searchClientsForm", searchForm);
        }
        return "/dealers/search/searchClients";
    }

    @GetMapping("/search/dealers")
    public String searchDealers(Model model, SearchDealersForm searchForm) {
        String role = "ROLE_CLIENT";
        for(GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            role = grantedAuthority.getAuthority();
        }
        model.addAttribute("error", false);
        model.addAttribute("result", new ArrayList<SearchDealerEntity>());
        model.addAttribute("role", role);
        model.addAttribute("searchDealersForm", searchForm);
        return "/dealers/search/searchDealers";
    }

    @PostMapping("/search/dealers")
    public String searchDealersPost(Model model, @ModelAttribute("searchDealersForm") SearchDealersForm searchForm) {
        String role = "ROLE_CLIENT";
        for(GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            role = grantedAuthority.getAuthority();
        }
        if (!validator.checkSearchDealerForm(searchForm)) {
            model.addAttribute("error", true);
            model.addAttribute("result", new ArrayList<SearchDealerEntity>());
            model.addAttribute("role", role);
            model.addAttribute("searchDealersForm", searchForm);
        } else {
            model.addAttribute("error", false);
            List<SearchDealerEntity> result = searchService.searchDealers(searchForm);
            model.addAttribute("result", result);
            model.addAttribute("role", role);
            model.addAttribute("searchDealersForm", searchForm);
        }
        return "/dealers/search/searchDealers";
    }

    @GetMapping("/search/orders")
    public String searchOffersDetailed(Model model, SearchOrdersForm searchForm) {
        String role = "ROLE_CLIENT";
        for(GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            role = grantedAuthority.getAuthority();
        }
        model.addAttribute("error", false);
        model.addAttribute("result", new ArrayList<SearchOfferEntity>());
        model.addAttribute("cities", serviceUtils.getCities());
        model.addAttribute("role", role);
        model.addAttribute("searchOffersForm", searchForm);
        return "/dealers/search/searchOffers";
    }

    @PostMapping("/search/orders")
    public String searchOffersDetailedPost(Model model, @ModelAttribute("searchOffersForm") SearchOrdersForm searchForm) {
        String role = "ROLE_CLIENT";
        for(GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            role = grantedAuthority.getAuthority();
        }
        if (!validator.checkSearchOffersForm(searchForm)) {
            model.addAttribute("error", true);
            model.addAttribute("result", new ArrayList<SearchOfferEntity>());
            model.addAttribute("cities", serviceUtils.getCities());
            model.addAttribute("role", role);
            model.addAttribute("searchOffersForm", searchForm);
        } else {
            model.addAttribute("error", false);
            List<SearchOfferEntity> result = searchService.searchOffersDetailed(searchForm);
            model.addAttribute("cities", serviceUtils.getCities());
            model.addAttribute("result", result);
            model.addAttribute("role", role);
            model.addAttribute("searchDealersForm", searchForm);
        }
        return "/dealers/search/searchOffers";
    }


}

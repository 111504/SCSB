package com.scsb.t.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class PageController {

//    @RequestMapping("/")
//    public String welcome(){
//        return "welcome";
//    }

    //當前頁面返回當前頁面
    @RequestMapping("/redirectToIndex")
    public String redirectToIndex() {
        return "redirect:/index";
    }

    @RequestMapping("/redirectToLogin")
    public String redirectToLogin() {
        return "redirect:/login";
    }

    @RequestMapping("/redirectToIncidentTable")
    public String redirectToIncidentTable() {return "redirect:/incident-table";}

    @RequestMapping("/redirectToUnreviewTable")
    public String redirectToUnreviewTable() {
        return "redirect:/unreview-table";
    }

    @RequestMapping("/redirectToAddFormV2")
    public String redirectToAddFormV2() {
        return "redirect:/addFormV2";
    }

    @RequestMapping("/redirectToDynFormGUI")
    public String redirectToDynFormGUI() {
        return "redirect:/Dyn_formGUI";
    }



    @RequestMapping("/login")
    public String Login() {
        return "login";
    }

    @RequestMapping ("/index")
    public String Index(){
        return "index";
    }

    @RequestMapping("/fail")
    public String Fail(){
        return "404";
    }

    //歷史紀錄
    @RequestMapping ("/incident-table")
    public String IncidentTable(){
        return "incident-table";
    }

    @RequestMapping ("/unreview-table")
    public String UnreviewTable(){
        return "unreview-table";
    }

    @RequestMapping ("/addFormV2")
    public String AddFormV2(){
        return "addFormV2";
    }

    @RequestMapping ("/Dyn_formGUI")
    public String DynFormGUI(){
        return "Dyn_formGUI";
    }

    @RequestMapping ("/Form_Analysis")
    public String Form_Analysis(){
        return "Form_Analysis";
    }

    @RequestMapping("/404")
    public String handle404() {
        return "404";
    }


}

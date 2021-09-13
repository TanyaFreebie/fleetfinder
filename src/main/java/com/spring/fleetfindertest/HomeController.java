package com.spring.fleetfindertest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//Вызываем контроллер который обрабатывает конкретный запрос в браузере
@Controller
public class HomeController {
    //GetMapping указывает по какому URL и какой HTTP запрос мы хотим обработать. Может быть например @PostMapping("/submit") или что то типа
	@GetMapping("/")
    
    //RequestParam ожидает параметр name в строке браузера(localhost:8080/?name=User) и создает аттрибут name который мы можем отобразить в шаблоне.
	public String index(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		model.addAttribute("name", name);
        //в ретурне мы должны указать ИМЯ файла шаблона из папки templates который хотим отдать пользователю
		return "index";
		System.out.println("This is my branch");
	}

}
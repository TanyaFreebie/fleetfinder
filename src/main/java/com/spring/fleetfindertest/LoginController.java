package com.spring.fleetfindertest;

import com.company.helpers.Auth;
import com.company.helpers.Character;
import net.troja.eve.esi.ApiClient;
import net.troja.eve.esi.ApiClientBuilder;
import net.troja.eve.esi.ApiException;
import net.troja.eve.esi.api.SsoApi;
import net.troja.eve.esi.auth.OAuth;
import net.troja.eve.esi.model.CharacterInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URISyntaxException;

//Вызываем контроллер который обрабатывает конкретный запрос в браузере
@Controller
public class LoginController {
	public static SsoApi api;
    //GetMapping указывает по какому URL и какой HTTP запрос мы хотим обработать. Может быть например @PostMapping("/submit") или что то типа
	@GetMapping("/")
    
   //RequestParam ожидает параметр name в строке браузера(localhost:8080/?name=User) и создает аттрибут name который мы можем отобразить в шаблоне.
	public String index(@RequestParam(name="code", required=false) String authCode, @RequestParam(name="state", required=false) String authState, Model model) throws ApiException {

		if(authCode != null){
			final String ClientId = Auth.get().getClientId();

			final OAuth auth = Auth.get();
			auth.finishFlow(authCode, authState, authState);
			model.addAttribute("name", auth.getRefreshToken());

			final ApiClient userClient = new ApiClientBuilder().clientID(ClientId).refreshToken(auth.getRefreshToken()).build();

			System.out.println(auth.getRefreshToken());
			System.out.println(auth.getAccessToken());

			api = new SsoApi(userClient);

			CharacterInfo character = api.getCharacterInfo();

			model.addAttribute("name", character.getCharacterName());
			System.out.println(Character.character().getCharacterID());
			System.out.println( Character.character().getCharacterName());

		}

		//в ретурне мы должны указать ИМЯ файла шаблона из папки templates который хотим отдать пользователю
		return "index";
	}


}
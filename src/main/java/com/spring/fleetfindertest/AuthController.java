package com.spring.fleetfindertest;

import net.troja.eve.esi.ApiClient;
import net.troja.eve.esi.ApiClientBuilder;
import net.troja.eve.esi.auth.OAuth;
import net.troja.eve.esi.auth.SsoScopes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
public class AuthController {

    @GetMapping("/login")

	public String index() {

        final String state = "d2NTpYVRNy2jnnFjQXgGdIHTp5gJexNZqWlHP9Zn";
        final String ClientId = "be64b8e2b18d408a9202fa8f27173d55";
    
        final ApiClient client;
        client = new ApiClientBuilder().clientID(ClientId).build();
    
        final OAuth auth = (OAuth) client.getAuthentication("evesso");
        
        final Set<String> scopes = new HashSet<>();
        scopes.add(SsoScopes.PUBLIC_DATA);

        String redirectUri;
        if (System.getenv().get("SSO_CALLBACK_URL") != null) {
            redirectUri = System.getenv().get("SSO_CALLBACK_URL");
        } else {
            redirectUri = "http://localhost:8080/";
        }
        
		return "redirect:"+auth.getAuthorizationUri(redirectUri, scopes, state); 
	}
    
}

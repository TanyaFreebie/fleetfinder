package com.spring.fleetfindertest.controller;

import com.spring.fleetfindertest.model.Auth;
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
        String state = "d2NTpYVRNy2jnnFjQXgGdIHTp5gJexNZqWlHP9Zn";
        final OAuth auth = Auth.get();
        final Set<String> scopes = new HashSet<>();

        scopes.add(SsoScopes.PUBLIC_DATA);
        scopes.add(SsoScopes.ESI_SKILLS_READ_SKILLS_V1);
        scopes.add(SsoScopes.ESI_CHARACTERS_READ_CORPORATION_ROLES_V1);

        String redirectUri;

        if (System.getenv().get("SSO_CALLBACK_URL") != null) {
            redirectUri = System.getenv().get("SSO_CALLBACK_URL");
        } else {
            redirectUri = "http://localhost:8080/";
        }

		return "redirect:"+auth.getAuthorizationUri(redirectUri, scopes, state);
	}

}

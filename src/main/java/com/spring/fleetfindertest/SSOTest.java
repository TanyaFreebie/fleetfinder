package com.spring.fleetfindertest;

import net.troja.eve.esi.ApiClient;
import net.troja.eve.esi.ApiClientBuilder;
import net.troja.eve.esi.ApiException;
import net.troja.eve.esi.auth.OAuth;
import net.troja.eve.esi.auth.SsoScopes;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SSOTest {

    /**
    * This main method can be used to generate a refresh token to run the unit
    * tests that need authentication. It is also an example how to use SSO in
    * an implementation.
    *
    * More description is in the README.md
    *
    * @param args
    *            The client id.
    * @throws IOException
    * @throws URISyntaxException
    * @throws ApiException
    */
    public static void main(final String... args) throws IOException, URISyntaxException, ApiException {
        

        final String state = "d2NTpYVRNy2jnnFjQXgGdIHTp5gJexNZqWlHP9Zn";
        final String ClientID = "be64b8e2b18d408a9202fa8f27173d55";
        
        //Создаем клиент для работы с АПИ
        final ApiClient client;
        if (args.length == 1) {
            client = new ApiClientBuilder().clientID(args[0]).build();
        } else {
            if ("be64b8e2b18d408a9202fa8f27173d55" != null) {
                client = new ApiClientBuilder().clientID(ClientID).build();
            } else {
                System.err.println("ClientId missing");
                System.exit(-1);
                client = new ApiClientBuilder().build();
            }
        }

        //Создаем клиент для логина
        final OAuth auth = (OAuth) client.getAuthentication("evesso");

        //Указываем права доступа для получения
        final Set<String> scopes = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(SsoScopes.PUBLIC_DATA)));

        //Указываем адрес куда нужно получить. Автор проекта ожидает что они будут хранится в ENV_VARIABLES
        String redirectUri;
        if (System.getenv().get("SSO_CALLBACK_URL") != null) {
            redirectUri = System.getenv().get("SSO_CALLBACK_URL");
        } else {
            redirectUri = "http://localhost";
        }

        //Генерируем ссылку для перехода и отправляем туда юзера
        final String authorizationUri = auth.getAuthorizationUri(redirectUri, scopes, state);
        System.out.println("Authorization URL: " + authorizationUri);
        Desktop.getDesktop().browse(new URI(authorizationUri));
        
        //Тестируем полученный код в консоле. В продукте мы должны получить код для работы с API и начать получать данные юзера(и писать их в базу)
        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Code from Answer: ");
        final String code = br.readLine();
        auth.finishFlow(code, state);
        System.out.println("Refresh Token: " + auth.getRefreshToken());
    }
    
}
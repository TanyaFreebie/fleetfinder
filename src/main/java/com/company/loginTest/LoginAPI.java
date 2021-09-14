package com.company.loginTest;

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
import java.util.Collections;
import java.util.Set;

public class LoginAPI {
    public static void main(final String... args) throws IOException, URISyntaxException, ApiException {
        final String state = "somesecret";
        final ApiClient client;
        final String SSO_CLIENT_ID = "be64b8e2b18d408a9202fa8f27173d55";
        if (args.length == 1) {
            client = new ApiClientBuilder().clientID(args[0]).build();
        } else {
            if (SSO_CLIENT_ID != null) {
                client = new ApiClientBuilder().clientID(SSO_CLIENT_ID).build();
            } else {
                System.err.println("ClientId missing");
                System.exit(-1);
                client = new ApiClientBuilder().build();
            }
        }
        final OAuth auth = (OAuth) client.getAuthentication("evesso");
        //scopes client
// needed SsoScopes:
// PUBLIC_DATA = "publicData";
// ESI_CHARACTERS_READ_CORPORATION_ROLES_V1 = "esi-characters.read_corporation_roles.v1";
        //

        final Set scopes = Collections.singleton(SsoScopes.PUBLIC_DATA);
        String redirectUri;
        if (System.getenv().get("SSO_CALLBACK_URL") != null) {
            redirectUri = System.getenv().get("SSO_CALLBACK_URL");
        } else {
            redirectUri = "http://localhost";
        }
        final String authorizationUri = auth.getAuthorizationUri(redirectUri, scopes, state);
        System.out.println("Authorization URL: " + authorizationUri);
        Desktop.getDesktop().browse(new URI(authorizationUri));

        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Code from Answer: ");
        final String code = br.readLine();
        auth.finishFlow(code, state);
        System.out.println("Refresh Token: " + auth.getRefreshToken());
    }
}

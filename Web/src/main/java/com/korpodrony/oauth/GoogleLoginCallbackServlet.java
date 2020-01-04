package com.korpodrony.oauth;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeCallbackServlet;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.korpodrony.dto.UserDTO;
import com.korpodrony.services.UsersWebService;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

@WebServlet("/oauth2callback")
public class GoogleLoginCallbackServlet extends AbstractAuthorizationCodeCallbackServlet {

    private static final Logger logger = Logger.getLogger(GoogleLoginCallbackServlet.class.getName());

    @Inject
    private UsersWebService usersWebService;

    @Override
    protected void onSuccess(HttpServletRequest req, HttpServletResponse resp, Credential credential)
            throws IOException {

        AccessToken accessToken = new AccessToken(credential.getAccessToken(), new Date(credential.getExpirationTimeMilliseconds()));

        GoogleCredentials credentials = GoogleCredentials.newBuilder()
                .setAccessToken(accessToken)
                .build();

        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);

        Oauth2 oauth2 = new Oauth2.Builder(
                new NetHttpTransport(),
                JacksonFactory.getDefaultInstance(),
                requestInitializer).setApplicationName("Korpodronny").build();

        Userinfoplus info = oauth2.userinfo().get().execute();
        String name = info.getGivenName();
        String surname = info.getFamilyName();
        String email = info.getEmail();

        if (usersWebService.findUserIdByEmail(email) == 0) {
            usersWebService.createUser(name, surname, email);
        }

        logger.info("Authentication success of user: " + name);

        UserDTO verifiedUser = usersWebService.findUserDTOByEmail(email);

        req.getSession().setAttribute("email", verifiedUser.getEmail());
        req.getSession().setAttribute("name", verifiedUser.getName());
        req.getSession().setAttribute("surname", verifiedUser.getName());
        req.getSession().setAttribute("userId", verifiedUser.getId());


        if (req.getSession().getAttribute("userType") == null) {
            req.getSession().setAttribute("userType", "guest");
        }
        resp.sendRedirect("/home");
    }

    @Override
    protected void onError(HttpServletRequest req, HttpServletResponse resp,
                           AuthorizationCodeResponseUrl errorResponse) {
        logger.severe("Google auth error: " + errorResponse.getError());
    }

    @Override
    protected String getRedirectUri(HttpServletRequest req) {
        return GoogleLoginCommons.buildRedirectUri(req);
    }

    @Override
    protected AuthorizationCodeFlow initializeFlow() {
        return GoogleLoginCommons.buildFlow();
    }

    @Override
    protected String getUserId(HttpServletRequest req) {
        String randomUserId = UUID.randomUUID().toString();
        logger.info("getUserId: " + randomUserId);
        return randomUserId;
    }
}

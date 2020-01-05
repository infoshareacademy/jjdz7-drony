package com.korpodrony.oauth;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeCallbackServlet;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@WebServlet("/oauth2callback")
public class GoogleLoginCallbackServlet extends AbstractAuthorizationCodeCallbackServlet {

    private static final Logger logger = LoggerFactory.getLogger("com.korpodrony.oauth");

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

        UserDTO verifiedUser = getVerifiedUser(name, surname, email);

        setSessionAttributes(req, verifiedUser);

        if (req.getSession().getAttribute("userType") == null) {
            req.getSession().setAttribute("userType", "guest");
        }
        resp.sendRedirect("/index");
    }

    private UserDTO getVerifiedUser(String name, String surname, String email) {
        if (usersWebService.findUserIdByEmail(email) == 0) {
            usersWebService.createUser(name, surname, email);
        }
        logger.info("Authentication success of user: " + name);
        return usersWebService.findUserDTOByEmail(email);
    }

    private void setSessionAttributes(HttpServletRequest req, UserDTO verifiedUser) {
        req.getSession().setAttribute("email", verifiedUser.getEmail());
        req.getSession().setAttribute("name", verifiedUser.getName());
        req.getSession().setAttribute("surname", verifiedUser.getName());
        req.getSession().setAttribute("userId", verifiedUser.getId());
    }

    @Override
    protected void onError(HttpServletRequest req, HttpServletResponse resp,
                           AuthorizationCodeResponseUrl errorResponse) {
        logger.error("Google auth error: " + errorResponse.getError());
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

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
    private static final String APP_NAME = "Korpodrony";
    private static final String EMAIL_ATTR = "email";
    private static final String NAME_ATTR = "name";
    private static final String SURNAME_ATTR = "surname";
    private static final String USER_ID_ATTR = "userId";
    private static final String USER_TYPE_ATTR = "userType";
    private static final String USER_TYPE_GUEST = "guest";
    private static final String REDIRECT = "/index";
    @Inject
    private UsersWebService usersWebService;

    @Override
    protected void onSuccess(HttpServletRequest req, HttpServletResponse resp, Credential credential)
            throws IOException {
        UserDTO verifiedUser = getUserDTO(credential);
        setSessionAttributes(req, verifiedUser);
        setSessionAtrrUserType(req);
        resp.sendRedirect(REDIRECT);
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

    private UserDTO getUserDTO(Credential credential) throws IOException {
        GoogleCredentials googleCredentials = getCredentials(credential);
        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(googleCredentials);
        Oauth2 oauth2 = getOauth2(requestInitializer);
        Userinfoplus userinfoplus = oauth2.userinfo().get().execute();
        return getVerifiedUser(userinfoplus);
    }

    private GoogleCredentials getCredentials(Credential credential) {
        AccessToken accessToken = new AccessToken(credential.getAccessToken(), new Date(credential.getExpirationTimeMilliseconds()));
        return GoogleCredentials.newBuilder()
                .setAccessToken(accessToken)
                .build();
    }

    private Oauth2 getOauth2(HttpRequestInitializer requestInitializer) {
        return new Oauth2.Builder(
                new NetHttpTransport(),
                JacksonFactory.getDefaultInstance(),
                requestInitializer).setApplicationName(APP_NAME).build();
    }

    private UserDTO getVerifiedUser(Userinfoplus userinfoplus) {
        String email = userinfoplus.getEmail();
        String name = userinfoplus.getGivenName();
        String sureName = userinfoplus.getFamilyName();
        if (usersWebService.findUserIdByEmail(email) == 0) {
            usersWebService.createUser(name, sureName, email);
        }
        logger.info("Authentication success of user: " + email);
        return usersWebService.findUserDTOByEmail(email);
    }

    private void setSessionAttributes(HttpServletRequest req, UserDTO verifiedUser) {
        req.getSession().setAttribute(EMAIL_ATTR, verifiedUser.getEmail());
        req.getSession().setAttribute(NAME_ATTR, verifiedUser.getName());
        req.getSession().setAttribute(SURNAME_ATTR, verifiedUser.getName());
        req.getSession().setAttribute(USER_ID_ATTR, verifiedUser.getId());
    }

    private void setSessionAtrrUserType(HttpServletRequest req) {
        if (req.getSession().getAttribute(USER_TYPE_ATTR) == null) {
            req.getSession().setAttribute(USER_TYPE_ATTR, USER_TYPE_GUEST);
        }
    }
}
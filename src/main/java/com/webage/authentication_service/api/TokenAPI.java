package com.webage.authentication_service.api;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webage.authentication_service.domain.Customer;

import com.webage.authentication_service.domain.Token;
import com.webage.authentication_service.config.JWTUtil;

@RestController
@RequestMapping("/account/token")
public class TokenAPI {

	//private static Key key = AuthFilter.key;	
	public static Token appUserToken;
	
	
	@PostMapping
	// public ResponseEntity<?> createTokenForCustomer(@RequestBody Customer customer, HttpRequest request, UriComponentsBuilder uri) {
	public ResponseEntity<?> createTokenForCustomer(@RequestBody Customer customer) {
		
		String email = customer.getEmail();
		String password = customer.getPassword();
		
		if (email != null && email.length() > 0 && password != null && password.length() > 0 ) {
			Token token = createToken(email);
			ResponseEntity<?> response = ResponseEntity.ok(token);
			return response;			
		}
		// bad request
		return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		
	}
    public static Token getAppUserToken() {
		if(appUserToken == null || appUserToken.getToken() == null || appUserToken.getToken().length() == 0) {
			appUserToken = createToken("ApiClientApp");
		}
		return appUserToken;
	}
    private static Token createToken(String email) {
    	String scopes = "com.webage.data.apis";
    	// special case for application user
    	if( email.equalsIgnoreCase("ApiClientApp")) {
    		scopes = "com.webage.auth.apis";
    	}
    	String token_string = JWTUtil.createToken(scopes);
    	
    	
    	return new Token(token_string);
    }
}
	

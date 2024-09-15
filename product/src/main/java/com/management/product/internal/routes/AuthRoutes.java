package com.management.product.internal.routes;

import com.management.product.datasources.domain.schema.AdminRequest;
import com.management.product.datasources.domain.schema.ValidationRequest;
import com.management.product.datasources.domain.usecase.AuthUseCase;
import com.management.product.scripts.constrains.DataConstrain;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(DataConstrain.ApiVersion+"/public/auth")
public class AuthRoutes {

    @Autowired
    private AuthUseCase authUseCase;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AdminRequest adminRequest){
        return authUseCase.register(adminRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody ValidationRequest validationRequest, HttpServletResponse response){
        return authUseCase.login(validationRequest, response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout( HttpServletResponse response){
        return authUseCase.logout(response);
    }
}

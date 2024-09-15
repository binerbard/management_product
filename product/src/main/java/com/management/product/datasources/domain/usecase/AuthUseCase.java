package com.management.product.datasources.domain.usecase;

import com.management.product.datasources.domain.schema.AdminRequest;
import com.management.product.datasources.domain.schema.TokenDTO;
import com.management.product.datasources.domain.schema.ValidationRequest;
import com.management.product.internal.service.AdminServiceImpl;
import com.management.product.scripts.constrains.ResponseMessage;
import com.management.product.scripts.utils.ResponseStatus;
import com.management.product.scripts.utils.ResponseWrapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class AuthUseCase {
    private final AdminServiceImpl adminService;

    public AuthUseCase(AdminServiceImpl adminService){
        this.adminService = adminService;
    }

    public ResponseEntity<ResponseStatus> register(AdminRequest adminRequest){
        try {
            adminService.store(adminRequest);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseStatus(
                    HttpStatus.ACCEPTED.value(),
                    ResponseMessage.StoreSuccess
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseStatus(
                    HttpStatus.BAD_REQUEST.value(),
                    ResponseMessage.StoreDenied
            ));
        }
    }

    public ResponseEntity<ResponseStatus> logout(HttpServletResponse response) {
        // Hapus cookie saat logout
        Cookie jwtCookie = new Cookie("token", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setMaxAge(0); // Set expiry to immediate
        jwtCookie.setPath("/");

        response.addCookie(jwtCookie);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseStatus(
                HttpStatus.ACCEPTED.value(),
                "Logout Success"
        ));
    }


    public ResponseEntity<ResponseWrapper<?>> login(ValidationRequest validationRequest, HttpServletResponse response){
        TokenDTO token = adminService.validation(validationRequest);

        if (!token.getToken().isEmpty()){
            Cookie jwtCookie = new Cookie("token", token.getToken());
            jwtCookie.setHttpOnly(true);
            jwtCookie.setMaxAge(60 * 60);
            jwtCookie.setPath("/");
            response.addCookie(jwtCookie);
        }

        if (!token.getToken().isEmpty()){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseWrapper<>(
                    HttpStatus.ACCEPTED.value(),
                    HttpStatus.ACCEPTED.toString(),
                    token
            ));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.toString(),
                null
        ));

    }
}

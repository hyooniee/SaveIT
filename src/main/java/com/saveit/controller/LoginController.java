

package com.saveit.controller;

import com.saveit.service.LoginService;
import com.saveit.util.JwtUtil;
import com.saveit.vo.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final JwtUtil jwtUtil;

    @PostMapping("/google")
    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> body) {
        String idToken = body.get("token");
        String tokenInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;

        try {
            // 1. tokeninfo로 ID 토큰 검증 
            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> tokenInfo = restTemplate.getForObject(tokenInfoUrl, Map.class);

            // 2. 사용자 정보 추출
            String googleId = (String) tokenInfo.get("sub");
            String email = (String) tokenInfo.get("email");
            String name = (String) tokenInfo.get("name");

            // 3. 사용자 등록 또는 조회
            Login user = loginService.findOrCreateUser(googleId, email, name);

            // 4. JWT 발급
            String jwt = jwtUtil.createToken(user);  

            // 5. 응답 구성
            Map<String, Object> result = new HashMap<>();
            result.put("jwt", jwt);
            result.put("user", user);

            return ResponseEntity.ok(result);
            
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body("Invalid Google ID Token");
        }
    }

}

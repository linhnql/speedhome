package com.speedhome.poc.service.provider;

import com.speedhome.poc.service.entity.ApiKeyEntity;
import com.speedhome.poc.service.model.ApiKeyAuthenticationToken;
import com.speedhome.poc.service.repository.ApiKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Component
public class ApiKeyAuthenticationProvider implements AuthenticationProvider {

  private ApiKeyRepository apiKeyRepository;

  @Autowired
  public ApiKeyAuthenticationProvider(ApiKeyRepository apiKeyRepository) {
    this.apiKeyRepository = apiKeyRepository;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String apiKey = (String) authentication.getPrincipal();

    if (ObjectUtils.isEmpty(apiKey)) {
      throw new InsufficientAuthenticationException("No API key in request");
    } else {

      Optional<ApiKeyEntity> apiKeyOptional = this.apiKeyRepository.findByKey(apiKey);

      if (apiKeyOptional.isPresent()) {
        return new ApiKeyAuthenticationToken(apiKey, true);
      }
      throw new BadCredentialsException("API Key is invalid");
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return ApiKeyAuthenticationToken.class.isAssignableFrom(authentication);
  }
}

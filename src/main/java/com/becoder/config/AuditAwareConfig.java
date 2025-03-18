package com.becoder.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.becoder.model.User;
import com.becoder.util.CommonUtils;

@Component // Ensure Spring can manage this bean
public class AuditAwareConfig implements AuditorAware<Integer> {

    @Override
    public Optional<Integer> getCurrentAuditor() {
        User loggedInUser = CommonUtils.getLoggedInUser();
        return loggedInUser != null ? Optional.of(loggedInUser.getId()) : Optional.empty();
    }
}

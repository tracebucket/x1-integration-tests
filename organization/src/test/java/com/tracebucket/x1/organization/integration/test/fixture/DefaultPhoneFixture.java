package com.tracebucket.x1.organization.integration.test.fixture;

import com.tracebucket.x1.dictionary.api.domain.PhoneType;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultPhone;
import com.tracebucket.x1.organization.integration.test.builder.DefaultPhoneBuilder;

import java.util.Date;

/**
 * Created by sadath on 25-Nov-14.
 */
public class DefaultPhoneFixture {
    public static DefaultPhone standardPhone() {
        DefaultPhone email = DefaultPhoneBuilder.aPhoneBuilder()
                .withExtension(new Long(new Date().getTime()).intValue())
                .withNumber(new Date().getTime())
                .withPhoneType(PhoneType.HOME)
                .build();
        return email;
    }
}

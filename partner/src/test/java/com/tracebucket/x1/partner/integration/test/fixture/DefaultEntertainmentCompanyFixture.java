package com.tracebucket.x1.partner.integration.test.fixture;

import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultAddress;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultPerson;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultEntertainmentCompany;
import com.tracebucket.x1.partner.integration.test.builder.DefaultEntertainmentCompanyBuilder;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by sadath on 01-Jun-2015.
 */
public class DefaultEntertainmentCompanyFixture {
    public static DefaultEntertainmentCompany standardEntertainmentCompany() {
        Set<DefaultAddress> addresses = new HashSet<DefaultAddress>();
        addresses.add(DefaultAddressFixture.standardAddress());

        Set<DefaultPerson> persons = new HashSet<DefaultPerson>();
        persons.add(DefaultPersonFixture.standardPerson());

        DefaultEntertainmentCompany entertainmentCompany = DefaultEntertainmentCompanyBuilder.anEntertainmentCompanyBuilder()
                .withName(UUID.randomUUID().toString())
                .withLogo(UUID.randomUUID().toString())
                .withWebsite(UUID.randomUUID().toString())
                .withAddresses(addresses)
                .withContactPersons(persons)
                .build();
        return entertainmentCompany;
    }
}
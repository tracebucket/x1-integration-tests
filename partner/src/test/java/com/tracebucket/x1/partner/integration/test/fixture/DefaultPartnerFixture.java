package com.tracebucket.x1.partner.integration.test.fixture;

import com.tracebucket.x1.partner.api.dictionary.PartnerCategory;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultAffiliate;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultOwner;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartner;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartnerRole;
import com.tracebucket.x1.partner.integration.test.builder.DefaultPartnerBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sadath on 11-Aug-14.
 */
public class DefaultPartnerFixture {

    public static DefaultPartner standardPartner() {

        Set<DefaultPartnerRole> partnerRoles = new HashSet<DefaultPartnerRole>();
        DefaultAffiliate affiliate = DefaultAffiliateFixture.standardAffiliate();
        partnerRoles.add(affiliate);

        DefaultOwner owner = DefaultOwnerFixture.standardOwner();

        DefaultPartner partner = DefaultPartnerBuilder.aPartnerBuilder()
                .withPartnerCategory(PartnerCategory.GROUP)
                .withImage("logo_1")
                .withTitle("title_1")
                //.withPartnerRoles(partnerRoles)
                .withOwner(owner)
                .withWebsite("www.yyy.nl").build();
        return partner;
    }
}
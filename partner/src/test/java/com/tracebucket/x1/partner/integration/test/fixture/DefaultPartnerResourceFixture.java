package com.tracebucket.x1.partner.integration.test.fixture;

import com.tracebucket.x1.partner.api.dictionary.PartnerCategory;
import com.tracebucket.x1.partner.api.rest.resources.DefaultPartnerResource;
import com.tracebucket.x1.partner.integration.test.builder.DefaultPartnerResourceBuilder;

/**
 * Created by Vishwajit on 27-05-2015.
 */
public class DefaultPartnerResourceFixture {

    public static DefaultPartnerResource standardPartner() {

        DefaultPartnerResource partner = DefaultPartnerResourceBuilder.aPartnerBuilder()
                                          .withPartnerCategory(PartnerCategory.GROUP)
                                          .withImage("logo_1")
                                          .withTitle("title_1")
                                          .withWebsite("www.yyy.nl").build();
        return partner;
    }
}

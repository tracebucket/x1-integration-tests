package com.tracebucket.x1.organization.partner.integration.test.fixture;

import com.tracebucket.x1.dictionary.api.domain.AddressType;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultAddress;
import com.tracebucket.x1.organization.partner.integration.test.builder.AddressBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sadath on 25-Nov-14.
 */
public class AddressFixture {

    public static DefaultAddress standardAddress() {
        Set<AddressType> addressTypes = new HashSet<AddressType>();
        addressTypes.add(AddressType.HEAD_OFFICE);
        DefaultAddress address = AddressBuilder.anAddressBuilder()
                .withName("MMP")
                .withBuilding("XYZ Complex")
                .withStreet("KR Road")
                .withRegion("Basavanagudi")
                .withCity("Bangalore")
                .withDistrict("Bangalore")
                .withState("Karnataka")
                .withCountry("India")
                .withZip("560004")
                .withAddressType(AddressType.BRANCH)
                .build();
        return address;
    }

    public static DefaultAddress headOffice() {
        Set<AddressType> addressTypes = new HashSet<AddressType>();
        addressTypes.add(AddressType.HEAD_OFFICE);
        DefaultAddress address = AddressBuilder.anAddressBuilder()
                .withName("MMPBV")
                .withBuilding("Vlasveen")
                .withStreet("9301 PS")
                .withRegion("Roden")
                .withCity("Roden")
                .withDistrict("Roden")
                .withState("Roden")
                .withCountry("NL")
                .withZip("9301 PS")
                .withAddressType(AddressType.HEAD_OFFICE)
                .build();
        return address;
    }
}

package com.tracebucket.x1.organization.integration.test.service;

import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganization;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganizationUnit;
import com.tracebucket.x1.organization.api.service.DefaultOrganizationService;
import com.tracebucket.x1.organization.integration.test.OrganizationStarter;
import com.tracebucket.x1.organization.integration.test.fixture.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;

/**
 * Created by sadath on 13-Jan-15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes  = {OrganizationStarter.class})
public class OrganizationServiceTest {

    @Autowired
    private DefaultOrganizationService organizationService;

    private DefaultOrganization organization = null;

    @Before
    public void setUp() throws Exception{

    }

    private void createOrganization() throws Exception{
        organization = DefaultOrganizationFixture.standardOrganization();
        organization = organizationService.save(organization);
    }

    @Test
    public void testCreate() throws Exception {
        createOrganization();
        Assert.assertNotNull(organization.getAggregateId());
    }

    @Test
    public void testAddOrganizationUnit() throws Exception {
        createOrganization();
        organization = organizationService.addOrganizationUnit(DefaultOrganizationUnitFixture.standardOrganizationUnit(), organization.getAggregateId());
        Assert.assertNotNull(organization);
        Assert.assertNotNull(organization.getOrganizationUnits());
        Assert.assertEquals(1, organization.getOrganizationUnits().size());
    }

    @Test
    public void testAddOrganizationUnitBelow() throws Exception {
        createOrganization();
        organization = organizationService.addOrganizationUnit(DefaultOrganizationUnitFixture.standardOrganizationUnit(), organization.getAggregateId());
        Assert.assertNotNull(organization);
        Assert.assertNotNull(organization.getOrganizationUnits());
        Assert.assertEquals(1, organization.getOrganizationUnits().size());
        DefaultOrganizationUnit childOrganizationUnit = DefaultOrganizationUnitFixture.standardOrganizationUnit();
        DefaultOrganizationUnit parentOrganizationUnit = null;
        for(DefaultOrganizationUnit organizationUnit : organization.getOrganizationUnits()) {
            parentOrganizationUnit = organizationUnit;
        }
        organization = organizationService.addOrganizationUnitBelow(childOrganizationUnit, parentOrganizationUnit.getEntityId(), organization.getAggregateId());
        Assert.assertNotNull(organization);
        Assert.assertNotNull(organization.getOrganizationUnits());
        Assert.assertEquals(1, organization.getOrganizationUnits().size());
    }

    @Test
    public void addBaseCurrency()throws Exception {
        createOrganization();
        organization = organizationService.addBaseCurrency(DefaultCurrencyFixture.standardBaseCurrency(), organization.getAggregateId());
        Assert.assertNotNull(organization);
        Assert.assertNotNull(organization.getBaseCurrencies());
        Assert.assertEquals(1, organization.getBaseCurrencies().size());
    }

    @Test
    public void addTimezone()throws Exception {
        createOrganization();
        organization = organizationService.addTimezone(DefaultTimezoneFixture.standardTimezone(), organization.getAggregateId());
        Assert.assertNotNull(organization);
        Assert.assertNotNull(organization.getTimezones());
        Assert.assertEquals(1, organization.getTimezones().size());
    }

    @Test
    public void addContactPerson()throws Exception {
        createOrganization();
        organization = organizationService.addContactPerson(DefaultPersonFixture.standardPerson(), organization.getAggregateId());
        Assert.assertNotNull(organization);
        Assert.assertNotNull(organization.getContactPersons());
        Assert.assertEquals(1, organization.getContactPersons().size());
    }

    @Test
    public void setDefaultContactPerson()throws Exception {
        createOrganization();
        organization = organizationService.setDefaultContactPerson(DefaultPersonFixture.standardPerson(), organization.getAggregateId());
        Assert.assertNotNull(organization);
        Assert.assertNotNull(organization.getContactPersons());
        Assert.assertEquals(1, organization.getContactPersons().size());
        Assert.assertTrue(new ArrayList<>(organization.getContactPersons()).get(0).isDefaultContactPerson());
    }

    @Test
    public void addContactNumber()throws Exception {
        createOrganization();
        organization = organizationService.addContactNumber(DefaultPhoneFixture.standardPhone(), organization.getAggregateId());
        Assert.assertNotNull(organization);
        Assert.assertNotNull(organization.getContactNumbers());
        Assert.assertEquals(1, organization.getContactNumbers().size());
    }

    @Test
    public void setDefaultContactNumber()throws Exception {
        createOrganization();
        organization = organizationService.setDefaultContactNumber(DefaultPhoneFixture.standardPhone(), organization.getAggregateId());
        Assert.assertNotNull(organization);
        Assert.assertNotNull(organization.getContactNumbers());
        Assert.assertEquals(1, organization.getContactNumbers().size());
        Assert.assertTrue(new ArrayList<>(organization.getContactNumbers()).get(0).isDefaultPhone());
    }

    @Test
    public void addEmail()throws Exception {
        createOrganization();
        organization = organizationService.addEmail(DefaultEmailFixture.standardEmail(), organization.getAggregateId());
        Assert.assertNotNull(organization);
        Assert.assertNotNull(organization.getEmails());
        Assert.assertEquals(1, organization.getEmails().size());
    }

    @Test
    public void setDefaultEmail()throws Exception {
        createOrganization();
        organization = organizationService.setDefaultEmail(DefaultEmailFixture.standardEmail(), organization.getAggregateId());
        Assert.assertNotNull(organization);
        Assert.assertNotNull(organization.getEmails());
        Assert.assertEquals(1, organization.getEmails().size());
        Assert.assertTrue(new ArrayList<>(organization.getEmails()).get(0).isDefaultEmail());
    }

    @Test
    public void setHeadOffice()throws Exception {
        createOrganization();
        organization = organizationService.setHeadOffice(DefaultAddressFixture.standardAddress(), organization.getAggregateId());
        Assert.assertNotNull(organization);
        Assert.assertNotNull(organization.getHeadOfficeAddress());
    }

    @Test
    public void moveHeadOfficeTo()throws Exception {
        createOrganization();
        organization = organizationService.moveHeadOfficeTo(DefaultAddressFixture.standardAddress(), organization.getAggregateId());
        Assert.assertNotNull(organization);
        Assert.assertNotNull(organization.getHeadOfficeAddress());
    }

    @Test
    public void getHeadOfficeAddress()throws Exception {
        createOrganization();
        organization = organizationService.setHeadOffice(DefaultAddressFixture.standardAddress(), organization.getAggregateId());
        Assert.assertNotNull(organization);
        Assert.assertNotNull(organization.getHeadOfficeAddress());
    }

    @Test
    public void getBaseCurrencies()throws Exception {
        createOrganization();
        organization = organizationService.addBaseCurrency(DefaultCurrencyFixture.standardBaseCurrency(), organization.getAggregateId());
        Assert.assertNotNull(organization);
        Assert.assertNotNull(organizationService.getBaseCurrencies(organization.getAggregateId()));
        Assert.assertEquals(1, organizationService.getBaseCurrencies(organization.getAggregateId()).size());
    }

    @Test
    public void getContactNumbers()throws Exception {
        createOrganization();
        organization = organizationService.addContactNumber(DefaultPhoneFixture.standardPhone(), organization.getAggregateId());
        Assert.assertNotNull(organization);
        Assert.assertNotNull(organizationService.getContactNumbers(organization.getAggregateId()));
        Assert.assertEquals(1, organizationService.getContactNumbers(organization.getAggregateId()).size());
    }

    @Test
    public void getEmails()throws Exception {
        createOrganization();
        organization = organizationService.addEmail(DefaultEmailFixture.standardEmail(), organization.getAggregateId());
        Assert.assertNotNull(organization);
        Assert.assertNotNull(organizationService.getEmails(organization.getAggregateId()));
        Assert.assertEquals(1, organizationService.getEmails(organization.getAggregateId()).size());
    }

    @Test
    public void testGetOrganizationUnits() throws Exception {
        createOrganization();
        organization = organizationService.addOrganizationUnit(DefaultOrganizationUnitFixture.standardOrganizationUnit(), organization.getAggregateId());
        Assert.assertNotNull(organization);
        Assert.assertNotNull(organization.getOrganizationUnits());
        Assert.assertEquals(1, organization.getOrganizationUnits().size());
    }

    @Test
    public void testFindById() throws Exception {
        createOrganization();
        organization = organizationService.findOne(organization.getAggregateId());
        Assert.assertNotNull(organization);
    }

    @After
    public void tearDown(){
        if(organization != null && organization.getAggregateId() != null) {
            organizationService.delete(organization.getAggregateId());
            organization = organizationService.findOne(organization.getAggregateId());
            Assert.assertNull(organization);
        }
    }
}
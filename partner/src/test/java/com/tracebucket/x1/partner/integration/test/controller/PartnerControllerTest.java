package com.tracebucket.x1.partner.integration.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracebucket.x1.partner.api.dictionary.PartnerCategory;
import com.tracebucket.x1.partner.api.rest.resources.DefaultAddressResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultPartnerResource;
import com.tracebucket.x1.partner.integration.test.PartnerStarter;
import com.tracebucket.x1.partner.integration.test.fixture.DefaultAddressResourceFixture;
import com.tracebucket.x1.partner.integration.test.fixture.DefaultAffiliateResourceFixture;
import com.tracebucket.x1.partner.integration.test.fixture.DefaultPartnerResourceFixture;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * Created by Vishwajit on 27-05-2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PartnerStarter.class)
@WebIntegrationTest
public class PartnerControllerTest {

    private static final Logger log = LoggerFactory.getLogger(PartnerControllerTest.class);

    private RestTemplate restTemplate = null;

    @Value("http://localhost:${server.port}${server.contextPath}")
    private String basePath;

    @Autowired
    private ObjectMapper objectMapper;

    private DefaultPartnerResource partner = null;

    @Before
    public void setUp() {
        restTemplate = new RestTemplate();
    }

    private void createPartner() throws Exception{
        partner = DefaultPartnerResourceFixture.standardPartner();
        log.info("Create Partner : " + objectMapper.writeValueAsString(partner));
        partner = restTemplate.postForObject(basePath + "/partner", partner, DefaultPartnerResource.class);
        Assert.assertNotNull(partner);
    }

    @Test
    public void testCreate() throws Exception {
        createPartner();
        Assert.assertNotNull(partner.getUid());
    }

    @Test
    public void testFindOne() throws Exception {
        createPartner();
        String uid = partner.getUid();
        log.info("Find Partner with UID : " + partner.getUid());
        partner = restTemplate.getForObject(basePath + "/partner/" + uid, DefaultPartnerResource.class);
        Assert.assertNotNull(partner);
        Assert.assertNotNull(partner.getUid());
        Assert.assertEquals(uid, partner.getUid());
        log.info("Found : " + objectMapper.writeValueAsString(partner));
    }

    @Test
    public void testSetPartnerCategory() throws Exception {
        createPartner();
        restTemplate.put(basePath+"/partner/"+partner.getUid()+"/partnercategory", PartnerCategory.GROUP);
        partner = restTemplate.getForObject(basePath + "/partner/" + partner.getUid(), DefaultPartnerResource.class);
        Assert.assertNotNull(partner.getUid());
        Assert.assertEquals(PartnerCategory.GROUP, partner.getPartnerCategory());
    }

    @Test
    public void testMovePartnerCategory() throws Exception {
        createPartner();
        restTemplate.put(basePath + "/partner/" + partner.getUid() + "/partner/tocategory", PartnerCategory.INDIVIDUAL);
        partner = restTemplate.getForObject(basePath + "/partner/" + partner.getUid(), DefaultPartnerResource.class);
        Assert.assertNotNull(partner.getUid());
        Assert.assertEquals(PartnerCategory.INDIVIDUAL, partner.getPartnerCategory());
    }

    @Test
    public void testAddPartnerRole() throws Exception {
        createPartner();
        partner.setAffiliate(DefaultAffiliateResourceFixture.standardAffiliate());
        log.info("Add Partner Role : " + objectMapper.writeValueAsString(partner));
        restTemplate.put(basePath+"/partner/partnerrole", partner);
        partner = restTemplate.getForObject(basePath + "/partner/" + partner.getUid(), DefaultPartnerResource.class);
        Assert.assertNotNull(partner.getUid());
        Assert.assertNotNull(partner.getAffiliate());
        Assert.assertNotNull(partner.getAffiliate().getUid());

    }

    @Test
    public void testAddAddressToRole() throws Exception {
        createPartner();
        partner.setAffiliate(DefaultAffiliateResourceFixture.standardAffiliate());
        log.info("Add Partner Role : " + objectMapper.writeValueAsString(partner));
        restTemplate.put(basePath + "/partner/partnerrole", partner);
        partner = restTemplate.getForObject(basePath + "/partner/" + partner.getUid(), DefaultPartnerResource.class);
        Assert.assertNotNull(partner.getUid());
        Assert.assertNotNull(partner.getAffiliate());
        Assert.assertNotNull(partner.getAffiliate().getUid());
        DefaultAddressResource addressResource = DefaultAddressResourceFixture.standardAddress();
        restTemplate.put(basePath + "/partner/" + partner.getUid() + "/partnerRole/" + partner.getAffiliate().getUid(), addressResource);
        partner = restTemplate.getForObject(basePath + "/partner/" + partner.getUid(), DefaultPartnerResource.class);
        Assert.assertNotNull(partner.getUid());
        Assert.assertNotNull(partner.getAffiliate());
        Assert.assertNotNull(partner.getAffiliate().getUid());
        Assert.assertNotNull(partner.getAffiliate().getAddresses());
        Assert.assertEquals(1, partner.getAffiliate().getAddresses().size());
    }

/*    @Test
    public void testChangeOwner() throws Exception {

    }*/

    @Test
    public void testMoveRoleAddressTo() throws Exception {
        createPartner();
        partner.setAffiliate(DefaultAffiliateResourceFixture.standardAffiliate());
        log.info("Add Partner Role : " + objectMapper.writeValueAsString(partner));
        restTemplate.put(basePath + "/partner/partnerrole", partner);
        partner = restTemplate.getForObject(basePath + "/partner/" + partner.getUid(), DefaultPartnerResource.class);
        Assert.assertNotNull(partner.getUid());
        Assert.assertNotNull(partner.getAffiliate());
        Assert.assertNotNull(partner.getAffiliate().getUid());
        DefaultAddressResource addressResource = DefaultAddressResourceFixture.standardAddress();
        restTemplate.put(basePath + "/partner/" + partner.getUid() + "/partnerRole/" + partner.getAffiliate().getUid(), addressResource);
        partner = restTemplate.getForObject(basePath + "/partner/" + partner.getUid(), DefaultPartnerResource.class);
        Assert.assertNotNull(partner.getUid());
        Assert.assertNotNull(partner.getAffiliate());
        Assert.assertNotNull(partner.getAffiliate().getUid());
        Assert.assertNotNull(partner.getAffiliate().getAddresses());
        Assert.assertEquals(1, partner.getAffiliate().getAddresses().size());

        addressResource = DefaultAddressResourceFixture.headOffice();
        restTemplate.put(basePath + "/partner/" + partner.getUid() + "/partnerRole/" + partner.getAffiliate().getUid(), addressResource);
        partner = restTemplate.getForObject(basePath + "/partner/" + partner.getUid(), DefaultPartnerResource.class);
        Assert.assertNotNull(partner.getUid());
        Assert.assertNotNull(partner.getAffiliate());
        Assert.assertNotNull(partner.getAffiliate().getUid());
        Assert.assertNotNull(partner.getAffiliate().getAddresses());
        Assert.assertEquals(2, partner.getAffiliate().getAddresses().size());
    }

    @Test
    public void testHasPartnerRole() throws Exception {
        createPartner();
        partner.setAffiliate(DefaultAffiliateResourceFixture.standardAffiliate());
        log.info("Add Partner Role : " + objectMapper.writeValueAsString(partner));
        restTemplate.put(basePath + "/partner/partnerrole", partner);
        partner = restTemplate.getForObject(basePath + "/partner/" + partner.getUid(), DefaultPartnerResource.class);
        Assert.assertNotNull(partner.getUid());
        Assert.assertNotNull(partner.getAffiliate());
        Assert.assertNotNull(partner.getAffiliate().getUid());
    }

    @After
    public void tearDown() throws Exception{
        if(partner != null && partner.getUid() != null) {
            restTemplate.delete(basePath + "/partner/" + partner.getUid());
            try {
                restTemplate.getForEntity(new URI(basePath + "/partner/" + partner.getUid()), DefaultPartnerResource.class);
            } catch (HttpClientErrorException httpClientErrorException) {
                Assert.assertEquals(HttpStatus.NOT_FOUND, httpClientErrorException.getStatusCode());
            }
        }
    }
}

package com.tracebucket.x1.organization.partner.integration.test.rest.controller;

import com.tracebucket.tron.assembler.AssemblerResolver;
import com.tracebucket.tron.assembler.ResourceAssembler;
import com.tracebucket.x1.organization.api.domain.impl.jpa.DefaultOrganization;
import com.tracebucket.x1.organization.partner.integration.test.rest.exception.OrganizationPartnerException;
import com.tracebucket.x1.organization.partner.integration.test.rest.resources.OrganizationResource;
import com.tracebucket.x1.organization.partner.integration.test.rest.resources.PartnerResource;
import com.tracebucket.x1.organization.partner.integration.test.service.OrganizationPartnerService;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartner;
import com.tracebucket.x1.partner.api.rest.resources.DefaultPartnerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by sadath on 04-Jun-2015.
 */
@RestController
public class OrganizationPartnerController {
    private static Logger log = LoggerFactory.getLogger(OrganizationPartnerController.class);

    @Autowired
    private OrganizationPartnerService organizationPartnerService;

    @Autowired
    private AssemblerResolver assemblerResolver;

    @RequestMapping(value = "/organization/{organizationUid}/partner/{partnerUid}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPartnerResource> addPartnerToOrganization(@PathVariable("organizationUid") String organizationUid, @PathVariable("partnerUid") String partnerUid) {
        DefaultPartner partner = null;
        try {
            partner = organizationPartnerService.addPartnerToOrganization(organizationUid, partnerUid);
        } catch (OrganizationPartnerException ope) {
            throw ope;
        }
        if(partner != null) {
            DefaultPartnerResource partnerResource = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResource(partner, DefaultPartnerResource.class);
            return new ResponseEntity<DefaultPartnerResource>(partnerResource, HttpStatus.CREATED);
        }
        return new ResponseEntity<DefaultPartnerResource>(new DefaultPartnerResource(), HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(value = "/organizations/partners", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrganizationResource>> getOrganizationsPartners() {
        List<OrganizationResource> organizationsPartnersResources = new ArrayList<OrganizationResource>();
        Map<DefaultOrganization, Set<DefaultPartner>> organizationsPartners = organizationPartnerService.getOrganizationsPartners();
        if(organizationsPartners != null && organizationsPartners.size() > 0) {
            ResourceAssembler<OrganizationResource, DefaultOrganization> organizationResourceAssembler = assemblerResolver.resolveResourceAssembler(OrganizationResource.class, DefaultOrganization.class);
            ResourceAssembler<PartnerResource, DefaultPartner> partnerResourceAssembler = assemblerResolver.resolveResourceAssembler(PartnerResource.class, DefaultPartner.class);
            organizationsPartners.entrySet().parallelStream().forEach(e -> {
                OrganizationResource organizationResource = organizationResourceAssembler.toResource(e.getKey(), OrganizationResource.class);
                if(organizationResource != null) {
                    organizationResource.getPartners().addAll(partnerResourceAssembler.toResources(e.getValue(), PartnerResource.class));
                    organizationsPartnersResources.add(organizationResource);
                }
            });
            return new ResponseEntity<List<OrganizationResource>>(organizationsPartnersResources, HttpStatus.FOUND);
        }
        return new ResponseEntity<List<OrganizationResource>>(Collections.emptyList(), HttpStatus.NOT_FOUND);
    }
}
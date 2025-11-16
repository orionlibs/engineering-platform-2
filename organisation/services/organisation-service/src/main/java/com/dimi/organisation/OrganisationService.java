package com.dimi.organisation;

import com.dimi.organisation.model.OrganisationModel;
import com.dimi.organisation.model.OrganisationsDAO;
import com.dimi.organisation.request.CreateOrganisationRequest;
import com.dimi.organisation.request.UpdateOrganisationRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrganisationService
{
    @Autowired private OrganisationCreator organisationCreator;
    @Autowired private OrganisationUpdater organisationUpdater;
    @Autowired private OrganisationsDAO dao;


    @Transactional
    public CreateOrganisationResult createOrganisation(CreateOrganisationRequest request)
    {
        return organisationCreator.createOrganisation(request);
    }


    @Transactional
    public UpdateOrganisationResult updateOrganisation(UUID organisationID, UpdateOrganisationRequest request)
    {
        return organisationUpdater.updateOrganisation(organisationID, request);
    }


    @Transactional
    public OrganisationModel save(OrganisationModel user)
    {
        return dao.save(user);
    }


    public Optional<OrganisationModel> getByID(UUID userID)
    {
        return dao.findById(userID);
    }


    public List<OrganisationModel> getAll()
    {
        return dao.findAll();
    }


    public List<OrganisationModel> getAllByOwnerUserID(UUID userID)
    {
        return dao.findAllByOwnerUserID(userID);
    }


    public void delete(UUID userID)
    {
        dao.deleteById(userID);
    }


    public void deleteAll()
    {
        dao.deleteAll();
    }
}

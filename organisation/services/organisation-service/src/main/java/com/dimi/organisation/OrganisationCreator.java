package com.dimi.organisation;

import com.dimi.core.data.DuplicateRecordException;
import com.dimi.organisation.model.OrganisationModel;
import com.dimi.organisation.model.OrganisationsDAO;
import com.dimi.organisation.request.CreateOrganisationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class OrganisationCreator
{
    @Autowired private OrganisationsDAO dao;


    @Transactional
    CreateOrganisationResult createOrganisation(CreateOrganisationRequest request)
    {
        OrganisationModel organisation = new OrganisationModel(request.getName(), request.getOwnerUserID());
        try
        {
            OrganisationModel saved = dao.save(organisation);
            dao.flush();
            return CreateOrganisationResult.builder()
                            .organisation(saved)
                            .build();
        }
        catch(DataIntegrityViolationException e)
        {
            throw new DuplicateRecordException(OrganisationError.ORGANISATION_ALREADY_EXISTS);
        }
    }
}

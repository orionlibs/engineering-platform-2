package com.dimi.organisation;

import com.dimi.core.data.DuplicateRecordException;
import com.dimi.core.exception.AError;
import com.dimi.organisation.model.OrganisationModel;
import com.dimi.organisation.model.OrganisationsDAO;
import com.dimi.organisation.request.UpdateOrganisationRequest;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class OrganisationUpdater
{
    @Autowired private OrganisationsDAO dao;


    @Transactional
    UpdateOrganisationResult updateOrganisation(UUID organisationID, UpdateOrganisationRequest request)
    {
        Optional<OrganisationModel> wrap = dao.findById(organisationID);
        if(wrap.isPresent())
        {
            OrganisationModel organisation = wrap.get();
            organisation.setName(request.getName());
            organisation.setOwnerUserID(request.getOwnerUserID());
            try
            {
                OrganisationModel saved = dao.save(organisation);
                dao.flush();
                return UpdateOrganisationResult.builder()
                                .organisation(saved)
                                .build();
            }
            catch(DataIntegrityViolationException e)
            {
                throw new DuplicateRecordException(OrganisationError.ORGANISATION_ALREADY_EXISTS);
            }
        }
        else
        {
            AError error = new AError<>();
            error.setErrorCode(OrganisationError.ORGANISATION_NOT_FOUND);
            return UpdateOrganisationResult.builder()
                            .error(error)
                            .build();
        }
    }
}

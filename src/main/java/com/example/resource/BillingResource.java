package com.example.resource;

import com.example.dao.BillingDAO;
import com.example.model.Billing;
import com.example.exception.UserNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/billings")
public class BillingResource {

    private static final Logger LOGGER = Logger.getLogger(BillingResource.class.getName());
    private BillingDAO billingDAO = new BillingDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_All_Billings() {
        try {
            List<Billing> billings = billingDAO.getAllBillings();
            LOGGER.log(Level.INFO, " successfully Retrieved all billings details ");
            return Response.ok().entity(billings).build();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Failed to collect all the billings details", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to collect all the billings details. Please Enter the Details Again.").build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_Billing_ById(@PathParam("id") int id) {
        try {
            Billing billing = billingDAO.getBilling(id);
            if (billing != null) {
                LOGGER.log(Level.INFO, "Retrieved billing with ID {0} successfully", id);
                return Response.ok().entity(billing).build();
            } else {
                throw new UserNotFoundException("Billing details with ID " + id + " cannot found.");
            }
        } catch (UserNotFoundException ex) {
            LOGGER.log(Level.WARNING, "Billing with ID {0} not found", id);
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Failed to collect all the billings detailswith ID " + id, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to collect all the billings details with ID  " + id + ". Please Enter the Details Again.").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add_Billing(Billing billing) {
        try {
            billingDAO.addBilling(billing);
            LOGGER.log(Level.INFO, " successfully Added All the billing Details ");
            return Response.status(Response.Status.CREATED).entity("Billing added successfully.").build();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Billing details adding unsucessfull", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to collect all the billings details. Please re Enter the details again!.").build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update_Billing(@PathParam("id") int billingId, Billing updatedBilling) {
        try {
            Billing existingBilling = billingDAO.getBilling(billingId);
            if (existingBilling != null) {
                updatedBilling.setNo(billingId);
                billingDAO.updateBilling(updatedBilling);
                LOGGER.log(Level.INFO, "Updated billing details sucessfully with ID {0} successfully", billingId);
                return Response.ok().entity("Billing with ID " + billingId + " successfully Updated").build();
            } else {
                throw new UserNotFoundException("Billing details with ID " + billingId + " cannot found.");
            }
        } catch (UserNotFoundException ex) {
            LOGGER.log(Level.WARNING, "Billing with ID {0} not found", billingId);
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Bbilling details updating is fail with ID " + billingId, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to collect all the billings details with ID " + billingId + ".Please re Enter the details again!.").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete_Billing(@PathParam("id") int id) {
        try {
            billingDAO.deleteBills(id);
            LOGGER.log(Level.INFO, "Deleted billing with ID {0} successfully", id);
            return Response.ok().entity("Billing details with ID " + id + "Deleted.").build();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Failed to collect all the billings details with ID " + id, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to collect all the billings details with ID " + id + ". Error: " + ex.getMessage()).build();
        }
    }
}

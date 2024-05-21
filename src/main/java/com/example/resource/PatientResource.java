package com.example.resource;

import com.example.dao.PatientDAO;
import com.example.model.Patient;
import com.example.exception.UserNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/patients")
public class PatientResource {
    

    private static final Logger LOGGER = Logger.getLogger(PatientResource.class.getName());
    private PatientDAO patientDAO = new PatientDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_All_Patients() {
        try {
            List<Patient> patients = patientDAO.getAllPatients();
            LOGGER.log(Level.INFO, "Patients details stored sucessfully");
            return Response.ok().entity(patients).build();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Failed to get patients details", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Getting patients detais is failed . Please Re-Enter details again.").build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_Patient_ById(@PathParam("id") int id) {
        try {
            Patient patient = patientDAO.getPatientById(id);
            if (patient != null) {
                LOGGER.log(Level.INFO, "Get All the patient details successfully", id);
                return Response.ok().entity(patient).build();
            } else {
                throw new UserNotFoundException("Patient details cannot found.");
            }
        } catch (UserNotFoundException ex) {
            LOGGER.log(Level.WARNING, "Patient details cannot found", id);
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "somthing wrong! ", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Thre is an error. Please Re-Enter details again!").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add_Patient(Patient patient) {
        try {
            patientDAO.addPatient(patient);
            LOGGER.log(Level.INFO, "Successfully Added the patient details ");
            return Response.status(Response.Status.CREATED).entity("Successfully Added the patient details.").build();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Patient details adding is faild", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Patient details adding is faild. Please Re-Enter details again!.").build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update_Patient(@PathParam("id") int patientId, Patient updatePatient) {
        try {
            Patient existingPatient = patientDAO.getPatientById(patientId);
            if (existingPatient != null) {
                updatePatient.setID(patientId);
                patientDAO.updatePatient(updatePatient);
                LOGGER.log(Level.INFO, "Sucessfully Updated patient details with ID {0} successfully", patientId);
                return Response.ok().entity("Patient with ID " + patientId + " successfully Updated the patient details").build();
            } else {
                throw new UserNotFoundException("Patient with ID " + patientId + " not found.");
            }
        } catch (UserNotFoundException ex) {
            LOGGER.log(Level.WARNING, "Patient with ID {0} not found", patientId);
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Updating patient details is failed with ID " + patientId, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Updating patient details is failed with ID " + patientId + ". Please Re-Enter details again!.").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete_Patient(@PathParam("id") int id) {
       
        try {
            patientDAO.deletePatient(id);
            LOGGER.log(Level.INFO, "Deleted patient with ID {0} successfully" , id);
            return Response.ok().entity("Patient with ID " + id + " successfully deleted.").build();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Deleting patient details is faild with ID " + id, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Deleting patient details is faild with ID" + id + ". Error: " + ex.getMessage()).build();
        }
    }
}

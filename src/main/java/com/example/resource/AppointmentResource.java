package com.example.resource;

import com.example.dao.AppointmentDAO;
import com.example.model.Appointment;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/appointments")
public class AppointmentResource {

    private static final Logger logger = Logger.getLogger(AppointmentResource.class.getName());
    private AppointmentDAO appointmentDAO = new AppointmentDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_All_Appointments() {
        try {
            List<Appointment> appointments = appointmentDAO.getAllAppointments();
            logger.log(Level.INFO, "Retrieved all appointments details sucessfully");
            return Response.ok().entity(appointments).build();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Failed to get appointments details. Try Again!", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed. Please again Enter the details.").build();
        }
    }

    @GET
    @Path("/{appointmentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_Appointment_ById(@PathParam("appointmentId") int appointmentId) {
        try {
            Appointment appointment = appointmentDAO.getAppointmentById(appointmentId);
            if (appointment != null) {
                logger.log(Level.INFO, "Retrieved appointment details with ID: " + appointmentId + " successfully");
                return Response.ok().entity(appointment).build();
            } else {
                logger.log(Level.INFO, "Appointment with ID: " + appointmentId + " not found");
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Appointment with ID: " + appointmentId + " not found").build();
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Failed to fetch appointment with ID: " + appointmentId, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to collect appointment details with ID: " + appointmentId + ". Please enter the details again.").build();
        }
    }

    @POST
    public Response Add_Appointment(Appointment appointment) {
        try {
            appointmentDAO.addAppointment(appointment);
            logger.log(Level.INFO, "Added new appointment successfully");
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Failed to add appointment details. Please Try Again Later! ", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to add appointment details. Please enter the details again.").build();
        }
    }

    @PUT
    @Path("/{appointmentId}")
    public Response update_Appointment(@PathParam("appointmentId") int appointmentId, Appointment updatedAppointment) {
        try {
            appointmentDAO.updateAppointment(appointmentId, updatedAppointment);
            logger.log(Level.INFO, "Updated appointment details sucessfully with ID: " + appointmentId + " successfully");
            return Response.ok().build();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Failed to update appointment with ID: " + appointmentId, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to update appointment with ID: " + appointmentId + ". Please enter the details again.").build();
        }
    }

    @DELETE
    @Path("/{appointmentId}")
    public Response delete_Appointment(@PathParam("appointmentId") int appointmentId) {
        try {
            appointmentDAO.deleteAppointment(appointmentId);
            logger.log(Level.INFO, " successfully Deleted appointment details");
            return Response.ok().entity("successfully Deleted appointment details.").build();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Failed to delete appointment ", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("appointment details deleteis unsucessfull. Please Try Again !").build();
        }
    }
}

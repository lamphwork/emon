package lamph.quarkus.common.exceptions;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lamph.emon.common.exceptions.BusinessException;

@Provider
@ApplicationScoped
public class BusinessExceptionMapping implements ExceptionMapper<BusinessException> {

    @Override
    public Response toResponse(BusinessException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ExceptionResponse(
                        exception.getMessage(),
                        null,
                        exception.getDetails()
                ))
                .build();
    }
}

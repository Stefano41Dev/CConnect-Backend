package com.stefano.web.dto.solicitud;

import lombok.Builder;

@Builder
public record MandarSolicitudAmistadDtoRequest(
        String receptorId
) {
}

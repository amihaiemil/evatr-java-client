/**
 * Copyright (c) 2026-, Extremely Distributed Technologies SRL
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 *  list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * Neither the name of the copyright holder nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package com.amihaiemil.web.evatr;

import com.amihaiemil.web.openapi.evatr.UstIdNrBestaetigungsabfrageApi;
import com.amihaiemil.web.openapi.evatr.invoker.ApiException;
import com.amihaiemil.web.openapi.evatr.model.BestaetigungsabfrageDto;
import com.amihaiemil.web.openapi.evatr.model.BestaetigungsantwortDto;
import com.amihaiemil.web.openapi.evatr.model.ErrorantwortDto;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;

/**
 * The VAT API of Evatr. This class is intentionally package protected.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 * @version $Id$
 * @since 0.0.1
 */
final class RestfulEvatrVatApi implements EvatrVatApi {

    /**
     * Support API to fetch and aggregate response messages, countries etc.
     */
    private final EvatrSupportApi supportApi;

    /**
     * OpenApi-generated client for the VAT API.
     */
    private final UstIdNrBestaetigungsabfrageApi ustIdApi = new UstIdNrBestaetigungsabfrageApi();

    /**
     * GSON instance for manual parsing of errors.
     */
    private final Gson gson;

    RestfulEvatrVatApi(final EvatrSupportApi supportApi, final Gson gson) {
        this.supportApi = supportApi;
        this.gson = gson;
    }

    @Override
    public EvatrVatStatus verifyExternalVatNumber(final String caller, final String toVerify) throws IOException {
        final List<EvatrMessage> messages = this.supportApi.evatrMessages();
        try {
            final BestaetigungsantwortDto antwort = this.ustIdApi.abfrageV1(new BestaetigungsabfrageDto().anfragendeUstid(caller).angefragteUstid(toVerify));
            return new EvatrVatStatusResponse(antwort, messages);
        } catch (final ApiException e) {
            return this.handleApiException(e, messages);
        }
    }

    private EvatrVatStatus handleApiException(final ApiException ex, final List<EvatrMessage> messages) throws IOException {
        try {
            return new EvatrVatStatusError(
                this.gson.fromJson(ex.getResponseBody(), ErrorantwortDto.class),
                messages
            );
        } catch (final RuntimeException unexpected) {
            throw new IOException("ApiException when calling /v1/abfrage", ex);
        }
    }
}

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

import com.amihaiemil.web.openapi.evatr.UnterstuetzendeOperationenApi;
import com.amihaiemil.web.openapi.evatr.invoker.ApiException;
import com.amihaiemil.web.openapi.evatr.model.EUMitgliedstaatDto;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Support API of Evatr. This class is intentionally package protected.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 * @version $Id$
 * @since 0.0.1
 */
final class RestfulEvatrSupportApi implements EvatrSupportApi {
    private final UnterstuetzendeOperationenApi utilApi = new UnterstuetzendeOperationenApi();

    @Override
    public List<EvatrCountry> supportedCountries() throws IOException {
        try {
            return this.utilApi.euMitgliedstaatenStatusV1().stream().filter(
                EUMitgliedstaatDto::getVerfuegbar
            ).map(
                mitglied -> new EvatrCountry() {
                    @Override
                    public String code() {
                        return mitglied.getAlpha2();
                    }

                    @Override
                    public String name() {
                        return mitglied.getName();
                    }
                }
            ).collect(Collectors.toList());
        } catch (final ApiException e) {
            throw new IOException("ApiException when calling /v1/info/eu_mitgliedstaaten", e);
        }
    }

    @Override
    public List<EvatrMessage> evatrMessages() throws IOException {
        try {
            return this.utilApi.statusmeldungenV1().stream().map(
                meldung -> new EvatrMessage() {
                    @Override
                    public String statusCode() {
                        return meldung.getStatus();
                    }

                    @Override
                    public String message() {
                        return meldung.getMeldung();
                    }
                }
            ).collect(Collectors.toList());
        } catch (final ApiException e) {
            throw new IOException("ApiException when calling /v1/info/statusmeldungen", e);
        }
    }
}
